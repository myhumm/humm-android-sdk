package humm.android.api;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.Playlist;
import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 26/11/15.
 */
public class SongsAPITest extends InstrumentationTestCase {

    public void testGet() throws Throwable {

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

        String idSong = "5649c572ae8c502824a46a99";

        HummSingleResult<Song> result = humm.getSongs().get(idSong);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
            assertEquals("track", result.getData_response().getType());
            assertEquals(0, result.getData_response().getPopularity());
            assertEquals(16, result.getData_response().getPlaylists());
            assertEquals("Airbag", result.getData_response().getTitle());
        } else {
            assertNull(result); //no content
        }
    }

    public void testGetFeatured() throws Throwable {

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

        int limit = 0;
        int offset = 0;
        String genre = null;

        HummMultipleResult<Song> result = humm.getSongs().getFeatured(limit, offset, genre);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
        } else {
            assertNull(result); //no content
        }
    }

    public void testPopular() throws Throwable {

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
        int offset = 0;
        String genre = null;

        HummMultipleResult<Song> result = humm.getSongs().getPopular(limit, offset, genre);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
            assertEquals(10, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }


    public void testRecent() throws Throwable {

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
        int offset = 0;
        String genre = null;

        HummMultipleResult<Song> result = humm.getSongs().getRecent(limit, offset, genre);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
            assertEquals(10, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

    public void testSearch() throws Throwable {

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

        String keyword = "heroes";
        int limit = 10;
        int offset = 0;
        String songtype = null;

        HummMultipleResult<Song> result = humm.getSongs().search(keyword, limit, offset, songtype);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
            assertEquals(10, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }


    public void testAppearsIn() throws Throwable {

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

        String idSong = "557ecbf86a64fc1b8bed533f"; //song2
        int limit = 0;

        HummMultipleResult<Playlist> result = humm.getSongs().appearsIn(idSong, limit);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(10, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

    public void testGetSimilar() throws Throwable {

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

        String idSong = "557ecbf86a64fc1b8bed533f"; //song2
        int offset = 0;

        HummMultipleResult<Song> result = humm.getSongs().getSimilar(idSong, offset);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(10, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

//    public void testStaffPicks() throws Throwable {
//
//        final CountDownLatch signal = new CountDownLatch(1);
//
//        final HummAPI humm = HummAPI.getInstance();
//        humm.login("deleteme", "deleteme", new OnActionFinishedListener() {
//            @Override
//            public void actionFinished(Object result) {
//                signal.countDown();
//
//            }
//
//            @Override
//            public void onError(Exception e) {
//                assertTrue(false);
//
//            }
//        });
//
//        //wait for login
//        signal.await(30, TimeUnit.SECONDS);
//
//        int offset = 0;
//        int limit = 0;
//        String genre = null;
//
//        HummMultipleResult<Song> result = humm.getSongs().getStaffPicks(limit, offset, genre);
//
//        if (result != null) {
//            assertEquals("ok", result.getStatus_response());
////            assertEquals(10, result.getData_response().size());
//        } else {
//            assertNull(result); //no content
//        }
//    }


}
