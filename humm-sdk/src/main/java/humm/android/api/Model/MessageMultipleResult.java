package humm.android.api.Model;

import java.util.ArrayList;

/**
 * Created by josealonsogarcia on 27/9/16.
 */

public class MessageMultipleResult<Message> {

    private String status;
    private ArrayList<Message> messages;

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
