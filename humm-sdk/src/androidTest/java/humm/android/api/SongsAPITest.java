package humm.android.api;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import humm.android.api.Model.PlaylistOwnerHashMap;
import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 26/11/15.
 */
public class SongsAPITest extends HummTest {

    public void testGet() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idSong = "557ecac76a64fc1b8bed450a";

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getSongs().get(idSong, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                Song song = (Song) result;

                if (song != null) {
                    assertEquals("track", song.getType());
                    assertEquals("0", song.getPopularity());
                    assertEquals("16", song.getStatsPlaylists());
                    assertEquals("Airbag", song.getTitle());
                } else {
                    assertNull(song); //no content
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

    public void testGetFeatured() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        int limit = 0;
        int offset = 0;
        String genre = null;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getSongs().getFeatured(limit, offset, genre, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<Song> songList = (List) result;
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
        final int limit = 10;
        int offset = 0;
        String genre = null;

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getSongs().getPopular(limit, offset, genre, new OnActionFinishedListener() {
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


    public void testRecent() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        final int limit = 10;
        int offset = 0;
        String genre = null;

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getSongs().getRecent(limit, offset, genre, new OnActionFinishedListener() {
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

    public void testSearch() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String keyword = "song";
        int limit = 10;
        int offset = 0;
        String songtype = null;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getSongs().search(keyword, limit, offset, songtype, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }


    public void testAppearsIn() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idSong = "557ecbf86a64fc1b8bed533f"; //song2
        int limit = 0;

        final CountDownLatch signal = new CountDownLatch(1);
        humm.getSongs().appearsIn(idSong, limit, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<PlaylistOwnerHashMap> playlist = (List) result;

                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                assertTrue(false);
            }
        });
        signal.await(30, TimeUnit.SECONDS);

    }

    public void testGetSimilar() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        doLogin();
        String idSong = "557ecbf86a64fc1b8bed533f"; //song2
        int offset = 0;

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getSongs().getSimilar(idSong, offset, new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<Song> songList = (List) result;

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
