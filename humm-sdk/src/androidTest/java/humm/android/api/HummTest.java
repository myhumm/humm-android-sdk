package humm.android.api;

import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by josealonsogarcia on 2/12/15.
 */
public class HummTest extends InstrumentationTestCase {

    private static String USERNAME = "deleteme100";
    private static String PASSWORD = "deleteme100";


    protected void doLogin() throws Throwable {
        final HummAPI humm = HummAPI.getInstance();
        final CountDownLatch signal = new CountDownLatch(1);

        humm.login(USERNAME, PASSWORD, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {

                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        return;
    }

    protected void doLogin(String username, String password) throws Throwable {
        final HummAPI humm = HummAPI.getInstance();
        final CountDownLatch signal = new CountDownLatch(1);

        humm.login(username, password, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {

                assertTrue(false);

            }
        });

        //wait for login
        signal.await(30, TimeUnit.SECONDS);

        return;
    }


}
