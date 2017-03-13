package com.springboot.common.util;

import com.google.common.collect.Maps;
import com.springboot.common.anotation.ExcelCellType;
import com.springboot.common.anotation.ExcelHeadMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static com.springboot.common.util.StringUtil.isNotEmpty;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ExcelUtil {


	public static Workbook getWorkbook(InputStream fileInputStream) {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(fileInputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (null != fileInputStream) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return workbook;
	}

	public static class ExcelHeadHelper {
		private Map<String, Integer> indexMap = Maps.newHashMap();
		private Map<String, ExcelCellType> typeMap = Maps.newHashMap();
		private Map<String, Field> fieldMap = Maps.newHashMap();

		public ExcelHeadHelper build(Class clazz) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				Annotation[] annos = f.getAnnotations();
				for (Annotation a : annos) {
					if (a instanceof ExcelHeadMap) {
						// //get all attributes
						// Map map = AnnotationUtils.getAnnotationAttributes(a);
						// //get value
						String key = (String) AnnotationUtils.getValue(a, "name");
						indexMap.put(key.trim(), null);
						fieldMap.put(key.trim(), f);
						ExcelCellType type = (ExcelCellType) AnnotationUtils.getValue(a, "type");
						typeMap.put(key.trim(), type);
					}
				}
			}
			return this;
		}

		public String index(Row row) {
			short lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				Cell cell = row.getCell(j);
				String cellStringValue = getStringValueOfCell(cell);
				if (isNotEmpty(cellStringValue)) {
					indexMap.putIfAbsent(cellStringValue, j);
				}
			}
			String missingColumns = indexMap.entrySet().stream().filter(e -> isNull(e.getValue()))
					.map(Map.Entry::getKey).collect(Collectors.joining(","));
			if (isNotEmpty(missingColumns)) {
				return missingColumns;
			}
			return null;
		}

		public Integer headIndex(String key) {
			return indexMap.get(key);
		}

		public String fill(Row row, Object dto) {
			for (Map.Entry<String, Field> stringFieldEntry : fieldMap.entrySet()) {
				String key = stringFieldEntry.getKey();
				Integer integer = indexMap.get(key);
				try {
					Field field = stringFieldEntry.getValue();
					ExcelCellType type = typeMap.get(key);
					Object setValue;
					if (nonNull(type) && type.equals(ExcelCellType.anDate)) {
						setValue = getAnsteelDateCellStringValue(row, integer);
					} else {
						if (field.getType() == String.class) {
							setValue = getStringValue(row, integer);
						} else if (field.getType() == BigDecimal.class) {
							setValue = getBigDecimalString(row, integer);
						} else if (field.getType() == Integer.class) {
							setValue = getBigDecimalString(row, integer).intValue();
						} else if (field.getType() == Boolean.class) {
							setValue = getBooleanString(row, integer);
						} else {
							return field.getType().getName();
						}

					}
					BeanUtils.setProperty(dto, field.getName(), setValue);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		private Object getBooleanString(Row row, Integer index) {
			Cell cell = row.getCell(index);
			if (!ExcelUtil.isCellEmpty(cell)) {
				if ("true".equalsIgnoreCase(cell.getStringCellValue())) {
					return Boolean.TRUE;
				} else if ("false".equalsIgnoreCase(cell.getStringCellValue())) {
					return Boolean.FALSE;
				}
			}
			return null;
		}

		public String index(Row[] rows) {
			for (Row row : rows) {
				short lastCellNum = row.getLastCellNum();
				for (int j = 0; j < lastCellNum; j++) {
					Cell cell = row.getCell(j);
					String cellStringValue = getStringValueOfCell(cell);
					if (isNotEmpty(cellStringValue)) {
						indexMap.putIfAbsent(cellStringValue, j);
					}
				}
			}
			String missingColumns = indexMap.entrySet().stream().filter(e -> isNull(e.getValue()))
					.map(Map.Entry::getKey).collect(Collectors.joining(","));
			if (isNotEmpty(missingColumns)) {
				return missingColumns;
			}
			return null;
		}
	}

	public static String getStringValueOfCell(Cell cell) {
		if (!isCellEmpty(cell)) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String cellValue = cell.getStringCellValue();
				if (isNotEmpty(cellValue)) {
					return cellValue.trim();
				}
			}
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return new BigDecimal(cell.getNumericCellValue()).toString();
			}
		}
		return null;
	}

	public static boolean isCellEmpty(Cell cell) {
		return cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK;
	}

	public static String getStringValue(Row row, int index) {
		return getStringValueOfCell(row.getCell(index));
	}

	public static BigDecimal getBigDecimalString(Row row, int idex) {
		return getBigDecimalString(row.getCell(idex));
	}

	public static BigDecimal getBigDecimalString(Cell cell) {
		BigDecimal value = BigDecimal.ZERO;
		if (!ExcelUtil.isCellEmpty(cell)) {
			if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
				value = new BigDecimal(Double.parseDouble(cell.getStringCellValue()));
			} else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				value = new BigDecimal(cell.getNumericCellValue());
			}
		}
		return value;
	}

	public static String getAnsteelDateCellStringValue(Row row, Integer index) {
		Cell cell = row.getCell(index);
		if (nonNull(cell)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String cellStringValue = getStringValueOfCell(cell);
				if (isNotEmpty(cellStringValue)) {
					try {
						if (cellStringValue.contains(":")) {
							Date date = simpleDateFormat.parse(cellStringValue.replace("/", "-"));
							return simpleDateFormat.format(date);
						} else if (cellStringValue.contains(".")) {
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(cellStringValue.replace(".", "-"));
							return simpleDateFormat.format(date);
						} else {
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(cellStringValue.replace("/", "-"));
							return simpleDateFormat.format(date);
						}
					} catch (ParseException e) {
						return  "无效的日期格式" + cellStringValue;
					}
				} else {
					return "";
				}
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return simpleDateFormat.format(cell.getDateCellValue());
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
}
