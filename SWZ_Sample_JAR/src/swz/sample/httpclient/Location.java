package swz.sample.httpclient;
/*package hua.examples.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;

public class Location {
    public static String LOCATIONS_URL = "http://www.google.com/loc/json";

    public static String getLocations(Context context) {
        // generate json request
        String jr = generateJsonRequest(context);

        try {
            DefaultHttpClient client = new DefaultHttpClient();

            StringEntity entity = new StringEntity(jr);

            HttpPost httpost = new HttpPost(LOCATIONS_URL);
            httpost.setEntity(entity);

            HttpResponse response = client.execute(httpost);

            String locationsJSONString = getStringFromHttp(response.getEntity());
            
            return extractLocationsFromJsonString(locationsJSONString);
        } catch (ClientProtocolException e) {

            //e.printStackTrace();
        } catch (IOException e) {
            
            //e.printStackTrace();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;

    }

    private static String extractLocationsFromJsonString(String jsonString) {
        String country = "";
        String region = "";
        String city = "";
        String street = "";
        String street_number = "";
        double latitude = 0.0;
        double longitude = 0.0;
        
        //"accuracy":901.0
        double accuracy = 0.0;
        
        try {
            JSONObject jo = new JSONObject(jsonString);
            JSONObject location = (JSONObject) jo.get("location");
            latitude = (Double) location.get("latitude");
            longitude = (Double) location.get("longitude");
            accuracy = (Double) location.get("accuracy");
            JSONObject address = (JSONObject) location.get("address");

            country = (String) address.get("country");
            region = (String) address.get("region");
            city = (String) address.get("city");
            street = (String) address.get("street");
            street_number = (String) address.get("street_number");
        } catch (JSONException e) {

            //e.printStackTrace();
        }

        return "(" + latitude + "," + longitude + ")\t" + country + region + city
                + street + street_number + "\t" + "精确度：" + accuracy;
    }

    // 获取所有的网页信息以String 返回
    private static String getStringFromHttp(HttpEntity entity) {

        StringBuffer buffer = new StringBuffer();

        try {
            // 获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    entity.getContent()));

            // 将返回的数据读到buffer中
            String temp = null;

            while ((temp = reader.readLine()) != null) {
                buffer.append(temp);
            }
        } catch (IllegalStateException e) {
            
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return buffer.toString();
    }

    private static String generateJsonRequest(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        List<NeighboringCellInfo> cellList = manager.getNeighboringCellInfo();

        if (cellList.size() == 0)
            return null;

        JSONStringer js = new JSONStringer();
        try {

            js.object();

            js.key("version").value("1.1.0");
            js.key("host").value("maps.google.com");
            js.key("home_mobile_country_code").value(460);
            js.key("home_mobile_network_code").value(0);
            js.key("radio_type").value("gsm");
            js.key("request_address").value(true);
            js.key("address_language").value("zh_CN");

            JSONArray ct = new JSONArray();

            for (NeighboringCellInfo info : cellList) {

                JSONObject c = new JSONObject();
                c.put("cell_id", info.getCid());
                c.put("location_area_code", info.getLac());
                c.put("mobile_country_code", 460);
                c.put("mobile_network_code", 0);
                c.put("signal_strength", info.getRssi()); // 获取邻居小区信号强度

                ct.put(c);
            }
            js.key("cell_towers").value(ct);
            js.endObject();
        } catch (JSONException e) {
            //e.printStackTrace();
            return null;
        }

        return js.toString().replace("true", "True");
    }
}
*/