package swz.infra.tools.db;

import org.apache.log4j.Logger;

import swz.infra.tools.log.Log;

public class ListView {
  static Logger logger = Log.getLogger(ListView.class.getName());
	
  java.util.HashMap hmFldDesc;  //pair: fieldname-index
  String[] sarrFldName;
  String data[][];
  int curRow = -1; //begin with 0
  MultiPages mp;

  public static ListView getDefault() {
	ListView v = new ListView();
	v.data = new String[0][0];
	v.sarrFldName = new String[0];
	v.hmFldDesc = new java.util.HashMap(0);
	return v;
  }

  public ListView() {
  }

  public int getLen() {
	return data.length;
  }

  public int getFldCount() {
	return hmFldDesc.size();
  }

  /**
   * 
   * @param fldName
   * @return: get column values indicated with fldName in data[][]
   */
  public String[] getFlds(String fldName) {
	int a = ((Integer) (hmFldDesc.get(fldName.toLowerCase()))).intValue();
	String r[] = new String[data.length];
	for (int i = 0; i < data.length; i++) {
	  r[i] = data[i][a];
	}
	return r;
  }

  /**
   * 
   * @param iFld
   * @return :get column values indicated with index iFld in data[][] 
   */
  public String[] getFlds(int iFld) {
	String r[] = new String[data.length];
	for (int i = 0; i < data.length; i++) {
	  r[i] = data[i][iFld];
	}
	return r;
  }
  
  /**
   * 
   * @param fldName
   * @return: get current row's specified spot value indicated with fldName
   */
  public String getFld(String fldName) {
	if (curRow < 0 || curRow >= data.length) {
	  return "";
	}
	else {
	  Integer a = (Integer) (hmFldDesc.get(fldName.toLowerCase()));
	  return data[curRow][a.intValue()];
	}
  }

  /**
   * 
   * @param iFld
   * @return:get current row's specified iFld spot value
   */
  public String getFld(int iFld) {
	if (curRow < 0 || curRow >= data.length) {
	  return "";
	}
	else {
	  return data[curRow][iFld];
	}
  }
  /**
   * 
   * @param iRow
   * @param fldName
   * @return get data[iRow][fldNmae]
   */
  public String getFld(int iRow, String fldName) {
	if (iRow < 0 || iRow >= data.length) {
	  return "";
	}
	else {
	  Integer a = (Integer) (hmFldDesc.get(fldName.toLowerCase()));
	  return data[iRow][a.intValue()];
	}
  }

  /**
   * 
   * @param iRow
   * @param iFld
   * @return : get data[iRow][iFld]
   */
  public String getFld(int iRow, int iFld) {
	if (iRow < 0 || iRow >= data.length) {
	  return "";
	}
	else {
	  return data[iRow][iFld];
	}
  }
 /**
  * 
  * @param iFld
  * @return : set sarrFldName from hmFldDesc
  */
  public String getFldName(int iFld) {
	if (sarrFldName == null) {
	  sarrFldName = new String[this.getFldCount()];
	  java.util.Iterator i = hmFldDesc.keySet().iterator();
	  for (; i.hasNext(); ) {
		Object o = i.next();
		Integer k = new Integer(hmFldDesc.get(o).toString());
		sarrFldName[k.intValue()] = o.toString();
	  }
	}
	return sarrFldName[iFld];
  }
 
  public void setFld(int iFld, String val) {
	if (curRow >= 0 && curRow < data.length) {
	  data[curRow][iFld] = val;
	}
  }

  public void setFld(int iRow, int iFld, String val) {
	if (iRow >= 0 && iRow < data.length) {
	  data[iRow][iFld] = val;
	}
  }

  public void setFld(String name, String val) {
	if (curRow >= 0 && curRow < data.length) {
	  Integer a = (Integer) (hmFldDesc.get(name.toLowerCase()));
	  data[curRow][a.intValue()] = val;
	}
  }

  public void setFld(int iRow, String name, String val) {
	if (iRow >= 0 && iRow < data.length) {
	  Integer a = (Integer) (hmFldDesc.get(name.toLowerCase()));
	  data[iRow][a.intValue()] = val;
	}
  }

  public boolean beforefirst() {
	curRow = -1;
	if (data.length == 0) {
	  return false;
	}
	else {
	  return true;
	}
  }

  public boolean first() {
	if (data.length == 0) {
	  curRow = -1;
	  return false;
	}
	else {
	  curRow = 0;
	  return true;
	}
  }

