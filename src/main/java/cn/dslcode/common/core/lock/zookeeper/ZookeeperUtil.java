package cn.dslcode.common.core.lock.zookeeper;

import cn.dslcode.common.core.collection.CollectionUtil;
import cn.dslcode.common.core.lock.CallBackExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.TimeUnit;

/**
 * @author dongsilin
 * @version 2018/8/31.
 * zookeeper操作工具类
 */
@Slf4j
public class ZookeeperUtil {

	private static CuratorFramework lockClient;
	private static ZooKeeper zooKeeperClient;

	/** session超时时间 */
	private static final int SESSION_TIMEOUT_MS = 10000;
	/** 连接超时时间 */
	private static final int CONNECTION_TIMEOUT_MS = 5000;
	/** 分布式锁根节点 */
	private static final String ROOT_LOCK = "/locks/";

	/**
	 * zookeeper 连接工厂初始化
	 * @param connectString list of server address: ip:port, ip:port, ip:port
	 */
	public static void init(String connectString) {
		// 1 重试策略：初试时间为10s 重试3次
		final int baseSleepTimeMs = 10000, maxRetries = 3;
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
		// 2 通过工厂创建连接
		lockClient = CuratorFrameworkFactory.newClient(connectString, SESSION_TIMEOUT_MS, CONNECTION_TIMEOUT_MS, retryPolicy);
		// 3 开启连接
		lockClient.start();

		try {
			zooKeeperClient = lockClient.getZookeeperClient().getZooKeeper();
			// 如果分布式锁根节点，则创建根节点
			if (zooKeeperClient.exists(ROOT_LOCK, false) == null) {
				zooKeeperClient.create(ROOT_LOCK, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public static<R> R tryLockAndCallBack(String lockPath, int waitTimeMs, CallBackExecutor<R> successExecutor, CallBackExecutor<R> failExecutor)
		throws Throwable {
		lockPath = ROOT_LOCK + lockPath;
		// 锁实例
		InterProcessMutex distributedLock = new InterProcessMutex(lockClient, lockPath);

		// 尝试获取锁
		boolean getLock;
		try {
			getLock = distributedLock.acquire(waitTimeMs, TimeUnit.MILLISECONDS);
		} catch (KeeperException e) {
			log.error("", e);
			// 获取锁失败回调
			return failExecutor.execute();
		}

		if (getLock) {
			log.info("ThreadName = {}, tryAddLock = {}", Thread.currentThread().getName(), "获取锁成功");
			try {
				// 获取锁成功回调
				return successExecutor.execute();
			} finally {
				// 释放锁
				distributedLock.release();
				// 删除节点，防止节点过多
				if (CollectionUtil.isEmpty(distributedLock.getParticipantNodes())) {

					try {
						// 安全删除
						zooKeeperClient.delete(lockPath, 0);
					} catch (Exception e) {}
				}
			}
		}
		log.info("ThreadName = {}, tryAddLock = {}", Thread.currentThread().getName(), "获取锁失败");
		return failExecutor.execute();
	}

}
