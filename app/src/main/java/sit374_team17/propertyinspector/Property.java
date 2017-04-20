package sit374_team17.propertyinspector;


public class Property {
    private int _id;
    private String _address;
    private String _image;
    private int _bedrooms;
    private int _bathrooms;
    private int _garages;
    private int _price;
    private String _status;
    private String _criteria;

    public int get_id() {
        return _id;
    }

    public String get_address() {
        return _address;
    }
    public void set_address(String address) {this._address = address;}

    public String get_image() {
        return _image;
    }
    public void set_image(String _image) {this._image = _image;}

    public int get_bedrooms() {
        return _bedrooms;
    }
    public void set_bedrooms(int _bathrooms) {this._bedrooms = _bedrooms;}

    public int get_bathrooms() {
        return _bathrooms;
    }
    public void set_bathrooms(int _bathrooms) {this._bathrooms = _bathrooms;}

    public int get_garages() {
        return _garages;
    }
    public void set_garages(int _garages) {this._garages = _garages;}

    public int get_price() {
        return _price;
    }
    public void set_price(int _price) {this._price = _price;}

    public String get_status() {
        return _status;
    }
    public void set_status(String _status) {this._status = _status;}

    public String get_criteria() {
        return _criteria;
    }
    public void set_criteria(String _criteria) {this._criteria = _criteria;}

    public Property(int id, String address, String image, int bedrooms, int bathrooms, int garages, int price, String status, String criteria) {
        _id = _id;
        _address = address;
        _image = image;
        _bedrooms = bedrooms;
        _bathrooms = bathrooms;
        _garages = garages;
        _price = price;
        _status = status;
        _criteria = criteria;
    }
}