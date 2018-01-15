package com.xczhihui.bxg.online.manager.common.util;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Milton
 * @date 2015年12月4日 下午2:46:31
 */
public class ExcelPoiUtil {
	
	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getStrByCell(fCell);
				}
			}
		}
		return null;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	@SuppressWarnings("resource")
	public static HSSFSheet getSheet(String filename) throws Exception {
		if (filename == null || "".equals(filename.trim())) {
            return null;
        }
		FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
			HSSFWorkbook book = new HSSFWorkbook(in);
			HSSFSheet sheet = book.getSheetAt(0);
			return sheet;
		} catch (Exception e) {
			throw new Exception("出错:" + filename);
		} finally
		{
			if (in != null)
			{
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("resource")
	public static XSSFSheet getSheet2007(String filename) throws Exception {
		if (filename == null || "".equals(filename.trim())) {
            return null;
        }

		FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
			XSSFWorkbook book = new XSSFWorkbook(in);
			XSSFSheet sheet = book.getSheetAt(0);
			return sheet;
		} catch (Exception e) {
			throw new Exception("出错:" + filename);
		} finally
		{
			if (in != null)
			{
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean checkAllNull(Row row) {
		Iterator<Cell> celliIterator = row.cellIterator();
		while (celliIterator.hasNext()) {
			String temp = getStrByCell(celliIterator.next());
			if (temp != null && !temp.trim().isEmpty()) {
                return false;
            }
		}
		return true;
	}

	public static String getStrByCell(Cell cell) {

		if (cell == null) {
            return null;
        }
		String temp = null;

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:

		case Cell.CELL_TYPE_STRING:
			temp = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				temp = cell.getStringCellValue();
			} catch (IllegalStateException e) {
				try {
					temp = cell.getNumericCellValue() + "";
				} catch (Exception e1) {
					temp = "";
				}
			}

			break;
		case Cell.CELL_TYPE_NUMERIC:
			boolean remar = false;
			try {
				remar = HSSFDateUtil.isCellDateFormatted(cell);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (remar) {

				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
				temp = a.format(date);
			} else {
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				String cellValue = cell.toString();
				temp = cellValue;
			}
			break;
		default:
			break;
		}

		if (temp == null || "".equals(temp.trim())) {
            temp = null;
        } else {
            temp = temp.trim();
        }

		return trim(temp);
	}

	public static String trim(String str) {
		if (str == null || "".equals(str)) {
			return "";
		} else {
			return str.replaceAll(" ", "");
		}
	}

	public static HSSFCellStyle setHeadStyle(HSSFWorkbook workbook,
	        HSSFCellStyle style) {
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样样式
		style.setFont(font);
		return style;
	}
}
