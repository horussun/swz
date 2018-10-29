/*
 * 1:This class supports below cache types:
 *   Type 1: HashMap<String,String>
 *   Type 2: HashMap<String,HashMap<String,String>>
 *   Type 3: HashMap<String,String[][]>
 *
 * 2:The external configuration come from properties files in the same directory with current class;
 *
 * 3:
*/
package swz.infra.tools.cache;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 */
public class CacheUtil {
	//logger
	private static final String sourceClass = CacheUtil.class.getName();
    private static final Logger logger = Logger.getLogger(sourceClass);
    
    //###############################cache
    private static boolean cacheLoadFlag = false;
    //cache type 1 sample;
    private static HashMap<String,String> hmCache_One2One = null;                  
    //cache type 2 sample;
    private static HashMap<String,HashMap<String,String>> hmCache_one2Multi = null;  
    //cache type 3 sample,one to two dimension array;
    private static HashMap<String,HashMap<String,String>> hmCache_one2DArray = null;  
    
    //The configuration info comes from the specified property file.
    /**
     * @param classNameInSameLoaction: class which in the same location with the properties;
     * @param fileName: file name of the property file;
     */
    
    public static Properties readProperty(Class<CacheUtil> classNameInSameLoaction, String fileName) throws IOException{
		String sourceMethod = "readProperty";
		logger.debug(sourceMethod + " [Entering]: " +classNameInSameLoaction.getName() +","+ fileName );
		
		Properties props = new Properties();
		 
		props.load(classNameInSameLoaction.getResourceAsStream(fileName));

		logger.debug(sourceMethod + " [Existing] ");
		return props;
    }
    
    /**
     * 
     * @param props:    property object
     * @param keyName   key Name in properties file
     * @param valueName value name in properties file
     * @return  HashMap<String,String> with key-value pairs;
     * @throws IOException
     */
    public static HashMap<String,String> loadProp_Str2StrMap(Properties props,String keyName,String valueName) throws IOException{
		String sourceMethod = "loadProp_Str2StrMap";
		HashMap<String,String> hmResult = new HashMap<String,String>();

		logger.debug(sourceMethod + " [Entering] :props.size = " + props.size());
		
		//load all service content
		int index=1;
		String keyValue = props.getProperty(keyName +  index );
		String value  = props.getProperty(valueName +  index );
		while(StringUtils.isNotBlank(keyValue)){
			logger.debug(sourceMethod + " [Executing]: " + keyValue+"-"+value);
			hmResult.put(keyValue, value);
			
			index=index+1;
			keyValue = props.getProperty(keyName +  index );
			value  = props.getProperty(valueName +  index );
		}
		logger.debug(sourceMethod + " [Existing]");
		return hmResult;
	}
	
