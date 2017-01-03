package humm.android.api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by josealonsogarcia on 23/9/16.
 */

public class Channel extends Humm implements Parcelable {

    protected String name;
    protected String description;
    protected String slug;
    //    protected Date date;
    protected ArrayList<Message> messages;
    protected ArrayList<Member> members;
    protected HashMap owner;
    protected HashMap stats;
    protected int messagesNotReaded;


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

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public HashMap getOwner() {
        return owner;
    }

    public void setOwner(HashMap owner) {
        this.owner = owner;
    }

    public int getMessagesNotReaded() {
        return messagesNotReaded;
    }

    public void setMessagesNotReaded(int messagesNotReaded) {
        this.messagesNotReaded = messagesNotReaded;
    }

    public String getOwnerUsername() {
        if (this.owner != null) {
            return (String) this.owner.get("uname");
        }

        return null;
    }

    public String getOwnerAvatar() {
        if (this.owner != null) {
            return (String) this.owner.get("uavatar");
        }

        return null;
    }

    public String getOwnerId() {
        if (this.owner != null) {
            return (String) this.owner.get("uid");
        }

        return null;
    }

    public HashMap getStats() {
        return stats;
    }

    public void setStats(HashMap stats) {
        this.stats = stats;
    }

    public int getStatsMembers() {
        if (this.stats != null) {
            return ((Double) this.stats.get("members")).intValue();
        }

        return 0;
    }

    public int getStatsPopularity() {
        if (this.stats != null) {
            return ((Double) this.stats.get("popularity")).intValue();
        }

        return 0;
    }

    public int getStatsMessages() {
        if (this.stats != null) {
            return ((Double) this.stats.get("messages")).intValue();
        }

        return 0;
    }

    public int getStatsDocs() {
        if (this.stats != null) {
            return ((Double) this.stats.get("docs")).intValue();
        }

        return 0;
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
        dest.writeInt(messagesNotReaded);
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
        messagesNotReaded = in.readInt();
    }

}
