package humm.android.api;

import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import humm.android.api.Model.Artist;
import humm.android.api.Model.HummMultipleResult;
import humm.android.api.Model.HummSingleResult;
import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 23/11/15.
 */

//TODO call login with callback
public class ArtistAPITest extends InstrumentationTestCase {


    public void testSearch() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        HummMultipleResult<Artist> result = humm.getArtist().doSearch("beck");

        assertEquals("ok", result.getStatus_response());


    }

    public void testSearchTask() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     *
     *
     */
        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        HummMultipleResult<Artist> result;
        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
//                myTask.execute("Do something");


                new HummTask<HummMultipleResult<Artist>>(new HummTask.Job() {
                    @Override
                    public Object onStart() throws Exception {
                        return humm.getArtist().doSearch("beck");
                    }

                    @Override
                    public void onComplete(Object object) {
                        HummMultipleResult<Artist> result = (HummMultipleResult<Artist>) object;
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
//        assertTrue("ok", true);

    }

    public void testTopSongs() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";
        final int limit = 10;
        final int offset = 0;
        final String songType = null;


        HummMultipleResult<Song> result = humm.getArtist().getTopSongs(idArtist, limit, offset, songType);
        assertEquals("ok", result.getStatus_response());
//        assertEquals(10, result.getStatus_response().length());

    }

    public void testTopSongsTask() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     *
     *
     */
        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";
        final int limit = 20;
        final int offset = 10;
        final String songType = null;

        HummMultipleResult<Song> result;
        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
//                myTask.execute("Do something");


                new HummTask<HummMultipleResult<Song>>(new HummTask.Job() {
                    @Override
                    public Object onStart() throws Exception {
                        return humm.getArtist().getTopSongs(idArtist, limit, offset, songType);
                    }

                    @Override
                    public void onComplete(Object object) {
                        HummMultipleResult<Song> result = (HummMultipleResult<Song>) object;
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
//        assertTrue("ok", true);

    }

    public void testGet() throws Throwable {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";
        final int limit = 0;
        final int offset = 0;

        HummSingleResult<Artist> result = humm.getArtist().get(idArtist, limit, offset);

        assertEquals("ok", result.getStatus_response());

        Artist artist = result.getData_response();
        assertEquals("Blur", artist.getName());
        assertEquals("#Blur", artist.getHashtag());
        assertEquals(434882, artist.getPopularity());
        assertEquals(77, artist.getPlaylists());
        assertEquals("UCI3EFb2lvZyBMykNd64JDhg", artist.getYoutubeURL());

        //todo assert images, avatar, following

    }

    public void testGetTask() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     *
     *
     */
        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";
        final int limit = 20;
        final int offset = 10;

        HummMultipleResult<Artist> result;
        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {

                new HummTask<HummSingleResult<Artist>>(new HummTask.Job() {
                    @Override
                    public Object onStart() throws Exception {
                        return humm.getArtist().get(idArtist, limit, offset);
                    }

                    @Override
                    public void onComplete(Object object) {
                        HummSingleResult<Artist> result = (HummSingleResult<Artist>) object;
                        assertEquals("ok", result.getStatus_response());

                        Artist artist = result.getData_response();
                        assertEquals("Blur", artist.getName());

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
//        assertTrue("ok", true);

    }

    public void testAddFollowers() {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";

        HummSingleResult<Artist> result = humm.getArtist().addFollowers(idArtist);

        assertEquals("ok", result.getStatus_response());

        Artist artist = result.getData_response();
        assertEquals("Blur", artist.getName());

    }

    public void testAddFollowersTask() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     *
     *
     */
        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";

        HummMultipleResult<Artist> result;
        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {

                new HummTask<HummSingleResult<Artist>>(new HummTask.Job() {
                    @Override
                    public Object onStart() throws Exception {
                        return humm.getArtist().addFollowers(idArtist);
                    }

                    @Override
                    public void onComplete(Object object) {
                        HummSingleResult<Artist> result = (HummSingleResult<Artist>) object;
                        assertEquals("ok", result.getStatus_response());

                        Artist artist = result.getData_response();
                        assertEquals("Blur", artist.getName());

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
//        assertTrue("ok", true);

    }

    public void testRemoveFollowers() {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";

        HummSingleResult<Artist> result = humm.getArtist().removeFollowers(idArtist);

        assertEquals("ok", result.getStatus_response());

        Artist artist = result.getData_response();
        assertEquals("Blur", artist.getName());

    }

    public void testRemoveFollowersTask() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     *
     *
     */
        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";

        HummMultipleResult<Artist> result;
        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {

                new HummTask<HummSingleResult<Artist>>(new HummTask.Job() {
                    @Override
                    public Object onStart() throws Exception {
                        return humm.getArtist().removeFollowers(idArtist);
                    }

                    @Override
                    public void onComplete(Object object) {
                        HummSingleResult<Artist> result = (HummSingleResult<Artist>) object;
                        assertEquals("ok", result.getStatus_response());

                        Artist artist = result.getData_response();
                        assertEquals("Blur", artist.getName());

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
//        assertTrue("ok", true);

    }

    public void testGetPlaylists() {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";
        int limit = 10;
        int offset = 0;

        HummMultipleResult<Song> result = humm.getArtist().getPlaylists(idArtist, limit, offset);

        assertEquals("ok", result.getStatus_response());

        assertEquals(10, result.getData_response().size());

    }

    public void testGetRadio() {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";
        int limit = 10;
        int offset = 0;

        HummMultipleResult<Song> result = humm.getArtist().getRadio(idArtist, limit, offset);

        assertEquals("ok", result.getStatus_response());

        assertEquals(10, result.getData_response().size());

    }

    public void testGetSimilar() {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";
        int limit = 10;
        int offset = 0;

        HummMultipleResult<Artist> result = humm.getArtist().getSimilar(idArtist, limit, offset);

        assertEquals("ok", result.getStatus_response());

        assertEquals(10, result.getData_response().size());

    }

    public void testGetFeatured() {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        final String idArtist = "55116991f9c816a0d639ea75";
        int limit = 10;
        int offset = 0;
        final String genre = null;

        HummMultipleResult<Artist> result = humm.getArtist().getFeatured(idArtist, limit, offset, genre);

        assertEquals("ok", result.getStatus_response());

        assertEquals(10, result.getData_response().size());

    }

    public void testGetPopular() {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        int limit = 10;
        int offset = 0;

        HummMultipleResult<Artist> result = humm.getArtist().getPopular(limit, offset);

        assertEquals("ok", result.getStatus_response());

        assertEquals(10, result.getData_response().size());

    }

    public void testRecent() {

        final HummAPI humm = HummAPI.getInstance();
        humm.login("deleteme", "deleteme");

        int limit = 10;
        int offset = 0;

        HummMultipleResult<Artist> result = humm.getArtist().getRecent(limit, offset);

        assertEquals("ok", result.getStatus_response());

        assertEquals(10, result.getData_response().size());

    }


}
