package humm.android.api;

import android.test.InstrumentationTestCase;
import android.util.Log;

import org.w3c.dom.ls.LSException;

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
public class PlaylistsAPITest extends HummTest {

    public void testGet() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        String idPlaylist = "5645aed5b4653cdb631d5632";

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getPlaylists().get(idPlaylist, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                PlaylistOwnerHashMap playlist = (PlaylistOwnerHashMap) result;
                assertEquals("Title", playlist.getTitle());
                assertEquals("MxAxG", playlist.getOwnerName());
                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);


    }

    public void testCreate() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        String namePlaylist = "MyTest";
        String description = "description";
        boolean isPrivate = false;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getPlaylists().create(namePlaylist, description, isPrivate, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                PlaylistOwnerHashMap playlist = (PlaylistOwnerHashMap) result;
                assertEquals("MyTest", playlist.getTitle());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testGetFeatured() throws Throwable {


        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        int limit = 5;
        int offset = 0;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getPlaylists().getFeatured(limit, offset, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<PlaylistOwnerHashMap> playlist = (List) result;

                if (playlist != null) {
                    assertEquals(5, playlist.size());
                } else {
                    assertNull(playlist); //no content
                }

                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testUpdate() throws Throwable {


        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        String idPlaylist = "565f32f298cd5758490cc24d";
        String title = "title new";
        String description = "description new";
        boolean isPrivate = false;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getPlaylists().update(idPlaylist, title, description, isPrivate, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                PlaylistOwnerHashMap playlist = (PlaylistOwnerHashMap) result;

                if (playlist != null) {
                    assertEquals("title new", playlist.getTitle());
                    assertEquals("description new", playlist.getDescription());
                } else {
                    assertNull(playlist); //no content
                }

                signal.countDown();

            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });

        signal.await(30, TimeUnit.SECONDS);

    }

    public void testOrder() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();

        doLogin();

        String idPlaylist = "5645aed5b4653cdb631d5632";

        List<HashMap<String, String>> order = new ArrayList<>();

        HashMap<String, String> sid1 = new HashMap<>();
        sid1.put("sid", "54d2be65ae8c5003198baa38");
        order.add(sid1);
        HashMap<String, String> sid2 = new HashMap<>();
        sid2.put("sid", "54d2be65ae8c5003198baa3a");
        order.add(sid2);
        HashMap<String, String> sid3 = new HashMap<>();
        sid3.put("sid", "54d2be65ae8c5003198baa37");
        order.add(sid3);
        HashMap<String, String> sid4 = new HashMap<>();
        sid4.put("sid", "54d2be65ae8c5003198baa39");
        order.add(sid4);

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getPlaylists().order(idPlaylist, order, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<Song> songList = (List) result;

                signal.countDown();
            }

            @Override
            public void onError(Exception e) {

            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testGetSongs() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        String idPlaylist = "5645aed5b4653cdb631d5632";
        final int limit = 5;
        int offset = 0;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getPlaylists().getSongs(idPlaylist, limit, offset, new OnActionFinishedListener() {
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

    public void testAddSongs() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        String idPlaylist = "565f32f298cd5758490cc24d";
        String idSong = "56403fd834017507dba11880";
        int position = 0;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getPlaylists().addSong(idPlaylist, idSong, position, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                PlaylistOwnerHashMap playlist = (PlaylistOwnerHashMap) result;

                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

    public void testRemoveSongs() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        String idPlaylist = "565f32f298cd5758490cc24d";
        String idSong = "54d2be65ae8c5003198baa39";

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getPlaylists().deleteSong(idPlaylist, idSong, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                PlaylistOwnerHashMap playlist = (PlaylistOwnerHashMap) result;
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testPopular() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        final int limit = 5;
        int offset = 0;
        int section = 0;
        String uid = null;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getPlaylists().getPopular(limit, offset, section, uid, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<PlaylistOwnerList> playlistList = (List) result;
                assertEquals(limit, playlistList.size());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testRecent() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        final int limit = 5;
        int offset = 0;

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getPlaylists().getRecent(limit, offset, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<PlaylistOwnerList> playlistList = (List) result;
                assertEquals(limit, playlistList.size());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testSearch() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        String keyword = "Thriller";
        final int limit = 5;
        int offset = 0;
        int album = 0;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getPlaylists().search(keyword, limit, offset, album, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<PlaylistOwnerList> playlistList = (List) result;
                assertEquals(limit, playlistList.size());
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testAddSubscriber() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        String idPlaylist = "563fdb0334017507dba11167";

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getPlaylists().addSubscriber(idPlaylist, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                PlaylistOwnerList playlist = (PlaylistOwnerList) result;

                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testRemoveSubscriber() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        String idPlaylist = "563fdb0334017507dba11167";

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getPlaylists().removeSubscriber(idPlaylist, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                PlaylistOwnerList playlist = (PlaylistOwnerList) result;

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
