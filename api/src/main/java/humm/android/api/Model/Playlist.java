package humm.android.api.Model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by josealonsogarcia on 16/9/15.
 */
public class Playlist extends Humm {

    public static String ENDPOINT_COVER = "http://wave.livingindietv.com/images/playlist?id=%s&size=thumb";

    private String title;
    private String description;
    private List<HashMap> owner;
    private HashMap stats;

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

    public List<HashMap> getOwner() {
        return owner;
    }

    public void setOwner(List<HashMap> owner) {
        this.owner = owner;
    }

    public HashMap getStats() {
        return stats;
    }

    public void setStats(HashMap stats) {
        this.stats = stats;
    }


    public int getSongs()
    {
        if (this.stats != null)
        {
//            return ((Double)stats.get("songs")).intValue();
            return 0;
        }

        return 0;
    }

    public String getAvatar()
    {
        if (this.getOwner() != null && this.getOwner().get(0) != null)
        {
            return (String)this.getOwner().get(0).get("avatar");
        }
        return null;
    }

    public String getOwnerName()
    {
        if (this.getOwner() != null && this.getOwner().get(0) != null)
        {
            return (String)this.getOwner().get(0).get("name");
        }
        return null;
    }

    public String getCover()
    {
//        return "http://wave.livingindietv.com/images/playlist?id=" + this.get_id() + "&size=thumb";
        return String.format(ENDPOINT_COVER, this.get_id());
    }
}
