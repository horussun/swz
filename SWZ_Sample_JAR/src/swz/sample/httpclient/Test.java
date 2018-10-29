package swz.sample.httpclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;

public class Test {
	public static void httpTest() {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(connectionManager);
		GetMethod get = new GetMethod("http://new.cnzz.com/v1/main.php?siteid=5052037&s=timeflux&st=2013-02-22&et=2013-03-23&t=30");
		try {
			client.executeMethod(get);
			System.out.println(get.getResponseBodyAsStream());
			//OptionsMethod options = new OptionsMethod("http://jakarta.apache.org");
			//Enumeration allowedMethods = options.getAllowedMethods();
			//options.releaseConnection();
			
			//HeadMethod head = new HeadMethod("http://jakarta.apache.org");
			//Header[] headers = head.getResponseHeaders();
			//String lastModified = head.getResponseHeader("last-modified").getValue();
			
			//
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
	}
	
	public static void main(String[] args) {
		
		/*
		try {
			String svcId = "1511100105";
			String reqt = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><reqt><svcHdr><corrId></corrId><svcId>1511100105</svcId><verNbr>1.0</verNbr><csmrId>179000</csmrId><csmrSerNbr>100010001000</csmrSerNbr><tmStamp>2011.12.29-15.00.00-218</tmStamp><reqtIp>10.1.80.185</reqtIp></svcHdr><appHdr><transSrc>INB</transSrc><destId>RBK</destId><termId>test1</termId><bnkNbr>01</bnkNbr><brNbr>80201</brNbr><userId>*IBUSER</userId><locRevuSupvId></locRevuSupvId><locTransSupvId></locTransSupvId><hostSupvId></hostSupvId><actCde>I</actCde><transMode>R</transMode><sysRefNbr></sysRefNbr><compntRefNbr>000000000</compntRefNbr><srvrReconcNbr></srvrReconcNbr><nbrOfRecToRetrv>010</nbrOfRecToRetrv><moreRecInd>N</moreRecInd><searchMeth>F</searchMeth><searchKeys></searchKeys><dtInFromClt>20111108</dtInFromClt><tmInFromClt>172054</tmInFromClt></appHdr><appBody><cifNo>9100200433</cifNo></appBody></reqt>";
			System.out.println("***invoke start:"+svcId+":"+getDateFormat(new java.util.Date(),"yyyy-MM-dd HH:mm:ss.SSS"));
			String resp = invokSvc(svcId,reqt);
			System.out.println("***invoke end:"+svcId+":"+getDateFormat(new java.util.Date(),"yyyy-MM-dd HH:mm:ss.SSS"));
			System.out.println(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		*/
		try {
			System.out.println(post("http://new.cnzz.com/v1/main.php?siteid=5052037&s=timeflux&st=2013-02-22&et=2013-03-23&t=30","","gb2312"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String invokSvc(String svcId, String reqt) {
		String resp = "";
		String svcURL = "http://10.1.82.15:8082/services/" + svcId;
		String result = "";
		boolean endFlag = false;
		String moreRecord = "N";
		String searchKey = "";
		int begionLoc = -1;
		int maxPageLimit = 5;
		int curPage = 1;

		try {
			begionLoc = reqt.indexOf("<moreRecInd>") - 1;
			while (!endFlag) {
				resp = post(svcURL, reqt, "UTF-8");
				String newValue = "<resp xsi:type=\"S" + svcId + "_Resp\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
				resp = replaceSubString(resp, 0, "encoding=\"UTF-8\"?>", "<svcHdr>", newValue);
				if (result.equalsIgnoreCase("")) {
					result = resp;
				} else {
					result = result + "@@@@####" + resp;
				}
				if (resp.indexOf("<moreRecInd>") != -1) {// sibs's multipage
															// flag.
					moreRecord = StringUtils.substringBetween(resp, "<moreRecInd>", "</moreRecInd>").trim();
					if (moreRecord.equalsIgnoreCase("Y")) {
						searchKey = StringUtils.substringBetween(resp, "<searchKeys>", "</searchKeys>").trim();
						if (curPage == 1) {
							// update reqt
							reqt = replaceSubString(reqt, begionLoc, "<searchKeys>", "</searchKeys>", searchKey);
							reqt = replaceSubString(reqt, begionLoc, "<moreRecInd>", "</moreRecInd>", "Y");
							reqt = replaceSubString(reqt, begionLoc, "<searchMeth>", "</searchMeth>", "F");
							curPage = curPage + 1;
						} else if (curPage > 1 && curPage <= maxPageLimit) {
							reqt = replaceSubString(reqt, begionLoc, "<searchKeys>", "</searchKeys>", searchKey);
							curPage = curPage + 1;
						} else if (curPage > maxPageLimit) {
							endFlag = true;
						}
					} else {
						endFlag = true;
					}
				} else { // resp.indexOf("<moreRecInd>")==-1
					endFlag = true;
				}
			}
		} catch (Exception e) {
		}
		resp = result;
		return resp;
	}
	
	public static String post(String url, String content, String encoding) throws Exception {
		String resp = "";
		HttpAsyncClient httpclient = null;
		HttpPost httpPost = null;
		InputStream stream = null, instream = null;
		try {
			httpclient = new DefaultHttpAsyncClient();
			httpclient.start();

			byte[] byteContent = content.getBytes(encoding);
			httpPost = new HttpPost(url);
			stream = new ByteArrayInputStream(byteContent);
			HttpEntity reqtEntity = new InputStreamEntity(stream, byteContent.length);
			httpPost.setEntity(reqtEntity);
			httpPost.setHeader("Content-Type", "application/xml;charset=UTF-8");

			Future<HttpResponse> future = httpclient.execute(httpPost, null);
			HttpResponse httpResponse = future.get();
			HttpEntity respEntity = httpResponse.getEntity();

			instream = respEntity.getContent();
			resp = StringUtils.join(IOUtils.readLines(instream, encoding).toArray());

		} catch (RuntimeException e) {
			httpPost.abort();
			// logger.error(e.getMessage(), e);
			throw e;
		} catch (IOReactorException e) {
			// logger.error(e.getMessage(), e);
			throw e;
		} catch (UnsupportedEncodingException e) {
			// logger.error(e.getMessage(), e);
			throw e;
		} catch (InterruptedException e) {
			// logger.error(e.getMessage(), e);
			throw e;
		} catch (ExecutionException e) {
			// logger.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {
			// logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				httpclient.shutdown();
			} catch (Exception e) {
				// logger.error(e.getMessage(), e);
			}
			try {
				stream.close();
				instream.close();
			} catch (Exception e) {
			}
		}
		return resp;
	}
	
	public static final String getDateFormat(Date d,String ft){
		SimpleDateFormat sdf = new SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE);
	    sdf.applyPattern(ft);
	    return sdf.format(d);
	  }
	
	public static String replaceSubString(String psResource, int pnBegin,
			String psBegin, String psEnd,String psNewValue) {

//		System.out.println("original content:" + psResource);
		int nBegin, nEnd;
		String result="";
		
		String sResource = psResource.substring(pnBegin);
		nBegin = sResource.indexOf(psBegin, 0);
		nEnd = sResource.indexOf(psEnd, nBegin);

		if (nBegin == -1) {
			nBegin = 0;
		} else if (nEnd == -1) {
			nEnd = sResource.length();
		}
		
		result=psResource.substring(0,pnBegin+nBegin+psBegin.length())+psNewValue+psResource.substring(pnBegin+nEnd);
//		System.out.println("result content:" + psResource);
		return result;
	}
}
