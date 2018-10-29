package swz.infra.tools.lang;

import java.util.TimeZone;

public class Constant {

	public static final TimeZone VAL_TIMEZONE_BJ = TimeZone.getTimeZone("Asia/Chongqing");
	// Global date format.
	public static final String VAL_LABEL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String VAL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String VAL_FILE_ENCODING = "UTF-8";

	// Task type of DML creation task.
	public static final String VAL_DML_CRT_TASK_TYPE_XML2TCP = "xml2tcp";
	public static final String VAL_DML_CRT_TASK_TYPE_XML2TCP_IBPS = "xml2tcp_ibps";
	public static final String VAL_DML_CRT_TASK_TYPE_XML2XML = "xml2xml";
	public static final String VAL_DML_CRT_TASK_TYPE_XML2ODBC = "xml2odbc";
	public static final String VAL_DML_CRT_TASK_TYPE_TCP2XML = "tcp2xml";

	// Offset used to calculate the beginning column and beginning row.
	public static final int VAL_OFFSET_COL = 10;
	public static final int VAL_OFFSET_ROW = 1;
	public static final int VAL_OFFSET_DML_TPLT_ROW_BEGIN = 5;

	// The maximum column number and row number that are used to place blank value.
	public static final int VAL_MAX_COL = 5000;
	public static final int VAL_MAX_ROW = 2000;

	// Names of table header of abbreviation.
	public static final String VAL_ABBR_TH_KEY_WORD = "Key Word";
	public static final String VAL_ABBR_TH_ABBR = "Abbreviation";
	public static final String VAL_ABBR_TH_REPEAT_ODR = "Repeat Order";
	public static final String VAL_ABBR_TH_SRC = "Source";
	public static final String VAL_ABBR_TH_LAST_UPD_TIME = "Update Time";
	public static final String VAL_ABBR_TH_CMNTS = "Comments";
	public static final String VAL_ABBR_TH_UPD_FLG = "Update Flag";

	// Name of source for abbreviation.
	public static final String VAL_LABEL_SRC_BQD = "BQD";

	// Flag of new added "dictEntry" in abbreviation table.
	public static final String VAL_LABEL_UPD_FLAG = "New";

	// Value for cells that are not identified by existing "SSWorkbookHelper".
	public static final String VAL_LABEL_NOT_IDENTIFIED = "[Not Identified Cell Type]";

	// Value of "end of line".
	public static final String VAL_EXCEL_CELL_EOL = "--";
	public static final String VAL_EXCEL_CELL_BLANK = "";
	public static final String VAL_EXCEL_CELL_SING_QUO = "'";

	// XPath separator.
	public static final String VAL_DOT_PATH_SEP = ".";
	public static final String VAL_X_PATH_SEP = "/";

	// Xml
	public static final String XML_Element = "Element";
	public static final String XML_ATTRIBUTE = "Attribute";

	// Names of table header of TCP message.
	public static final String VAL_TCP_TH_FIELD_NAME = "Field Name";
	public static final String VAL_TCP_TH_FIELD_DESC = "Field Desc";
	public static final String VAL_TCP_TH_LENGTH = "Length";
	public static final String VAL_TCP_TH_DEC_POS = "DecPos";
	public static final String VAL_TCP_TH_ATTR = "Type";
	public static final String VAL_TCP_TH_POSITION = "Position";
	public static final String VAL_TCP_TH_MANDATE = "Mandate";
	public static final String VAL_TCP_TH_ISPACKED = "IsPacked";
	public static final String VAL_TCP_TH_SAMPLE_VAL = "Sample Value";
	public static final String VAL_TCP_TH_REMARK = "Remark";
	public static final String VAL_TCP_MAND_TRUE = "M";
	public static final String VAL_TCP_MAND_OPT = "O";
	public static final String VAL_TCP_DATA_TYPE_CHAR = "A";
	public static final String VAL_TCP_DATA_TYPE_NUM = "S";
	public static final String VAL_TCP_DATA_TYPE_EXT = "T";

	// Internal data type definition of elements in TCP EXCEL file.
	public static final int _VAL_TCP_DATA_TYPE_CHAR = 0;
	public static final int _VAL_TCP_DATA_TYPE_NUM = 1;

	// Attributes of XSD file.
	public static final String VAL_XSD_KIND_ALL = "all";
	public static final String VAL_XSD_KIND_SEQUENCE = "sequence";
	public static final String VAL_XSD_TYPE_STRING = "string";
	public static final String VAL_XSD_MIN_OCCUR_0 = "0";
	public static final String VAL_XSD_MIN_OCCUR_1 = "1";
	public static final String VAL_XSD_MAX_OCCUR_0 = "0";
	public static final String VAL_XSD_MAX_OCCUR_1 = "1";
	public static final String VAL_XSD_MAX_OCCUR_N = "n";
	public static final String VAL_XSD_UNIT_INDENT = "    ";

