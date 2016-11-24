package humm.android.api.Model;

/**
 * Created by josealonsogarcia on 24/11/16.
 */

public class Member {

    protected String uavatar;
    protected String uname;
    protected String uid;
    protected boolean online;
//    protected Date last_visit;

    public String getUavatar() {
        return uavatar;
    }

    public void setUavatar(String uavatar) {
        this.uavatar = uavatar;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

//    public Date getLast_visit() {
//        return last_visit;
//    }
//
//    public void setLast_visit(Date last_visit) {
//        this.last_visit = last_visit;
//    }
}