    /**
     * @func: transf one-one relationship from property file to hashmap string-string pair.     *
     * @param hmValue:: format:  "J-1","K-2","L-3","M-4","N-5","O-6","P-7","Q-8","R-9","}-0"
     * @param Level1Seperator:,
     * @param Level2Seperator:-
     * @return  HashMap<String,String> with key-value pairs;
     * @throws IOException
     */
    public static HashMap<String,String> loadProp_Str2StrMap(String hmValue,String Level1Seperator,String Level2Seperator) throws IOException{
		String sourceMethod = "transfStr2Map";
		HashMap<String,String> hmResult = new HashMap<String,String>();

		logger.debug(sourceMethod + " [Entering]:"+hmValue);
		
		int index=1;
		String keyValue,value;
		String[] pair = StringUtils.split(hmValue, Level1Seperator);
		for(int i=0;i<pair.length;i++){
			keyValue = StringUtils.substringBefore(pair[i], Level2Seperator);
			value=StringUtils.substringAfter(pair[i],Level2Seperator);
			hmResult.put(keyValue, value);
		}
		logger.debug("[Existing]:"+ sourceMethod + hmResult.toString());
		return hmResult;
	}
	
    
	/**
     * Init the substition rule based on static reqt template;
     */
//	public static HashMap<String,String[][]> loadCache_One2DArray_ByProp(Properties props,String keyName,String[] lstValueName,String suffix ) throws IOException{
//		String sourceMethod = "loadCache_One2DArray_ByProp";
//		logger.debug(sourceMethod + " [Entering]:" + props.size());
//		
//		HashMap<String,String[][]> result = new HashMap<String,String[][]>();
//		int keyValueNum = -1;  //1-dimension len of 2D array;
//		String[][] arrayUnit;
//		int rowNum = -1;
//	    int colNum = lstValueName.length;
//		int indexI=1,indexJ=1,indexK=1;     
//
//		//get all keyValue existing number 
//		keyValueNum=calcItemNum(props,keyName+suffix );
//		
//		//populate every keyValue's cacheUnit[][]
//		for(indexI=1;indexI<=keyValueNum;indexI++){
//			String keyValue = props.getProperty(keyName + suffix + indexI );
//			
//			//get 1-dimention len for cacheUnit[][]:
//			rowNum=calcItemNum(props,lstValueName[0] + suffix + indexI + suffix);
//			arrayUnit = new String[rowNum][lstValueName.length];
//			
//			//populate value to cacheUnit[][];
//			for(indexJ=0;indexJ<rowNum;indexJ++){
//				for(indexK=0;indexK<colNum;indexK++){
//					arrayUnit[indexJ][indexK]=props.getProperty(lstValueName[indexK] + suffix + indexI + suffix + (indexJ+1));
//				}
//			}
//			
//			//cache the rule 
//			result.put(keyValue, arrayUnit);
//		}
//		logger.debug(sourceMethod + " [Existing] :hmCacheReqtContentRule.size = " + result.size());
//		return result;
//	}
	
    /**
     * Given props and itemName, return the num of item with format of itemName+"_"+n, with condition of n beginning from 1;
     * @param props
     * @param itemName
     * @return
     */
//    private static int calcItemNum(Properties props,String itemName){
//    	int index=1;
//    	String keyName = props.getProperty(itemName + index );
//		while(StringUtils.isNotBlank(keyName)){
//			index = index + 1;
//			keyName = props.getProperty(itemName + index ); 
//		}
//		
//		return index-1; 
//    }
    
    /**
     * Given props, return hashmap with below format:
     * key =  prop.getProperty(keyName + "_" + n),with n beginning from 1;
     * value= the hashmap with below format;
     *        key = valueNameList[i]+ "_" + n;
     *        value = prop.getProperty(valueNameList[i]+ "_" + n) 
     * @param props
     * @param keyName
     * @param valueNameList
     * @param suffix: 
     * @return 
     */
    public static HashMap<String,HashMap<String,String>> loadCache_One2Multi_ByProp(Properties props,String keyName,String[] valueNameList,String suffix){
    	String sourceMethod = "loadCache_One2Multi_ByProp";
		logger.debug(sourceMethod + " [Entering]:"+ keyName+","+valueNameList+","+suffix);
		
    	HashMap<String,HashMap<String,String>> hmResult = null;
    	HashMap<String,String> hmOneUnit = null;
    	int lenValueNameList=valueNameList.length;
    	String sItemValue = "";
    	int indexI=1,indexJ=1;

    	indexI=1;
    	String keyValue = props.getProperty(keyName + suffix + indexI );  //value of keyName
    	if(StringUtils.isNotBlank(keyValue)) {
    		hmResult = new HashMap<String,HashMap<String,String>>();
    	}
    	else{
    		return null;
    	}
    	
		while(StringUtils.isNotBlank(keyValue)){
			hmOneUnit = new HashMap<String,String> ();
			
			for(indexJ=0;indexJ<lenValueNameList;indexJ++){
				sItemValue =  props.getProperty(valueNameList[indexJ] + suffix + indexI);
				hmOneUnit.put(valueNameList[indexJ], sItemValue);
			}
			hmResult.put(keyValue, hmOneUnit);
			//get next keyValue
			indexI = indexI + 1;
			keyValue = props.getProperty(keyName + suffix + indexI );  //value of keyName
		}
		logger.debug(sourceMethod + " [Exiting]");
		return hmResult; 
    }
    
