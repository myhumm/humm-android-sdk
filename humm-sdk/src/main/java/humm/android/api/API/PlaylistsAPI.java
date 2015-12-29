package humm.android.api.API;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import humm.android.api.HttpURLConnectionHelper;
import humm.android.api.HummAPI;
import humm.android.api.HummException;
import humm.android.api.HummTask;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.Playlist;
import humm.android.api.Model.PlaylistOwnerHashMap;
import humm.android.api.Model.PlaylistOwnerList;
import humm.android.api.Model.Song;
import humm.android.api.OnActionFinishedListener;

/**
 * Created by josealonsogarcia on 26/11/15.
 */
public class PlaylistsAPI extends HummAPI {

    private static PlaylistsAPI instance = null;

    protected PlaylistsAPI() {
    }

    public static PlaylistsAPI getInstance() {
        if (instance == null) {
            instance = new PlaylistsAPI();
        }

        return instance;
    }

    /**
     * Get a playlist; returns playlist object for a given id
     *
     * @param idPlaylist id of the playlist
     * @param listener   called when action is completed or when happens a error. The parameter of onComplete method is
     *                   a <code>Playlist</code>.
     *                   Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void get(final String idPlaylist, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerHashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return get(idPlaylist);
            }

            @Override
            public void onComplete(Object object) {

                HummSingleResult<PlaylistOwnerHashMap> result = (HummSingleResult<PlaylistOwnerHashMap>) object;
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
     * Get a playlist; returns playlist object for a given id
     *
     * @param idPlaylist id of the playlist
     */
    public HummSingleResult<PlaylistOwnerHashMap> get(String idPlaylist) {

        HummSingleResult<PlaylistOwnerHashMap> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<PlaylistOwnerHashMap>>() {
            }.getType();

            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/playlists/" + idPlaylist, null, token);
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
     * Add a playlist for the current user; returns a playlist object
     *
     * @param title       playlist title
     * @param description playlist description
     * @param isPrivate   Private (true) or public (false; default)
     * @param listener    called when action is completed or when happens a error. The parameter of onComplete method is
     *                    a <code>Playlist</code>.
     *                    Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void create(final String title, final String description, final boolean isPrivate, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerHashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return create(title, description, isPrivate);
            }

            @Override
            public void onComplete(Object object) {

                HummSingleResult<PlaylistOwnerHashMap> result = (HummSingleResult<PlaylistOwnerHashMap>) object;
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
     * Add a playlist for the current user; returns a playlist object
     *
     * @param title       playlist title
     * @param description playlist description
     * @param isPrivate   Private (true) or public (false; default)
     */
    public HummSingleResult<PlaylistOwnerHashMap> create(final String title, final String description, final boolean isPrivate) {

        HummSingleResult<PlaylistOwnerHashMap> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<PlaylistOwnerHashMap>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (title == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("title parameter is mandatory");

                return result;
            }

            parameters.put("title", title);
            parameters.put("private", isPrivate);

            if (description != null) {
                parameters.put("description", description);
            }

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/playlists", parameters, true, token);
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
     * Delete a playlist for the current user; returns a playlist object
     *
     * @param idPlaylist playlist id
     * @param listener    called when action is completed or when happens a error. The parameter of onComplete method is
     *                    a <code>Playlist</code>.
     *                    Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void delete(final String idPlaylist, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerHashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return delete(idPlaylist);
            }

            @Override
            public void onComplete(Object object) {

                HummSingleResult<PlaylistOwnerHashMap> result = (HummSingleResult<PlaylistOwnerHashMap>) object;
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
     * Delete the playlist for the current user; returns a playlist object
     *
     * @param idPlaylist playlist id
     */
    public HummSingleResult<PlaylistOwnerHashMap> delete(final String idPlaylist) {

        HummSingleResult<PlaylistOwnerHashMap> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<PlaylistOwnerHashMap>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("idPlaylist parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.deleteHttpConnection(endpoint + "/playlists/" + idPlaylist, parameters, token);
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
     * Get a list of playlists featured by Humm; returns a list of playlist objects
     *
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerHashMap</code> featured by Humm.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getFeatured(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerHashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getFeatured(limit, offset);
            }

            @Override
            public void onComplete(Object object) {

                HummMultipleResult<PlaylistOwnerHashMap> result = (HummMultipleResult<PlaylistOwnerHashMap>) object;
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
     * Get a list of playlists featured by Humm; returns a list of playlist objects
     *
     * @param limit  Number of returned results (20 by default)
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<PlaylistOwnerHashMap> getFeatured(final int limit, final int offset) {

        HummMultipleResult<PlaylistOwnerHashMap> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerHashMap>>() {
            }.getType();

            JSONObject parameters = new JSONObject();
            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/playlists/featured", parameters, token);
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
     * @param idPlaylist  Unique identifier of playlist
     * @param title       Update / confirm playlist title
     * @param description Update / confirm playlist description
     * @param isPrivate   Private (true) or public (false; default)
     * @param listener    called when action is completed or when happens a error. The parameter of onComplete method is
     *                    a list of <code>PlaylistOwnerHashMap</code>.
     *                    Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void update(final String idPlaylist, final String title, final String description, final boolean isPrivate, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerHashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return update(idPlaylist, title, description, isPrivate);
            }

            @Override
            public void onComplete(Object object) {

                HummSingleResult<PlaylistOwnerHashMap> result = (HummSingleResult<PlaylistOwnerHashMap>) object;
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
     * @param idPlaylist  Unique identifier of playlist
     * @param title       Update / confirm playlist title
     * @param description Update / confirm playlist description
     * @param isPrivate   Private (true) or public (false; default)
     */
    public HummSingleResult<PlaylistOwnerHashMap> update(String idPlaylist, String title, String description, boolean isPrivate) {

        HummSingleResult<PlaylistOwnerHashMap> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<PlaylistOwnerHashMap>>() {
            }.getType();


            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            if (title == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("title parameter is mandatory");

                return result;
            }
            if (description == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("description parameter is mandatory");

                return result;
            }

            JSONObject parameters = new JSONObject();
            parameters.put("title", title);
            parameters.put("description", description);
            parameters.put("private", isPrivate);

            Reader reader = HttpURLConnectionHelper.patchHttpConnection(endpoint + "/playlists/" + idPlaylist, parameters, true, token);
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
     * Reorder the songs in a playlist; returns a song list for the given playlist
     *
     * @param idPlaylist Unique identifier of playlist
     * @param order      JSON with the order of songs in the body
     * @param listener   called when action is completed or when happens a error. The parameter of onComplete method is
     *                   a list of <code>Song</code> of the playlist
     *                   Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void order(final String idPlaylist, final List<HashMap<String, String>> order, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return order(idPlaylist, order);
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
     * Reorder the songs in a playlist; returns a song list for the given playlist
     *
     * @param idPlaylist Unique identifier of playlist
     * @param order      JSON with the order of songs in the body
     */
    public HummMultipleResult<Song> order(final String idPlaylist, final List<HashMap<String, String>> order) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();


            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            if (order == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("songs parameter is mandatory");

                return result;
            }

            JSONObject parameters = new JSONObject();
            parameters.put("songs", order);

            Reader reader = HttpURLConnectionHelper.patchHttpConnection(endpoint + "/playlists/" + idPlaylist + "/reorder", parameters, false, token);
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
     * Get a list of playlist songs; return a list of song objects for a given id
     *
     * @param idPlaylist Unique identifier of playlist
     * @param limit      Number of returned results (20 by default)
     * @param offset     Offset results by said number (0 by default)
     * @param listener   called when action is completed or when happens a error. The parameter of onComplete method is
     *                   a list of <code>Song</code> .
     *                   Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getSongs(final String idPlaylist, final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getSongs(idPlaylist, limit, offset);
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
     * Get a list of playlist songs; return a list of song objects for a given id
     *
     * @param idPlaylist Unique identifier of playlist
     * @param limit      Number of returned results (20 by default)
     * @param offset     Offset results by said number (0 by default)
     */
    public HummMultipleResult<Song> getSongs(final String idPlaylist, final int limit, final int offset) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();


            if (idPlaylist == null) {
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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/playlists/" + idPlaylist + "/songs", parameters, token);
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
     * Add a song to a playlist given their ids
     *
     * @param idPlaylist Unique identifier of playlist
     * @param idSong     Unique identifier of song
     * @param position   Playlist index position; where song is to be inserted
     * @param listener   called when action is completed or when happens a error. The parameter of onComplete method is
     *                   a  <code>PlaylistOwnerHashMap</code> .
     *                   Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void addSong(final String idPlaylist, final String idSong, final int position, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerHashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addSong(idPlaylist, idSong, position);
            }

            @Override
            public void onComplete(Object object) {

                HummSingleResult<PlaylistOwnerHashMap> result = (HummSingleResult<PlaylistOwnerHashMap>) object;
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
     * Add a song to a playlist given their ids
     *
     * @param idPlaylist Unique identifier of playlist
     * @param idSong     Unique identifier of song
     * @param position   Playlist index position; where song is to be inserted
     */
    public HummSingleResult<PlaylistOwnerHashMap> addSong(final String idPlaylist, final String idSong, final int position) {

        HummSingleResult<PlaylistOwnerHashMap> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<PlaylistOwnerHashMap>>() {
            }.getType();


            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            if (idSong == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id song parameter is mandatory");

                return result;
            }

            if (position < 0) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("position parameter is mandatory");

                return result;
            }


            JSONObject parameters = new JSONObject();
            parameters.put("sid", idSong);
            parameters.put("position", position);

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/playlists/" + idPlaylist + "/songs", parameters, true, token);
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
     * Remove a song from a playlist for a given id
     *
     * @param idPlaylist Unique identifier of playlist
     * @param idSong     Unique identifier of song
     * @param listener   called when action is completed or when happens a error. The parameter of onComplete method is
     *                   a  <code>PlaylistOwnerHashMap</code> .
     *                   Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void deleteSong(final String idPlaylist, final String idSong, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerHashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return deleteSong(idPlaylist, idSong);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<PlaylistOwnerHashMap> result = (HummSingleResult<PlaylistOwnerHashMap>) object;
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
     * Remove a song from a playlist for a given id
     *
     * @param idPlaylist Unique identifier of playlist
     * @param idSong     Unique identifier of song
     */
    public HummSingleResult<PlaylistOwnerHashMap> deleteSong(final String idPlaylist, final String idSong) {

        HummSingleResult<PlaylistOwnerHashMap> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<PlaylistOwnerHashMap>>() {
            }.getType();


            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            if (idSong == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id song parameter is mandatory");

                return result;
            }

            JSONObject parameters = new JSONObject();
            parameters.put("sid", idSong);

            Reader reader = HttpURLConnectionHelper.deleteHttpConnection(endpoint + "/playlists/" + idPlaylist + "/songs", parameters, token);
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
     * Add playlist to the list current user has subscriptions to for a given id     *
     *
     * @param idPlaylist Unique identifier of playlist
     * @param listener   called when action is completed or when happens a error. The parameter of onComplete method is
     *                   a  <code>PlaylistOwnerList</code>
     *                   Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void addSubscriber(final String idPlaylist, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addSubscriber(idPlaylist);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<PlaylistOwnerList> result = (HummSingleResult<PlaylistOwnerList>) object;
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
     * Add playlist to the list current user has subscriptions to for a given id     *
     *
     * @param idPlaylist Unique identifier of playlist
     */
    public HummSingleResult<PlaylistOwnerList> addSubscriber(final String idPlaylist) {

        HummSingleResult<PlaylistOwnerList> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<PlaylistOwnerList>>() {
            }.getType();


            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/playlists/" + idPlaylist + "/subscribers", null, true, token);
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
     * Remove a song from a playlist for a given id
     *
     * @param idPlaylist Unique identifier of playlist
     * @param listener   called when action is completed or when happens a error. The parameter of onComplete method is
     *                   a  <code>PlaylistOwnerList</code>
     *                   Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void removeSubscriber(final String idPlaylist, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return removeSubscriber(idPlaylist);
            }

            @Override
            public void onComplete(Object object) {
                HummSingleResult<PlaylistOwnerList> result = (HummSingleResult<PlaylistOwnerList>) object;
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
     * Remove a song from a playlist for a given id
     *
     * @param idPlaylist Unique identifier of playlist
     */
    public HummSingleResult<PlaylistOwnerList> removeSubscriber(final String idPlaylist) {

        HummSingleResult<PlaylistOwnerList> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<PlaylistOwnerList>>() {
            }.getType();


            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.deleteHttpConnection(endpoint + "/playlists/" + idPlaylist + "/subscribers", null, token);
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
     * Get a list of playlists popular on Humm; returns a list of playlist objects (for an optional selection or user id)
     *
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param section  Filter by "featured" (popular featured playlists), "own" (popular playlists for given user), "following" (popular playlists within given user subscriptions)
     * @param idUser   Unique identifier of user
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a  list of <code>PlaylistOwnerList</code> .
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getPopular(final int limit, final int offset, final int section, final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPopular(limit, offset, section, idUser);
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
     * Get a list of playlists popular on Humm; returns a list of playlist objects (for an optional selection or user id)
     *
     * @param limit   Number of returned results (20 by default)
     * @param offset  Offset results by said number (0 by default)
     * @param section Filter by "featured" (popular featured playlists), "own" (popular playlists for given user), "following" (popular playlists within given user subscriptions)
     * @param idUser  Unique identifier of user
     */
    public HummMultipleResult<PlaylistOwnerList> getPopular(final int limit, final int offset, final int section, final String idUser) {

        HummMultipleResult<PlaylistOwnerList> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerList>>() {
            }.getType();

            JSONObject parameters = new JSONObject();

            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }
            if (section > 0) {
                parameters.put("section", section);
            }

            if (idUser != null) {
                parameters.put("uid", idUser);
            }


            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/playlists/popular", parameters, token);
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
     * Get a list of playlists recently added on Humm; returns a list of playlist objects
     *
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a  list of <code>PlaylistOwnerList</code> .
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getRecent(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getRecent(limit, offset);
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
     * Get a list of playlists recently added on Humm; returns a list of playlist objects
     *
     * @param limit  Number of returned results (20 by default)
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<PlaylistOwnerList> getRecent(final int limit, final int offset) {

        HummMultipleResult<PlaylistOwnerList> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerList>>() {
            }.getType();

            JSONObject parameters = new JSONObject();

            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/playlists/recent", parameters, token);
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
     * Search Playlists / Albums; returns a list of playlist / album objects
     *
     * @param keyword  search text
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param album    Search an album (true) or a playlist (false; default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a  list of <code>PlaylistOwnerList</code> .
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void search(final String keyword, final int limit, final int offset, final int album, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return search(keyword, limit, offset, album);
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
     * Search Playlists / Albums; returns a list of playlist / album objects
     *
     * @param keyword search text
     * @param limit   Number of returned results (20 by default)
     * @param offset  Offset results by said number (0 by default)
     * @param album   Search an album (true) or a playlist (false; default)
     */
    public HummMultipleResult<PlaylistOwnerList> search(final String keyword, final int limit, final int offset, final int album) {

        HummMultipleResult<PlaylistOwnerList> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerList>>() {
            }.getType();


            JSONObject parameters = new JSONObject();

            if (keyword == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("keyword parameter is mandatory");

                return result;
            }

            parameters.put("keyword", keyword);

            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }

            parameters.put("album", album);

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/playlists", parameters, token);
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
