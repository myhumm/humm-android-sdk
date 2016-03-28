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
import java.util.ArrayList;
import java.util.List;

import humm.android.api.HttpURLConnectionHelper;
import humm.android.api.HummAPI;
import humm.android.api.HummException;
import humm.android.api.HummTask;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.Song;
import humm.android.api.OnActionFinishedListener;

/**
 * Created by josealonsogarcia on 28/3/16.
 */
public class RadioAPI extends HummAPI {

    private static RadioAPI instance = null;

    protected RadioAPI() {
    }

    public static RadioAPI getInstance() {
        if (instance == null) {
            instance = new RadioAPI();
        }

        return instance;
    }


    /**
     * Get a list of songs for a radio; returns a list of song objects
     *
     * @param limit     Number of returned results (20 by default)
     * @param genres    List of String with genres to play
     * @param moods     List of String with moods to play
     * @param discovery Current user's discovery radio (false by default)
     * @param own       Current user's radio (false by default)
     * @param listener  called when action is completed or when happens a error. The parameter of onComplete method is
     *                  a list of <code>Song</code> .
     *                  Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void radio(final int limit, final List<String> genres, final List<String> moods, final boolean discovery, final boolean own, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return radio(limit, genres, moods, discovery, own);
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
     * Get a list of songs for a radio; returns a list of song objects
     *
     * @param limit     Number of returned results (20 by default)
     * @param genres    List of String with genres to play
     * @param moods     List of String with moods to play
     * @param discovery Current user's discovery radio (false by default)
     * @param own       Current user's radio (false by default)
     */
    public HummMultipleResult<Song> radio(final int limit, List<String> genres, List<String> moods, boolean discovery, boolean own) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();

            JSONObject parameters = new JSONObject();

            if (limit > 0) {
                parameters.put("limit", limit);
            }
            if (genres != null) {
                List<String> genresEncoded = new ArrayList<>();
                for (String genre : genres) {
                    genresEncoded.add(Uri.encode(genre));
                }
                parameters.put("genres", genresEncoded);
            }
            if (moods != null) {
                List<String> moodsEncoded = new ArrayList<>();
                for (String mood : moods) {
                    moodsEncoded.add(Uri.encode(mood));
                }
                parameters.put("moods", moodsEncoded);
            }
            parameters.put("discovery", discovery);
            parameters.put("own", own);

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/radio", parameters, token, DEBUG);
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
     * Get a list of songs for a radio; returns a list of song objects
     *
     * @param limit     Number of returned results (20 by default)
     * @param genres    List of String with genres to play
     * @param moods     List of String with moods to play
     * @param discovery Current user's discovery radio (false by default)
     * @param own       Current user's radio (false by default)
     * @param listener  called when action is completed or when happens a error. The parameter of onComplete method is
     *                  a list of <code>Song</code> .
     *                  Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void weeklyPlaylist(final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return weeklyPlaylist();
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
     * Get a list of songs for a radio; returns a list of song objects
     *
     * @param limit     Number of returned results (20 by default)
     * @param genres    List of String with genres to play
     * @param moods     List of String with moods to play
     * @param discovery Current user's discovery radio (false by default)
     * @param own       Current user's radio (false by default)
     */
    public HummMultipleResult<Song> weeklyPlaylist() {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();

            JSONObject parameters = new JSONObject();

            parameters.put("weekly", true);

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/radio", parameters, token, DEBUG);
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
