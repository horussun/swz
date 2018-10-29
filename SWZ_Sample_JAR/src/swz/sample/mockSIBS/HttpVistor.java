package swz.sample.mockSIBS;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.log4j.Logger;

public class HttpVistor {
	private static final Logger logger = Logger.getLogger(HttpVistor.class.getName());
	
	public static String post(String url, String content, String encoding) {
		String resp = "";
		HttpAsyncClient httpclient = null;
		HttpPost httpPost = null;
		InputStream stream = null ,instream = null;
		try {
			httpclient = new DefaultHttpAsyncClient();
			httpclient.start();
			
			byte[] byteContent = content.getBytes(encoding);
			httpPost = new HttpPost(url);
			stream = new ByteArrayInputStream(byteContent);
			HttpEntity reqtEntity = new InputStreamEntity(stream,byteContent.length);
	        httpPost.setEntity(reqtEntity);
			httpPost.setHeader("Content-Type", "application/xml;charset=UTF-8");
			//httpclient.execute(httpPost, null);
			
			Future<HttpResponse> future = httpclient.execute(httpPost, null);
			HttpResponse httpResponse = future.get();
			//HttpEntity respEntity = httpResponse.getEntity();
			
			//instream = respEntity.getContent();
			//resp = StringUtils.join(IOUtils.readLines(instream,encoding).toArray());
			
		} catch (Exception e) {
			httpPost.abort();
			logger.info(e.getMessage());
			//throw e;
		} finally {
			try {
				httpclient.shutdown();
			} catch (Exception e) {
				//logger.error(e.getMessage(), e);
			}
			try {
				stream.close();
				//instream.close();		
			} catch (Exception e) {
			}
		}
		return resp;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(System.currentTimeMillis());
			String reqt = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><reqt><svcHdr><corrId/><svcId>1010020101</svcId><verNbr>1.0</verNbr><csmrId>179000</csmrId><csmrSerNbr>100010001000</csmrSerNbr><tmStamp>2011.12.29-15.00.00-218</tmStamp><reqtIp>10.1.80.185</reqtIp></svcHdr><appHdr><transSrc>AXS</transSrc><destId>RBK</destId><termId>test1</termId><bnkNbr>01</bnkNbr><brNbr>80201</brNbr><userId>*IBUSER</userId><locRevuSupvId/><locTransSupvId/><hostSupvId/><actCde>I</actCde><transMode>R</transMode><sysRefNbr/><compntRefNbr>000000000</compntRefNbr><srvrReconcNbr/><nbrOfRecToRetrv>010</nbrOfRecToRetrv><moreRecInd>N</moreRecInd><searchMeth>F</searchMeth><searchKeys/><dtInFromClt>20111108</dtInFromClt><tmInFromClt>172054</tmInFromClt></appHdr><appBody><bnkCde>01</bnkCde><basedCurr>RMB</basedCurr><effDt/></appBody></reqt>";
			post("http://127.0.0.1:8082/services/1010020101",reqt,"UTF-8");
			System.out.println(System.currentTimeMillis());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
