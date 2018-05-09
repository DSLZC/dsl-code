package cn.dslcode.common.core.office.excel;

import cn.dslcode.common.core.office.excel.annotation.CellStyle$;
import cn.dslcode.common.core.office.excel.annotation.ExcelHeader;
import cn.dslcode.common.core.office.excel.annotation.ExportEntity;
import cn.dslcode.common.core.office.excel.annotation.ExportField;
import cn.dslcode.common.core.office.excel.enums.Format;
import cn.dslcode.common.core.reflect.ReflectUtil;
import cn.dslcode.common.core.string.StringUtil;
import cn.dslcode.common.core.thread.ThreadPoolUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 导出Excel类
 *
 * @author dongsilin
 * @version 2018-05-04
 */
@Slf4j
public class ExportExcel<T> {

    /**
     * 带有ExcelField注解的field列表
     */
    private List<ComplexField> complexFields;
    /**
     * 导出实体
     */
    private ExportEntity exportEntity;
    /**
     * 数据格式，格式化
     */
    private DataFormat dataFormat;
    /**
     * 导出格式
     */
    private Format format;
    /**
     * 工作薄对象
     */
    private Workbook workbook;
    /**
     * 工作表对象
     */
    private Sheet sheet;
    /**
     * 当前行号
     */
    private AtomicInteger rowNum = new AtomicInteger(0);
    /**
     * 是否创建Header
     */
    private boolean isCreateHeader;
    /**
     * 创建Header完毕
     */
    private volatile boolean createHeaderOver;

    /**
     * 构造函数
     * @param clazz
     * @param format
     */
    public ExportExcel(Class<T> clazz, Format format) {
        this.format = format;
        exportEntity = ReflectUtil.getExistsAnnotation(clazz, ExportEntity.class);

        // 获取带有ExcelField注解的field
        complexFields = Stream.of(clazz.getDeclaredFields())
            .filter(javaField -> ReflectUtil.hasAnnotation(javaField, ExportField.class))
            .map(javaField -> new ComplexField(ReflectUtil.getExistsAnnotation(javaField, ExportField.class), javaField))
            .sorted((a, b) -> Integer.compare(a.excelField.sort(), b.excelField.sort()))
            .collect(Collectors.toList());
        if (complexFields == null) {
            throw new RuntimeException("excelField cannot null!");
        }

        workbook = format == Format._2003 ? new HSSFWorkbook() : new SXSSFWorkbook();
        dataFormat = workbook.createDataFormat();
    }

    /**
     * 创建Header Head
     */
    protected ExportExcel createHeaderAndHead() {
        String headerTitle = exportEntity.header().title();
        isCreateHeader = !StringUtil.isEmpty(headerTitle);
        sheet = isCreateHeader ? workbook.createSheet() : workbook.createSheet(headerTitle);

        Row headerRow = isCreateHeader? sheet.createRow(rowNum.getAndIncrement()) : null;
        Row headRow = sheet.createRow(rowNum.getAndIncrement());
        ExportExcel obj = this;
        ThreadPoolUtil.run(() -> {
            // 创建Header
            if (isCreateHeader) {
                createHeader(headerRow, exportEntity);
            }
            // 创建表头
            createHead(headRow, exportEntity);
            obj.createHeaderOver = true;
        });
        return this;
    }

    /**
     * 创建Head
     * @param headRow
     * @param exportEntity
     */
    private void createHead(Row headRow, ExportEntity exportEntity) {
        // 高度
        headRow.setHeightInPoints(exportEntity.head().height());

        int colNum = 0;
        // 显示序号
        if (exportEntity.body().showIndex()) {
            Cell cell = headRow.createCell(colNum++);
            addStyle(cell, exportEntity.head());
            sheet.setColumnWidth(colNum - 1, 1200);
            cell.setCellValue(exportEntity.body().indexName());
        }

        for (ComplexField complexField : complexFields) {
            Cell cell = headRow.createCell(colNum++);
            addStyle(cell, exportEntity.head());
            // 宽度
            sheet.setColumnWidth(colNum - 1, complexField.excelField.style().width());
            cell.setCellValue(complexField.excelField.title());
        }
    }

