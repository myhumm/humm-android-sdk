package humm.android.api;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.Playlist;
import humm.android.api.Model.PlaylistOwnerHashMap;
import humm.android.api.Model.PlaylistOwnerList;
import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 26/11/15.
 */
public class PlaylistsAPITest extends InstrumentationTestCase {

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

        String idPlaylist = "5645aed5b4653cdb631d5632";

        HummSingleResult<PlaylistOwnerHashMap> result = humm.getPlaylists().get(idPlaylist);

        assertEquals("ok", result.getStatus_response());
        assertEquals("Bustin' + Dronin'", result.getData_response().getTitle());
        assertEquals("MxAxG", result.getData_response().getOwnerName());
    }

    public void testCreate() throws Throwable {

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

        String namePlaylist = "MyTest";
        String description = "description";
        boolean isPrivate = false;

        HummSingleResult<Playlist> result = humm.getPlaylists().create(namePlaylist, description, isPrivate);

        assertEquals("ok", result.getStatus_response());
        assertEquals("MyTest", result.getData_response().getTitle());

        Log.d("DEBUG", result.getData_response().get_id());
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

        int limit = 5;
        int offset = 0;

        HummMultipleResult<Playlist> result = humm.getPlaylists().getFeatured(limit, offset);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
            assertEquals(5, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

    public void testUpdate() throws Throwable {

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

        String idPlaylist = "";
        String title = "";
        String description = "";
        boolean isPrivate = false;

        HummSingleResult<Playlist> result = humm.getPlaylists().update(idPlaylist, title, description, isPrivate);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(5, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

    public void testOrder() throws Throwable {

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

        String idPlaylist = "5645aed5b4653cdb631d5632";

        List<HashMap<String, String>> order = new ArrayList<>();

        HashMap<String, String> sid = new HashMap<String, String>();
        sid.put("sid", "54d2be65ae8c5003198baa38");
        order.add(sid);
        sid.put("sid", "54d2be65ae8c5003198baa3a");
        order.add(sid);
        sid.put("sid", "54d2be65ae8c5003198baa37");
        order.add(sid);
        sid.put("sid", "54d2be65ae8c5003198baa39");
        order.add(sid);

        HummSingleResult<Playlist> result = humm.getPlaylists().order(idPlaylist, order);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(5, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

    public void testGetSongs() throws Throwable {

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

        String idPlaylist = "5645aed5b4653cdb631d5632";
        int limit = 5;
        int offset = 0;
        HummMultipleResult<Song> result = humm.getPlaylists().getSongs(idPlaylist, limit, offset);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
            assertEquals(5, result.getData_response().size());
//            assertEquals(5, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

    //no tested
    public void testAddSongs() throws Throwable {

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

        String idPlaylist = "565812e9e33b107e4e4f69d2";
        String idSong = "54d2be65ae8c5003198baa39";
        int position = 0;
        HummSingleResult<Playlist> result = humm.getPlaylists().addSong(idPlaylist, idSong, position);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(5, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

    //no tested
    public void testRemoveSongs() throws Throwable {

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

        String idPlaylist = "565812e9e33b107e4e4f69d2";
        String idSong = "54d2be65ae8c5003198baa39";

        HummSingleResult<Playlist> result = humm.getPlaylists().deleteSong(idPlaylist, idSong);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(5, result.getData_response().size());
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

        int limit = 0;
        int offset = 0;
        int section = 0;
        String uid = null;

        HummMultipleResult<PlaylistOwnerList> result = humm.getPlaylists().getPopular(limit, offset, section, uid);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(5, result.getData_response().size());
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

        int limit = 0;
        int offset = 0;

        HummMultipleResult<PlaylistOwnerList> result = humm.getPlaylists().getRecent(limit, offset);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(5, result.getData_response().size());
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

        String keyword = "Thriller";
        int limit = 0;
        int offset = 0;
        int album = 0;

        HummMultipleResult<PlaylistOwnerList> result = humm.getPlaylists().search(keyword, limit, offset, album);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(5, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

    public void testAddSubscriber() throws Throwable {

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

        String idPlaylist = "563fdb0334017507dba11167";

        HummSingleResult<PlaylistOwnerList> result = humm.getPlaylists().addSubscriber(idPlaylist);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(5, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }
    public void testRemoveSubscriber() throws Throwable {

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

        String idPlaylist = "563fdb0334017507dba11167";

        HummSingleResult<PlaylistOwnerList> result = humm.getPlaylists().removeSubscriber(idPlaylist);

        if (result != null) {
            assertEquals("ok", result.getStatus_response());
//            assertEquals(5, result.getData_response().size());
        } else {
            assertNull(result); //no content
        }
    }

}
