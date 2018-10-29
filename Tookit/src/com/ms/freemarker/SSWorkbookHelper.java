package com.ms.freemarker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SSWorkbookHelper {

	private static Logger logger = Logger.getLogger(SSWorkbookHelper.class);
	static HashMap<String,String> excelLabelMap=new HashMap<String,String>();
	public static final int VAL_MAX_COL = 5000;
	public static final int VAL_MAX_ROW = 2000;
	public static final int _VAL_TCP_DATA_TYPE_CHAR = 0;
	public static final int _VAL_TCP_DATA_TYPE_NUM = 1;
	public static final String VAL_TCP_DATA_TYPE_CHAR = "A";
	public static final String VAL_LABEL_NOT_IDENTIFIED = "[Not Identified Cell Type]";
	
	static{
		excelLabelMap.clear();
		excelLabelMap.put("0", "A");
		excelLabelMap.put("1", "B");
		excelLabelMap.put("2", "C");
		excelLabelMap.put("3", "D");
		excelLabelMap.put("4", "E");
		excelLabelMap.put("5", "F");
		excelLabelMap.put("6", "G");
		excelLabelMap.put("7", "H");
		excelLabelMap.put("8", "I");
		excelLabelMap.put("9", "J");
		excelLabelMap.put("10", "K");
		excelLabelMap.put("11", "L");
		excelLabelMap.put("12", "M");
		excelLabelMap.put("13", "N");
		excelLabelMap.put("14", "O");
		excelLabelMap.put("15", "P");
		excelLabelMap.put("16", "Q");
		excelLabelMap.put("17", "R");
		excelLabelMap.put("18", "S");
		excelLabelMap.put("19", "T");
		excelLabelMap.put("20", "U");
		excelLabelMap.put("21", "V");
		excelLabelMap.put("22", "W");
		excelLabelMap.put("23", "X");
		excelLabelMap.put("24", "Y");
		excelLabelMap.put("25", "Z");
	}
	
	public static Workbook getWorkbook(String fileName) throws InvalidFormatException, IOException {

		InputStream is = null;
		Workbook ssWb = null;
		try {
			// For EXCEL 2007
			is = new FileInputStream(fileName);
			ssWb = WorkbookFactory.create(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}

		return ssWb;

	}

	public static void writeWorkbook(Workbook ssWb, String fileName) throws IOException {

		OutputStream os = null;
		try {
			// Output the updated "sheet" in "ssWb" to file.
			os = new FileOutputStream(fileName);
			ssWb.write(os);
		} finally {
			if (os != null) {
				os.close();
			}
		}

	}

	public static Sheet getWorksheet(String fileName, String sheetName) throws IOException, InvalidFormatException {

		logger.info("READ: Read sheet \"" + sheetName + "\" from EXCEL \"" + fileName + "\".");
		Workbook ssWb = getWorkbook(fileName);
		Sheet sheet = ssWb.getSheet(sheetName);

		return sheet;

	}

	public static Row createRow(Sheet sheet, int rowIdx) {
		return sheet.createRow(rowIdx);
	}

	public static Row getRow(Sheet sheet, int rowIdx) {
		return sheet.getRow(rowIdx);
	}

	/**
	 * Remove rows between "startRow" and "endRow".
	 * 
	 * @param sheet
	 * @param startRow
	 * @param endRow
	 */
	public static void removeRow(Sheet sheet, int startRow, int endRow) {
		if (startRow > endRow) {
			return;
		}
		Row row = null;
		for (int i = startRow; i <= endRow; i++) {
			row = sheet.getRow(i);
			// Skip those rows are null.
			if (row == null) {
				continue;
			}
			sheet.removeRow(row);
		}
	}

	/**
	 * Transform the character column name into numeric column number.
	 * 
	 * @param colName
	 * @param offsetCol
	 * @return
	 */
	public static int getColIdx(String colName, int offsetCol) {

		if (StringUtils.isBlank(colName) == true) {
			return VAL_MAX_COL;
		}

		int i = -1;
		char c1 = 0;
		char c2 = 0;
		int i1 = 0;
		int i2 = 0;
		if (colName.length() == 1) {
			c1 = colName.charAt(0);
			i = Character.getNumericValue(c1) - offsetCol;
		} else {
			c1 = colName.charAt(0);
			c2 = colName.charAt(1);
			i1 = Character.getNumericValue(c1);
			i2 = Character.getNumericValue(c2);
			i = (i1 - 9) * 26 + i2 - offsetCol;
		}

		return i;

	}
	
	/**
	 * Transform the character column name into numeric column number.
	 * 
	 * @param colName
	 * @param offsetCol
	 * @return
	 */
	public static int getColIdx(String colName) {
		int nCharValue=-1;
		int result=0;
		
		if (StringUtils.isBlank(colName) == true) {
			return VAL_MAX_COL;
		}

		for(int i=0;i<colName.length();i++){
			nCharValue = Character.getNumericValue(colName.charAt(i))-10;
			result=result*26+nCharValue;
		}
		
		return result;
	}
	
	/**
	 * Transform the character column name into numeric column number.
	 * 
	 * @param colName
	 * @param offsetCol
	 * @return
	 */
	public static String getColLabel(int colNum) throws  InvalidFormatException{
		int result = 0 ;
		int left = 0 ;
		String rtnLbl = "";
		if (colNum > VAL_MAX_COL) {
			throw new InvalidFormatException("InvalidFormatException -> BadInputException:输入列数过大.");
		}
		result = colNum;
		while (result >= 0){
			left = result % 26;
			rtnLbl = excelLabelMap.get(left+"") + rtnLbl;
			result = result - left;
			result = result / 26 -1;
		}
		
		return rtnLbl;
	}

	public static int getFieldDataType(String dataType) {

		if (StringUtils.isNotBlank(dataType) && dataType.trim().equalsIgnoreCase(VAL_TCP_DATA_TYPE_CHAR)) {
			return _VAL_TCP_DATA_TYPE_CHAR;
		} else {
			return _VAL_TCP_DATA_TYPE_NUM;
		}

	}

	public static boolean isPackedField(String packFlag) {

		if (StringUtils.isNotBlank(packFlag)) {
			return true;
		} else {
			return false;
		}

	}

	public static String trimCellVal(String cellValAsStr, String lenDef) {

		if (StringUtils.isNotBlank(lenDef)) {
			int len = (int) Double.parseDouble(lenDef);
			if (cellValAsStr.length() >= len) {
				cellValAsStr = cellValAsStr.substring(0, len);
			}
		}

		return cellValAsStr;

	}

	public static String getCellValueAsString(Sheet sheet, int colIdx, int rowIdx) {
		return getCellValueAsString(sheet, colIdx, rowIdx, null, null);
	}

	public static String getCellValueAsFormula(Sheet sheet, int colIdx, int rowIdx) {
		
		String cellValueAsFormula = "";
		
	    if(sheet==null){
	    	logger.error("Sheet object is null!");
	    }
	    else{
			Row row = sheet.getRow(rowIdx);
			if (row == null) {
				return cellValueAsFormula;
			}
			Cell cell = row.getCell(colIdx);
			if (cell == null) {
				return cellValueAsFormula;
			}
			cellValueAsFormula = cell.getCellFormula();
	    }
		
		return cellValueAsFormula;
	}
	
	public static String getCellFormulaAsString(FormulaEvaluator formula, Sheet sheet, int colIdx, int rowIdx) {
		
		String cellFormulaAsString = "";
		//FormulaEvaluator formula = ssWb.getCreationHelper().createFormulaEvaluator();
		Row row = sheet.getRow(rowIdx);
		Cell cell = row.getCell(colIdx); 
		CellValue cellValue = formula.evaluate(cell);
		if (cellValue == null) {
			return cellFormulaAsString;
		}
		switch (cellValue.getCellType()) {
	        case Cell.CELL_TYPE_STRING:
	            cellFormulaAsString = cellValue.getStringValue();
	            break;    
		    case Cell.CELL_TYPE_BOOLEAN:
		    	cellFormulaAsString = String.valueOf(cellValue.getBooleanValue());
		        break;
		    case Cell.CELL_TYPE_NUMERIC:
		    	cellFormulaAsString = String.valueOf(cellValue.getNumberValue());
		        break;
		    case Cell.CELL_TYPE_BLANK:
		        break;
		    case Cell.CELL_TYPE_ERROR:
		        break;
		    // CELL_TYPE_FORMULA will never happen
		    case Cell.CELL_TYPE_FORMULA: 
		        break;
		}
		return cellFormulaAsString;
	}
	
	public static String getCellValueAsString(Sheet sheet, int colIdx, int rowIdx, String typeDef, String lenDef) {
		// logger.info("entering  = " + colIdx + "-"+rowIdx);
		String cellValueAsString = "";
    
		    if(sheet==null){
		    	logger.error("Sheet object is null!");
		    }
		    else{
				Row row = sheet.getRow(rowIdx);
				if (row == null) {
					return cellValueAsString;
				}
				Cell cell = row.getCell(colIdx);
				if (cell == null) {
					return cellValueAsString;
				}
				int cellType = cell.getCellType();

				if (cellType == Cell.CELL_TYPE_STRING || cellType == Cell.CELL_TYPE_FORMULA) {
					try {
						cellValueAsString = cell.getStringCellValue();
					} catch (IllegalStateException e1) {
						// If the type of cell value is not string, then try to use numeric.
						try {
							cellValueAsString = String.valueOf(cell.getNumericCellValue());
						} catch (IllegalStateException e2) {
							cellValueAsString = "NOT INVALID SAMPLE VALUE!!!";
						}
					}
				} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
					cellValueAsString = String.valueOf(cell.getNumericCellValue());
					if (StringUtils.isNotBlank(typeDef) && VAL_TCP_DATA_TYPE_CHAR.indexOf(typeDef) != -1) {
						if (StringUtils.isNotBlank(lenDef)) {
							int len = (int) Double.parseDouble(lenDef);
							if (cellValueAsString.length() >= len) {
								cellValueAsString = cellValueAsString.substring(0, len);
							}
							logger.warn("!!!!! Wrong data type defined in EXCEL sheet|colIdx|rowIdx: " + sheet.getSheetName() + "|" + colIdx + "|" + rowIdx
									+ ", cut it forcely into length: " + len);
						}
					}
				} else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
					cellValueAsString = String.valueOf(cell.getBooleanCellValue());
				} else if (cellType == Cell.CELL_TYPE_BLANK) {
					cellValueAsString = "";
				} else {
					cellValueAsString = VAL_LABEL_NOT_IDENTIFIED;
				}
		    }
		return cellValueAsString;
	}

	public static double getCellValueAsNumeric(Sheet sheet, int colIdx, int rowIdx) {

		double doubleCellValue = -1.0;

		Row row = sheet.getRow(rowIdx);
		if (row == null) {
			return doubleCellValue;
		}
		Cell cell = row.getCell(colIdx);
		if (cell == null) {
			return doubleCellValue;
		}
		int cellType = cell.getCellType();

		if (cellType == Cell.CELL_TYPE_NUMERIC || cellType == Cell.CELL_TYPE_FORMULA) {
			doubleCellValue = cell.getNumericCellValue();
		}

		return doubleCellValue;

	}

	public static Cell createStringCell(Row row, int colIdx, String strValue) {

		Cell cell = row.createCell(colIdx);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(strValue);

		return cell;

	}

	public static Cell createNumericCell(Row row, int colIdx, double doubleValue) {

		Cell cell = row.createCell(colIdx);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(doubleValue);

		return cell;

	}

	public static void updateStringCell(Row row, int colIdx, String strValue) {

		logger.debug("CELL: Update \"" + strValue + "\" into row/col: " + row.getRowNum() + "/" + colIdx);
		Cell cell = row.getCell(colIdx);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(strValue);

	}
	
	public static void updateFormulaCell(Row row, int colIdx, String strValue, CellStyle cellStyle) {

		logger.debug("CELL: Update \"" + strValue + "\" into row/col: " + row.getRowNum() + "/" + colIdx);
		Cell cell = row.getCell(colIdx);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		cell.setCellFormula(strValue);
	}
	
	public static void createFormulaCell(Row row, int colIdx, String strValue, CellStyle cellStyle) {

		logger.debug("CELL: Update \"" + strValue + "\" into row/col: " + row.getRowNum() + "/" + colIdx);
		Cell cell = row.createCell(colIdx);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		cell.setCellFormula(strValue);
	}

	public static void setCellStyle4TblHdr(Workbook ssWb, Cell cell, short bgColorIdx) {

		// Set background color.
		// CellStyle cellStyle = ssWb.createCellStyle();
		// cellStyle.setFillForegroundColor(bgColorIdx);
		// cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		// Set bold font.
		// Font font = ssWb.createFont();
		// font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// cellStyle.setFont(font);

		// cell.setCellStyle(cellStyle);

	}
	
	public static void setCellStyle4TblHdr(Workbook ssWb, Cell cell, short bgColorIdx, short fontColorIdx) {

		// Set background color.
		CellStyle style = ssWb.createCellStyle();
		style.setFillForegroundColor(bgColorIdx);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        
        //set font
		Font font = ssWb.createFont();
		font.setColor(fontColorIdx);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		
		//set border
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		
		cell.setCellStyle(style);

	}


	public static void setCellStyle4UpdFlag(Workbook ssWb, Cell cell, short bgColorIdx) {
		// CellStyle cellStyle = ssWb.createCellStyle();
		// cellStyle.setFillForegroundColor(bgColorIdx);
		// cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// cell.setCellStyle(cellStyle);
	}
	
	public static void createExcelFile(String filePath){
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			wb.createSheet();
			
			FileOutputStream fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

}