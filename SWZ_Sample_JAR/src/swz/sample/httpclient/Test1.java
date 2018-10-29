package swz.sample.httpclient;

import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.protocol.HTTP;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			/*
			HttpClient client = new HttpClient();
			client.getHostConfiguration().setHost("www.cnzz.com", 80);
			PostMethod post = new PostMethod("http://www.cnzz.com/login.php");
			NameValuePair username = new NameValuePair("login_cnzz_username", "horus_sun@163.com");
			NameValuePair password = new NameValuePair("login_cnzz_password", "123456");
			NameValuePair product = new NameValuePair("login_cnzz_pro", "0");
			post.setRequestBody(new NameValuePair[] { username, password,product });
			client.executeMethod(post);
			
			String responseString = new String(post.getResponseBodyAsString().getBytes("gb2312"));
			
			post.releaseConnection();
			System.out.println(responseString);
			*/
			
			String APPLICATION_JSON = "application/json";
			String CONTENT_TYPE_TEXT_JSON = "text/json";
			String json = "{username:horus_sun@163.com,passwd:123456,productType:0,verifyCode:0}";
			String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
			
			//HttpAsyncClient httpclient = new DefaultHttpAsyncClient();
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://www.cnzz.com/login.php");
			httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
			StringEntity se = new StringEntity(encoderJson);
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
	        httpPost.setEntity(se);
	        HttpResponse response = httpClient.execute(httpPost);
	        
	        HttpEntity respEntity = response.getEntity();
			InputStream instream = respEntity.getContent();
			String resp = StringUtils.join(IOUtils.readLines(instream, "UTF-8").toArray());
			System.out.println(resp);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