    /**
     * 
     * @param hmOne2OneCache: the One to One type cache(HashMap<String,String>
     * @param keyValue: key value
     */
//    public static String getCache_One2One(HashMap<String,String> hmOne2OneCache,String keyValue){
//    	String result = null;
//    	
//    	result = hmOne2OneCache.get(keyValue);	
//    	
//    	return result;
//    }
	
    /**
     *     
     * @param props:   property object（属性类）
     * @param attrLst: 
     *              en:properties name list in property files. 
     *                 The first name is the keyname, whose corresponding value in the property file is the key value  of the result hashmap
     *              cn:属性文件中字段名列表
     *                 其中第一个字段对应的值，是返回结果的key值.
     * @param targetClassNameName: the target class name; 
     * @return: HashMap<String,Object>
     *          cn: key值是lstAttrName中第一个字段名对应的属性值。value是targetClassNameName指定类。
     * 
     * @Usage Desc:
     *   example of property file：
			Prop_Type_Cde_3=DemandDeposit
			Prop_Type_Nm_3=活期存款
			Prop_FatherType_Cde_3=Asset
			Prop_Level_3=2
			Acct_TypeCde_3=D|S
			Acct_ProdCde_3=
			
			Prop_Type_Cde_4=FixedDeposit
			Prop_Type_Nm_4=定期存款
			Prop_FatherType_Cde_4=Asset
			Prop_Level_4=2
			Acct_TypeCde_4=T
			Acct_ProdCde_4=<>08
			
	 *   Calling example:
	 *    Init_Prop2Obj(props,new String[]{"Prop_Type_Cde_",
	 *                "Prop_Type_Nm_","Prop_FatherType_Cde","Prop_Level_","Acct_TypeCde_","Acct_ProdCde_"},
	 *                "bqd.soa.bpm.cbs.ucv.Property");
	 *   Return example:
	 *     [("DemandDeposit",property1),("FixedDeposit",property2)] 
	 *                
     * @throws Exception 
     */
     public static HashMap<String,Object> loadProp_Str2ObjectMap(Properties props, String[] lstAttrName, String targetClassNameName) throws Exception {
    	String sourceMethod = "loadProp_One2OneMap";
    	logger.debug("[Entering]:" + sourceMethod );
    		
    	HashMap<String,Object> hmResult = new HashMap<String,Object>();
    	Object[] args = new Object[lstAttrName.length];	  
    	Object objCfg=null;
    		
    	int level1Index=1;
    	String keyValue;
    	String sWork = "";
    	
    	keyValue=props.getProperty(lstAttrName[0] + level1Index );
    	while(StringUtils.isNotBlank(keyValue)){
    		args[0]=keyValue.trim();
    		for (int i=1;i<lstAttrName.length; i++) {         
    			sWork =  props.getProperty(lstAttrName[i] +level1Index );
    			if(StringUtils.isNotBlank(sWork)) {
    				sWork = sWork.trim();
    			}
    			args[i] =sWork;
    		}
    				
    		objCfg = newInstance(targetClassNameName,args);
                    				
    		//get file content
    		hmResult.put(keyValue, objCfg);
    			
    		level1Index=level1Index+1;
    		keyValue=props.getProperty(lstAttrName[0] + level1Index );
    	}
    	logger.debug("[Exiting]:" + sourceMethod + ", size="+hmResult.size());	
    	return hmResult;
    }
     
