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

    public void get(final String idPlaylist, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerHashMap>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return get(idPlaylist);
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

    public void create(final String title, final String description, final boolean isPrivate, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return create(title, description, isPrivate);
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

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/playlists", parameters, true,  token);
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

    public void getFeatured(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getFeatured(limit, offset);
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

    public HummMultipleResult<Playlist> getFeatured(final int limit, final int offset) {

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


    public void update(final String idPlaylist, final String title, final String description, final boolean isPrivate, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return update(idPlaylist, title, description, isPrivate);
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


    public void order(final String idPlaylist, final List<HashMap<String, String>> order, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return order(idPlaylist, order);
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

    public HummMultipleResult<Song> order(final String idPlaylist, final List<HashMap<String, String>> songs) {

        HummMultipleResult<Song> result = new HummMultipleResult<>();
        try {

            Type listType = new TypeToken<HummMultipleResult<Song>>() {
            }.getType();


            if (idPlaylist == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("id parameter is mandatory");

                return result;
            }

            if (songs == null) {
                result.setStatus_response(HttpURLConnectionHelper.KO);
                result.setError_response("songs parameter is mandatory");

                return result;
            }

            JSONObject parameters = new JSONObject();
            parameters.put("songs", songs);

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

    public void getSongs(final String idPlaylist, final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getSongs(idPlaylist, limit, offset);
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


    //no tested
    public void addSong(final String idPlaylist, final String idSong, final int position, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addSong(idPlaylist, idSong, position);
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

    //no tested
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

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/playlists/" + idPlaylist + "/songs",  parameters, false, token);
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

    //no tested
    public void deleteSong(final String idPlaylist, final String idSong, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<Playlist>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return deleteSong(idPlaylist, idSong);
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

    //no tested
    public HummSingleResult<Playlist> deleteSong(final String idPlaylist, final String idSong) {

        HummSingleResult<Playlist> result = new HummSingleResult<>();
        try {

            Type listType = new TypeToken<HummSingleResult<Playlist>>() {
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


    public void addSubscriber(final String idPlaylist, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return addSubscriber(idPlaylist);
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

            Reader reader = HttpURLConnectionHelper.postHttpConnection(endpoint + "/playlists/" + idPlaylist + "/subscribers",  null, true, token);
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

    public void removeSubscriber(final String idPlaylist, final OnActionFinishedListener listener) {
        new HummTask<HummSingleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return removeSubscriber(idPlaylist);
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

    public void getPopular(final int limit, final int offset, final int section, final String idUser, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getPopular(limit, offset, section, idUser);
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

    public void getRecent(final int limit, final int offset, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return getRecent(limit, offset);
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

    public void search(final String keyword, final int limit, final int offset, final int album, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<PlaylistOwnerList>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return search(keyword, limit, offset, album);
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
