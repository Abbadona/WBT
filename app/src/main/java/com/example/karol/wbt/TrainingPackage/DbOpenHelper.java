package com.example.karol.wbt.TrainingPackage;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.HashMap;

public class DbOpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "wild_boar_db";
    // private static String DB_PATH = "";
    private static final int DB_VERSION = 1;
    private static final String TABLE_TRAININGS = "trainings";
    private static final String TABLE_TRAINING_EXERCISES = "training_exercises";
    public static final String COL_TRAINING_ID = "training_id";
    public static final String COL_LOGIN = "login";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_EXERCISE_ID = "exercise_id";
    public static final String COL_REPLACEMENT_GROUP = "replacement_group";
    public static final String COL_VIDEO_LINK = "video_link";
    private Context context;

    public DbOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        // DB_PATH = context.getApplicationInfo().dataDir +context.getPackageName() +"/databases/";
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_TRAININGS +"("
                            +COL_TRAINING_ID +" TEXT PRIMARY KEY NOT NULL,"
                            +COL_LOGIN +" TEXT NOT NULL,"
                            +COL_NAME +" TEXT NOT NULL,"
                            +COL_DESCRIPTION +" TEXT NOT NULL"
                        +")");

        database.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_TRAINING_EXERCISES +"("
                            +COL_TRAINING_ID +" TEXT NOT NULL,"
                            +COL_EXERCISE_ID +" TEXT PRIMARY KEY NOT NULL,"
                            +COL_REPLACEMENT_GROUP +" TEXT NOT NULL,"
                            +COL_NAME +" TEXT NOT NULL,"
                            +COL_DESCRIPTION +" TEXT NOT NULL,"
                            +COL_VIDEO_LINK +" TEXT NOT NULL"
                        +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " +TABLE_TRAININGS);
        database.execSQL("DROP TABLE IF EXISTS " +TABLE_TRAINING_EXERCISES);
        onCreate(database);
    }

    public void saveTrainingToDatabase(HashMap data){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_TRAINING_ID, data.get("trainind_id").toString());
        contentValues.put(COL_LOGIN, "Jak to wziąć?"); // TODO
        contentValues.put(COL_NAME, "Gdzie to w ogóle jest?"); // TODO
        contentValues.put(COL_DESCRIPTION, data.get("description").toString());
        database.insert(TABLE_TRAININGS, null, contentValues);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " +TABLE_TRAININGS, null);
    }
}
