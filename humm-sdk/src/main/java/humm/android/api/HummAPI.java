package humm.android.api;

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
import java.util.Date;
import java.util.List;

import humm.android.api.API.ArtistAPI;
import humm.android.api.API.PlaylistsAPI;
import humm.android.api.API.RadioAPI;
import humm.android.api.API.SongsAPI;
import humm.android.api.API.UserAPI;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.LoginInfo;
import humm.android.api.Model.PlaylistOwnerList;
import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 24/11/15.
 */
public class HummAPI {

    protected static boolean DEBUG = false;
    protected static String TAG = "HUMM_API";

    private static HummAPI instance = null;

    protected static String clientId;
    protected static String grantType;
    protected static String endpoint;
    protected static String endpoint_covers;
    protected static String token;
    protected static String refresh_token;
    protected static int token_expires;

    private static UserAPI userAPI = UserAPI.getInstance();
    private static ArtistAPI artistAPI = ArtistAPI.getInstance();
    private static PlaylistsAPI playlistsAPI = PlaylistsAPI.getInstance();
    private static SongsAPI songsAPI = SongsAPI.getInstance();
    private static RadioAPI radioAPI = RadioAPI.getInstance();

    public static HummAPI getInstance() {
        if (instance == null) {
            instance = new HummAPI();
        }

        return instance;
    }


    protected HummAPI() {
        clientId = "5433be703acd3952a3e9ec28";
        grantType = "password";
        endpoint = "http://api.myhumm.com/v2";
        token_expires = 0;
    }

    /**
     * login into humm with the username and password given
     *
     * @param username username
     * @param password password
     */
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
            }
        });

    }

    /**
     * login into humm with the username and password given
     *
     * @param username username
     * @param password password
     * @param listener called when action is completed or when happens a error. The parameter of onComplete method is
     *                 a <code>LoginInfo</code> object.
     *                 Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void login(String username, String password, final OnActionFinishedListener listener) {

        getUser().doLogin(username, password, new OnActionFinishedListener() {

            @Override
            public void actionFinished(Object result) {
                HummSingleResult<LoginInfo> login = (HummSingleResult<LoginInfo>) result;
                if ((login == null) || (login.getData_response() == null) || (login.getData_response().getAccess_token() == null)) {
                    listener.onError(new HummException("login failed"));
                }

                if (login != null && login.getData_response() != null) {
                    updateLoginData(login.getData_response());
                    listener.actionFinished(login.getData_response());
                } else {
                    listener.onError(new HummException("login failed"));
                }

            }

            public void onError(Exception e) {
                listener.onError(e);

            }
        });

    }


    /**
     * creates a new user into humm with the data given.
     *
     * @param username  username
     * @param password  password
     * @param email     email
     * @param firstname firstname
     * @param lastname  lastname
     * @param listener  called when action is completed or when happens a error. The parameter of onComplete method is
     *                  a <code>LoginInfo</code> object.
     *                  Is guaranteed that is called in main thread, so is safe call UI elements inside it.
     */
    public void signup(String username, String password, String email, String firstname, String lastname, String referal, final OnActionFinishedListener listener) {

//        HummSingleResult<LoginInfo> login = getUser().doSignup(username, password, email, firstname, lastname);
//
//        if (login == null || login.getData_response() == null || login.getData_response().getAccess_token() == null) {
//            return;
//        }
//
        getUser().doSignup(username, password, email, firstname, lastname, referal, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                HummSingleResult<LoginInfo> login = (HummSingleResult<LoginInfo>) result;

                if ((login == null) || (login.getData_response() == null) || (login.getData_response().getAccess_token() == null)) {
                    listener.onError(new HummException("signup failed"));
                }

                if (login != null && login.getData_response() != null) {
                    updateLoginData(login.getData_response());
                    listener.actionFinished(login.getData_response());
                } else {
                    listener.onError(new HummException("signup failed"));
                }

            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        });

    }

    public void updateUserToken() {

        if (DEBUG) {
            Log.d(TAG, "updateUserToken");
        }

        int secondsNow = (int) (new Date().getTime() / 1000);

        if (DEBUG) {
            Log.d(TAG, "expires " + token_expires + "");
            Log.d(TAG, "now " + secondsNow + "");
            Log.d(TAG, "token" + token + "");
        }
        if (secondsNow < token_expires) {
            //user is loged
            if (token != null) {
                if (DEBUG) {
                    Log.d(TAG, "Token valid");
                }
                return;
            }
        }

        if (DEBUG) {
            Log.d(TAG, "Token not valid, refresh it!");
        }

        //we need refresh token
        HummSingleResult<LoginInfo> login = UserAPI.refreshToken();

        updateLoginData(login.getData_response());

    }

    public static void updateLoginData(LoginInfo loginInfo) {

        if (loginInfo != null) {
            token = loginInfo.getAccess_token();
            refresh_token = loginInfo.getRefresh_token();
            token_expires = (int) (new Date().getTime() / 1000) + loginInfo.getExpires_in();
        }
        if (DEBUG) {
            Log.d(TAG, "expires " + token_expires + "");
            Log.d(TAG, "token" + token + "");
            Log.d(TAG, "refresh" + refresh_token + "");
        }
    }



    public ArtistAPI getArtist() {
        return artistAPI;
    }

    public UserAPI getUser() {
        return userAPI;
    }

    public SongsAPI getSongs() {
        return songsAPI;
    }

    public PlaylistsAPI getPlaylists() {
        return playlistsAPI;
    }

    public RadioAPI getRadio()
    {
        return radioAPI;
    }

    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static void setDEBUG(boolean DEBUG) {
        HummAPI.DEBUG = DEBUG;
    }

}
