package sit374_team17.propertyinspector;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

class Comment implements Parcelable {

    private int id;
    private int userId;
    private int propertyId;
    private boolean isPublic;
    private String description;
    private Bitmap photo;

    public Comment() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Comment(int id, int userId, int propertyId, boolean isPublic, String description) {
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.isPublic = isPublic;
        this.description = description;

    }


    public Comment(int id, int userId, int propertyId, String description, Bitmap photo) {
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.description = description;
        this.photo = photo;
    }



    protected Comment(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        propertyId = in.readInt();
        isPublic = in.readByte() != 0;
        description = in.readString();
        photo = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeInt(propertyId);
        dest.writeByte((byte) (isPublic ? 1 : 0));
        dest.writeString(description);
        dest.writeParcelable(photo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
