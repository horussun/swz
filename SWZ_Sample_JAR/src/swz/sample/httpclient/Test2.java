package swz.sample.httpclient;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Test2 {

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = "http://www.cnzz.com/login.php?";
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
		String encoderJson = "{username:horus_sun@163.com,passwd:123456,productType:0,verifyCode:0}";
		encoderJson = URLEncoder.encode(encoderJson, HTTP.UTF_8);
		StringEntity se = new StringEntity(encoderJson);
		se.setContentType(CONTENT_TYPE_TEXT_JSON);
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
		httpPost.setEntity(se);
		// 返回服务器响应
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		try {
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine()); // 服务器返回状态
			Header[] headers = response.getAllHeaders(); // 返回的HTTP头信息
			for (int i = 0; i < headers.length; i++) {
				System.out.println(headers[i]);
			}
			System.out.println("----------------------------------------");
			String responseString = null;
			if (response.getEntity() != null) {
				responseString = EntityUtils.toString(response.getEntity()); // 返回服务器响应的HTML代码
				System.out.println(responseString); // 打印出服务器响应的HTML代码
			}
		} finally {
			if (entity != null)
				entity.consumeContent(); // release connection gracefully
		}
		return;
	}

}
