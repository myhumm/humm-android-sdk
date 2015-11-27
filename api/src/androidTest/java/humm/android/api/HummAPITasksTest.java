package humm.android.api;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.LoginInfo;
import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 24/11/15.
 */
public class HummAPITasksTest extends InstrumentationTestCase {

    public void testLoginTask() throws Throwable {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     *
     *
     */
        final HummAPI humm = HummAPI.getInstance();

        HummSingleResult<LoginInfo> result;
        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
//                myTask.execute("Do something");

                new HummTask<HummSingleResult<LoginInfo>>(new HummTask.Job() {
                    @Override
                    public Object onStart() throws Exception {
                        return humm.getUser().doLogin("deleteme10", "deleteme10");
                    }

                    @Override
                    public void onComplete(Object object) {
                        HummSingleResult<LoginInfo> result = (HummSingleResult<LoginInfo>) object;
                        assertEquals("ok", result.getStatus_response());

                        signal.countDown();

                    }

                    @Override
                    public void onError(Exception e) {

                        return;
                    }
                }).start();

            }
        });

    /* The testing thread will wait here until the UI thread releases it
     * above with the countDown() or 30 seconds passes and it times out.
     */
        signal.await(30, TimeUnit.SECONDS);

        // The task is done, and now you can assert some things!
        assertTrue("ok", true);
    }

    public void testSignup() throws Throwable {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     *
     *
     */
        final HummAPI humm = HummAPI.getInstance();

        final String username = "deleteme10";
        final String password = "deleteme10";
        final String email = "deleteme@delete.com";
        final String firstname = "delete";
        final String lastname = "me";

        final HummSingleResult<LoginInfo> result;
        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {

                new HummTask<HummSingleResult<LoginInfo>>(new HummTask.Job() {
                    @Override
                    public Object onStart() throws Exception {
                        return humm.getUser().doSignup(username, password, email, firstname, lastname);
                    }

                    @Override
                    public void onComplete(Object object) {
                        HummSingleResult<LoginInfo> result = (HummSingleResult<LoginInfo>) object;
                        assertEquals("ok", result.getStatus_response());

                        signal.countDown();

                    }

                    @Override
                    public void onError(Exception e) {

                        return;
                    }
                }).start();

            }
        });

    /* The testing thread will wait here until the UI thread releases it
     * above with the countDown() or 30 seconds passes and it times out.
     */
        signal.await(30, TimeUnit.SECONDS);

        // The task is done, and now you can assert some things!
        assertTrue("ok", true);

    }

    public void testRadio() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
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

        int limit = 10;
        List<String> genres = new ArrayList<>();
        List<String> moods = new ArrayList<>();
        boolean discovery = false;
        boolean own = false;

        HummMultipleResult<Song> result = humm.getSongs().radio(limit, genres, moods, discovery, own);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
            assertEquals(10, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }


}
