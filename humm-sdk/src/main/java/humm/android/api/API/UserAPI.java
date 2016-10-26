package humm.android.api.API;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult;

import humm.android.api.Deserializers.PlaylistDeserializer;
import humm.android.api.Deserializers.SongDeserializer;
import humm.android.api.HttpURLConnectionHelper;
import humm.android.api.HummAPI;
import humm.android.api.HummException;
import humm.android.api.HummTask;
import humm.android.api.Model.Artist;
import humm.android.api.Model.HummBasicResult;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.LoginInfo;
import humm.android.api.Model.PlaylistOwnerInt;
import humm.android.api.Model.PlaylistOwnerList;
import humm.android.api.Model.Settings;
import humm.android.api.Model.Song;
import humm.android.api.Model.User;
import humm.android.api.OnActionFinishedListener;

/**
 * Created by josealonsogarcia on 24/11/15.
 */
public class UserAPI extends HummAPI {

    private static UserAPI instance = null;

    protected UserAPI() {
    }

    public static UserAPI getInstance() {
        if (instance == null) {
            instance = new UserAPI();
        }

        return instance;
    }

    private static String RESPONSE_USER_EXITS = "User exists";
    private static String RESPONSE_PASSWORD_CHANGED = "Password changed";

    public void doSignup(final String username, final String password, final String email, final String firstname, final String lastname, final String referal, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<LoginInfo>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return doSignup(username, password, email, firstname, lastname, referal);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }


    public HummSingleResult<LoginInfo> doSignup(String username, String password, String email, String firstname, String lastname, String referal) {

        HummSingleResult<LoginInfo> result = new HummSingleResult<LoginInfo>();
        try {

            Type listType = new TypeToken<HummSingleResult<LoginInfo>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            parameters.put("client_id", clientId);
            parameters.put("username", Uri.encode(username));
            parameters.put("password", password);
            parameters.put("email", email);
            parameters.put("first_name", Uri.encode(firstname));
            parameters.put("last_name", Uri.encode(lastname));
            if (referal != null) {
                parameters.put("referal", referal);

            }

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/users/signup", parameters, true, null, DEBUG);
            result = new Gson().fromJson(reader, listType);

//            return res;
        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");
//            result.put(HttpURLConnectionHelper.DATA, res);

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
//            result.put(HttpURLConnectionHelper.DATA, res);
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
//            result.put(HttpURLConnectionHelper.DATA, res);

        }


        return result;
    }

