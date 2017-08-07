package humm.android.api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by josealonsogarcia on 6/10/16.
 */

public class Message extends Humm implements Parcelable {

        /*
    {"__v":0,
    "message":"<p>test<\/p>\n",
    "type":"text",
    "_id":"57f64116ac6a58884324c405",
    "edit":"2016-10-06T12:18:30.670Z",
    "tags":[],
    "reply_to":[],
    "links":[],
    "channel_mentions":[],
    "group_mentions":[],
    "mentions":[],
    "channel":{"cid":"56e64d692982cce847cb116d","cname":"Sandbox"},
    "attributes":{"word_count":3},
    "fav":[],
    "owner":{"uid":"5661783496a4f4e521fe3f65"},
    "date":"2016-10-06T12:18:30.670Z"}
    */

    private String message;
    private String type;
    private Date date;
    private HashMap attributes;
    private HashMap owner;
    private HashMap channel;
    private HashMap to;
    private ArrayList tags;
    private ArrayList reply_to;
    private ArrayList links;
    private ArrayList channel_mentions;
    private ArrayList group_mentions;
    private ArrayList mentions;
    private ArrayList fav;

    public HashMap getTo() {
        return to;
    }

    public void setTo(HashMap to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashMap getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap attributes) {
        this.attributes = attributes;
    }

    public HashMap getOwner() {
        return owner;
    }

    public void setOwner(HashMap owner) {
        this.owner = owner;
    }

    public HashMap getChannel() {
        return channel;
    }

    public void setChannel(HashMap channel) {
        this.channel = channel;
    }

    public ArrayList getTags() {
        return tags;
    }

    public void setTags(ArrayList tags) {
        this.tags = tags;
    }

    public ArrayList getReply_to() {
        return reply_to;
    }

    public void setReply_to(ArrayList reply_to) {
        this.reply_to = reply_to;
    }

    public ArrayList getLinks() {
        return links;
    }

    public void setLinks(ArrayList links) {
        this.links = links;
    }

    public ArrayList getChannel_mentions() {
        return channel_mentions;
    }

    public void setChannel_mentions(ArrayList channel_mentions) {
        this.channel_mentions = channel_mentions;
    }

    public ArrayList getGroup_mentions() {
        return group_mentions;
    }

    public void setGroup_mentions(ArrayList group_mentions) {
        this.group_mentions = group_mentions;
    }

    public ArrayList getMentions() {
        return mentions;
    }

    public void setMentions(ArrayList mentions) {
        this.mentions = mentions;
    }

    public ArrayList getFav() {
        return fav;
    }

    public void setFav(ArrayList fav) {
        this.fav = fav;
    }

    public String getOwnerId() {
        if (this.owner != null) {
            return (String) this.owner.get("uid");
        }
        return null;
    }

    public String getOwnerName() {
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

    public String getChannelId() {
        if (this.channel != null) {
            return (String) this.channel.get("cid");
        }
        return null;

    }

    public String getToId() {
        if (this.to != null) {
            return (String) this.to.get("uid");
        }
        return null;

    }

    public boolean getToReaded()
    {
        if (this.to != null && this.to.get("read") != null)
        {
            return (boolean) this.to.get("read");
        }
        return false;
    }

    public String getAttributesName() {
        if (this.attributes != null && this.attributes.get("name") != null) {
            return (String) this.attributes.get("name");
        }
        return null;
    }

    public String getAttributesArtist() {
        if (this.attributes != null && this.attributes.get("artist") != null) {
            return (String) this.attributes.get("artist");
        }
        return null;
    }

    public String getAttributesAvatar() {
        if (this.attributes != null && this.attributes.get("avatar") != null) {
            return (String) this.attributes.get("avatar");
        }
        return null;
    }

    public String getAttributesBackdrop() {
        if (this.attributes != null && this.attributes.get("backdrop") != null) {
            return (String) this.attributes.get("backdrop");
        }
        return null;
    }

    public String getAttributesURL() {
        if (this.attributes != null && this.attributes.get("url") != null) {
            return (String) this.attributes.get("url");
        }
        return null;
    }

    public String getAttributesId() {
        if (this.attributes != null && this.attributes.get("id") != null) {
            return (String) this.attributes.get("id");
        }
        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

//    private String message;
//    private String type;
//    private Date date;
//    private HashMap attributes;
//    private HashMap owner;
//    private HashMap channel;
//    private HashMap to;
//    private ArrayList tags;
//    private ArrayList reply_to;
//    private ArrayList links;
//    private ArrayList channel_mentions;
//    private ArrayList group_mentions;
//    private ArrayList mentions;
//    private ArrayList fav;
//

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(get_id());
        dest.writeString(message);
        dest.writeString(type);
        dest.writeSerializable(date);
        dest.writeMap(attributes);
        dest.writeMap(owner);
        dest.writeMap(channel);
        dest.writeMap(to);
        dest.writeList(tags);
        dest.writeList(reply_to);
        dest.writeList(links);
        dest.writeList(channel_mentions);
        dest.writeList(group_mentions);
        dest.writeList(mentions);
        dest.writeList(fav);

    }

    public static final Parcelable.Creator<Message> CREATOR
            = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    private Message(Parcel in) {
        set_id(in.readString());
        message = in.readString();
        type = in.readString();
        date = (java.util.Date) in.readSerializable();
        attributes = new HashMap();
        in.readMap(attributes, null);
        owner = new HashMap();
        in.readMap(owner, null);
        channel = new HashMap();
        in.readMap(channel, null);
        to = new HashMap();
        in.readMap(to, null);
        tags = new ArrayList<>();
        in.readList(tags, null);
        reply_to = new ArrayList<>();
        in.readList(reply_to, null);
        links = new ArrayList<>();
        in.readList(links, null);
        channel_mentions = new ArrayList<>();
        in.readList(channel_mentions, null);
        group_mentions = new ArrayList<>();
        in.readList(group_mentions, null);
        mentions = new ArrayList<>();
        in.readList(mentions, null);
        fav = new ArrayList<>();
        in.readList(fav, null);
    }

}
