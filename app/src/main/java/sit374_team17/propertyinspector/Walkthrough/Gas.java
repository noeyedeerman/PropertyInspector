package sit374_team17.propertyinspector.Walkthrough;

import android.os.Parcel;
import android.os.Parcelable;

public class Gas implements Parcelable {
    private String id;
    private String name;
    private String pitch;
    private String info;


    public Gas(String id, String name, String pitch, String info) {
        this.id = id;
        this.name = name;
        this.pitch = pitch;
        this.info = info;
    }

    public void setId(String id) {this.id = id;}
    public String getId() {
        return id;
    }

    public void setName(String name) {this.name = name;}
    public String getName() {
        return name;
    }

    public void setPitch(String pitch) {this.pitch = pitch;}
    public String getPitch() {
        return pitch;
    }

    public void setInfo(String info) {this.info = info;}
    public String getInfo() {
        return info;
    }


    protected Gas(Parcel in) {
        id = in.readString();
        name = in.readString();
        pitch = in.readString();
        info = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(pitch);
        dest.writeString(info);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Gas> CREATOR = new Creator<Gas>() {
        @Override
        public Gas createFromParcel(Parcel in) {
            return new Gas(in);
        }

        @Override
        public Gas[] newArray(int size) {
            return new Gas[size];
        }
    };


}
