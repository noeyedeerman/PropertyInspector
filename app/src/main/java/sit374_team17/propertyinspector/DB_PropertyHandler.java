package sit374_team17.propertyinspector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.KeyStoreSpi;
import java.util.ArrayList;
import java.util.List;

class DB_PropertyHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PropertyInspectorDatabase";
    private static final String TABLE_PROPERTIES = "properties";
    private static final String KEY_ID = "id";
    private static final String KEY_UNITNUMBER = "unitNumber";
    private static final String KEY_STREETNUMBER = "streetNumber";
    private static final String KEY_STREETNAME = "streetName";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_POSTCODE = "postCode";
    private static final String KEY_TYPE = "type";
    private static final String KEY_BEDROOMS= "bedrooms";
    private static final String KEY_BATHROOMS = "bathrooms";
    private static final String KEY_CARS = "cars";
    private static final String KEY_PRICE= "price";
    private static final String KEY_RENTBUY = "rentBuy";
    private static final String KEY_LEASELENGTH = "leaseLength";
    private static final String KEY_DESCRIPTION = "description";

    DB_PropertyHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROPERTY_TABLE = "CREATE TABLE " + TABLE_PROPERTIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + KEY_UNITNUMBER + " TEXT,"
                + KEY_STREETNUMBER + " TEXT,"
                + KEY_STREETNAME + " TEXT,"
                + KEY_CITY + " TEXT,"
                + KEY_STATE + " TEXT,"
                + KEY_POSTCODE + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_BEDROOMS + " TEXT,"
                + KEY_BATHROOMS + " TEXT,"
                + KEY_CARS + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_RENTBUY + " TEXT,"
                + KEY_LEASELENGTH + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + " TEXT" + ")";
        db.execSQL(CREATE_PROPERTY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);
        onCreate(db);
    }

    void resetTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);

        onCreate(db);
    }

    void addProperty(Property property) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UNITNUMBER, property.getUnitNumber());
        values.put(KEY_STREETNUMBER, property.getStreetNumber());
        values.put(KEY_STREETNAME, property.getStreetName());
        values.put(KEY_CITY, property.getCity());
        values.put(KEY_STATE, property.getState());
        values.put(KEY_POSTCODE, property.getPostCode());
        values.put(KEY_TYPE, property.getType());
        values.put(KEY_BEDROOMS, property.getBedrooms());
        values.put(KEY_BATHROOMS, property.getBathrooms());
        values.put(KEY_CARS, property.getCars());
        values.put(KEY_PRICE, property.getPrice());
        values.put(KEY_RENTBUY, property.getRentBuy());
        values.put(KEY_LEASELENGTH, property.getLeaseLength());
        values.put(KEY_DESCRIPTION, property.getDescription());

        db.insert(TABLE_PROPERTIES, null, values);
        db.close();
    }

    Property getProperty(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        id = id + 1;
        Cursor cursor = db.query(TABLE_PROPERTIES, new String[]{KEY_ID,
                        KEY_UNITNUMBER, KEY_STREETNUMBER, KEY_STREETNAME, KEY_CITY, KEY_STATE, KEY_POSTCODE, KEY_TYPE, KEY_BEDROOMS, KEY_BATHROOMS, KEY_CARS, KEY_PRICE, KEY_RENTBUY, KEY_LEASELENGTH, KEY_DESCRIPTION}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Property property = new Property(Integer.parseInt(cursor.getString(0)) - 1, cursor.getString(1),
                cursor.getString(2), cursor.getString(3), (cursor.getString(4)),
                cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8), cursor.getString(9), cursor.getString(10),
                cursor.getString(11), cursor.getString(12), cursor.getString(13),
                cursor.getString(14));

        return property;
    }

    List<Property> getAllProperties() {
        List<Property> propertyList = new ArrayList<Property>();
        String selectQuery = "SELECT * FROM " + TABLE_PROPERTIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Property property = new Property();
                property.setId(Integer.parseInt(cursor.getString(0)) - 1);
                property.setUnitNumber(cursor.getString(1));
                property.setStreetNumber(cursor.getString(2));
                property.setStreetName(cursor.getString(3));
                property.setCity(cursor.getString(4));
                property.setState(cursor.getString(5));
                property.setPostCode(cursor.getString(6));
                property.setType(cursor.getString(7));
                property.setBedrooms(cursor.getString(8));
                property.setBathrooms(cursor.getString(9));
                property.setCars(cursor.getString(10));
                property.setPrice(cursor.getString(11));
                property.setRentBuy(cursor.getString(12));
                property.setLeaseLength(cursor.getString(13));
                property.setDescription(cursor.getString(14));

                propertyList.add(property);
            } while (cursor.moveToNext());
        }
        return propertyList;
    }

    int updateProperty(Property property) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = property.getId() + 1;

        ContentValues values = new ContentValues();
        values.put(KEY_UNITNUMBER, property.getUnitNumber());
        values.put(KEY_STREETNUMBER, property.getStreetNumber());
        values.put(KEY_STREETNAME, property.getStreetName());
        values.put(KEY_CITY, property.getCity());
        values.put(KEY_STATE, property.getState());
        values.put(KEY_POSTCODE, property.getPostCode());
        values.put(KEY_TYPE, property.getType());
        values.put(KEY_BEDROOMS, property.getBedrooms());
        values.put(KEY_BATHROOMS, property.getBathrooms());
        values.put(KEY_CARS, property.getCars());
        values.put(KEY_PRICE, property.getPrice());
        values.put(KEY_RENTBUY, property.getRentBuy());
        values.put(KEY_LEASELENGTH, property.getLeaseLength());
        values.put(KEY_DESCRIPTION, property.getDescription());

        // updating row
        return db.update(TABLE_PROPERTIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    void deleteGroup(Property property) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = property.getId() + 1;

        db.delete(TABLE_PROPERTIES, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}