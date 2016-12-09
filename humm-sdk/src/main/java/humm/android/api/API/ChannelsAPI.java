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

import humm.android.api.HttpURLConnectionHelper;
import humm.android.api.HummAPI;
import humm.android.api.HummException;
import humm.android.api.HummTask;
import humm.android.api.Model.Channel;
import humm.android.api.Model.ChatMultipleResult;
import humm.android.api.Model.ChatResult;
import humm.android.api.Model.Message;
import humm.android.api.Model.MessageMultipleResult;
import humm.android.api.OnActionFinishedListener;

/**
 * Created by josealonsogarcia on 23/9/16.
 */

public class ChannelsAPI extends HummAPI {

    private static ChannelsAPI instance = null;

    protected ChannelsAPI() {
    }

    public static ChannelsAPI getInstance() {
        if (instance == null) {
            instance = new ChannelsAPI();
        }

        return instance;
    }

    public void getPopularChannels(final OnActionFinishedListener listener) {
        new HummTask<ChatMultipleResult<Channel>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPopularChannels();
            }

            @Override
            public void onComplete(Object object) {
                ChatMultipleResult<Channel> result = (ChatMultipleResult<Channel>) object;


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

    public ChatMultipleResult<Channel> getPopularChannels() {

        ChatMultipleResult<Channel> result = new ChatMultipleResult<Channel>();
        try {

            Type listType = new TypeToken<ChatMultipleResult<Channel>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

//            JSONObject parameters = new JSONObject();
//            parameters.put("grant_type", grantType);
//            parameters.put("client_id", clientId);

//            Log.d("DEBUG", token);
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/channels/popular", null, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

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


    public void joinChannel(final String channelId, final OnActionFinishedListener listener) {
        new HummTask<ChatResult<Channel>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return joinChannel(channelId);
            }

            @Override
            public void onComplete(Object object) {
                ChatResult<Channel> result = (ChatResult<Channel>) object;


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

    public ChatResult<Channel> joinChannel(String channelId) {

        ChatResult<Channel> result = new ChatResult<Channel>();
        try {

            Type listType = new TypeToken<ChatResult<Channel>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
//            parameters.put("grant_type", grantType);
//            parameters.put("client_id", clientId);

            Log.d("DEBUG", token);
            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/channels/" + channelId + "/join", parameters, false, token, true);
            result = new Gson().fromJson(reader, listType);

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

    public void getMessagesHistory(final String channelId, final int limit, final int offset, final String mid, final OnActionFinishedListener listener) {
        new HummTask<ChatResult<Channel>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getMessagesHistory(channelId, limit, offset, mid);
            }

            @Override
            public void onComplete(Object object) {
                ChatResult<Channel> result = (ChatResult<Channel>) object;


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

    public ChatResult<Channel> getMessagesHistory(String channelId, int limit, int offset, String mid) {

        ChatResult<Channel> result = new ChatResult<Channel>();
        try {

            Type listType = new TypeToken<ChatResult<Channel>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            parameters.put("limit", limit);
            parameters.put("offset", offset);
            if (mid != null) {
                parameters.put("mid", mid);
            }
//            parameters.put("client_id", clientId);

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/channels/" + channelId + "/messages", parameters, token, true);

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

    public void getChannel(final String channelId, final OnActionFinishedListener listener) {
        new HummTask<ChatResult<Channel>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getChannel(channelId);
            }

            @Override
            public void onComplete(Object object) {
                ChatResult<Channel> result = (ChatResult<Channel>) object;


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

    public ChatResult<Channel> getChannel(String channelId) {

        ChatResult<Channel> result = new ChatResult<Channel>();
        try {

            Type listType = new TypeToken<ChatResult<Channel>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/channels/" + channelId, null, token, true);

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

    public void leaveChannel(final String channelId, final OnActionFinishedListener listener) {
        new HummTask<ChatResult<Channel>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return leaveChannel(channelId);
            }

            @Override
            public void onComplete(Object object) {
                ChatResult<Channel> result = (ChatResult<Channel>) object;


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

    public ChatResult<Channel> leaveChannel(String channelId) {

        ChatResult<Channel> result = new ChatResult<Channel>();
        try {

            Type listType = new TypeToken<ChatResult<Channel>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/channels/" + channelId + "/leave", null, token, true);

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

    public void createChannel(final String nameChannel, final String descriptionChannel, final boolean isPrivateChannel, final OnActionFinishedListener listener) {
        new HummTask<ChatResult<Channel>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return createChannel(nameChannel, descriptionChannel, isPrivateChannel);
            }

            @Override
            public void onComplete(Object object) {
                ChatResult<Channel> result = (ChatResult<Channel>) object;


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

    public ChatResult<Channel> createChannel(final String nameChannel, final String descriptionChannel, final boolean isPrivateChannel) {

        ChatResult<Channel> result = new ChatResult<Channel>();
        try {

            Type listType = new TypeToken<ChatResult<Channel>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            parameters.put("name", nameChannel);
            parameters.put("description", descriptionChannel);
            parameters.put("private", isPrivateChannel);

//            Log.d("DEBUG", token);
            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/channels/add", parameters, false, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

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


    public void tracksFromChannel(final String channelId, final OnActionFinishedListener listener) {
        new HummTask<MessageMultipleResult<Message>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return tracksFromChannel(channelId);
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

    public MessageMultipleResult<Channel> tracksFromChannel(String channelId) {

        MessageMultipleResult<Channel> result = new MessageMultipleResult<Channel>();
        try {

            Type listType = new TypeToken<MessageMultipleResult<Channel>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/channels/" + channelId + "/tracks", null, token, true);

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


}
