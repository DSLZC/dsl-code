package cn.dslcode.common.core.office.excel;

import cn.dslcode.common.core.office.excel.enums.Format;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

/**
 * excel工具类
 * @author dongsilin
 * @version 2018/5/7.
 */
@Slf4j
public class ExcelUtil {


    /**
     * 导出excel文件
     * @param format
     * @param clazz
     * @param datas
     * @param file
     * @param <T>
     */
    public static <T> void export(Format format, Class<T> clazz, Collection<T> datas, String file) {
        try {
            new ExportExcel(clazz, format)
                .createHeaderAndHead()
                .setData(datas)
                .write(file);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 导出excel文件
     * @param format
     * @param clazz
     * @param datas
     * @param response
     * @param fileName
     * @param <T>
     */
    public static <T> void export(Format format, Class<T> clazz, Collection<T> datas, HttpServletResponse response, String fileName) {
        new ExportExcel(clazz, format)
            .createHeaderAndHead()
            .setData(datas)
            .write(response, fileName);
    }


    /**
     * 导入excel文件
     * @param fileName
     * @param headRowNum
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> import_(String fileName, int headRowNum, Class<E> clazz)  {
        return import_(new File(fileName), headRowNum, clazz);
    }

    /**
     * 导入excel文件
     * @param file
     * @param headRowNum
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> import_(File file, int headRowNum, Class<E> clazz) {
        return import_(file, headRowNum, 0, clazz);
    }

    /**
     * 导入excel文件
     * @param fileName
     * @param headRowNum
     * @param sheetIndex
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> import_(String fileName, int headRowNum, int sheetIndex, Class<E> clazz) {
        return import_(new File(fileName), headRowNum, sheetIndex, clazz);
    }

    /**
     * 导入excel文件
     * @param file
     * @param headRowNum
     * @param sheetIndex
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> import_(File file, int headRowNum, int sheetIndex, Class<E> clazz) {
        try {
            return import_(file.getName(), new FileInputStream(file), headRowNum, sheetIndex, clazz);
        } catch (FileNotFoundException e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 导入excel文件
     * @param fileName
     * @param inputStream
     * @param headRowNum
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> import_(String fileName, InputStream inputStream, int headRowNum, Class<E> clazz) {
        return import_(fileName, inputStream, headRowNum, 0, clazz);
    }

    /**
     * 导入excel文件
     * @param fileName
     * @param inputStream
     * @param headRowNum
     * @param sheetIndex
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> import_(String fileName, InputStream inputStream, int headRowNum, int sheetIndex, Class<E> clazz) {
        List<E> list = null;
        try(InputStream is = inputStream) {
            ImportExcel importExcel = new ImportExcel<E>(fileName, is, headRowNum, sheetIndex, clazz);
            list = importExcel.getData();
            importExcel.close();
        } catch (IOException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            log.error("", e);
        }
        return list;
    }

}
