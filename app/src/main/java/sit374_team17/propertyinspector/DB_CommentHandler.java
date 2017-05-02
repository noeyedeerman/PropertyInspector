package sit374_team17.propertyinspector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

class DB_CommentHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PropertyInspectorDatabase";
    private static final String TABLE_COMMENTS = "comments";
    private static final String KEY_ID = "id";
    private static final String KEY_USERID = "userId";
    private static final String KEY_PROPERTYID = "propertyId";
    private static final String KEY_ISPUBLIC = "isPublic";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PHOTO = "photo";


    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }

    DB_CommentHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_COMMENTS_TABLE = "CREATE TABLE " + TABLE_COMMENTS + "("
//                + KEY_ID + " INTEGER PRIMARY KEY NOT NULL,"
//                + KEY_USERID + " INTEGER,"
//                + KEY_PROPERTYID + " INTEGER,"
//                + KEY_ISPUBLIC + " BOOLEAN,"
//                + KEY_DESCRIPTION + " TEXT,"
//              //  + KEY_PHOTO + " BLOB,"
//                + " TEXT" + ")";
//        db.execSQL(CREATE_COMMENTS_TABLE);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COMMENT_TABLE = "CREATE TABLE " + TABLE_COMMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + KEY_USERID + " TEXT,"
                + KEY_PROPERTYID + " TEXT,"
                + KEY_ISPUBLIC + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + " TEXT" + ")";
        db.execSQL(CREATE_COMMENT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

    void addComment(Comment comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] photoByte = getBitmapAsByteArray(comment.getPhoto());

        ContentValues values = new ContentValues();
        values.put(KEY_ID, comment.getId());
        values.put(KEY_USERID, comment.getUserId());
        values.put(KEY_PROPERTYID, comment.getPropertyId());
        values.put(KEY_ISPUBLIC, comment.getIsPublic());
        values.put(KEY_DESCRIPTION, comment.getDescription());
       // values.put(KEY_PHOTO, photoByte);

        db.insert(TABLE_COMMENTS, null, values);
        db.close();
    }

    Comment getComment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        id = id + 1;
        Cursor cursor = db.query(TABLE_COMMENTS, new String[]{KEY_ID,
                        KEY_ID, KEY_USERID, KEY_PROPERTYID, KEY_ISPUBLIC, KEY_DESCRIPTION}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

      //  byte[] photoByte = cursor.getBlob(id);
     //   Bitmap photo = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);

        Comment comment = new Comment(Integer.parseInt(cursor.getString(0)) - 1, Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)), Boolean.parseBoolean(cursor.getString(3)), cursor.getString(4));

        return comment;
    }

    List<Comment> getAllComments() {
        List<Comment> commentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_COMMENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Comment comment = new Comment();
                comment.setId(Integer.parseInt(cursor.getString(0)) - 1);
                comment.setUserId(Integer.parseInt(cursor.getString(1)));
                comment.setPropertyId(Integer.parseInt(cursor.getString(2)));
                comment.setIsPublic(Boolean.parseBoolean(cursor.getString(2)));
                comment.setDescription(cursor.getString(3));

               // byte[] photoByte = cursor.getBlob(4);
                //Bitmap photo = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);


              //  comment.setPhoto(photo);


                commentList.add(comment);
            } while (cursor.moveToNext());
        }
        return commentList;
    }

    int updateComment(Comment comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = comment.getId() + 1;

        ContentValues values = new ContentValues();
        values.put(KEY_ID, comment.getId());
        values.put(KEY_USERID, comment.getUserId());
        values.put(KEY_PROPERTYID, comment.getPropertyId());
        values.put(KEY_ISPUBLIC, comment.getIsPublic());
        values.put(KEY_DESCRIPTION, comment.getDescription());

      //  byte[] photoByte = getBitmapAsByteArray(comment.getPhoto());
      //  values.put(KEY_PHOTO, photoByte);

        // updating row
        return db.update(TABLE_COMMENTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    void deleteComment(Comment comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = comment.getId() + 1;

        db.delete(TABLE_COMMENTS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}