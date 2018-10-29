package swz.sample.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import swz.infra.tools.poi.SSWorkbookHelper;

public class DataDict {
	
	public static String en,ab,cn,tp,ln,pr,rm;
	public static String destBe = "BQD";
	public static String destDt = getCurrDate();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//dict("ATMV");
		//dict("LOS");
		//dict("SIBS");
		//dict("UPAY");
		//dict("PAS");
		//dict("PMS");
		//dict("ES");
		//dict("ICMS");
		dict("OCS");
		//dict("INM");
		//dict("INB");
		//dict("GBIS");
		//dict("PPS");
		//dict("TFS");
		//dict("BMS");
		//dict("SMS");
		//dict("JHY");
		//dict("CSS");
		//dict("merge");
	}
	
	public static void dict(String src) {
		try {
			long ls = System.currentTimeMillis();
			ResourceBundle rsBundle = ResourceBundle.getBundle("dict");
			String srcFilePath = rsBundle.getString(src+".srcFile");
			String destFilePath = rsBundle.getString(src+".destFile");
			
			String[] srcSheetNMs = getArrayFromString(rsBundle.getString(src+".srcSheetNMs"), ",");
			String invalidString = rsBundle.getString(src+".invalidString");
			String destSrc = rsBundle.getString(src+".src");
			
			int srcRowS = Integer.parseInt(rsBundle.getString(src+".srcRowS"));
			int srcRowS1 = Integer.parseInt(rsBundle.getString(src+".srcRowS1"));
			int iEN = Integer.parseInt(rsBundle.getString(src+".srcEN"));
			int iAB = Integer.parseInt(rsBundle.getString(src+".srcAB"));
			int iCN = Integer.parseInt(rsBundle.getString(src+".srcCN"));
			int iTP = Integer.parseInt(rsBundle.getString(src+".srcTP"));
			int iLN = Integer.parseInt(rsBundle.getString(src+".srcLN"));
			int iPR = Integer.parseInt(rsBundle.getString(src+".srcPR"));
			int iRM = Integer.parseInt(rsBundle.getString(src+".srcRM"));
			int iEN1 = Integer.parseInt(rsBundle.getString(src+".srcEN1"));
			int iAB1 = Integer.parseInt(rsBundle.getString(src+".srcAB1"));
			int iCN1 = Integer.parseInt(rsBundle.getString(src+".srcCN1"));
			int iTP1 = Integer.parseInt(rsBundle.getString(src+".srcTP1"));
			int iLN1 = Integer.parseInt(rsBundle.getString(src+".srcLN1"));
			int iPR1 = Integer.parseInt(rsBundle.getString(src+".srcPR1"));
			int iRM1 = Integer.parseInt(rsBundle.getString(src+".srcRM1"));
			String reduplicate = rsBundle.getString(src+".reduplicate");
			String isAllAB = rsBundle.getString(src+".allAB");
			String isAllCN = rsBundle.getString(src+".allCN");
			String isResp = rsBundle.getString(src+".resp");
			
			String isNew = rsBundle.getString(src+".isNew")==null?"Y":rsBundle.getString(src+".isNew");
			if (isNew.equalsIgnoreCase("Y")) {
				FileInputStream input = new FileInputStream(rsBundle.getString("DICT.TEMPLATE"));
				FileOutputStream output = new FileOutputStream(destFilePath);
				int in = input.read();
			    while (in != -1) {
			    	output.write(in);
			    	in = input.read();
			    }
			    input.close();
			    output.close();
			}
			
			Workbook destWb = SSWorkbookHelper.getWorkbook(destFilePath);
			for(int i=0;i<srcSheetNMs.length;i++) {
				Sheet srcSheet = SSWorkbookHelper.getWorksheet(srcFilePath, srcSheetNMs[i]);
				int srcRowE = srcSheet.getLastRowNum();
				rm = "";
				if (!reduplicate.equalsIgnoreCase("Y")&&!src.equalsIgnoreCase("merge")&&!src.equalsIgnoreCase("SIBS")) {
					rm = srcSheetNMs[i]+".reqt";
				}
				for(int j=srcRowS;j<=srcRowE;j++) {
					getSrcValues(src, iEN, iAB, iCN, iTP, iLN, iPR, iRM,
							srcSheet, j);
					
					if (ab != null && !ab.equals("") && invalidString.indexOf(ab) == -1) {
						String destSheetNM = ab.substring(0, 1).toUpperCase();
						Sheet destSheet = destWb.getSheet(destSheetNM);
						//System.out.println("ab:"+ab);
						//writeToDict(destFilePath, destWb, destSrc, destBe,
						//		destDt, reduplicate, rm, en, ab, cn, tp, ln,
						//		pr, destSheet, 0);
						
						if(isAllAB.equalsIgnoreCase("Y")) {
							destSheet = destWb.getSheet("ALL-ab");
							writeToDict(destFilePath, destWb, destSrc, destBe,
									destDt, reduplicate, rm, en, ab, cn, tp, ln,
									pr, destSheet, 0);
						}
						if(isAllCN.equalsIgnoreCase("Y")) {
							destSheet = destWb.getSheet("ALL-cn");
							writeToDict(destFilePath, destWb, destSrc, destBe,
									destDt, reduplicate, rm, en, ab, cn, tp, ln,
									pr, destSheet, 1);
						}
						
					}
				}
				
				if (isResp.equalsIgnoreCase("Y")) {
					if (!reduplicate.equalsIgnoreCase("Y")) {
						rm = srcSheetNMs[i]+".resp";
					}
					for(int j=srcRowS1;j<=srcRowE;j++) {
						getSrcValues(src, iEN1, iAB1, iCN1, iTP1, iLN1, iPR1, iRM1,
								srcSheet, j);
						
						if (ab != null && !ab.equals("") && invalidString.indexOf(ab) == -1) {
							String destSheetNM = ab.substring(0, 1).toUpperCase();
							Sheet destSheet = destWb.getSheet(destSheetNM);
							//writeToDict(destFilePath, destWb, destSrc, destBe,
							//		destDt, reduplicate, rm, en, ab, cn, tp, ln,
							//		pr, destSheet, 0);
							
							if(isAllAB.equalsIgnoreCase("Y")) {
								destSheet = destWb.getSheet("ALL-ab");
								writeToDict(destFilePath, destWb, destSrc, destBe,
										destDt, reduplicate, rm, en, ab, cn, tp, ln,
										pr, destSheet, 0);
							}
							if(isAllCN.equalsIgnoreCase("Y")) {
								destSheet = destWb.getSheet("ALL-cn");
								writeToDict(destFilePath, destWb, destSrc, destBe,
										destDt, reduplicate, rm, en, ab, cn, tp, ln,
										pr, destSheet, 1);
							}
							
						}
					}
				}
			}
			
			long le = System.currentTimeMillis();
			System.out.println(le-ls);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	private static void getSrcValues(String src, int iEN, int iAB, int iCN,
			int iTP, int iLN, int iPR, int iRM, Sheet srcSheet, int j) {
		en = SSWorkbookHelper.getCellValueAsString(srcSheet, iEN, j);
		en = en==null?"":en.trim();
		ab = SSWorkbookHelper.getCellValueAsString(srcSheet, iAB, j);
		ab = ab==null?"":ab.trim();
		cn = SSWorkbookHelper.getCellValueAsString(srcSheet, iCN, j);
		cn = cn==null?"":cn.trim();
		tp = SSWorkbookHelper.getCellValueAsString(srcSheet, iTP, j);
		tp = tp==null?"":tp.trim();
		if(tp.equalsIgnoreCase("string")) tp = "字符";
		else if(tp.equalsIgnoreCase("double")) tp = "数值";
		else if(tp.equalsIgnoreCase("int")) tp = "数值";
		else if(tp.equalsIgnoreCase("number")) tp = "数值";
		
		ln = SSWorkbookHelper.getCellValueAsString(srcSheet, iLN, j);
		ln = ln==null?"":ln.trim();
		if(ln.indexOf(".") != -1) {
			ln = ln.substring(0,ln.indexOf("."));
		}
		pr = SSWorkbookHelper.getCellValueAsString(srcSheet, iPR, j);
		pr = pr==null?"":pr.trim();
		if (pr.equals("(null)") || pr.equals("0")) pr = "";
		if(pr.indexOf(".") != -1) {
			pr = pr.substring(0,pr.indexOf("."));
		}
		if (src.equalsIgnoreCase("UPAY")) {
			rm = SSWorkbookHelper.getCellValueAsString(srcSheet, iRM, j);
			rm = rm==null?"":rm.trim();
			if(rm.equalsIgnoreCase("NO")) rm = "节点";
			else if(rm.equalsIgnoreCase("YES")) rm = "叶子";
		}
	}

	private static void writeToDict(String destFilePath, Workbook destWb,
			String destSrc, String destBe, String destDt, String reduplicate,
			String rm, String en, String ab, String cn, String tp, String ln,
			String pr, Sheet destSheet, int orderFlg) throws IOException {
		int count = 0;
		int rowNum = destSheet.getLastRowNum();
		int insertNum = rowNum+1;
		int colorIndex = 0;
		for (int k=1; k<=rowNum;k++ ) {
			
			String tempAB = SSWorkbookHelper.getCellValueAsString(destSheet,1,k).trim();
			String tempCN = SSWorkbookHelper.getCellValueAsString(destSheet,2,k).trim();
			if (reduplicate.equalsIgnoreCase("Y")) {
				if (tempAB.equals(ab)) {
					count ++;
					break;
				}
			} else {
				//set red color for check
				if (colorIndex == 0) {
					String tempTP = SSWorkbookHelper.getCellValueAsString(destSheet,3,k).trim();
					String tempLN = SSWorkbookHelper.getCellValueAsString(destSheet,4,k).trim();
					String tempPR = SSWorkbookHelper.getCellValueAsString(destSheet,5,k).trim();
					if (orderFlg == 0) {
						if (tempAB.equals(ab) && (!tempCN.equals(cn)||!tempTP.equals(tp)||!tempLN.equals(ln)||!tempPR.equals(pr))) {
							colorIndex ++;
							//if (insertNum != rowNum+1) break;
						}
					} else {
						if (tempCN.equals(cn) && (!tempAB.equals(ab)||!tempTP.equals(tp)||!tempLN.equals(ln)||!tempPR.equals(pr))) {
							colorIndex ++;
							//if (insertNum != rowNum+1) break;
						}
					}
				}
			}
			if (orderFlg == 0) {
				//order by ab
				if(ab.compareTo(tempAB) < 0) {
					if(insertNum == rowNum+1)
						insertNum = k;
				}
			} else {
				//order by cn
				if(cn.compareTo(tempCN) < 0) {
					if(insertNum == rowNum+1)
						insertNum = k;
				}
			}
			//if (insertNum != rowNum+1) break;
			
		}
		if (count==0) {
			//long ls = System.currentTimeMillis();
			if (insertNum != rowNum+1) {
				destSheet.shiftRows(insertNum, rowNum, 1);
			}
			Row destRow = SSWorkbookHelper.createRow(destSheet, insertNum);
			//SSWorkbookHelper.createStringCell(destRow, 0, toFirstUpCase(en));

			SSWorkbookHelper.createStringCell(destRow, 0, en);
			Cell abCell = SSWorkbookHelper.createStringCell(destRow, 1, ab);
			if (colorIndex > 0 || !(ab.toCharArray()[0] >= 'a' && ab.toCharArray()[0] <= 'z')) {
				CellStyle cellStyle = destWb.createCellStyle();
				cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				abCell.setCellStyle(cellStyle);
			}
			SSWorkbookHelper.createStringCell(destRow, 2, cn);
			SSWorkbookHelper.createStringCell(destRow, 3, tp);
			SSWorkbookHelper.createStringCell(destRow, 4, ln);
			SSWorkbookHelper.createStringCell(destRow, 5, pr);
			SSWorkbookHelper.createStringCell(destRow, 7, destSrc);
			SSWorkbookHelper.createStringCell(destRow, 8, destBe);
			SSWorkbookHelper.createStringCell(destRow, 9, destDt);
			SSWorkbookHelper.createStringCell(destRow, 10, rm);
			SSWorkbookHelper.writeWorkbook(destWb, destFilePath);
			//long le = System.currentTimeMillis();
			//System.out.println(le-ls);
		}
	}

	public static String getCurrDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern("YYYY-MM-dd");
		return sdf.format(new Date(System.currentTimeMillis()));
	}

	public static String toFirstUpCase(String str) {
		char[] cs=str.toCharArray();
		for(int i=0;i<cs.length;i++) {
			if(i==0) {
				if(cs[0]>='a' && cs[0] <= 'z') {
					cs[0]-=32;
				}
			} else {
				if(Character.isLetter(cs[i])) {
					if(Character.isLetter(cs[i-1])) {
						if(cs[i]>='A' && cs[i] <= 'Z') {
							cs[i]+=32;
						}
					} else {
						if(cs[i]>='a' && cs[i] <= 'z') {
							cs[i]-=32;
						}
					}
				}
			}
		}
		return String.valueOf(cs);
	}
	
	public static String[] getArrayFromString(String separatedString,String delim){
		String sWork = separatedString+delim;
		HashMap<String, String> mapSpecialRegex=getSpecialRegexFormat();

		if(mapSpecialRegex.get(delim)!=null){
			delim=mapSpecialRegex.get(delim).toString();
		}
		
		String[] arrWork=sWork.split(delim);
		return arrWork;
	}
	
	private static HashMap<String, String> getSpecialRegexFormat(){
		HashMap<String, String> specialRegexFormat=new HashMap<String, String>();
		specialRegexFormat.put("+","[+]");
		return specialRegexFormat;
	}

}