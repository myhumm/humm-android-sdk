package humm.android.api.API;

import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import humm.android.api.HttpURLConnectionHelper;
import humm.android.api.HummAPI;
import humm.android.api.HummException;
import humm.android.api.HummTask;
import humm.android.api.Model.Channel;
import humm.android.api.Model.ChatMultipleResult;
import humm.android.api.Model.ChatResult;
import humm.android.api.Model.Message;
import humm.android.api.OnActionFinishedListener;



public class FilesAPI extends HummAPI {

    private static FilesAPI instance = null;
    private static final int BUFFER_SIZE = 4096;


    protected FilesAPI() {
    }

    public static FilesAPI getInstance() {
        if (instance == null) {
            instance = new FilesAPI();
        }

        return instance;
    }


    public void uploadFile(final String channelId, final String file, final OnActionFinishedListener listener) {
        new HummTask<ChatResult<Message>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return uploadFile(channelId, file);
            }

            @Override
            public void onComplete(Object object) {
                ChatResult<Message> result = (ChatResult<Message>) object;


                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus())) {
                    listener.actionFinished(result.getChannel());
                } else {
                    listener.onError(new HummException("Error"));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    public ChatResult<Message> uploadFile(final String channelId, final String file) {

        ChatResult<Message> result = new ChatResult<Message>();
        try {

            Type listType = new TypeToken<ChatResult<Channel>>() {
            }.getType();

            String fileName = file;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(file);




                FileInputStream fileInputStream = new FileInputStream(sourceFile);

                HttpURLConnection conn = null;


                Log.d("HUMM_API", "POST petition to " + endpoint + "/files/upload?cid=" + channelId);


            URL url = new URL(endpoint + "/files/upload?cid=" + channelId);

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(15000);
//        conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection","Keep-Alive");
            conn.setRequestProperty("Cache-Control","no-cache");
            //conn.setRequestProperty("Content-Type", "multipart/form-data");
            conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
            //conn.setRequestProperty("uploaded_file", fileName);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            if (token != null) {
                conn.setRequestProperty("Authorization", token);
            }

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());


            dos.writeBytes(twoHyphens + boundary + lineEnd);



            dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""+ fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


            Integer serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();



            Log.i("uploadFile " + fileName , "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode + " token" + token);

            if (serverResponseCode == 200) {


            }

            // close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();


        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

//            result.setStatus_response(HttpURLConnectionHelper.KO);
//            result.setError_response("sync error");

        }


        return result;
    }



        /**
         * Downloads a file from a URL
         * @param fileURL HTTP URL of the file to be downloaded
         * @param saveDir path of the directory to save the file
         * @throws IOException
         */
        public static void downloadFile(String idRoom, String fileURL, String saveDir)
                throws IOException {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");
                String contentType = httpConn.getContentType();
                int contentLength = httpConn.getContentLength();

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10,
                                disposition.length() - 1);
                    }
                } else {
                    // extracts file name from URL
                    fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                            fileURL.length());
                }
                Random randomGenerator = new Random();

                int randomInt = randomGenerator.nextInt(1000);

                System.out.println("Content-Type = " + contentType);
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("Content-Length = " + contentLength);
                System.out.println("fileName = " + randomInt +"-" + fileName);

                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();




                String saveFilePath = saveDir + File.separator + randomInt + "-" + fileName;

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                System.out.println("File downloaded" + saveFilePath );

                HummAPI.getInstance().getFilesAPI().uploadFile(idRoom, Uri.parse((saveFilePath)).toString(), new OnActionFinishedListener() {
                    @Override
                    public void actionFinished(Object result) {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }
            httpConn.disconnect();
        }


}
