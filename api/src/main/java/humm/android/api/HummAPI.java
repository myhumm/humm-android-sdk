package humm.android.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import humm.android.api.API.ArtistAPI;
import humm.android.api.API.PlaylistsAPI;
import humm.android.api.API.SongsAPI;
import humm.android.api.API.UserAPI;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.LoginInfo;
import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 24/11/15.
 */
public class HummAPI {

    private static HummAPI instance = null;

    protected static String clientId;
    protected static String grantType;
    protected static String endpoint;
    protected static String endpoint_covers;
    protected static String token;
    protected static String refresh_token;
    protected static int token_expires;

    public static HummAPI getInstance() {
        if (instance == null) {
            instance = new HummAPI();
        }

        return instance;
    }


    protected HummAPI() {
        clientId = "5433be703acd3952a3e9ec28";
        grantType = "password";
//        endpoint = "http://api.myhumm.com";
        endpoint = "http://api.myhumm.com/v2";
//        endpoint = "http://134.213.62.164:8080";
//        endpoint_covers = "http://wave.livingindietv.com/images/playlist?id=%s&size=thumb";
        token_expires = 0;
    }

    //    public void login(String username, String password, final OnActionFinishedListener listener) {
    public void login(String username, String password) {

        getUser().doLogin(username, password, new OnActionFinishedListener() {

            @Override
            public void actionFinished(Object result) {
                HummSingleResult<LoginInfo> login = (HummSingleResult<LoginInfo>) result;
                if (login == null || login.getData_response() == null || login.getData_response().getAccess_token() == null) {
                    return;
                }

                updateLoginData(login.getData_response());

            }

            public void onError(Exception e) {
//                if (listener != null) {
//                    listener.onError(e);
//                }

            }
        });

    }

    public void login(String username, String password, final OnActionFinishedListener listener) {

        getUser().doLogin(username, password, new OnActionFinishedListener() {

            @Override
            public void actionFinished(Object result) {
                HummSingleResult<LoginInfo> login = (HummSingleResult<LoginInfo>) result;
                if (login == null || login.getData_response() == null || login.getData_response().getAccess_token() == null) {
                    listener.onError(new HummException("login failed"));
                }

                updateLoginData(login.getData_response());

                listener.actionFinished(login.getData_response());

            }

            public void onError(Exception e) {
//                if (listener != null) {
//                    listener.onError(e);
//                }
                listener.onError(e);

            }
        });

    }


    public void signup(String username, String password, String email, String firstname, String lastname) {

        HummSingleResult<LoginInfo> login = getUser().doSignup(username, password, email, firstname, lastname);

        if (login == null || login.getData_response() == null || login.getData_response().getAccess_token() == null) {
            return;
        }

        getUser().doSignup(username, password, email, firstname, lastname, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                HummSingleResult<LoginInfo> login = (HummSingleResult<LoginInfo>) result;
                if (login == null || login.getData_response() == null || login.getData_response().getAccess_token() == null) {
                    return;
                }

                updateLoginData(login.getData_response());

            }

            @Override
            public void onError(Exception e) {

            }
        });

        updateLoginData(login.getData_response());

    }

    public void updateUserToken() {

        int secondsNow = (int) (new Date().getTime() / 1000);

        if (secondsNow < token_expires) {
            //user is loged
            if (token != null) {
                return;
            }
        }

        //we need refresh token
        HummSingleResult<LoginInfo> login = UserAPI.refreshToken();

        updateLoginData(login.getData_response());

    }

    private static void updateLoginData(LoginInfo loginInfo) {
        token = loginInfo.getAccess_token();
        refresh_token = loginInfo.getRefresh_token();
        token_expires = loginInfo.getExpires_in();
    }

    public void radio(final int limit, final List<String> genres, final List<String> moods, final boolean discovery, final boolean own, final OnActionFinishedListener listener) {
        new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
            @Override
            public Object onStart() throws Exception {
                return radio(limit, genres, moods, discovery, own);
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
                parameters.put("genres", genres);
            }
            if (moods != null) {
                parameters.put("moods", moods);
            }
            parameters.put("discovery", discovery);
            parameters.put("own", own);

            Reader reader = HttpURLConnectionHelper.getHttpConnection(endpoint + "/radio", parameters, token);
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


    public ArtistAPI getArtist() {
        return ArtistAPI.getInstance();
    }

    public UserAPI getUser() {
        return UserAPI.getInstance();
    }

    public SongsAPI getSongs() {
        return SongsAPI.getInstance();
    }

    public PlaylistsAPI getPlaylists() {
        return PlaylistsAPI.getInstance();
    }

}
