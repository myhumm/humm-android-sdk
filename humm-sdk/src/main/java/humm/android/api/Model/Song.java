package humm.android.api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import humm.android.api.HummAPI;


/**
 * Created by josealonsogarcia on 16/9/15.
 */
public class Song extends Humm implements Parcelable {

    protected String title;
    protected String description;
    protected String type;
    protected String date;
    protected HashMap urls;
    protected List<HashMap<String, String>> artists;
    protected List<HashMap> playlists;
    protected HashMap<String, String> foreign_ids;
    protected HashMap<String, String> stats;
    protected List contributors;
    protected List stories;
    protected List genres;

    public Song(String _id, String title, String description, String type, String date, HashMap urls, List<HashMap<String, String>> artists, List<HashMap> playlists, HashMap<String, String> foreign_ids, HashMap<String, String> stats, List contributors, List stories, List genres) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.urls = urls;
        this.artists = artists;
        this.playlists = playlists;
        this.foreign_ids = foreign_ids;
        this.stats = stats;
        this.contributors = contributors;
        this.stories = stories;
        this.genres = genres;
    }

    public Song() {
    }

    public static String ENDPOINT_COVER = "http://wave.livingindietv.com/images/playlist?id=%s&size=thumb";


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap getUrls() {
        return urls;
    }

    public void setUrls(HashMap urls) {
        this.urls = urls;
    }

    public List<HashMap<String, String>> getArtists() {
        return artists;
    }

    public void setArtists(List<HashMap<String, String>> artists) {
        this.artists = artists;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPlaylists(List<HashMap> playlists) {
        this.playlists = playlists;
    }

    public HashMap<String, String> getForeign_ids() {
        return foreign_ids;
    }

    public void setForeign_ids(HashMap<String, String> foreign_ids) {
        this.foreign_ids = foreign_ids;
    }

    public HashMap<String, String> getStats() {
        return stats;
    }

    public void setStats(HashMap<String, String> stats) {
        this.stats = stats;
    }

    public List getContributors() {
        return contributors;
    }

    public void setContributors(List contributors) {
        this.contributors = contributors;
    }

    public List getStories() {
        return stories;
    }

    public void setStories(List stories) {
        this.stories = stories;
    }

    public List getGenres() {
        return genres;
    }

    public void setGenres(List genres) {
        this.genres = genres;
    }

    public String getArtistName() {
        if (this.artists != null && artists.get(0) != null) {
            return (String) artists.get(0).get("name");
        }
        return null;
    }

    public String getAvatar() {
        if (this.artists != null && artists.get(0) != null) {
            return (String) artists.get(0).get("avatar");
        }
        return null;

    }

    public List<HashMap> getPlaylists() {
        return playlists;
    }

    public String getYoutubeURL() {
        if (this.urls != null && this.urls.get("youtube") != null) {
            return (String) this.urls.get("youtube");
        }
        return null;
    }

    public String getYoutubeId() {
        if (this.foreign_ids != null && this.foreign_ids.get("youtube") != null) {
            return this.foreign_ids.get("youtube");
        }
        return null;
    }

    public String getPopularity() {
        if (this.stats != null && this.stats.get("popularity") != null) {
            return this.stats.get("popularity");
        }
        return null;
    }

    public String getStatsPlaylists() {
        if (this.stats != null && this.stats.get("playlists") != null) {
            return this.stats.get("playlists");
        }
        return null;
    }

    public String getPlaylistCover() {
        if (this.playlists != null && this.playlists.size() > 0 && playlists.get(0) != null) {
            String idPlaylist = (String) playlists.get(0).get("pid");

//            return "http://wave.livingindietv.com/images/playlist?id=" + idPlaylist + "&size=thumb";
            return String.format(ENDPOINT_COVER, idPlaylist);
        }
        return null;

    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Song)) return false;

        Song otherSong = (Song) o;
        return this.get_id().equals(otherSong.get_id());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(get_id());
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeList(artists);
        dest.writeList(playlists);
        dest.writeMap(urls);
        dest.writeMap(foreign_ids);
        dest.writeMap(stats);
    }

    public static final Parcelable.Creator<Song> CREATOR
            = new Parcelable.Creator<Song>() {
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    private Song(Parcel in) {
        set_id(in.readString());
        title = in.readString();
        description = in.readString();
        type = in.readString();
        artists = new ArrayList<HashMap<String, String>>();
        in.readList(artists, null);
        playlists = new ArrayList<HashMap>();
        in.readList(playlists, null);
        urls = new HashMap();
        in.readMap(urls, null);
        foreign_ids = new HashMap<String, String>();
        in.readMap(foreign_ids, null);
        stats = new HashMap<String, String>();
        in.readMap(stats, null);
    }

}
