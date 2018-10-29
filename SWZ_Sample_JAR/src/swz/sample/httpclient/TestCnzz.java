package swz.sample.httpclient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.HttpResponse;

public class TestCnzz {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String loginPage = "http://www.cnzz.com/login.php";
		String readPage = "http://new.cnzz.com/v1/main.php?siteid=5135754&s=timeflux&st=2013-02-22&et=2013-03-23&t=30";
		try {
			HttpPost httpost = new HttpPost(loginPage); 
			List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
			//nvps.add(new NameValuePair("origURL", readPage));
			nvps.add(new BasicNameValuePair("login_cnzz_username", "horus_sun@163.com"));
			nvps.add(new BasicNameValuePair("login_cnzz_password", "123456"));
			nvps.add(new BasicNameValuePair("login_cnzz_pro", "0"));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); 
			DefaultHttpClient httpclient = new DefaultHttpClient(); 
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity respEntity = response.getEntity();
			InputStream instream = respEntity.getContent();
			String resp = StringUtils.join(IOUtils.readLines(instream, "UTF-8").toArray());
			System.out.println(resp);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
