package humm.android.api.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josealonsogarcia on 18/11/15.
 */
public class LoginInfo extends Humm implements Parcelable {

    private String access_token;
    private int expires_in;
    private String token_type;
    private String refresh_token;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getAccess_token());
        dest.writeString(getToken_type());
        dest.writeString(getRefresh_token());
        dest.writeString(getScope());
        dest.writeInt(getExpires_in());

    }

    public static final Parcelable.Creator<LoginInfo> CREATOR
            = new Parcelable.Creator<LoginInfo>() {
        public LoginInfo createFromParcel(Parcel in) {
            return new LoginInfo(in);
        }

        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };

    private LoginInfo(Parcel in) {
        access_token = in.readString();
        token_type = in.readString();
        refresh_token = in.readString();
        scope = in.readString();
        expires_in = in.readInt();
    }
}
