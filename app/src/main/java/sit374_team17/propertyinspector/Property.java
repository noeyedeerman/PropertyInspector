package sit374_team17.propertyinspector;


import android.os.Parcel;
import android.os.Parcelable;

public class Property implements Parcelable {
    private int _id;
    private String _address;
    //private String _image;
    private String _bedrooms;
    private String _bathrooms;
    private String _garages;
    private String _price;
    private String _status;
    private String _criteria;

    public Property(){};

    public void set_id(int id) {this._id = id;}
    public int get_id() {
        return _id;
    }

    public void set_address(String address) {this._address = address;}
    public String get_address() {
        return _address;
    }


    //public void set_image(String _image) {this._image = _image;}
    //public String get_image() {
    //    return _image;
    //}

    public void set_bedrooms(String _bedrooms) {this._bedrooms = _bedrooms;}
    public String get_bedrooms() {
        return _bedrooms;
    }

    public void set_bathrooms(String _bathrooms) {this._bathrooms = _bathrooms;}
    public String get_bathrooms() {
        return _bathrooms;
    }

    public void set_garages(String _garages) {this._garages = _garages;}
    public String get_garages() {
        return _garages;
    }

    public void set_price(String _price) {this._price = _price;}
    public String get_price() {
        return _price;
    }

    public void set_status(String _status) {this._status = _status;}
    public String get_status() {
        return _status;
    }

    public void set_criteria(String _criteria) {this._criteria = _criteria;}
    public String get_criteria() {
        return _criteria;
    }

    public Property(int id) {
        _id = id;
      }

    public Property(int id, String address, String bedrooms, String bathrooms, String garages, String price, String status, String criteria) {
        _id = id;
        _address = address;
        //_image = image;
        _bedrooms = bedrooms;
        _bathrooms = bathrooms;
        _garages = garages;
        _price = price;
        _status = status;
        _criteria = criteria;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(_address);
        //dest.writeString(_image);
        dest.writeString(_bedrooms);
        dest.writeString(_bathrooms);
        dest.writeString(_garages);
        dest.writeString(_price);
        dest.writeString(_status);
        dest.writeString(_criteria);
    }

    protected Property(Parcel in) {
        _id = in.readInt();
        _address = in.readString();
        //_image = in.readString();
        _bedrooms = in.readString();
        _bathrooms = in.readString();
        _garages = in.readString();
        _price = in.readString();
        _status = in.readString();
        _criteria = in.readString();
    }

    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

}