package humm.android.api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by josealonsogarcia on 23/9/16.
 */

public class Channel extends Humm implements Parcelable {

    protected String name;
    protected String description;
    protected String slug;
    protected ArrayList<Message> messages;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(get_id());
        dest.writeString(name);
        dest.writeString(description);
    }


    public static final Parcelable.Creator<Channel> CREATOR
            = new Parcelable.Creator<Channel>() {
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    private Channel(Parcel in) {
        set_id(in.readString());
        name = in.readString();
        description = in.readString();
    }

}