    public void doLogin(final String username, final String password, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<LoginInfo>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return doLogin(username, password);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    public HummSingleResult<LoginInfo> doLogin(String username, String password) {

        HummSingleResult<LoginInfo> result = new HummSingleResult<LoginInfo>();
        try {

            Type listType = new TypeToken<HummSingleResult<LoginInfo>>() {
            }.getType();


//            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            parameters.put("grant_type", grantType);
            parameters.put("client_id", clientId);
            parameters.put("username", username);
            parameters.put("password", password);

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/token", parameters, true, null, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }


        return result;
    }

    public void doLoginWithService(final String userId, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<LoginInfo>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return doLoginWithService(userId);
            }

            @Override
            public void onComplete(Object object) {

                HummSingleResult<LoginInfo> loginInfo = (HummSingleResult<LoginInfo>) object;
                if (loginInfo == null || loginInfo.getData_response() == null || loginInfo.getData_response().getAccess_token() == null) {
                    listener.actionFinished(null);
                    return;
                }

                listener.actionFinished(loginInfo.getData_response());
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    public HummSingleResult<LoginInfo> doLoginWithService(String userId) {

        HummSingleResult<LoginInfo> result = new HummSingleResult<LoginInfo>();
        try {

            Type listType = new TypeToken<HummSingleResult<LoginInfo>>() {
            }.getType();


            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            parameters.put("grant_type", grantType);
            parameters.put("client_id", clientId);
            parameters.put("userId", userId);
//            parameters.put("password", password);

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/loginWithService", parameters, true, null, DEBUG);
            result = new Gson().fromJson(reader, listType);

            if (result != null && result.getData_response() != null) {

                updateLoginData(result.getData_response());
            }
        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }


        return result;
    }

    public static HummSingleResult<LoginInfo> refreshToken() {

        HummSingleResult<LoginInfo> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<LoginInfo>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            parameters.put("grant_type", grantType);
            parameters.put("client_id", clientId);
            parameters.put("refresh_token", refresh_token);

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/token", parameters, true, null, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }

        return result;
    }

    /**
     * Get the current user; returns a user object
     *
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>User</code> .
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void me(final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return me();
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<User> result = (HummSingleResult<User>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get the current user; returns a user object
     */
    public HummSingleResult<User> me() {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();

            HummAPI.getInstance().updateUserToken();

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/me", null, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }

        return result;
    }

    /**
     * Get a list of songs featured by Humm; returns a list of song objects
     *
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Playlist</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void discoverRealeses(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return discoverRealeses(limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<PlaylistOwnerList> result = (HummMultipleResult<PlaylistOwnerList>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of songs featured by Humm; returns a list of song objects
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<PlaylistOwnerList> discoverRealeses(int limit, int offset) {

        HummMultipleResult<PlaylistOwnerList> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerList>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/me/discover/releases", parameters, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }


        return result;
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Artist</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void discoverArtists(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return discoverArtists(limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<Artist> result = (HummMultipleResult<Artist>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<Artist> discoverArtists(int limit, int offset) {

        HummMultipleResult<Artist> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Artist>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/me/discover/artists", parameters, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }


        return result;
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Playlist</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void discoverPlaylists(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return discoverPlaylists(limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<PlaylistOwnerList> result = (HummMultipleResult<PlaylistOwnerList>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<PlaylistOwnerList> discoverPlaylists(int limit, int offset) {

        HummMultipleResult<PlaylistOwnerList> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerList>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/me/discover/playlists", parameters, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }


        return result;
    }

    /**
     * Add song to current user favourites; returns a song object
     *
     * @param idSong   Unique identifier of song
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a <code>Song</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void addFavourites(final String idSong, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addFavourites(idSong);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Song> result = (HummSingleResult<Song>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Add song to current user favourites; returns a song object
     *
     * @param idSong Unique identifier of song
     */
    public HummSingleResult<Song> addFavourites(String idSong) {

        HummSingleResult<Song> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<Song>>() {
            }.getType();

            HummAPI.getInstance().updateUserToken();

            if (idSong == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/songs/" + idSong + "/favourites", null, true, token, DEBUG);
//            result = new Gson().fromJson(reader, listType);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Song.class, new SongDeserializer()).create();

            result = gson.fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }

        return result;
    }

    /**
     * Get a list of songs user favourites
     *
     * @param idUser   Unique identifier of user
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Song</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getFavourites(final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getFavourites(idUser);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<Song> result = (HummMultipleResult<Song>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of songs user favourites
     *
     * @param idUser Unique identifier of user
     */
    public HummMultipleResult<Song> getFavourites(String idUser) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser + "/favourites", null, token, DEBUG);
//            result = new Gson().fromJson(reader, listType);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Song.class, new SongDeserializer()).create();

            result = gson.fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }

        return result;
    }

    /**
     * Add song to current user favourites; returns a song object
     *
     * @param idUser   Unique identifier of user
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a <code>User</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void addFollows(final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addFollows(idUser);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<User> result = (HummSingleResult<User>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Add song to current user favourites; returns a song object
     *
     * @param idUser Unique identifier of user
     */
    public HummSingleResult<User> addFollows(String idUser) {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/users/" + idUser + "/follows", null, true, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }

        return result;
    }

    /**
     * Remove user from the list current user follows; returns a user object
     *
     * @param idUser   Unique identifier of user
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of hashmaps with uids code.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void removeFollows(final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return removeFollows(idUser);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult result = (HummMultipleResult) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Remove user from the list current user follows; returns a user object
     *
     * @param idUser Unique identifier of user
     */
    public HummMultipleResult removeFollows(String idUser) {

        HummMultipleResult result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.deleteHttpConnection(endpoint + "/users/" + idUser + "/follows", null, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }

        return result;
    }

    /**
     * Get a user; returns a user object for a given id
     *
     * @param idUser   Unique identifier of user
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a <code>User</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void get(final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return get(idUser);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<User> result = (HummSingleResult<User>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a user; returns a user object for a given id
     *
     * @param idUser Unique identifier of user
     */
    public HummSingleResult<User> get(String idUser) {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser, null, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }

    /**
     * Get users subscriptions; returns a user object for a given id
     *
     * @param idUser   Unique identifier of user
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a <code>User</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getSubscriptions(final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getSubscriptions(idUser);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<PlaylistOwnerList> result = (HummMultipleResult<PlaylistOwnerList>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a user; returns a user object for a given id
     *
     * @param idUser Unique identifier of user
     */
    public HummMultipleResult<PlaylistOwnerList> getSubscriptions(String idUser) {

        HummMultipleResult<PlaylistOwnerList> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerList>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser + "/subscriptions", null, token, DEBUG);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(PlaylistOwnerList.class, new PlaylistDeserializer()).create();

            result = gson.fromJson(reader, listType);

//            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }

    /**
     * Get a list of the users a user is following; returns a list of user objects for a given id
     *
     * @param idUser   Unique identifier of user
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>User</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getFollows(final String idUser, final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getFollows(idUser, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<Artist> result = (HummMultipleResult<Artist>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of the users a user is following; returns a list of user objects for a given id
     *
     * @param idUser Unique identifier of user
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<Artist> getFollows(String idUser, int limit, int offset) {

        HummMultipleResult<Artist> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Artist>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser + "/follows", parameters, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }


    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param idUser   Unique identifier of user
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerInt</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getPlaylists(final String idUser, final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerInt>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPlaylists(idUser, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<PlaylistOwnerInt> result = (HummMultipleResult<PlaylistOwnerInt>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param idUser Unique identifier of user
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<PlaylistOwnerInt> getPlaylists(String idUser, int limit, int offset) {

        HummMultipleResult<PlaylistOwnerInt> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerInt>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser + "/playlists", parameters, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }


    /**
     * Put genres to current user
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerInt</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void putGenres(final ArrayList<String> likes, final ArrayList<String> dislikes, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<LoginInfo>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return putGenres(likes, dislikes);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<LoginInfo> result = (HummSingleResult<LoginInfo>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     */
    public HummSingleResult<LoginInfo> putGenres(ArrayList<String> likes, ArrayList<String> dislikes) {

        HummSingleResult<LoginInfo> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<LoginInfo>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (likes == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("likes parameter is mandatory");

                return result;
            }
            if (dislikes == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("dislikes parameter is mandatory");

                return result;
            }

            JSONObject preferences = new JSONObject();

            ArrayList<String> likesEncoded = new ArrayList<>();
            for (String like : likes) {
                likesEncoded.add(Uri.encode(like));
            }
            ArrayList<String> dislikesEncoded = new ArrayList<>();
            for (String dislike : dislikes) {
                dislikesEncoded.add(Uri.encode(dislike));
            }

            preferences.put("like", new JSONArray(likesEncoded));
            preferences.put("dislike", new JSONArray(dislikesEncoded));

            JSONObject parameters = new JSONObject();
            parameters.put("preferences", preferences);

            Reader reader = HttpURLConnectionHelper.patchHttpConnection(endpoint + "/users/me/settings", parameters, false, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }

    /**
     * Put genres to current user
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerInt</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void putLang(final String lang, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<LoginInfo>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return putLang(lang);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<LoginInfo> result = (HummSingleResult<LoginInfo>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     */
    public HummSingleResult<LoginInfo> putLang(String lang) {

        HummSingleResult<LoginInfo> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<LoginInfo>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (lang == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("lang parameter is mandatory");

                return result;
            }

            JSONObject preferences = new JSONObject();


            preferences.put("lang", lang);

            JSONObject parameters = new JSONObject();
            parameters.put("account", preferences);

            Reader reader = HttpURLConnectionHelper.patchHttpConnection(endpoint + "/users/me/settings", parameters, false, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }



    /**
     * Add service to user
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerInt</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void addService(final String serviceName, final String serviceId, final String token, final String serviceUsername, final String secret, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Settings>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addService(serviceName, serviceId, token, serviceUsername, secret);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Settings> result = (HummSingleResult<Settings>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     */
    public HummSingleResult<Settings> addService(String serviceName, String serviceId, String serviceToken, String serviceUsername, String secret) {

        HummSingleResult<Settings> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<Settings>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (serviceName == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("serviceName parameter is mandatory");

                return result;
            }

            if (serviceId == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("serviceId parameter is mandatory");

                return result;
            }
            if (serviceToken == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("serviceToken parameter is mandatory");

                return result;
            }

//            if (secret == null) {
//                result.setStatus_response(HttpURLConnectionHelper.KO);
//                result.setError_response("secret parameter is mandatory");
//
//                return result;
//            }

            JSONObject parameters = new JSONObject();
            parameters.put("service", serviceName);
            parameters.put("sid", serviceId);
            parameters.put("token", serviceToken);
            parameters.put("uname", serviceUsername);
            parameters.put("secret", secret);

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/user/me/settings/services", parameters, true, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }


    /**
     * Add service to user
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerInt</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void removeService(final String serviceName, final String serviceId, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return removeService(serviceName, serviceId);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<User> result = (HummSingleResult<User>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     */
    public HummSingleResult<User> removeService(String serviceName, String serviceId) {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (serviceName == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("serviceName parameter is mandatory");

                return result;
            }

            if (serviceId == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("serviceId parameter is mandatory");

                return result;
            }
            if (token == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("token parameter is mandatory");

                return result;
            }

//            if (secret == null) {
//                result.setStatus_response(HttpURLConnectionHelper.KO);
//                result.setError_response("secret parameter is mandatory");
//
//                return result;
//            }

            JSONObject parameters = new JSONObject();
            parameters.put("service", serviceName);
            parameters.put("sid", serviceId);

            Reader reader = HttpURLConnectionHelper.deleteHttpConnection(endpoint + "/user/me/settings/services", parameters, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }


    /**
     * Add service to user
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerInt</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void checkEmail(final String email, final OnActionFinishedListener listener) {
        new HummTask<HummBasicResult>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return checkEmail(email);
            }

            @Override
            public void onComplete(Object object) {
                HummBasicResult result = (HummBasicResult) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    if (result.getData_response().equalsIgnoreCase(RESPONSE_USER_EXITS)) {
                        listener.actionFinished(true);
                    } else {

                        listener.actionFinished(false);
                    }
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     */
    public HummResult checkEmail(final String email) {

        HummBasicResult result = new HummBasicResult();
        try {

            Type listType = new TypeToken<HummBasicResult>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (email == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("email parameter is mandatory");

                return result;
            }

            JSONObject parameters = new JSONObject();
            parameters.put("email", email);

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/checkemail", parameters, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }


    /**
     * Add service to user
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerInt</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void checkUsername(final String username, final OnActionFinishedListener listener) {
        new HummTask<HummBasicResult>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return checkUsername(username);
            }

            @Override
            public void onComplete(Object object) {
                HummBasicResult result = (HummBasicResult) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    if (result.getData_response().equalsIgnoreCase(RESPONSE_USER_EXITS)) {
                        listener.actionFinished(true);
                    } else {

                        listener.actionFinished(false);
                    }
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     */
    public HummResult checkUsername(final String username) {

        HummBasicResult result = new HummBasicResult();
        try {

            Type listType = new TypeToken<HummBasicResult>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (username == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("username parameter is mandatory");

                return result;
            }

            JSONObject parameters = new JSONObject();
            parameters.put("uname", username);

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/checkusername", parameters, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }

    /**
     * Add service to user
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerInt</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void resetPassword(final String email, final OnActionFinishedListener listener) {
        new HummTask<HummBasicResult>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return resetPassword(email);
            }

            @Override
            public void onComplete(Object object) {
                HummBasicResult result = (HummBasicResult) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    if (result.getData_response().equalsIgnoreCase(RESPONSE_PASSWORD_CHANGED)) {
                        listener.actionFinished(true);
                    } else {

                        listener.actionFinished(false);
                    }
                } else {
//                    listener.onError(new HummException(result.getError_response()));
                    listener.actionFinished(false);
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of recommended playlists / albums; returns a list of playlist / album objects
     *
     * @param likes    Array of Strings with genres that user likes
     * @param dislikes Array of Strings with genres that user likes
     */
    public HummResult resetPassword(final String email) {

        HummAPI.setDEBUG(true);
        HummBasicResult result = new HummBasicResult();
        try {

            Type listType = new TypeToken<HummBasicResult>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();

            if (email == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("email parameter is mandatory");

                return result;
            }

            JSONObject parameters = new JSONObject();
            parameters.put("email", email);

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/users/reset", parameters, false, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);
            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");
        }

        return result;
    }


    /*
    grandtype service
     */


    public void findUser(final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return findUser(idUser);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<User> result = (HummSingleResult<User>) object;

                if (result == null) {
                    listener.actionFinished(null);
                    return;
                }

                if (HttpURLConnectionHelper.OK.equalsIgnoreCase(result.getStatus_response())) {
                    listener.actionFinished(result.getData_response());
                } else {
                    listener.onError(new HummException(result.getError_response()));
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        }).start();
    }

    /**
     * Get a list of songs featured by Humm; returns a list of song objects
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     */
    public HummSingleResult<User> findUser(String idUser) {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();
            HummAPI.getInstance().updateUserToken();


            //FIXME:
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser, null, token, DEBUG);
            result = new Gson().fromJson(reader, listType);

            result.setStatus_response(HttpURLConnectionHelper.OK);


        } catch (IOException ex) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            Log.e("Debug", "error " + ex.getMessage(), ex);
            //android bug with 401

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("Unauthorized");

        } catch (JSONException e) {
            Log.e("Debug", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("error in params");
        } catch (Exception e) {
            Log.e("ERROR", "error " + e.getMessage(), e);

            result.setStatus_response(HttpURLConnectionHelper.KO);
            result.setError_response("sync error");

        }


        return result;
    }

}

//http://api.myhumm.com/v2/settings/5661783496a4f4e521fe3f65/add-service?service=test&sid=test&token=test&uname=test