     /**
      *     
      * @param props
      * @param attrLst: properties name list in property files;
      * @param targetClassNameName: the target class name; 
      * @return
      * @throws Exception 
      * @UsageDesc
      * Propertie example:
      * Svc_Index_1=1
		Svc_Cde_1=1511100105
		Svc_NM_1=存款账户列表查询-CIF
		Reqt_Msg_Tplt_1=
		Svc_Consumer_Rel_1_1=179000
		
		Rule_Verb_1_1=default
		Rule_SourceXpath_1_1_1=reqt.appBody.idNbr
		Rule_TargetXpath_1_1_1=cifNo
		Rule_Default_Value_1_1_1=
		
		Rule_Verb_1_2=default
		Rule_SourceXpath_1_1_2=reqt.appBody.idNbr
		Rule_TargetXpath_1_1_2=cifNo
		Rule_Default_Value_1_1_2=
		
		-----------------------------
		Svc_Consumer_Rel_1_2=180000
		
		Rule_Verb_1_1=default
		Rule_SourceXpath_1_2_1=reqt.appBody.idNbr
		Rule_TargetXpath_1_2_1=cifNo
		Rule_Default_Value_1_2_1=
		------------------------------
		Rule_Verb_1_2=default
		Rule_SourceXpath_1_2_2=reqt.appBody.idNbr
		Rule_TargetXpath_1_2_2=cifNo
		Rule_Default_Value_1_2_2=
	 *   
	 *   Calling example:
	 *    loadProp_One2ListMap(props,"Svc_rule_Consumer_Id_1"
	 *              new String[]{"Svc_rule_Verb_1_","Svc_rule_SourceXpath_1_","Svc_rule_targetXpath_1_","Svc_rule_Value_1_"},
	 *              ,"bqd.soa.bpm.cbs.ucv.ServiceRule");
	 *   Return example:
	 *     [("179000",{rule11,rule12}),("193000",{rule21,rule22})] 			
      */
    public static HashMap<String,List<Object>> loadProp_Str2ObjListMap(
    		  Properties props, String keyName,String[] lstValueName, String targetClassNameName) throws Exception {
    	String sourceMethod = "loadProp_One2ListMap";
    	logger.debug(sourceMethod + "[Entering]:" + sourceMethod );
     		
    	HashMap<String,List<Object>> hmResult = new HashMap<String,List<Object>>();
    	List<Object> lstWork = null;
    	Object[] args = new Object[lstValueName.length];	  
    	Object objCfg=null;
     		
    	int level1Index=1;
    	int level2Index=1;
    	String keyValue;
    	String firstAttrValue;
    		
    	keyValue=props.getProperty(keyName + "_"+level1Index );
    	while(StringUtils.isNotBlank(keyValue)){
    		  //get first attribute's value=>firstAttrValue
    		  firstAttrValue = props.getProperty(lstValueName[0] + level1Index+"_"+level2Index);
    		  lstWork = new ArrayList<Object>();
              while(StringUtils.isNotBlank(firstAttrValue)){
               	    for (int i=0;i<lstValueName.length; i++) {         
               	    	 args[i]=props.getProperty(lstValueName[i] + level1Index+"_"+level2Index);
        		    }
       			    objCfg = newInstance(targetClassNameName,args);
       			    lstWork.add(objCfg);
       			    level2Index=level2Index+1;
       			    firstAttrValue = props.getProperty(lstValueName[0] + level1Index+"_"+level2Index);
              }
              
              level2Index=1;      				
		      hmResult.put(keyValue, lstWork);
    			   
    		  //get next keyvalue
    		  level1Index=level1Index+1;
    	      keyValue=props.getProperty(keyName + "_"+level1Index );
    	}
    	if(hmResult.size()==0) hmResult = null;	
     	return hmResult;
     } 
      
 	/**
 	 * Create object instance with class name and parameters
 	 * */
 	public static Object newInstance(String className, Object[] args) throws Exception {     
 	    Class<?> targetClass = Class.forName(className);                                 
 	    @SuppressWarnings("rawtypes")
		Class[] argsClass = new Class[args.length];                                   
 	    for (int i = 0, j = args.length; i < j; i++) {    
             //default argument as String type;
 	    	if(args[i]==null){
 	    		argsClass[i]=String.class;
 	    	}
 	    	else{
 	    		argsClass[i] = args[i].getClass();	
 	    	}
 	    }	  
 	    Constructor<?> cons = targetClass.getConstructor(argsClass);	    
 	    
 	    return cons.newInstance(args);                                                 
 	}
 	
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			 Properties props = readProperty(CacheUtil.class,"test");
			 hmCache_One2One = loadProp_Str2StrMap(props,"keyName","valueName");		
			 hmCache_one2Multi = loadCache_One2Multi_ByProp(props,"keyName",new String[]{"valueName"},"_");
			 cacheLoadFlag = true;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
