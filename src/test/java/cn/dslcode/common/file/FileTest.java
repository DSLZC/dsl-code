package cn.dslcode.common.file;

import cn.dslcode.common.core.file.ZipUtil;
import org.junit.Test;

/**
 * @author dongsilin
 * @version 2018/4/10.
 */
public class FileTest {

    @Test
    public void testZip(){
//        File file = ZipUtil.toZip("数据库", new File("D:\\mall-debug.sql"), new File("D:\\mall-prod.sql"),
//            new File("D:\\哈哈.jpg"), new File("D:\\dongsilin\\workspace\\zlgx-wl-cloud\\zlgxwl-web")
//            , new File("C:\\Users\\Administrator\\AppData\\Local\\Temp\\哈哈"));
//        File file = null;
//        try {
//            file = ZipUtil.toZip("D:\\dongsilin\\workspace\\zlgx-wl-cloud");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        file.deleteOnExit();

        try {
            ZipUtil.unzip("C:\\Users\\Administrator\\AppData\\Local\\Temp\\数据库.zip", "D:\\000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
