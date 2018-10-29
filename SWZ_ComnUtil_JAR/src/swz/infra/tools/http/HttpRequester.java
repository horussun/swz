package swz.infra.tools.http;

/**
 * @author martin
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import swz.infra.tools.file.FileUtil;


/* HTTP请求对象  
 *   
 * @author YYmmiinngg  
 */
public class HttpRequester {
	// logger
	private static final String sourceClass = HttpRequester.class.getName();
	private static final Logger logger = Logger.getLogger(sourceClass);

//	private static final String DefaultEncodingSet = Charset.defaultCharset().name();
	private static final String DefaultEncodingSet = "UTF-8";
	private String CRLF="\r\n";
	
	/**
	 * send HTTP Get request
	 * 
	 * @param reqtURL
	 *            : requested URL
	 * @param params
	 *            : parameters set
	 * @param propertys
	 *            :request properties
	 * @param msgContent
	 *            :request body content
	 * @return http response
	 * @throws IOException
	 */
	public static String sendGetReqt(String reqtURL,
			Map<String, String> parameters, Map<String, String> propertys,
			String msg, int connectTimeOut, int readTimeOut,String charSet) throws IOException {
		String sourceMethod = "sendMsg";
		logger.debug(sourceMethod + " [Entering]:" + reqtURL);

		HttpURLConnection conn = null;
		URL url = new URL(reqtURL);
		conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);

		conn.setConnectTimeout(connectTimeOut);
		conn.setReadTimeout(readTimeOut);

		if (propertys != null) {
			for (String key : propertys.keySet()) {
				conn.setRequestProperty(key, propertys.get(key));
			}
		}

		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : parameters.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(parameters.get(key));
			i++;
		}
		reqtURL += param;

		HttpResponse resp = makeContent(reqtURL, conn);
		
		logger.debug(sourceMethod + " [Exiting]:" + reqtURL);
		return resp.getContent();
	}

	/**
	 * send HTTP Post request
	 * @param reqtURL: requested URL
	 * @param params: parameters set
	 * @param propertys:request properties
	 * @param msgContent:request body content
	 * @return http response
	 * @throws IOException
	 */
	public static String sendPostReqt(String reqtURL,
			Map<String, String> propertys, String msg, int connectTimeOut,
			int readTimeOut) throws IOException {
		String sourceMethod = "sendPostReqt";
		logger.debug(sourceMethod + " [Entering]:" + reqtURL+","+ connectTimeOut +","+readTimeOut);

//		HttpResponse resp=null;
		HttpURLConnection conn = null;
		URL url = new URL(reqtURL);
		conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);

		conn.setConnectTimeout(connectTimeOut);
		conn.setReadTimeout(readTimeOut);

		if (propertys != null) {
			for (String key : propertys.keySet()) {
				conn.setRequestProperty(key, propertys.get(key));
			}
		}

		OutputStream connOutputStream = conn.getOutputStream();
		if (msg != null) {
			connOutputStream.write(msg.getBytes());
		}
		conn.getOutputStream().flush();
		conn.getOutputStream().close();

		logger.debug(sourceMethod + " [Exiting]:" + reqtURL);
//		resp = makeContent(reqtURL, conn);
		
		String line="";
		InputStream in = conn.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		StringBuffer resp = new StringBuffer();
		line = bufferedReader.readLine();
		while (line != null) {
			resp.append(new String(line.getBytes("UTF-8")));
            System.out.println(resp.toString());
			line = bufferedReader.readLine();
		}
		
		return resp.toString();
	}

	/**
	 * send HTTP request based on specified method, with the body from given file. 
	 * @param reqtURL: requested URL
	 * @param params: parameters set
	 * @param propertys:request properties
	 * @param msgContent:request body content
	 * @return http response
	 * @throws IOException
	 */
	public static String sendHttpReqtFromFile(String reqtURL, String method,
			Map<String, String> parameters, Map<String, String> propertys,
			String fileName, int connectTimeOut, int readTimeOut)
			throws IOException {

		HttpResponse HttpResponse = null;
		String resp = null;
		String msg = FileUtil.getStringFromFile(fileName, "UTF-8");
        if(method.equalsIgnoreCase("POST")){
        	resp = sendPostReqt(reqtURL, propertys, msg, connectTimeOut, readTimeOut);	
        }
        else if (method.equalsIgnoreCase("GET")){
//        	resp = sendGetReqt(reqtURL, parameters,
//    				propertys, msg, connectTimeOut, readTimeOut);
        }
		
		return resp;
	}


	/**
	 * 得到响应对象
	 * 
	 * @param urlConnection
	 * @return 响应对象
	 * @throws IOException
	 */
	private static HttpResponse makeContent(String urlString,
			HttpURLConnection urlConnection) throws IOException {
		HttpResponse httpResponser = new HttpResponse();
		String line = null;
		try {
			if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("connect failed!");
				httpResponser = null;
			} else {
				InputStream in = urlConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
				httpResponser.contentCollection = new Vector<String>();
				StringBuffer temp = new StringBuffer();
				line = bufferedReader.readLine();
//				System.out.println("tmp:"+line);
				while (line != null) {
					httpResponser.contentCollection.add(line);
					temp.append(line).append("\r\n");
					line = bufferedReader.readLine();
				}
				bufferedReader.close();

						
//				httpResponser.urlString = urlString;
//
//				httpResponser.defaultPort = urlConnection.getURL()
//						.getDefaultPort();
//				httpResponser.file = urlConnection.getURL().getFile();
//				httpResponser.host = urlConnection.getURL().getHost();
//				httpResponser.path = urlConnection.getURL().getPath();
//				httpResponser.port = urlConnection.getURL().getPort();
//				httpResponser.protocol = urlConnection.getURL().getProtocol();
//				httpResponser.query = urlConnection.getURL().getQuery();
//				httpResponser.ref = urlConnection.getURL().getRef();
//				httpResponser.userInfo = urlConnection.getURL().getUserInfo();

				httpResponser.content = new String(temp.toString().getBytes(),DefaultEncodingSet);
				httpResponser.contentEncoding = DefaultEncodingSet;
				httpResponser.code = urlConnection.getResponseCode();
				httpResponser.message = urlConnection.getResponseMessage();
				httpResponser.contentType = urlConnection.getContentType();
				httpResponser.method = urlConnection.getRequestMethod();
				httpResponser.connectTimeout = urlConnection.getConnectTimeout();
				httpResponser.readTimeout = urlConnection.getReadTimeout();
			}
			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}
}
