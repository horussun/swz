package swz.infra.tools.string;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HashMapUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String printHashMap(HashMap dataHash, String sIndent) {
		String result = "\n";
		String delimiter = "��";
		String sWork=null;
		sIndent+=delimiter;
        
		Iterator ketIter = dataHash.keySet().iterator();
		while (ketIter.hasNext()) {
			Object key = ketIter.next();
			if (dataHash.get(key) instanceof HashMap) {
				HashMap subMap = (HashMap) dataHash.get(key);
				result += sIndent+"[SubHashMap_Begin:" + key + "]"+ printHashMap(subMap,sIndent); 
				result += sIndent+"[SubHashMap_End:" + key + "]\n\n";
			} else if (dataHash.get(key) instanceof List) {
				result += sIndent+"[List_Begin:" + key + "]\n";
				List list = (List) dataHash.get(key);
				for (int i = 0; i < list.size(); i++) {
					HashMap subMap = (HashMap) list.get(i);
					result +=sIndent+ key + "[" + i + "]:" + printHashMap(subMap,sIndent);
				}
				result += sIndent+"[List_End:" + key + "]\n\n";
			} else {
				sWork = StringUtil.getStr(dataHash.get(key)); 
				if(!sWork.equalsIgnoreCase("")){
					result += sIndent + key + "=" + sWork +"\n";					
				}
			}
		}
		return result;
	}
}
