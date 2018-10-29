package swz.infra.tools.db;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import swz.infra.tools.log.Log;
import swz.infra.tools.string.StringUtil;


public class DBComOp {
	static Logger logger = Log.getLogger(DBComOp.class.getName());
	/**
	 * string separator
	 */
	public static DBComOp instance = new DBComOp();
	
	public static final String STRSEP = "`";
	public static final String STRSEP2 = "~@";
	public static final String STRSEP3 = "$^!";
	// public CN cn = null;
	public boolean keep = false; // �Ƿ񱣳�����
	public static java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // normal
	public static java.text.SimpleDateFormat sfs = new java.text.SimpleDateFormat("yyyy-MM-dd"); // short
	public static java.text.SimpleDateFormat sfm = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm"); // middle
	public static java.text.SimpleDateFormat sfc = new java.text.SimpleDateFormat("yyyyMMddHHmmss"); // continue
	public static java.text.SimpleDateFormat sfa = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS"); // all
	public static java.text.SimpleDateFormat sfz = new java.text.SimpleDateFormat("yyyyMMdd");
	public int iPage; // cur page,begin from 1
	public int numpp = 0; // row num per page,0 means not to split to pages
	public java.text.SimpleDateFormat fldsf; // the format of date fld,if this's

	// null,then use default method
	// to format

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

    /**
     * 
     * @param sql
     * @param vals
     * @param type
     * @return  
     */
	public ListView getRs(String sql, String vals[], int type[]) {
		Connection conn = null;
		java.sql.PreparedStatement pst = null;
		try {
			conn = DBCon.getConn();
			pst = conn.prepareStatement(sql, java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
					java.sql.ResultSet.CONCUR_READ_ONLY);
			if (vals != null) {
				if (type == null || type.length == 0) {
					for (int i = 0; i < vals.length; i++) {
						setStatementPara(pst, i + 1, java.sql.Types.VARCHAR, vals[i]);
					}
				} else {
					for (int i = 0; i < vals.length; i++) {
						setStatementPara(pst, i + 1, type[i % type.length], vals[i]);
					}
				}
			}
			return getRs(pst);
		} catch (Throwable e) {
			logger.error(sql + ":" + e.getMessage());
		}finally {
			DBCon.closeQuietly(pst);
			DBCon.closeQuietly(conn);
		}
		
		return null;
	}

