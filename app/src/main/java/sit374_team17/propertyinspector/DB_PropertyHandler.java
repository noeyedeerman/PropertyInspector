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
    private static final String DATABASE_NAME = "PropertyDatabase";
    private static final String TABLE_PROPERTIES = "properties";
    private static final String KEY_ID = "id";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_BEDROOMS= "bedrooms";
    private static final String KEY_BATHROOMS = "bathrooms";
    private static final String KEY_GARAGES = "garages";
    private static final String KEY_PRICE= "price";
    private static final String KEY_STATUS = "status";
    private static final String KEY_CRITERIA = "criteria";

    DB_PropertyHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROPERTIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY NOT NULL," + KEY_ADDRESS + " TEXT NOT NULL," + KEY_BEDROOMS + " TEXT,"  + KEY_BATHROOMS + " TEXT,"  + KEY_GARAGES + " TEXT,"  + KEY_PRICE + " TEXT,"  + KEY_STATUS + " TEXT,"  + KEY_CRITERIA+ " TEXT,"
                + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);
        onCreate(db);
    }

    void addProperty(Property property) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS, property.get_address());
        values.put(KEY_BEDROOMS, property.get_bedrooms());
        values.put(KEY_BATHROOMS, property.get_bathrooms());
        values.put(KEY_GARAGES, property.get_garages());
        values.put(KEY_PRICE, property.get_price());
        values.put(KEY_STATUS, property.get_status());
        values.put(KEY_CRITERIA, property.get_criteria());

        db.insert(TABLE_PROPERTIES, null, values);
        db.close();
    }

    Property getProperty(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        id = id + 1;
        Cursor cursor = db.query(TABLE_PROPERTIES, new String[]{KEY_ID,
                        KEY_ADDRESS, KEY_BEDROOMS, KEY_BATHROOMS, KEY_GARAGES, KEY_PRICE, KEY_STATUS, KEY_CRITERIA}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();



        Property contact = new Property(Integer.parseInt(cursor.getString(0)) - 1, cursor.getString(1),
                cursor.getString(2), cursor.getString(3), (cursor.getString(4)),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));


        return contact;
    }

    List<Property> getAllProperties() {
        List<Property> propertyList = new ArrayList<Property>();
        String selectQuery = "SELECT * FROM " + TABLE_PROPERTIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Property property = new Property();
                property.set_id(Integer.parseInt(cursor.getString(0)) - 1);
                property.set_address(cursor.getString(1));
                property.set_bedrooms(cursor.getString(2));
                property.set_bathrooms(cursor.getString(3));
                property.set_garages(cursor.getString(4));
                property.set_price(cursor.getString(5));
                property.set_status(cursor.getString(6));
                property.set_criteria(cursor.getString(7));;

                propertyList.add(property);
            } while (cursor.moveToNext());
        }

        return propertyList;
    }

    public int getPropertiesCount() {
        String countQuery = "SELECT * FROM " + TABLE_PROPERTIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    int updateProperty(Property property) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = property.get_id() + 1;

        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS, property.get_address());

        // updating row
        return db.update(TABLE_PROPERTIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    void deleteGroup(Property property) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = property.get_id() + 1;

        db.delete(TABLE_PROPERTIES, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}