    /**
     * 创建Header
     * @param headerRow
     * @param exportEntity
     */
    private void createHeader(Row headerRow, ExportEntity exportEntity) {
        ExcelHeader excelHeader = exportEntity.header();
        headerRow.setHeightInPoints(excelHeader.style().height());
        Cell headerRowCell = headerRow.createCell(0);
        headerRowCell.setCellValue(excelHeader.title());
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, exportEntity.body().showIndex() ? complexFields.size() : (complexFields.size() - 1)));
        addStyle(headerRowCell, excelHeader.style());
    }


    /**
     * 添加style
     * @param cell
     * @param style
     * @return
     */
    private CellStyle addStyle(Cell cell, CellStyle$ style) {
        CellStyle cellStyle = workbook.createCellStyle();

        cellAddBorder(cellStyle);
        if (style != null) {
            cellStyle.setAlignment(style.align());
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            if (style.bgColor() != IndexedColors.WHITE) {
                cellStyle.setFillForegroundColor(style.bgColor().index);
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }

            Font font = workbook.createFont();
            font.setFontName(style.fontStyle().family());
            font.setFontHeightInPoints(style.fontStyle().size());
            font.setBoldweight(style.fontStyle().weight());
            if (style.fontStyle().color() != IndexedColors.BLACK) {
                font.setColor(style.fontStyle().color().index);
            }
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
        }
        return cellStyle;
    }

    /**
     * 添加边框
     * @param cellStyle
     */
    private void cellAddBorder(CellStyle cellStyle) {
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.index);
        cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.index);
        cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.index);
        cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.index);
    }

    /**
     * 添加一个单元格
     *
     * @param cellValue 添加值
     * @return 单元格对象
     */
    private Cell addCell(Cell cell, Object cellValue, ExportField excelField) {
        CellStyle cellStyle = addStyle(cell, excelField.style());
        if (cellValue == null) {
            cell.setCellValue("");
        } else {
            if (StringUtil.isNotEmpty(excelField.pattern())) {
                cellStyle.setDataFormat(cellValue instanceof BigDecimal ? HSSFDataFormat.getBuiltinFormat(excelField.pattern()) : dataFormat.getFormat(excelField.pattern()));
            }
            if (cellValue instanceof String) {
                cell.setCellValue((String) cellValue);
            } else if (cellValue instanceof Integer) {
                cell.setCellValue((Integer) cellValue);
            } else if (cellValue instanceof Long) {
                cell.setCellValue((Long) cellValue);
            } else if (cellValue instanceof Double) {
                cell.setCellValue((Double) cellValue);
            } else if (cellValue instanceof Float) {
                cell.setCellValue((Float) cellValue);
            } else if (cellValue instanceof Date) {
                cell.setCellValue((Date) cellValue);
            } else if (cellValue instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal) cellValue).doubleValue());
            } else {
                cell.setCellValue(cellValue.toString());
            }
        }
        return cell;
    }

    /**
     * 添加数据
     *
     * @return datas 数据列表
     */
    protected ExportExcel setData(Collection<T> datas) {
        short maxHeight = complexFields.stream().map(o -> o.excelField.style().height()).max((o1, o2) -> Short.compare(o1, o2)).orElse((short) 30);
        try {
            int index = 0;
            for (T data : datas) {
                int colNum = 0;
                Row bodyRow = sheet.createRow(rowNum.getAndIncrement());
                // 高度
                bodyRow.setHeightInPoints(maxHeight);
                if (exportEntity.body().showIndex()) {
                    Cell cell = bodyRow.createCell(colNum++);
                    addStyle(cell, exportEntity.body().indexStyle());
                    cell.setCellValue(++index);
                }
                for (ComplexField complexField : complexFields) {
                    Object cellValue = ReflectUtil.invokeGetter(data, complexField.javaField.getName());
                    this.addCell(bodyRow.createCell(colNum++), cellValue, complexField.excelField);
                }
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error("", e);
        }
        return this;
    }

    /**
     * 输出到客户端
     *
     * @param fileName 输出文件名
     */
    protected ExportExcel write(HttpServletResponse response, String fileName) {
        fileName = fileName.concat(format.suffix);
        try (OutputStream os = response.getOutputStream()) {
            response.reset();
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
            this.write(os);
        } catch (IOException e) {
            log.error("", e);
        }
        return this;
    }

    /**
     * 输出数据流
     *
     * @param os 输出数据流
     */
    protected ExportExcel write(OutputStream os) throws IOException {
        int waitTimes = 0;
        while (!this.createHeaderOver && waitTimes++ < 1000){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }

        workbook.write(os);
        os.flush();
        // 清理临时文件
        ThreadPoolUtil.run(() -> {
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("", e);
            }
        });
        return this;
    }

    /**
     * 输出到文件
     *
     * @param fileName 输出文件名
     */
    protected ExportExcel write(String fileName) throws IOException {
        fileName = fileName.concat(format.suffix);
        try (FileOutputStream os = new FileOutputStream(fileName)) {
            this.write(os);
            return this;
        }
    }

    @AllArgsConstructor
    private static class ComplexField {
        public ExportField excelField;
        public Field javaField;
    }

}