	   /**
     * 
     * @param sql
     * @param vals
     * @param type
     * @return  return java.sql.ResultSet, 
     *          in the same time,close conn and statement,but remain resultset to be close by the callee
     */
	public ResultSet getRSet(String sql, String vals[], int type[]) {
		Connection conn = null;
		java.sql.PreparedStatement pst = null;
		java.sql.ResultSet rs = null;
		try {
			conn = DBCon.getConn();
			pst = conn.prepareStatement(sql, java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
					java.sql.ResultSet.CONCUR_READ_ONLY);
			if (vals != null) {
				if (type == null || type.length == 0) {
					for (int i = 0; i < vals.length; i++) {
						setStatementPara(pst, i + 1, java.sql.Types.VARCHAR, vals[i]);
					}
				} else {
					for (int i = 0; i < vals.length; i++) {
						setStatementPara(pst, i + 1, type[i % type.length], vals[i]);
					}
				}
			}
			rs = pst.executeQuery();
//			logger.debug("rs.getstatement:"+rs.getStatement().toString());
//			logger.debug("rs.getstatement.getconn:"+rs.getStatement().getConnection().toString());
//			DBComOp.testResultSet(rs, 1);
			return rs;
		} catch (Throwable e) {
			logger.error(sql + ":" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 
	 * @param st
	 * @param iParam
	 * @param type
	 * @param val
	 * @throws Exception
	 * 
	 */
	private void setStatementPara(java.sql.PreparedStatement st, int iParam, int type, String val) throws Exception {
		// iParam: st�е�ǰ��Ҫ���ģ�//type:�ֶε�����//val:�ֶε�ֵ
		// �����ֶ�ֵ
		if (StringUtil.IsNull(val)) {
			st.setNull(iParam, type);
		} else {
			try {
				switch (type) {
				case java.sql.Types.TIMESTAMP:
					java.util.Date d;
					if (val.length() > 10) {
						d = sf.parse(val);
					} else {
						d = sfs.parse(val);
					}
					long l = d.getTime();
					java.sql.Timestamp ts = new java.sql.Timestamp(l);
					st.setTimestamp(iParam, ts);
					break;
				case java.sql.Types.TIME:
					if (val.length() > 10) {
						d = sf.parse(val);
					} else {
						d = sfs.parse(val);
					}
					l = d.getTime();
					java.sql.Time stm = new java.sql.Time(l);
					st.setTime(iParam, stm);
					break;
				case java.sql.Types.DATE:
					if (val.length() > 10) {
						d = sf.parse(val);
					} else {
						d = sfs.parse(val);
					}
					l = d.getTime();
					java.sql.Date sd = new java.sql.Date(l);
					st.setDate(iParam, sd);
					break;
				// ������������,�ɼ����
				case java.sql.Types.VARCHAR:
					st.setString(iParam, val);
				default:
					st.setObject(iParam, val);
					break;
				}
			} catch (Exception e) { // error ,set null
				st.setNull(iParam, type);
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param st
	 * @return  get ResultSet in ListView format based paramized statement.
	 * @throws Throwable
	 */
	private ListView getRs(java.sql.PreparedStatement st) throws Throwable {
		// all getRs funcs invoke this func finally
		java.sql.ResultSet rs = null;
		ListView lvResult = new ListView();
		int i;
		try {
			rs = st.executeQuery();
			if (rs.last()) {
				int rowSize = rs.getRow(); // rs size
				// cur page
				MultiPages mp = new MultiPages(rowSize, iPage, numpp);
				int beginRow = mp.getBegin() + 1; // begin row,index begin from 0 in
				// MultiPage
				int sz = mp.getSize(); // real size of this page
				lvResult.setPage(mp);
			
				// prepare listview
				java.sql.ResultSetMetaData rsMetaData = rs.getMetaData();
				int colSize = rsMetaData.getColumnCount(); // -1;
				lvResult.setLen(sz, colSize);
				for (i = 1; i <= colSize; i++) {
					lvResult.setDesc(rsMetaData.getColumnName(i).toLowerCase(), i - 1);
				}
	
				// get rows
				rs.absolute(beginRow);
				for (i = 0; i < sz; i++, rs.next()) {
					// get columns
					for (int k = 1; k <= colSize; k++) {
						String t = getColumnValue(rs, rsMetaData, k);
						lvResult.setFld(i, k - 1, t);
					}
				}
			} else {
				MultiPages mp = new MultiPages(0, iPage, numpp);
				lvResult.setPage(mp);
				lvResult.setLen(0, 0);
			}
		} catch (Exception e) {
			logger.error(st.toString() + " " + e.getMessage());
			throw e;
		} finally {
			DBCon.closeQuietly(st);
		}
		return lvResult;
	}

	/**
	 * 
	 * @param rs
	 * @param rm
	 * @param i
	 * @return according metadate, get specified column's value
	 * @throws Exception
	 */
	private String getColumnValue(java.sql.ResultSet rs, java.sql.ResultSetMetaData rm, int i) throws Exception {
		String s = null;
		try {
			switch (rm.getColumnType(i)) {
			case java.sql.Types.TIMESTAMP: // 93
				java.sql.Timestamp t = rs.getTimestamp(i);
				if (t != null) {
					if (this.fldsf == null) {
						java.util.Calendar c = java.util.Calendar.getInstance();
						c.setTime(t);
						if (c.get(java.util.Calendar.HOUR_OF_DAY) == 0 && c.get(java.util.Calendar.MINUTE) == 0
								&& c.get(java.util.Calendar.SECOND) == 0) { // �����ͣ�����ʱ��
							return sfs.format(t);
						} else { // ��ʱ�䣬��ȷ����
							return sf.format(t);
						}
					} else {
						return fldsf.format(t);
					}
				}
				break;
			case java.sql.Types.DATE: // 91
				java.sql.Date t2 = rs.getDate(i);
				if (t2 != null) {
					if (this.fldsf == null) {
						return sf.format(t2);
					} else { // ����������,DB2��,�Ƚ��ϸ�
						return fldsf.format(t2);
					}
				}
				break;
			// ������������,�ɼ����
			default:
				s = rs.getString(i);
				break;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (s == null) {
			return "";
		} else {
			return s;
		}
	}
	
	/**
	 * test for resultset's content
	 * @param rs
	 * @param iColumn
	 */
	static public void testResultSet(ResultSet rs,int iColumn){
		try {
			 if(!rs.first()) {
				 logger.info("there is no rows in this result");	 
			 }
			 else {
				 rs.beforeFirst();
				 while(rs.next()) {
			         logger.info(rs.getString(iColumn));    	
			     }	 
				 rs.beforeFirst();  //restore
			 }
		}
		catch(Exception e) {
             logger.error(e.getMessage());       			
		}
	}

}
