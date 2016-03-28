package humm.android.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 28/3/16.
 */
public class RadioTest extends HummTest {

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

        humm.getRadio().radio(limit, genres, moods, discovery, own, new OnActionFinishedListener() {
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


    public void testWeeklyPlaylist() throws Throwable {
        final HummAPI humm = HummAPI.getInstance();
        doLogin();

        final CountDownLatch signal = new CountDownLatch(1);

        humm.getRadio().weeklyPlaylist( new OnActionFinishedListener() {
            @Override
            public void actionFinished(Object result) {
                List<Song> songList = (List) result;
//                assertEquals(limit, songList.size());
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
