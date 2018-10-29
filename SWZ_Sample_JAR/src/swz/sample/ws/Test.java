package swz.sample.ws;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//http://schemas.xmlsoap.org/soap/envelope/
		//http://www.w3.org/2003/05/soap-envelope
		try {
			StringBuilder soap = new StringBuilder();
			String soap1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
						 + "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">"
						 + "<soap:Header/><soap:Body>"
						 + "<urn:ZTEST_WSDL xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\">";
			String soap2 = "<GDF_TEST_IN_FIELD>test</GDF_TEST_IN_FIELD><GDT_TEST_TABLE></GDT_TEST_TABLE>";
			String soap3 = "</urn:ZTEST_WSDL></soap:Body></soap:Envelope>";
			String soapStr = soap.append(soap1).append(soap2).append(soap3).toString();
			
			URL url = new URL(
			        "http://10.1.81.185:8000/sap/bc/srt/rfc/sap/zhrtest_wsdl/400/sap_wsdl_test/zsap_wsdl_test");
			URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
            conn.setRequestProperty("Content-Type", "application/soap+xml;charset=UTF-8;action=\"urn:sap-com:document:sap:rfc:functions:ZHRTEST_WSDL:ZTEST_WSDLRequest\"");
            //conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            //conn.setRequestProperty("SOAPAction","urn:sap-com:document:sap:rfc:functions:ZHRTEST_WSDL:ZTEST_WSDLRequest");
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
            osw.write(soapStr);
            osw.flush();
            osw.close();
            InputStream is = conn.getInputStream();
            
            Document document=null;
            SAXReader reader = new SAXReader();
            document = reader.read(is);
            String s = document.asXML();
            
            System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
