package humm.android.api;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

    public static Reader getHttpConnection(String host, JSONObject params, String auth_token, boolean debug) throws IOException, JSONException {
        HttpURLConnection conn = null;
        Reader reader = null;
        host = host + getParams(params);

        if (debug) {
            Log.d("HUMM_API", "GET petition to " + host);
        }
//        Log.d("DEBUG", host);
        URL url = new URL(host);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(10000);
        conn.setDoInput(true);
//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty("Content-Type", "application/json");

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
                    value = Uri.encode(value);
                } else if (params.get(key) instanceof ArrayList) {
                    ArrayList<String> paramsArray = (ArrayList<String>) params.get(key);

                    String subvalue = "";
                    int i = 0;
                    for (String paramArray : paramsArray) {
                        if (i == paramsArray.size() - 1) {
                            subvalue = subvalue + paramArray;
                        } else {
                            subvalue = subvalue + paramArray + "+";
                        }
                        i++;
                    }
                    value = subvalue;

                } else if (params.get(key) instanceof Boolean) {
                    Boolean bool = (Boolean) params.get(key);
                    value = bool ? "true" : "false";
                    value = Uri.encode(value);

                } else {
                    value = Integer.valueOf((int) params.get(key)).toString();
                    value = Uri.encode(value);
                }
//                value = Uri.encode(value);
                String getParam = allGetParams.length() > 1 ? "&" + key + "=" + value : "?" + key + "=" + value;
                allGetParams = allGetParams + getParam;
            }

        }
        return allGetParams;
    }

    public static Reader postHttpConnection(String host, JSONObject params, boolean getParams, String auth_token, boolean debug) throws IOException, JSONException {
        HttpURLConnection conn = null;

        if (getParams) {
            host = host + getParams(params);
            if (auth_token != null) {
                host = host + (params != null && params.length() > 1 ? "&auth=" : "?auth=") + auth_token;
            }
        }

        if (debug) {
            Log.d("HUMM_API", "POST petition to " + host);
        }

        URL url = new URL(host);

        conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(5000);
//        conn.setConnectTimeout(10000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
//        conn.setChunkedStreamingMode(0); // Use default chunk size

//        Log.d("HTTPURLConnection", host);

//        conn.setRequestProperty("Accept", "application/json");
        if (!getParams) {
            conn.setRequestProperty("Content-Type", "application/json");
        }
        if (auth_token != null) {
            conn.setRequestProperty("Authorization", auth_token);
        }

        if (!getParams) {
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(params.toString());
            writer.close();
            out.close();
        }


        InputStream in = new BufferedInputStream(conn.getInputStream());

        Reader reader = new InputStreamReader(in);

        return reader;
    }

    public static Reader putHttpConnection(String host, JSONObject params, boolean getParams, String auth_token, boolean debug) throws IOException, JSONException {
        HttpURLConnection conn = null;

        if (getParams) {
            host = host + getParams(params);
        }

        if (debug) {
            Log.d("HUMM_API", "PUT petition to " + host);
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
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(params.toString());
            writer.close();
            out.close();

        }

        InputStream in = new BufferedInputStream(conn.getInputStream());
        Reader reader = new InputStreamReader(in);

        return reader;
    }

    public static Reader patchHttpConnection(String host, JSONObject params, boolean getParams, String auth_token, boolean debug) throws IOException, JSONException {
        HttpURLConnection conn = null;

        if (getParams) {
            host = host + getParams(params);
            if (auth_token != null) {
                host = host + (params != null && params.length() > 1 ? "&auth=" : "?auth=") + auth_token;
            }

        }

        if (debug) {
            Log.d("HUMM_API", "PATCH petition to " + host);
        }

        URL url = new URL(host);

        conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(5000);
//        conn.setConnectTimeout(10000);
        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        if (!getParams) {
            conn.setRequestProperty("Content-Type", "application/json");
        }

        if (auth_token != null) {
            conn.setRequestProperty("Authorization", auth_token);
        }

        if (!getParams) {
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(params.toString());
            writer.close();
            out.close();

        }

//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty("Content-Type", "application/json");

        InputStream in = new BufferedInputStream(conn.getInputStream());
        Reader reader = new InputStreamReader(in);

        return reader;
    }


    public static Reader deleteHttpConnection(String host, JSONObject params, String auth_token, boolean debug) throws IOException, JSONException {
        HttpURLConnection conn = null;

        host = host + getParams(params);

        if (debug) {
            Log.d("HUMM_API", "DELETE petition to " + host);
        }
        URL url = new URL(host);
        conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(5000);
//        conn.setConnectTimeout(10000);
        conn.setRequestProperty("X-HTTP-Method-Override", "DELETE");
//        conn.setRequestMethod("DELETE");
        conn.setRequestMethod("POST");
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
