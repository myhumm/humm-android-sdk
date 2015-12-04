package humm.android.api.API;

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
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.Playlist;
import humm.android.api.Model.PlaylistOwnerHashMap;
import humm.android.api.Model.Song;
import humm.android.api.OnActionFinishedListener;

/**
 * Created by josealonsogarcia on 26/11/15.
 */
public class SongsAPI extends HummAPI {

    private static SongsAPI instance = null;

    protected SongsAPI() {
    }

    public static SongsAPI getInstance() {
        if (instance == null) {
            instance = new SongsAPI();
        }

        return instance;
    }

    /**
     * Get a song; returns song object for given id
     *
     * @param idSong   Unique identifier of song
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a the <code>Song</code> requireds.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void get(final String idSong, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return get(idSong);
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
     * Get a song; returns song object for given id
     *
     * @param idSong Unique identifier of song
     */
    public HummSingleResult<Song> get(String idSong) {

        HummSingleResult<Song> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<Song>>() {
            }.getType();

            if (idSong == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/songs/" + idSong, null, token);
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
     * @param genre    Filter by genre: rock, pop, indie pop, electronic, hip hop, hip house, metal, blues, classical, bossa nova, cantautor, hard rock, punk, soundtrack, new age, honky tonk, flamenco, samba, salsa, ska, world, poetry, beats & rhymes, motivation, soul, folk, reggae, latin, jazz, dance, ambient, romantic, workout, easy listening, chill-out, a cappella
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Song</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getFeatured(final int limit, final int offset, final String genre, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getFeatured(limit, offset, genre);
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
     * Get a list of songs featured by Humm; returns a list of song objects
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     * @param genre  Filter by genre: rock, pop, indie pop, electronic, hip hop, hip house, metal, blues, classical, bossa nova, cantautor, hard rock, punk, soundtrack, new age, honky tonk, flamenco, samba, salsa, ska, world, poetry, beats & rhymes, motivation, soul, folk, reggae, latin, jazz, dance, ambient, romantic, workout, easy listening, chill-out, a cappella
     */
    public HummMultipleResult<Song> getFeatured(int limit, int offset, String genre) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<Song>>() {
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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/songs/featured", parameters, token);
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
     * Get a list of songs recently added on Humm; returns a list of song objects
     *
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param genre    Filter by genre: rock, pop, indie pop, electronic, hip hop, hip house, metal, blues, classical, bossa nova, cantautor, hard rock, punk, soundtrack, new age, honky tonk, flamenco, samba, salsa, ska, world, poetry, beats & rhymes, motivation, soul, folk, reggae, latin, jazz, dance, ambient, romantic, workout, easy listening, chill-out, a cappella
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Song</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getPopular(final int limit, final int offset, final String genre, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPopular(limit, offset, genre);
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
     * Get a list of songs recently added on Humm; returns a list of song objects
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     * @param genre  Filter by genre: rock, pop, indie pop, electronic, hip hop, hip house, metal, blues, classical, bossa nova, cantautor, hard rock, punk, soundtrack, new age, honky tonk, flamenco, samba, salsa, ska, world, poetry, beats & rhymes, motivation, soul, folk, reggae, latin, jazz, dance, ambient, romantic, workout, easy listening, chill-out, a cappella
     */
    public HummMultipleResult<Song> getPopular(int limit, int offset, String genre) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/songs/popular", parameters, token);
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
     * Get a list of songs recently added on Humm; returns a list of song objects
     *
     * @param limit    Number of returned results (no used)
     * @param offset   Offset results by said number (0 by default)
     * @param genre    Filter by genre: rock, pop, indie pop, electronic, hip hop, hip house, metal, blues, classical, bossa nova, cantautor, hard rock, punk, soundtrack, new age, honky tonk, flamenco, samba, salsa, ska, world, poetry, beats & rhymes, motivation, soul, folk, reggae, latin, jazz, dance, ambient, romantic, workout, easy listening, chill-out, a cappella
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Song</code>.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getRecent(final int limit, final int offset, final String genre, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getRecent(limit, offset, genre);
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
     * Get a list of songs recently added on Humm; returns a list of song objects
     *
     * @param limit  Number of returned results (no used)
     * @param offset Offset results by said number (0 by default)
     * @param genre  Filter by genre: rock, pop, indie pop, electronic, hip hop, hip house, metal, blues, classical, bossa nova, cantautor, hard rock, punk, soundtrack, new age, honky tonk, flamenco, samba, salsa, ska, world, poetry, beats & rhymes, motivation, soul, folk, reggae, latin, jazz, dance, ambient, romantic, workout, easy listening, chill-out, a cappella
     */
    public HummMultipleResult<Song> getRecent(int limit, int offset, String genre) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
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

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/songs/recent", parameters, token);
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
     * Search for a song; returns a list of song objects
     *
     * @param keyword  keyword to search by
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param songtype Filter by type of song: track (default), cover, video
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Song</code> objects for the given keyword.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void search(final String keyword, final int limit, final int offset, final String songtype, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return search(keyword, limit, offset, songtype);
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
     * Search for a song; returns a list of song objects
     *
     * @param keyword  keyword to search by
     * @param limit    Number of returned results (20 by default)
     * @param offset   Offset results by said number (0 by default)
     * @param songtype Filter by type of song: track (default), cover, video
     */
    public HummMultipleResult<Song> search(String keyword, final int limit, final int offset, final String songtype) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();

            JSONObject parameters = new JSONObject();

            if (keyword == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("keyword parameter is mandatory");

                return result;
            }


            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (offset > 0) {
                parameters.put("offset", offset);
            }
            if (songtype != null) {
                parameters.put("genre", songtype);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/songs", parameters, token);
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
     * Get a list of playlists a song appears in; returns a list of playlist objects for a given id
     *
     * @param idSong   Unique identifier of song
     * @param limit    Number of returned results (20 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>PlaylistOwnerHashMap</code> .
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void appearsIn(final String idSong, final int limit, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerHashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return appearsIn(idSong, limit);
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
     * Get a list of playlists a song appears in; returns a list of playlist objects for a given id
     *
     * @param idSong Unique identifier of song
     * @param limit  Number of returned results (20 by default)
     */
    public HummMultipleResult<PlaylistOwnerHashMap> appearsIn(String idSong, final int limit) {

        HummMultipleResult<PlaylistOwnerHashMap> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<PlaylistOwnerHashMap>>() {
            }.getType();

            JSONObject parameters = new JSONObject();

            if (idSong == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("keyword parameter is mandatory");

                return result;
            }

            if (limit > 0) {
                parameters.put("limit", limit);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/songs/" + idSong + "/appearsin", parameters, token);
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
     * Get a list of similar songs; returns a list of song objects for a given id
     *
     * @param idSong   Unique identifier of song
     * @param offset   Offset results by said number (0 by default)
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a list of <code>Song</code> .
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void getSimilar(final String idSong, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getSimilar(idSong, offset);
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
     * Get a list of similar songs; returns a list of song objects for a given id
     *
     * @param idSong Unique identifier of song
     * @param offset Offset results by said number (0 by default)
     */
    public HummMultipleResult<Song> getSimilar(final String idSong, final int offset) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();

            JSONObject parameters = new JSONObject();

            if (idSong == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("keyword parameter is mandatory");

                return result;
            }

            if (offset > 0) {
                parameters.put("offset", offset);
            }

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/songs/" + idSong + "/similar", parameters, token);
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