	// Code and value definition in database "ESBDB".
	public static final String VAL_TCP_SEG_NAME_SVCHDR = "SvcHdr";
	public static final String VAL_TCP_SEG_NAME_APPHDR = "AppHdr";
	public static final String VAL_TCP_SEG_NAME_CAMPHDR = "CampHdr";
	public static final String VAL_TCP_SEG_NAME_APPBODY = "AppBody";
	public static final String VAL_MSG_ELM_CNT_CD_01 = "150";
	public static final String VAL_MSG_ELM_CNT_NM_01 = "0..1";
	public static final String VAL_MSG_ELM_CNT_CD_11 = "160";
	public static final String VAL_MSG_ELM_CNT_NM_11 = "1..1";
	public static final String VAL_MSG_ELM_CNT_CD_1N = "170";
	public static final String VAL_MSG_ELM_CNT_NM_0N = "0..n";
	public static final String VAL_MSG_ELM_CNT_NM_1N = "1..n";
	public static final String VAL_TCP_ELM_NUM_DFLT = "NULL";
	public static final String VAL_TCP_ELM_BEGIN_POSTN_DFLT = "1";
	public static final String VAL_TCP_ELM_MAND_CD_M = "150";
	public static final String VAL_TCP_ELM_MAND_NM_M = "必填";
	public static final String VAL_TCP_ELM_MAND_CD_O = "160";
	public static final String VAL_TCP_ELM_MAND_NM_O = "可选";
	public static final String VAL_TCP_ELM_TYPE_CD_SEG = "150";
	public static final String VAL_TCP_ELM_TYPE_NM_SEG = "区段";
	public static final String VAL_TCP_ELM_TYPE_CD_FIELD = "160";
	public static final String VAL_TCP_ELM_TYPE_NM_FIELD = "域";
	public static final String VAL_TCP_DATA_TYPE_CD_CHAR = "150";
	public static final String VAL_TCP_DATA_TYPE_NM_CHAR = "字符";
	public static final String VAL_TCP_DATA_TYPE_CD_NUM = "160";
	public static final String VAL_TCP_DATA_TYPE_NM_NUM = "数字";
	public static final String VAL_TCP_PACK_CD_TRUE = "150";
	public static final String VAL_TCP_PACK_NM_TRUE = "压缩";
	public static final String VAL_TCP_PACK_CD_FALSE = "160";
	public static final String VAL_TCP_PACK_NM_FALSE = "非压缩";
	public static final String VAL_TCP_ROW_ID_PREFIX = "RID";
	public static final String VAL_TCP_PACK_FLAG_Y = "Y";
	public static final String VAL_TCP_PACK_FLAG_N = "N";
	public static final String VAL_XML_ELM_TYPE_CD_ELM = "150";
	public static final String VAL_XML_ELM_TYPE_NM_ELM = "元素";
	public static final String VAL_XML_ELM_TYPE_CD_ATTR = "160";
	public static final String VAL_XML_ELM_TYPE_NM_ATTR = "属性";
	public static final String VAL_XML_ELM_MAND_CD_M = "150";
	public static final String VAL_XML_ELM_MAND_NM_M = "必填";
	public static final String VAL_XML_ELM_MAND_CD_O = "160";
	public static final String VAL_XML_ELM_MAND_NM_O = "可选";
	// public static final String VAL_FORMAT_TRANSF_TYPE_CD_XML2FL = "150";
	// public static final String VAL_FORMAT_TRANSF_TYPE_NM_XML2FL = "XML2FL";
	// public static final String VAL_FORMAT_TRANSF_TYPE_CD_XML2XML = "160";
	// public static final String VAL_FORMAT_TRANSF_TYPE_NM_XML2XML = "XML2XML";
	public static final String VAL_TRANSF_RG_SRC_FORMAT_XML_CD = "150";
	public static final String VAL_TRANSF_RG_SRC_FORMAT_XML_NM = "XML";
	public static final String VAL_TRANSF_RG_TARGET_FORMAT_FLTCP_CD = "160";
	public static final String VAL_TRANSF_RG_TARGET_FORMAT_FLTCP_NM = "Fix Length";
	public static final String VAL_TRANSF_FLAG_CD_OPEN = "150";
	public static final String VAL_TRANSF_FLAG_NM_OPEN = "开放";
	public static final String VAL_TRANSF_FLAG_CD_CLOSE = "160";
	public static final String VAL_TRANSF_FLAG_NM_CLOSE = "关闭";
	public static final String VAL_TRANSF_RG_TYPE_CD_CMMN = "140";
	public static final String VAL_TRANSF_RG_TYPE_NM_CMMN = "Cmmn";
	public static final String VAL_TRANSF_RG_TYPE_CD_REQT = "150";
	public static final String VAL_TRANSF_RG_TYPE_NM_REQT = "Reqt";
	public static final String VAL_TRANSF_RG_TYPE_CD_RESP = "160";
	public static final String VAL_TRANSF_RG_TYPE_NM_RESP = "Resp";
	public static final String VAL_TRANSF_RG_TYPE_CD_GOOD_RESP = "170";
	public static final String VAL_TRANSF_RG_TYPE_NM_GOOD_RESP = "Good Resp";
	public static final String VAL_TRANSF_RG_TYPE_CD_BAD_RESP = "180";
	public static final String VAL_TRANSF_RG_TYPE_NM_BAD_RESP = "Bad Resp";
	public static final String VAL_TRANSF_R_TYPE_CD_DFLT_EBCDIC = "150";
	public static final String VAL_TRANSF_R_TYPE_NM_DFLT_EBCDIC = "Default+EBCDIC";
	public static final String VAL_TRANSF_R_TYPE_CD_DFLT = "151";
	public static final String VAL_TRANSF_R_TYPE_NM_DFLT = "Default";
	public static final String VAL_TRANSF_R_TYPE_CD_COPY_EBCDIC = "160";
	public static final String VAL_TRANSF_R_TYPE_NM_COPY_EBCDIC = "Copy+EBCDIC";
	public static final String VAL_TRANSF_R_TYPE_CD_COPY = "161";
	public static final String VAL_TRANSF_R_TYPE_NM_COPY = "Copy";
	public static final String VAL_TRANSF_R_TYPE_CD_COPY_PACK = "162";
	public static final String VAL_TRANSF_R_TYPE_NM_COPY_PACK = "Copy+Pack";
	public static final String VAL_TRANSF_R_TYPE_CD_COPY_PACK_EBCDIC = "163";
	public static final String VAL_TRANSF_R_TYPE_NM_COPY_PACK_EBCDIC = "Copy+Pack+EBCDIC";
	public static final String VAL_TRANSF_R_TYPE_CD_COPY_BCD = "164";
	public static final String VAL_TRANSF_R_TYPE_NM_COPY_BCD = "Copy+BCD";
	public static final String VAL_TRANSF_R_TYPE_CD_OPTN_EBCDIC = "165";
	public static final String VAL_TRANSF_R_TYPE_NM_OPTN_EBCDIC = "Option+EBCDIC";
	public static final String VAL_TRANSF_R_TYPE_CD_OPTN = "166";
	public static final String VAL_TRANSF_R_TYPE_NM_OPTN = "Option";
	public static final String VAL_TRANSF_R_TYPE_CD_MAP = "170";
	public static final String VAL_TRANSF_R_TYPE_NM_MAP = "Map";
	public static final String VAL_TRANSF_R_TYPE_CD_CUST = "180";
	public static final String VAL_TRANSF_R_TYPE_NM_CUST = "Custom";
	// public static final String VAL_TRANSF_RG_ROWID_REQT = "_RID200001,_RID201001,_RID202001,_RID203001,_RID204001";
	// public static final String VAL_TRANSF_RG_ROWID_GOOD_RESP = "_RID200002,_RID201002,_RID202002,_RID203002,_RID204002";
	// public static final String VAL_TRANSF_RG_ROWID_BAD_RESP = "_RID200003,_RID201003,_RID202003,_RID203003,_RID204003";
	public static final String VAL_TRANSF_RG_ROWID_REQT = "_RID200001";
	public static final String VAL_TRANSF_RG_ROWID_GOOD_RESP = "_RID200002";
	public static final String VAL_TRANSF_RG_ROWID_BAD_RESP = "_RID200003";
	public static final String VAL_TRANSF_R_COPY_ACT_NAME_OPTID = "optId";
	public static final String VAL_TRANSF_R_COPY_ACT_NAME_CDE = "Cde";
	// public static final String VAL_TRANSF_R_COPY_ACT_NAME_SOCKET_MSGLEN = "socketMsgLen";
	// public static final String VAL_TRANSF_R_COPY_ACT_NAME_DSPHDR_SRCID = "hdrType";

	// The begin value of column "REL_ORD" used by element relationship in sheet "T_FLTCP_ELM_REL" and "T_XML_ELM_REL".
	public static final int VAL_REL_ORD_BEGIN = 100000;

	// The begin value of column "PARSE_ORD" used by transformation rule in sheet "T_TRANSF_R".
	public static final int VAL_PARSE_ORD_BEGIN = 100000;
	public static final int VAL_PARSE_ORD_PACE_LEN = 50;

	// The default context.
	public static final String VAL_CTX_ID_DFLT = "150100";
	public static final String BQD_SOA2_PROPERTIES_FILE = "BQD_SOA2";
}