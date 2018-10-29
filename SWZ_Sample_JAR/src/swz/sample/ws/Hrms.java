package swz.sample.ws;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Hrms {

	public static void main(String[] args) {
		System.out.println(Hrms.getResult("str"));
	}
	
	private static String getSoapRequest(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                        + "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">"
                        + "<soap:Header/>"
                        + "<soap:Body>"
                        + "<urn:ZTEST_WSDL xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\">"
                        + "<GDF_TEST_IN_FIELD>test</GDF_TEST_IN_FIELD>"
                        + "<GDT_TEST_TABLE></GDT_TEST_TABLE></urn:ZTEST_WSDL>"
                        + "</soap:Body></soap:Envelope>");
        return sb.toString();
    }
	
	public static InputStream getSoapInputStream(String str) throws Exception {
        try {
            String soap = getSoapRequest(str);
            if (soap == null) {
                return null;
            }
            URL url = new URL(
                    "http://10.1.81.185:8000/sap/bc/srt/rfc/sap/zhrtest_wsdl/400/sap_wsdl_test/zsap_wsdl_test");
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
            conn.setRequestProperty("Content-Type", "application/soap+xml;charset=UTF-8;action=\"urn:sap-com:document:sap:rfc:functions:ZHRTEST_WSDL:ZTEST_WSDLRequest\"");
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
            osw.write(soap);
            osw.flush();
            osw.close();
            InputStream is = conn.getInputStream();
            //System.out.println(is.toString());
            return is;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static String getResult(String str) {
        Document document=null;
        SAXReader reader = new SAXReader();
        String s="";
        //Map map=new HashMap();
        //map.put("design", "http://WebXml.com.cn/");
        //reader.getDocumentFactory().setXPathNamespaceURIs(map);
        try {
            InputStream is = getSoapInputStream(str);//得到输入流
            document=reader.read(is);//将输入流转化为document
            s=document.asXML();
            //System.out.println(t);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //"//*[local-name()='string' and namespace-uri()='http://WebXml.com.cn/']"
        /*
        List nodes = document.selectNodes("//design:string");
        for (Iterator it = nodes.iterator(); it.hasNext();) {
            Element elm = (Element) it.next();
            String text=elm.getText();
            //System.out.println("fsffs"+text);
            s=s+elm.getText()+"\n";
        }
        */
        return s;
    }
}
