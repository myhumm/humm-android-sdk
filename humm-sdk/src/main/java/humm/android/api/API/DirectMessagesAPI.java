package humm.android.api.API;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;

import humm.android.api.HttpURLConnectionHelper;
import humm.android.api.HummAPI;
import humm.android.api.HummException;
import humm.android.api.HummTask;
import humm.android.api.Model.DMConversationsMultipleResult;
import humm.android.api.Model.Message;
import humm.android.api.Model.MessageMultipleResult;
import humm.android.api.OnActionFinishedListener;

/**
 * Created by josealonsogarcia on 17/10/16.
 */

public class DirectMessagesAPI extends HummAPI {

    private static DirectMessagesAPI instance = null;

    protected DirectMessagesAPI() {
    }

    public static DirectMessagesAPI getInstance() {
        if (instance == null) {
            instance = new DirectMessagesAPI();
        }

        return instance;
    }


    public void getLastDirectMessages(final OnActionFinishedListener listener) {
        new HummTask<DMConversationsMultipleResult<HashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getLastDirectMessages();
            }

            @Override
            public void onComplete(Object object) {
                DMConversationsMultipleResult<HashMap> result = (DMConversationsMultipleResult<HashMap>) object;


                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus())) {
                    listener.actionFinished(result.getMessages());
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

    public DMConversationsMultipleResult<HashMap> getLastDirectMessages() {

        DMConversationsMultipleResult<HashMap> result = new DMConversationsMultipleResult<HashMap>();
        try {

            Type listType = new TypeToken<DMConversationsMultipleResult<HashMap>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

//            JSONObject parameters = new JSONObject();
//            parameters.put("grant_type", grantType);
//            parameters.put("client_id", clientId);

//            Log.d("DEBUG", token);
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/messages/direct/conversations", null, token, DEBUG);

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

            result = gson.fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

//            result.setStatus_response(HttpURLConnectionHelper.KO);
//            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

//            result.setStatus_response(HttpURLConnectionHelper.KO);
//            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

//            result.setStatus_response(HttpURLConnectionHelper.KO);
//            result.setError_response("sync error");

        }


        return result;
    }

    public void getConversation(final String userId, final String username, final String lastMessageId, final OnActionFinishedListener listener) {
        new HummTask<MessageMultipleResult<Message>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getConversation(userId, username, lastMessageId);
            }

            @Override
            public void onComplete(Object object) {
                MessageMultipleResult<Message> result = (MessageMultipleResult<Message>) object;


                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus())) {
                    listener.actionFinished(result.getMessages());
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

    public MessageMultipleResult<Message> getConversation(String userId, String username, String lastMessageId) {

        MessageMultipleResult<Message> result = new MessageMultipleResult<Message>();
        try {

            Type listType = new TypeToken<MessageMultipleResult<Message>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            parameters.put("uid", userId);
            parameters.put("uname", username);
            if (lastMessageId != null) {
                parameters.put("mid", lastMessageId);
            }
//            parameters.put("client_id", clientId);

//            Log.d("DEBUG", "uid = " + userId);
//            Log.d("DEBUG", "mid = " + lastMessageId);

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/messages/direct/conversation", parameters, token, DEBUG);
            result = gson.fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

//            result.setStatus_response(HttpURLConnectionHelper.KO);
//            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

//            result.setStatus_response(HttpURLConnectionHelper.KO);
//            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

//            result.setStatus_response(HttpURLConnectionHelper.KO);
//            result.setError_response("sync error");

        }


        return result;
    }


}
