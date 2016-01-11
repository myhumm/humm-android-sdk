package humm.android.api.Model;

import java.util.HashMap;

/**
 * Created by josealonsogarcia on 27/11/15.
 */
public class PlaylistOwnerHashMap extends Playlist {

    protected HashMap<String, String> owner;

    public PlaylistOwnerHashMap() {
    }

    public HashMap<String, String> getOwner() {
        return owner;
    }

    public void setOwner(HashMap<String, String> owner) {
        this.owner = owner;
    }

    public String getOwnerAvatar() {
        if (this.owner != null && this.owner.get("avatar") != null) {
            return this.owner.get("avatar");
        }
        return null;
    }

    public String getOwnerName() {
        if (this.owner != null && this.owner.get("name") != null) {
            return this.owner.get("name");
        }
        return null;
    }

    public String getOwnerUid() {
        if (this.owner != null && this.owner.get("uid") != null) {
            return this.owner.get("uid");
        }
        return null;
    }


}
