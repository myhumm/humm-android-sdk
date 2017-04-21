package humm.android.api.Model;

import java.util.ArrayList;

/**
 * Created by josealonsogarcia on 18/10/16.
 */

public class DMConversationsMultipleResult<UserConversation> {

    private String status;
    private ArrayList<UserConversation> messages;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<UserConversation> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<UserConversation> messages) {
        this.messages = messages;
    }
}
