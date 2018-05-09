package cn.dslcode.common.core.file;

import cn.dslcode.common.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author dongsilin
 * @version 2018/4/10.
 */
@Slf4j
public class ZipUtil {

    /**
     * 临时目录
     */
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    /**
     * zip压缩，使用完后请删除返回的zip文件
     *
     * @param zipName zip文件名称
     * @param files   传谣压缩的文件
     * @return zip文件， 使用完后请删除返回的zip文件
     */
    public static File toZip(String zipName, File... files) {
        String zipFilePath = StringUtil.append2String(TEMP_DIR, zipName, ".zip");
        try (
            OutputStream outputStream = new FileOutputStream(zipFilePath);
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream, StandardCharsets.UTF_8)
        ) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 文件夹压缩
                    dirCompress(zipOutputStream, file, file.getName().concat("/"));
                    continue;
                }
                // 单个文件压缩
                compress(zipOutputStream, new FileInputStream(file), file.getName());
            }
            zipOutputStream.flush();
        } catch (IOException e) {
            log.info("", e);
        }
        return new File(zipFilePath);
    }

    /**
     * 文件夹zip压缩
     *
     * @param parentDir 文件夹路径
     * @return
     * @throws IOException
     */
    public static File toZip(String parentDir) throws IOException {
        File parentFile = new File(parentDir);
        if (!parentFile.exists()) throw new FileNotFoundException("parentDir is not exists");
        if (!parentFile.isDirectory()) throw new IOException("parentDir is not a directory");

        String zipFilePath = StringUtil.append2String(TEMP_DIR, parentFile.getName(), ".zip");
        try (
            OutputStream outputStream = new FileOutputStream(zipFilePath);
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream, StandardCharsets.UTF_8)
        ) {
            File[] childrenFiles = parentFile.listFiles();
            for (File file : childrenFiles) {
                if (file.isDirectory()) {
                    // 文件夹压缩
                    dirCompress(zipOutputStream, file, file.getName().concat("/"));
                    continue;
                }
                // 单个文件压缩
                compress(zipOutputStream, new FileInputStream(file), file.getName());
            }
            zipOutputStream.flush();
        } catch (IOException e) {
            log.info("", e);
        }
        return new File(zipFilePath);
    }

    /**
     * 文件夹压缩
     *
     * @param zipOutputStream
     * @param dirFile
     * @param fileName
     * @throws IOException
     */
    private static void dirCompress(ZipOutputStream zipOutputStream, File dirFile, String fileName) throws IOException {
        File[] childrenFiles = dirFile.listFiles();
        // 写空文件夹
        if (childrenFiles != null && childrenFiles.length == 0) {
            zipOutputStream.putNextEntry(new ZipEntry(fileName));
            zipOutputStream.closeEntry();
        }
        for (File file : childrenFiles) {
            if (file.isDirectory()) {
                // 递归
                dirCompress(zipOutputStream, file, fileName.concat(file.getName()).concat("/"));
            } else {
                // 单个文件压缩
                compress(zipOutputStream, new FileInputStream(file), fileName.concat(file.getName()));
            }
        }
    }


    /**
     * 单个文件压缩
     *
     * @param zipOutputStream
     * @param inputStream
     * @param fileName
     * @throws IOException
     */
    private static void compress(ZipOutputStream zipOutputStream, InputStream inputStream, String fileName) throws IOException {
        if (inputStream == null) return;
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        int bytesRead;
        byte[] buffer = new byte[FileUtil.BUFFER_SIZE];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            zipOutputStream.write(buffer, 0, bytesRead);
        }
        zipOutputStream.closeEntry();
        inputStream.close();
    }

    /**
     * 解压缩zip包
     *
     * @param zipFilePath   zip文件的全路径
     * @param unzipFilePath 解压后的文件保存的路径
     */
    public static void unzip(String zipFilePath, String unzipFilePath) throws IOException {
        File zfile = new File(zipFilePath);
        if (!zfile.exists()) throw new FileNotFoundException("zipFile is not exists");
        String zfileName = zfile.getName();
        unzipFilePath = StringUtil.append2String(unzipFilePath, FileUtil.PATH_SEPARATOR, StringUtil.substring(zfileName, 0, zfileName.indexOf(".")));
        //开始解压
        ZipEntry entry;
        String entryFilePath;
        File entryFile;
        byte[] buffer;
        InputStream inputStream;
        OutputStream outputStream;
        ZipFile zipFile = new ZipFile(zfile, StandardCharsets.UTF_8);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        //循环对压缩包里的每一个文件进行解压
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            log.info("{}", entry.getName());
            //构建压缩包中一个文件解压后保存的文件全路径
            entryFilePath = StringUtil.append2String(unzipFilePath, "/", entry.getName());
            entryFile = new File(entryFilePath);
            // 判断是否是空文件夹
            if (entry.isDirectory()) {
                entryFile.mkdirs();
                continue;
            }
            new File(StringUtil.substring(entryFilePath, 0, entryFilePath.lastIndexOf("/"))).mkdirs();
            //写入文件
            outputStream = new FileOutputStream(entryFile);
            inputStream = zipFile.getInputStream(entry);
            int bytesRead;
            buffer = new byte[FileUtil.BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        }
    }


}
