package humm.android.api.API;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import humm.android.api.Deserializers.SongDeserializer;
import humm.android.api.HttpURLConnectionHelper;
import humm.android.api.HummAPI;
import humm.android.api.HummException;
import humm.android.api.HummTask;
import humm.android.api.Model.Artist;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.PlaylistOwnerHashMap;
import humm.android.api.Model.PlaylistOwnerList;
import humm.android.api.Model.Song;
import humm.android.api.Model.User;
import humm.android.api.OnActionFinishedListener;

/**
 * Created by josealonsogarcia on 23/11/15.
 */

public class ArtistAPI extends HummAPI {

    public static String SONG_TYPE_COVER = "cover";
    public static String SONG_TYPE_TRACK = "track";
    public static String SONG_TYPE_VERSION = "version";
    public static String SONG_TYPE_VIDEO = "video";
    public static String SONG_TYPE_LIVE = "live";

    private static ArtistAPI instance = null;

    protected ArtistAPI() {
    }

    public static ArtistAPI getInstance() {
        if (instance == null) {
            instance = new ArtistAPI();
        }

        return instance;
    }

    /**
     * Get an artist; returns <code>Artist</code>  for a given id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a <code>Artist</code> for the idArtists.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void get(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return get(idArtist, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Artist> result = (HummSingleResult<Artist>) object;
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();

    }

    public void get(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener, HummTask.TaskMode mode) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return get(idArtist, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Artist> result = (HummSingleResult<Artist>) object;
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start(mode);

    }


    /**
     * Get an artist; returns <code>Artist</code>  for a given id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     */
    public HummSingleResult<Artist> get(String idArtist, int limit, int offset) {


        HummSingleResult<Artist> result = new HummSingleResult<>();
        try {

            if (idArtist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("idArtist parameter is mandatory");

                return result;
            }

            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Type listType = new TypeToken<HummSingleResult<Artist>>() {
            }.getType();
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist, parameters, token, DEBUG);
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
     * Add current user to an artist's list of followers; returns artist object for a given id
     *
     * @param idArtist Unique identifier of artist
     * @param listener called when action is completed or when happens a error. The parameter of <code>onComplete</code> method is
     *                 the <code>Artist</code> of the given id
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void addFollowers(final String idArtist, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addFollowers(idArtist);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Artist> result = (HummSingleResult<Artist>) object;
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();
    }

    /**
     * Add current user to an artist's list of followers; returns artist object for a given id
     *
     * @param idArtist Unique identifier of artist
     */
    public HummSingleResult<Artist> addFollowers(String idArtist) {

        HummSingleResult<Artist> result = new HummSingleResult<>();
        try {

            if (idArtist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("idArtist parameter is mandatory");

                return result;
            }


            HummAPI.getInstance().updateUserToken();


            Type listType = new TypeToken<HummSingleResult<Artist>>() {
            }.getType();
            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/artists/" + idArtist + "/followers", null, true, token, DEBUG);
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

    public HummSingleResult<User> removeFollowers(String idArtist) {

        HummSingleResult<User> result = new HummSingleResult<>();
        try {

            if (idArtist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("idArtist parameter is mandatory");

                return result;
            }

            HummAPI.getInstance().updateUserToken();

            Type listType = new TypeToken<HummSingleResult<User>>() {
            }.getType();
            Reader reader = HttpURLConnectionHelper.deleteHttpConnection(endpoint + "/artists/" + idArtist + "/followers", null, token, DEBUG);
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

    public void removeFollowers(final String idArtist, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return removeFollowers(idArtist);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Artist> result = (HummSingleResult<Artist>) object;
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();
    }

    /**
     * Get playlists / albums associated with an artist; returns a list of playlist / album objects for a given id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     */
    public HummMultipleResult<PlaylistOwnerList> getPlaylists(String idArtist, int limit, int offset) {
        HummMultipleResult<PlaylistOwnerList> result = new HummMultipleResult<>();
        try {

            if (idArtist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("idArtist parameter is mandatory");

                return result;
            }


            HummAPI.getInstance().updateUserToken();

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerList>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist + "/playlists", parameters, token, DEBUG);
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
     * Get playlists / albums associated with an artist; returns a list of playlist / album objects for a given id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerList</code> for the idArtist.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getPlaylists(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPlaylists(idArtist, limit, offset);
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();
    }


    /**
     * Get a curated list of artist songs; returns a list of song objects for a given artist id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     */
    public HummMultipleResult<Song> getRadio(String idArtist, int limit, int offset) {
        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            if (idArtist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("idArtist parameter is mandatory");

                return result;
            }

            HummAPI.getInstance().updateUserToken();

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist + "/radio", parameters, token, DEBUG);
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
     * Get a curated list of artist songs; returns a list of song objects for a given artist id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Song</code> for the idArtist given.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getRadio(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getRadio(idArtist, limit, offset);
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();
    }

    /**
     * Get a list of musically similar artists; returns a list of artist objects for a given id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     */
    public HummMultipleResult<Artist> getSimilar(String idArtist, int limit, int offset) {
        HummMultipleResult<Artist> result = new HummMultipleResult<>();
        try {

            if (idArtist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("idArtist parameter is mandatory");

                return result;
            }

            HummAPI.getInstance().updateUserToken();

            Type listType = new TypeToken<HummMultipleResult<Artist>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist + "/similar", parameters, token, DEBUG);
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
     * Get a list of musically similar artists; returns a list of artist objects for a given id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of similar <code>Artist</code> for the idArtist given.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getSimilar(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getSimilar(idArtist, limit, offset);
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();
    }

    public void getSimilar(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener, HummTask.TaskMode mode) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getSimilar(idArtist, limit, offset);
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start(mode);
    }

    /**
     * Search an artist; returns <code>Artist</code>  for a given keyword
     *
     * @param keyword  keyword to search by
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Artist</code> objects for the given keyword.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void search(final String keyword, final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return doSearch(keyword, limit, offset);
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();

    }

    /**
     * Search an artist; returns <code>Artist</code>  for a given keyword
     *
     * @param keyword keyword to search by
     * @param limit   Number of returned results (20 by default)
     * @param offset  Offset results by said number (0 by default)
     */
    public HummMultipleResult<Artist> doSearch(String keyword, final int limit, final int offset) {


        HummMultipleResult<Artist> result = new HummMultipleResult<>();
        try {

            if (keyword == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("keyword parameter is mandatory");

                return result;
            }


            HummAPI.getInstance().updateUserToken();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }
            if (keyword != null) {
                parameters.put("keyword", keyword);
            }


            Type listType = new TypeToken<HummMultipleResult<Artist>>() {
            }.getType();
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists", parameters, token, DEBUG);
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
     * Get a list of an artist's top songs for a given id; returns a list of song objects for a given artist id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param songType
     * @param listener called when action is completed or when happens a error. The <code>Object</code> parameter of onComplete method is
     *                 a list of top <code>Song</code> for the given id.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getTopSongs(final String idArtist, final int limit, final int offset, final String songType, final boolean live, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getTopSongs(idArtist, limit, offset, songType, live);
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();

    }

    /**
     * Get a list of an artist's top songs for a given id; returns a list of song objects for a given artist id
     *
     * @param idArtist Unique identifier of artist
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param songType
     */
    public HummMultipleResult<Song> getTopSongs(String idArtist, int limit, int offset, String songType, boolean live) {


        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            if (idArtist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("idArtist parameter is mandatory");

                return result;
            }

            HummAPI.getInstance().updateUserToken();

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }
            if (songType != null) {
                parameters.put("songtype", songType);
            }

            parameters.put("live", live);

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist + "/topsongs", parameters, token, DEBUG);
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
     * Get a list of artists featured by Humm; returns a list of artist objects
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     * @param genre  Filter results by genre
     */
    public HummMultipleResult<Artist> getFeatured(int limit, int offset, String genre) {

        HummMultipleResult<Artist> result = new HummMultipleResult<>();
        try {

            HummAPI.getInstance().updateUserToken();

            Type listType = new TypeToken<HummMultipleResult<Artist>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }
            if (genre != null) {
                parameters.put("genre", genre);
            }
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/featured", parameters, token, DEBUG);
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
     * Get a list of artists featured by Humm; returns a list of artist objects
     *
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param genre    Filter results by genre
     * @param listener called when action is completed or when happens a error. The <code>Object</code> parameter of onComplete method is
     *                 a list of  <code>Artist</code>  featured by Humm.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getFeatured(final int limit, final int offset, final String genre, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getFeatured(limit, offset, genre);
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();

    }

    /**
     * Get a list of artists popular on Humm; returns a list of artist objects     *
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<Artist> getPopular(int limit, int offset) {

        HummMultipleResult<Artist> result = new HummMultipleResult<>();
        try {

            HummAPI.getInstance().updateUserToken();

            Type listType = new TypeToken<HummMultipleResult<Artist>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/popular", parameters, token, DEBUG);
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
     * Get a list of artists popular on Humm; returns a list of artist objects     *
     *
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The <code>Object</code> parameter of onComplete method is
     *                 a list of  <code>Artist</code>  popular on Humm.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getPopular(final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPopular(limit, offset);
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();

    }

    /**
     * Get a list of artists recently added on Humm; returns a list of artist objects
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<Artist> getRecent(int limit, int offset) {

        HummMultipleResult<Artist> result = new HummMultipleResult<>();
        try {

            HummAPI.getInstance().updateUserToken();

            Type listType = new TypeToken<HummMultipleResult<Artist>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/recent", parameters, token, DEBUG);
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
     * Get a list of artists recently added on Humm; returns a list of artist objects
     *
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The <code>Object</code> parameter of onComplete method is
     *                 a list of  <code>Artist</code>  recently added on Humm.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getRecent(final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getRecent(limit, offset);
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
                listener.onError(new HummException(e.getMessage()));
            }
        }).start();

    }


}
