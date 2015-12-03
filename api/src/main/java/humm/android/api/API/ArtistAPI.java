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
import humm.android.api.HummTask;
import humm.android.api.Model.Artist;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.Song;
import humm.android.api.Model.User;
import humm.android.api.OnActionFinishedListener;

/**
 * Created by josealonsogarcia on 23/11/15.
 */

//TODO validate mandatory params

public class ArtistAPI extends HummAPI {

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
     * @param listener called when action is completed or when happens a error. The paramenter of onComplete method is
     *                 the <code>Artist</code> for a given id.
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
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist, parameters, token);
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

    public void addFollowers(final String idArtist, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addFollowers(idArtist);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Artist> result = (HummSingleResult<Artist>) object;

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
            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/artists/" + idArtist + "/followers", null, true, token);
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
            Reader reader = HttpURLConnectionHelper.deleteHttpConnection(endpoint + "/artists/" + idArtist + "/followers", null, token);
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

    public HummMultipleResult<Song> getPlaylists(String idArtist, int limit, int offset) {
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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist + "/playlists", parameters, token);
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

    public void getPlaylists(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPlaylists(idArtist, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Song> result = (HummSingleResult<Song>) object;

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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist + "/radio", parameters, token);
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

    public void getRadio(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getRadio(idArtist, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Song> result = (HummSingleResult<Song>) object;

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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist + "/similar", parameters, token);
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

    public void getSimilar(final String idArtist, final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getSimilar(idArtist, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<Artist> result = (HummSingleResult<Artist>) object;

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

    public void search(final String keyword,final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return doSearch(keyword, limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<Artist> result = (HummMultipleResult<Artist>) object;

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
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists", parameters, token);
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

    public void getTopSongs(final String idArtist, final int limit, final int offset, final String songType, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getTopSongs(idArtist, limit, offset, songType);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<Song> result = (HummMultipleResult<Song>) object;

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


    public HummMultipleResult<Song> getTopSongs(String idArtist, int limit, int offset, String songType) {


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
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/" + idArtist + "/topsongs", parameters, token);
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
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/featured", parameters, token);
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

    public void getFeatured( final int limit, final int offset, final String genre, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getFeatured( limit, offset, genre);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<Artist> result = (HummMultipleResult<Artist>) object;

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
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/popular", parameters, token);
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

    public void getPopular(final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPopular(limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<Artist> result = (HummMultipleResult<Artist>) object;

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
            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/artists/recent", parameters, token);
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

    public void getRecent(final int limit, final int offset, final OnActionFinishedListener listener) {

        new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getRecent(limit, offset);
            }

            @Override
            public void onComplete(Object object) {
                HummMultipleResult<Artist> result = (HummMultipleResult<Artist>) object;

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
