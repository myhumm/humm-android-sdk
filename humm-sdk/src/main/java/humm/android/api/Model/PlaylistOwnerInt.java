package humm.android.api.Model;

import java.util.HashMap;

/**
 * Created by josealonsogarcia on 27/11/15.
 */
public class PlaylistOwnerInt extends Playlist {

    private int owner;

    public PlaylistOwnerInt(int owner) {
        this.owner = owner;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    @Override
    public String getOwnerAvatar() {
        return null;
    }

    @Override
    public String getOwnerName() {
        return null;
    }

    @Override
    public String getOwnerUid() {
        return null;
    }
}
