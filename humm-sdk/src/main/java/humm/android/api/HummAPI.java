package humm.android.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

import humm.android.api.API.ArtistAPI;
import humm.android.api.API.ChannelsAPI;
import humm.android.api.API.DirectMessagesAPI;
import humm.android.api.API.FilesAPI;
import humm.android.api.API.PlaylistsAPI;
import humm.android.api.API.RadioAPI;
import humm.android.api.API.SongsAPI;
import humm.android.api.API.UserAPI;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.LoginInfo;

/**
 * Created by josealonsogarcia on 24/11/15.
 */
public class HummAPI {

    protected static boolean DEBUG = false;
    protected static String TAG = "HUMM_API";

    private static HummAPI instance = null;
    private static Context context = null;

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
    private static ChannelsAPI channelsAPI = ChannelsAPI.getInstance();
    private static FilesAPI filesAPI = FilesAPI.getInstance();
    private static DirectMessagesAPI directMessagesAPI = DirectMessagesAPI.getInstance();

    private static String SHARED_PREFERENCES_SDK = "SharedPreferencesSDK";
    private static String REFRESH_TOKEN_PREFERENCES = "humm_api_refresh_token";
    private static String TOKEN_PREFERENCES = "humm_api_token";

    public static HummAPI getInstance() {
        if (instance == null) {
            instance = new HummAPI();
        }

        return instance;
    }

    protected HummAPI() {
        clientId = "5433be703acd3952a3e9ec28";
        grantType = "password";
        endpoint = "http://hummchannels.azurewebsites.net/v2";
//        endpoint = "http://192.168.0.15:8080/v2";

        token_expires = 0;
    }

    public static String getEndpoint() {
        return endpoint;
    }

    public static void setEndpoint(String endpoint) {
        HummAPI.endpoint = endpoint;
    }

    public void init(Context context) {
        this.context = context;

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_SDK, Context.MODE_PRIVATE);
        refresh_token = sharedPreferences.getString(REFRESH_TOKEN_PREFERENCES, null);
        token = sharedPreferences.getString(TOKEN_PREFERENCES, null);

        if (refresh_token != null) {
            updateUserToken();
//            getUser().refreshToken(new OnActionFinishedListener() {
//                @Override
//                public void actionFinished(Object result) {
//                    LoginInfo loginInfo = (LoginInfo) result;
//                    updateLoginData(loginInfo);
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    Log.e("HUMM_API", e.getLocalizedMessage());
//                }
//            });
        }

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

        if (refresh_token == null)
        {
            if (DEBUG)
            {
                Log.d(TAG, "refresh_token is null => don't refresh!");
            }
            return;
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
            if (context != null) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_SDK, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(REFRESH_TOKEN_PREFERENCES, refresh_token);
                if (token != null) {
                    editor.putString(TOKEN_PREFERENCES, token);
                }
                editor.apply();
            }
            token_expires = (int) (new Date().getTime() / 1000) + loginInfo.getExpires_in();
        }
        if (DEBUG) {
            Log.d(TAG, "expires " + token_expires + "");
            Log.d(TAG, "token " + token + "");
            Log.d(TAG, "refresh " + refresh_token + "");
        }
    }

    public static boolean isLogged() {
        if (refresh_token == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_SDK, Context.MODE_PRIVATE);
            return sharedPreferences.getString(REFRESH_TOKEN_PREFERENCES, null) != null;
        }

        return true;
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

    public RadioAPI getRadio() {
        return radioAPI;
    }

    public ChannelsAPI getChannelsAPI() {
        return channelsAPI;
    }

    public DirectMessagesAPI getDirectMessagesAPI() {
        return directMessagesAPI;
    }

    public FilesAPI getFilesAPI() {
        return filesAPI;
    }

    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static void setDEBUG(boolean DEBUG) {
        HummAPI.DEBUG = DEBUG;
    }

}
