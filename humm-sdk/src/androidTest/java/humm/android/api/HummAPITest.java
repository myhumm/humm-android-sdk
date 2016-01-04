package humm.android.api;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import humm.android.api.Model.LoginInfo;
import humm.android.api.Model.Song;
import humm.android.api.Model.User;

/**
 * Created by josealonsogarcia on 24/11/15.
 */
public class HummAPITest extends HummTest {

    public void testLogin() throws Throwable {
        doLogin("deleteme110", "deleteme110");
    }


    public void testSignup() throws Throwable {
        final HummAPI humm = HummAPI.getInstance();

        final String username = "deleteme111";
        final String password = "deleteme111";
        final String email = "deleteme111@delete.com";
        final String firstname = "delete";
        final String lastname = "me";

        final CountDownLatch signal = new CountDownLatch(1);

        humm.signup(username, password, email, firstname, lastname, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                LoginInfo login = (LoginInfo) result;
//                assertEquals("deleteme100", login.);
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

    public void testRadio() throws Throwable {
        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        final int limit = 10;
        List<String> genres = new ArrayList<>();
        genres.add("pop");
        genres.add("rock");
        List<String> moods = null;
        boolean discovery = false;
        boolean own = false;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.radio(limit, genres, moods, discovery, own, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<Song> songList = (List) result;
                assertEquals(limit, songList.size());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });

        signal.await(30, TimeUnit.SECONDS);

    }

}
