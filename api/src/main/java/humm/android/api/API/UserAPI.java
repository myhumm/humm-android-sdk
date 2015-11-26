package humm.android.api.API;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import humm.android.api.HttpURLConnectionHelper;
import humm.android.api.HummAPI;
import humm.android.api.HummException;
import humm.android.api.Model.Artist;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.LoginInfo;
import humm.android.api.Model.Playlist;
import humm.android.api.Model.Song;
import humm.android.api.Model.User;
import humm.android.api.OnActionFinishedListener;
import humm.android.api.HummTask;

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

    public void doSignup(final String username, final String password, final String email, final String firstname, final String lastname, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<LoginInfo>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return doSignup(username, password, email, firstname, lastname);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }


    public HummSingleResult<LoginInfo> doSignup(String username, String password, String email, String firstname, String lastname) {

        HummSingleResult<LoginInfo> result = new HummSingleResult<LoginInfo>();
        try {

            Type listType = new TypeToken<HummSingleResult<LoginInfo>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            parameters.put("client_id", clientId);
            parameters.put("username", Uri.encode(username));
            parameters.put("password", password);
            parameters.put("email", email);
            parameters.put("first_name", Uri.encode(firstname));
            parameters.put("last_name", Uri.encode(lastname));

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/users/signup", parameters, null);
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

            JSONObject parameters = new JSONObject();
            parameters.put("grant_type", grantType);
            parameters.put("client_id", clientId);
            parameters.put("username", Uri.encode(username));
            parameters.put("password", password);

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/token", parameters, null);
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

    public static HummSingleResult<LoginInfo> refreshToken() {

        HummSingleResult<LoginInfo> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<LoginInfo>>() {
            }.getType();


            JSONObject parameters = new JSONObject();
            parameters.put("grant_type", grantType);
            parameters.put("client_id", clientId);
            parameters.put("refresh_token", refresh_token);

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/token", parameters, null);
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

    public void me(final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return me();
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummSingleResult<User> me() {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/me", null, token);
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

    public void discoverRealeses(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return discoverRealeses(limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummMultipleResult<Playlist> discoverRealeses(int limit, int offset) {

        HummMultipleResult<Playlist> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Playlist>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/me/discover/releases", parameters, token);
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

    public void discoverArtists(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return discoverArtists(limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummMultipleResult<Artist> discoverArtists(int limit, int offset) {

        HummMultipleResult<Artist> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Artist>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/me/discover/artists", parameters, token);
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

    public void discoverPlaylists(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return discoverPlaylists(limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummMultipleResult<Playlist> discoverPlaylists(int limit, int offset) {

        HummMultipleResult<Playlist> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Playlist>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/me/discover/playlists", parameters, token);
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

    public void addFavourites(final String idSong, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addFavourites(idSong);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummSingleResult<Song> addFavourites(String idSong) {

        HummSingleResult<Song> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<Song>>() {
            }.getType();

            if (idSong == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/songs/" + idSong + "/favourites", null, token);
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


    public void addFollowing(final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addFollowing(idUser);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummSingleResult<User> addFollowing(String idUser) {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/users/" + idUser + "/following", null, token);
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

    public void removeFollowing(final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return removeFollowing(idUser);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummSingleResult<User> removeFollowing(String idUser) {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.deleteHttpConnection(endpoint + "/users/" + idUser + "/following", token);
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

    public void addPlays(final String idSong, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addPlays(idSong);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummSingleResult<Song> addPlays(String idSong) {

        HummSingleResult<Song> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<Song>>() {
            }.getType();

            if (idSong == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/users/" + idSong + "/plays", null, token);
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

    //not available for now
    private void search(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return search(limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    //not available for now
    private HummMultipleResult<User> search(int limit, int offset) {

        HummMultipleResult<User> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<User>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/", parameters, token);
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

    //FIXME not sure about result, doesnt documented
    public void addSubscriptions(final String idPlaylist, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addSubscriptions(idPlaylist);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummSingleResult<Playlist> addSubscriptions(String idPlaylist) {

        HummSingleResult<Playlist> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<Playlist>>() {
            }.getType();

            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/users/" + idPlaylist + "/subscriptions", null, token);
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

    //FIXME not sure about result, doesnt documented
    public void removeSubscriptions(final String idPlaylist, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return removeSubscriptions(idPlaylist);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummSingleResult<Playlist> removeSubscriptions(String idPlaylist) {

        HummSingleResult<Playlist> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<Playlist>>() {
            }.getType();

            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.deleteHttpConnection(endpoint + "/users/" + idPlaylist + "/subscriptions", token);
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

    public void get(final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return get(idUser);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummSingleResult<User> get(String idUser) {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();

            if (idUser == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser, null, token);
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

    public void getFavourites(final String idUser, final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getFavourites(idUser, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummMultipleResult<Song> getFavourites(String idUser, int limit, int offset) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();

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


            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser + "/favourites", parameters, token);
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


    public void getFollowing(final String idUser, final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<User>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getFollowing(idUser, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummMultipleResult<User> getFollowing(String idUser, int limit, int offset) {

        HummMultipleResult<User> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<User>>() {
            }.getType();

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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser + "/following", parameters, token);
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

    public void getPlaylists(final String idUser, final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPlaylists(idUser, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummMultipleResult<Playlist> getPlaylists(String idUser, int limit, int offset) {

        HummMultipleResult<Playlist> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Playlist>>() {
            }.getType();

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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser + "/playlists", parameters, token);
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

    public void getPlays(final String idUser, final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPlays(idUser, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                listener.actionFinished(object);
            }

            @Override
            public void onError(Exception e) {
                listener.onError(new HummException(e.getLocalizedMessage()));
            }
        });
    }

    public HummMultipleResult<Song> getPlays(String idUser, int limit, int offset) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();

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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/users/" + idUser + "/plays", parameters, token);
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


}
