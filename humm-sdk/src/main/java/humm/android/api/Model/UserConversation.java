package humm.android.api.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by josealonsogarcia on 13/2/17.
 */

public class UserConversation {

    private HashMap<String, String> _id;
    private ArrayList<Message> messages;
    private int num_unread;

    public HashMap<String, String> get_id() {
        return _id;
    }

    public void set_id(HashMap<String, String> _id) {
        this._id = _id;
    }

    public String getUid()
    {
        if (_id != null)
        {
            return _id.get("uid");
        }

        return null;
    }

    public String getUname()
    {
        if (_id != null)
        {
            return _id.get("uname");
        }

        return null;

    }
    public String getAvatar()
    {
        if (_id != null)
        {
            return _id.get("uavatar");
        }

        return null;

    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public int getNum_unread() {
        return num_unread;
    }

    public void setNum_unread(int num_unread) {
        this.num_unread = num_unread;
    }
}
