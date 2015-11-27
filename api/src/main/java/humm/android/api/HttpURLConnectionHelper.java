package humm.android.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by josealonsogarcia on 31/7/15.
 */
public class HttpURLConnectionHelper {

    public static final String DATA = "data";
    public static final String OK = "ok";
    public static final String KO = "ko";

    public static Reader getHttpConnection(String host, JSONObject params, String auth_token) throws IOException, JSONException {
        HttpURLConnection conn = null;
        Reader reader = null;
        host = host + getParams(params);

        URL url = new URL(host);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(10000);
        conn.setDoInput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");

        if (auth_token != null) {
            conn.setRequestProperty("Authorization", auth_token);
        }


        InputStream in = new BufferedInputStream(conn.getInputStream());
        reader = new InputStreamReader(in);

        return reader;
    }

    private static String getParams(JSONObject params) throws JSONException {

        String allGetParams = "";
        if (params != null) {
            Iterator<?> keys = params.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = "";
                if (params.get(key) instanceof String) {
                    value = (String) params.get(key);
                } else if (params.get(key) instanceof ArrayList) {
                    ArrayList<String> paramsArray = new ArrayList<String>();

                    String subvalue = "";
                    for (String paramArray : paramsArray) {
                        subvalue = subvalue + paramArray + ",";
                    }
                    value = subvalue;

                } else if (params.get(key) instanceof Boolean) {
                    Boolean bool = (Boolean) params.get(key);
                    value = bool ? "true" : "false";
                } else {
                    value = Integer.valueOf((int) params.get(key)).toString();
                }
                String getParam = allGetParams.length() > 1 ? "&" + key + "=" + value : "?" + key + "=" + value;
                allGetParams = allGetParams + getParam;
            }

        }
        return allGetParams;
    }

    public static Reader postHttpConnection(String host, JSONObject params, String auth_token) throws IOException, JSONException {
        HttpURLConnection conn = null;

        host = host + getParams(params);
        URL url = new URL(host);

        conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(5000);
//        conn.setConnectTimeout(10000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty("Content-Type", "application/json");
        if (auth_token != null) {
            conn.setRequestProperty("Authorization", auth_token);
        }

        InputStream in = new BufferedInputStream(conn.getInputStream());
        Reader reader = new InputStreamReader(in);

        return reader;
    }

    public static Reader putHttpConnection(String host, JSONObject params, boolean getParams, String auth_token) throws IOException, JSONException {
        HttpURLConnection conn = null;

        if (getParams) {
            host = host + getParams(params);
        }

        URL url = new URL(host);

        conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(5000);
//        conn.setConnectTimeout(10000);
        conn.setRequestMethod("PUT");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        if (!getParams) {
            conn.setRequestProperty("Content-Type", "application/json");
        }

        if (auth_token != null) {
            conn.setRequestProperty("Authorization", auth_token);
        }

        if (!getParams) {
            String str = params.toString();
            byte[] data = str.getBytes("UTF-8");
            DataOutputStream printout = new DataOutputStream(conn.getOutputStream());
            printout.write(data);
            printout.flush();
            printout.close();
        }

//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty("Content-Type", "application/json");

        InputStream in = new BufferedInputStream(conn.getInputStream());
        Reader reader = new InputStreamReader(in);

        return reader;
    }

    public static Reader deleteHttpConnection(String host, JSONObject params, String auth_token) throws IOException, JSONException {
        HttpURLConnection conn = null;

        host = host + getParams(params);

        Log.d("DEBUG", host);

        URL url = new URL(host);
        conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(5000);
//        conn.setConnectTimeout(10000);
        conn.setRequestMethod("DELETE");
//        conn.setDoInput(true);
        conn.setDoOutput(true);

//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty("Content-Type", "application/json");
        if (auth_token != null) {
            conn.setRequestProperty("Authorization", auth_token);
        }

        InputStream in = new BufferedInputStream(conn.getInputStream());
        Reader reader = new InputStreamReader(in);

        return reader;
    }

}
