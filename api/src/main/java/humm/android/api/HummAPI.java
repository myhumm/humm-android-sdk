package humm.android.api;

import android.util.Log;

import java.util.Date;

import humm.android.api.API.ArtistAPI;
import humm.android.api.API.UserAPI;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.LoginInfo;

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
        endpoint = "http://api.myhumm.com";
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

                Log.d("DEBUG", token);
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
                    return;
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


    public ArtistAPI getArtist() {
        return ArtistAPI.getInstance();
    }

    public UserAPI getUser() {
        return UserAPI.getInstance();
    }

}
