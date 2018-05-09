package cn.dslcode.common.core.office.excel;

import cn.dslcode.common.core.collection.CollectionUtil;
import cn.dslcode.common.core.file.FileUtil;
import cn.dslcode.common.core.office.excel.annotation.ImportEntity;
import cn.dslcode.common.core.office.excel.annotation.ImportField;
import cn.dslcode.common.core.office.excel.enums.Format;
import cn.dslcode.common.core.reflect.ReflectUtil;
import cn.dslcode.common.core.string.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 导入Excel文件
 * @author dongsilin
 * @version 2018/5/7.
 */
@Slf4j
public class ImportExcel<E> {

	/**
	 * 工作薄对象
	 */
	private Workbook workbook;

	/**
	 * 工作表对象
	 */
	private Sheet sheet;

	/**
	 * 标题行号
	 */
	private int headRowNum;

	/**
	 * 带有ExcelField注解的field列表
	 */
	private List<ComplexField> complexFields = new ArrayList<>();

	/**
	 * 导出对象class
	 */
	private Class<E> targetClazz;

	public ImportExcel(String fileName, int headRowNum, Class<E> clazz) throws IOException {
		this(new File(fileName), headRowNum, clazz);
	}

	public ImportExcel(File file, int headRowNum, Class<E> clazz) throws IOException {
		this(file, headRowNum, 0, clazz);
	}

	public ImportExcel(String fileName, int headRowNum, int sheetIndex, Class<E> clazz) throws IOException {
		this(new File(fileName), headRowNum, sheetIndex, clazz);
	}

	public ImportExcel(File file, int headRowNum, int sheetIndex, Class<E> clazz) throws IOException {
		this(file.getName(), new FileInputStream(file), headRowNum, sheetIndex, clazz);
	}

	public ImportExcel(String fileName, InputStream inputStream, int headRowNum, Class<E> clazz) throws IOException {
		this(fileName, inputStream, headRowNum, 0, clazz);
	}

	public ImportExcel(String fileName, InputStream inputStream, int headRowNum, int sheetIndex, Class<E> clazz) throws IOException {
		Assert.notNull(inputStream, "导入空文档!");
		Assert.hasLength(fileName, "导入文档空文件名!");
		Assert.notNull(clazz, "targetClazz cannot null!");

		String suffix = FileUtil.suffix(fileName, true);
		if (StringUtil.eq(suffix, Format._2003.suffix)) {
			this.workbook = new HSSFWorkbook(inputStream);
		} else if (StringUtil.eq(suffix, Format._2007.suffix)) {
			this.workbook = new XSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("文档格式不正确!");
		}

		if (this.workbook.getNumberOfSheets() < sheetIndex) {
			throw new IllegalArgumentException("文档中没有工作表!");
		}

		this.sheet = this.workbook.getSheetAt(sheetIndex);
		this.headRowNum = headRowNum;
		this.targetClazz = clazz;
	}

	/**
	 * 获取导入数据列表
	 * @param <E>
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	public <E> List<E> getData() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		ImportEntity importEntity = ReflectUtil.getExistsAnnotation(targetClazz, ImportEntity.class);

		// 获取带有注解的Field
		List<Field> javaFields = Stream.of(targetClazz.getDeclaredFields())
			.parallel()
			.filter(f -> ReflectUtil.hasAnnotation(f, ImportField.class))
			.collect(Collectors.toList());

		// 获取head title对应的注解
		Row headRow = this.sheet.getRow(headRowNum);
		Iterator<Cell> headRowCellIterator = headRow.cellIterator();
		// 是否含有序号
		if (importEntity.showIndex() && headRowCellIterator.hasNext()) {
			headRowCellIterator.next();
		}
		while (headRowCellIterator.hasNext()) {
			String title = String.valueOf(getCellValue(headRowCellIterator.next()));
			for (Field javaField : javaFields) {
				ImportField excelField = ReflectUtil.getExistsAnnotation(javaField, ImportField.class);
				if (StringUtil.eq(title, excelField.title())) {
					complexFields.add(new ComplexField(javaField.getName(), javaField.getType()));
					break;
				}
			}
		}

		// 获body取数据
		List<E> dataList = CollectionUtil.newArrayList();
		int lastRowNum = this.sheet.getLastRowNum();
		for (int bodyRowNum = headRowNum + 1; bodyRowNum <= lastRowNum; bodyRowNum++) {
			E e = (E) targetClazz.newInstance();
			int column = 0;
			Iterator<Cell> cellIterator = this.sheet.getRow(bodyRowNum).cellIterator();
			// 是否含有序号
			if (importEntity.showIndex() && cellIterator.hasNext()) {
				cellIterator.next();
			}
			while (cellIterator.hasNext()) {
				setCellValueToField(cellIterator.next(), column++, e);
			}
			dataList.add(e);
		}
		return dataList;
	}

	/**
	 * 关闭excel
	 * @throws IOException
	 */
	public void close() throws IOException {
		this.workbook.close();
	}

	/**
	 * 获取单元格值
	 * @param cell
	 * @return
	 */
	private Object getCellValue (Cell cell) {
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC: return cell.getNumericCellValue();
				case Cell.CELL_TYPE_STRING: return cell.getStringCellValue();
				case Cell.CELL_TYPE_FORMULA: return cell.getNumericCellValue();
				case Cell.CELL_TYPE_BOOLEAN: return cell.getBooleanCellValue();
				case Cell.CELL_TYPE_BLANK: return null;
				case Cell.CELL_TYPE_ERROR: return cell.getErrorCellValue();
				default: return null;
			}
		}
		return null;
	}

	/**
	 * 将单元格值通过反射注入对象的对应field
	 * @param cell
	 * @param column
	 * @param e
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	private void setCellValueToField (Cell cell, int column, Object e) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Object cellValue = getCellValue(cell);
		ComplexField complexField = complexFields.get(column);
		if (cellValue != null) {
			if (complexField.fieldClass == String.class) {
				cellValue = String.valueOf(cellValue);
			} else if (complexField.fieldClass == Integer.class || complexField.fieldClass == int.class) {
				cellValue = new BigDecimal(cellValue.toString()).intValue();
			} else if (complexField.fieldClass == Long.class || complexField.fieldClass == long.class) {
				cellValue = new BigDecimal(cellValue.toString()).longValue();
			} else if (complexField.fieldClass == Double.class || complexField.fieldClass == double.class) {
				cellValue = Double.valueOf(cellValue.toString());
			} else if (complexField.fieldClass == Float.class || complexField.fieldClass == float.class) {
				cellValue = Float.valueOf(cellValue.toString());
			} else if (complexField.fieldClass == Date.class) {
				cellValue = DateUtil.getJavaDate((Double) cellValue);
			} else if (complexField.fieldClass == BigDecimal.class) {
				cellValue = BigDecimal.valueOf((Double) cellValue);
			}
		}
		// 反射注入
		ReflectUtil.invokeSetter(e, complexField.fieldName, cellValue, complexField.fieldClass.isPrimitive());
	}

	@AllArgsConstructor
	private static class ComplexField {
		public String fieldName;
		public Class<?> fieldClass;
	}

}
