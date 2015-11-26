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


    private String title;
    private String description;
    private String type;
    private HashMap urls;
    private List<HashMap> artists;
    private List<HashMap> playlists;

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

    public List<HashMap> getArtists() {
        return artists;
    }

    public void setArtists(List<HashMap> artists) {
        this.artists = artists;
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

    public String getPlaylistCover() {
        if (this.playlists != null && playlists.get(0) != null) {
            String idPlaylist = (String) playlists.get(0).get("pid");

//            return "http://wave.livingindietv.com/images/playlist?id=" + idPlaylist + "&size=thumb";
            return String.format(ENDPOINT_COVER, idPlaylist);
        }
        return null;

    }

    public String getYoutubeURL()
    {
        if (this.urls != null && this.urls.get("youtube") != null)
        {
            return (String) this.urls.get("youtube");
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if ( !(o instanceof Song) ) return false;

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
        artists = new ArrayList<HashMap>();
        in.readList(artists, null);
        playlists = new ArrayList<HashMap>();
        in.readList(playlists, null);
        urls = new HashMap();
        in.readMap(urls, null);
    }

}