  public boolean afterlast() {
	if (data.length == 0) {
	  curRow = -1;
	  return false;
	}
	else {
	  curRow = data.length;
	  return true;
	}
  }

  public boolean last() {
	if (data.length == 0) {
	  curRow = -1;
	  return false;
	}
	else {
	  curRow = data.length - 1;
	  return true;
	}
  }

  public boolean next() {
	if (data.length == 0) {
	  curRow = -1;
	  return false;
	}
	else if (data.length > curRow + 1) {
	  curRow++;
	  return true;
	}
	else {
	  curRow++;
	  return false;
	}
  }

  public boolean pre() {
	if (data.length == 0) {
	  curRow = -1;
	  return false;
	}
	else if (curRow > -1) {
	  curRow--;
	  return true;
	}
	else {
	  return false;
	}
  }

  public void setLen(int row, int col) {
	data = new String[row<0 ? 0 : row][col < 0 ? 0 : col];
  }

  public void setDesc(String name, int col) {
	if (hmFldDesc == null) {
	  hmFldDesc = new java.util.HashMap();
	}
	hmFldDesc.put(name, new Integer(col));
  }

  public void setDesc(String[] name) {
	hmFldDesc = new java.util.HashMap();
	for (int i = 0; i < name.length; i++) {
	  hmFldDesc.put(name[i], new Integer(i));
	}
  }

  /**
   * 得行的下标，从0开始记数
   * @return int
   */
  public int getCur() {
	return curRow;
  }

  /**
   * 得序号，从1开始记数
   * @return int
   */
  public int getNo() {
	if (curRow == -1) {
	  return -1;
	}
	return curRow + 1;
  }

  /**
   * 得当前行在各页中所有行中的下标,begin with 0
   * @return int
   */
  public int getCurInAll() {
	if (curRow == -1) {
	  return -1;
	}
	return (this.getCurPage() - 1) * this.getNumPerPage() + curRow;
  }

  /**
   * 得当前行在各页中所有行中的序号,begin with 1
   * @return int
   */
  public int getNoInAll() {
	if (curRow == -1) {
	  return -1;
	}
	return (this.getCurPage() - 1) * this.getNumPerPage() + curRow + 1;
  }

  public boolean Valid() {
	return curRow < getLen() && curRow > -1;
  }

  public boolean isLast() {
	return curRow == getLen() - 1;
  }

  public boolean isFirst() {
	return curRow == 0;
  }

  //the functions below is for split pages,if havn't splited pages,don't use them
  public int getPageNum() { //get page count
	if (mp != null) {
	  return mp.getPageNum();
	}
	else if (getLen() > 0) {
	  return 1;
	}
	else {
	  return 0;
	}
  }

  public int getNumPerPage() { //get num per page
	if (mp != null) {
	  return mp.getNumPerPage();
	}
	else {
	  return getLen();
	}
  }

  public int getRowNum() { //get row count of all pages
	if (mp != null) {
	  return mp.getRowNum();
	}
	else {
	  return getLen();
	}
  }

  public int getCurPage() { //get current page index,from 1
	if (mp != null) {
	  return mp.getCurPage();
	}
	else {
	  return 1;
	}
  }

  public MultiPages getPage() {
	return mp;
  }

  public void setPage(MultiPages mp) {
	this.mp = mp;
  }

  public static ListView add(ListView v1,ListView v2,int col,int iPage,int numpp){

      ListView lv=new ListView();
      int row=v1.getLen()+v2.getLen();
      lv.setLen(row,col);

      MultiPages mp = new MultiPages(row, iPage, numpp);
      lv.setPage(mp);

      for(int i=0;i<col;i++){
          lv.setDesc(String.valueOf(i),i);
      }
      int i=0;
      v1.beforefirst();
      while(v1.next()){
          for(int j=0;j<v1.getFldCount();j++){
              lv.setFld(i,j,v1.getFld(j) );
          }
          i++;
      }
      v2.beforefirst();
      while(v2.next()){
          for(int j=0;j<v2.getFldCount();j++){
              lv.setFld(i,j,v2.getFld(j) );
          }
          i++;
      }
      return lv;
  }
  
  public void print() {
	StringBuffer strBuffer= new StringBuffer();
	for(int i=0;i<data.length;i++) {
		strBuffer = strBuffer.append("row:"+i);
		for(int j=0;j<data[i].length;j++) {
			strBuffer.append("  " + data[i][j]);
		}
		logger.info(strBuffer.toString());
		strBuffer.delete(0, 1000);
	}
  }
}
