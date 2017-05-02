package sit374_team17.propertyinspector;


import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Property implements Parcelable {
    private int id;
    private String unitNumber;
    private String streetNumber;
    private String streetName;
    private String city;
    private String state;
    private String postCode;
    private String type;
    private String bedrooms;
    private String bathrooms;
    private String cars;
    private String price;
    private String rentBuy;
    private String leaseLength;
    private String description;

    private Drawable photo;

    public Property(){};


    public void setId(int id) {this.id = id;}
    public int getId() {
        return id;
    }

    public void setUnitNumber(String unitNumber) {this.unitNumber = unitNumber;}
    public String getUnitNumber() {
        return unitNumber;
    }

    public void setStreetNumber(String streetNumber) {this.streetNumber = streetNumber;}
    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetName(String streetName) {this.streetName = streetName;}
    public String getStreetName() {
        return streetName;
    }

    public void setCity(String city) {this.city = city;}
    public String getCity() {
        return city;
    }

    public void setState(String state) {this.state = state;}
    public String getState() {
        return state;
    }

    public void setPostCode(String postCode) {this.postCode = postCode;}
    public String getPostCode() {
        return postCode;
    }

    public void setType(String state) {this.type = type;}
    public String getType() {
        return type;
    }

    public void setBedrooms(String bedrooms) {this.bedrooms = bedrooms;}
    public String getBedrooms() {
        return bedrooms;
    }

    public void setBathrooms(String bathrooms) {this.bathrooms = bathrooms;}
    public String getBathrooms() {
        return bathrooms;
    }

    public void setCars(String cars) {this.cars = cars;}
    public String getCars() {
        return cars;
    }

    public void setPrice(String price) {this.price = price;}
    public String getPrice() {
        return price;
    }

    public void setRentBuy(String rentBuy) {this.rentBuy = rentBuy;}
    public String getRentBuy() {
        return rentBuy;
    }

    public void setLeaseLength(String leaseLength) {this.leaseLength = leaseLength;}
    public String getLeaseLength() {
        return leaseLength;
    }

    public void setDescription(String description) {this.description = description;}
    public String getDescription() {
        return description;
    }


    public void setPhoto(Drawable photo) {this.photo = photo;}
    public Drawable getPhoto() {
        return photo;
    }


    public String getText() {
        return unitNumber + streetNumber + streetName + city + state + postCode;
    }
    public Property(int id) {
        this.id = id;
      }

    public Property(int id, String unitNumber, String streetNumber, String streetName, String city, String state, String postCode, String type, String bedrooms, String bathrooms, String cars, String price, String rentBuy, String leaseLength, String description) {
        this.id = id;
        this.unitNumber = unitNumber;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
        this.type = type;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.cars = cars;
        this.price = price;
        this.rentBuy = rentBuy;
        this.leaseLength = leaseLength;
        this.description = description;
    }


    public Property(int id, String streetNumber, String streetName, String city, String state, String postCode, String bedrooms, String bathrooms, String cars, String price, Drawable photo) {
        this.id = id;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.cars = cars;
        this.price = price;
        this.photo = photo;
    }




    protected Property(Parcel in) {
        id = in.readInt();
        unitNumber = in.readString();
        streetNumber = in.readString();
        streetName = in.readString();
        city = in.readString();
        state = in.readString();
        postCode = in.readString();
        type = in.readString();
        bedrooms = in.readString();
        bathrooms = in.readString();
        cars = in.readString();
        price = in.readString();
        rentBuy = in.readString();
        leaseLength = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(unitNumber);
        dest.writeString(streetNumber);
        dest.writeString(streetName);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(postCode);
        dest.writeString(type);
        dest.writeString(bedrooms);
        dest.writeString(bathrooms);
        dest.writeString(cars);
        dest.writeString(price);
        dest.writeString(rentBuy);
        dest.writeString(leaseLength);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
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