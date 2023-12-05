package com.pets.dog.cat.petmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProfilesDatabaseAdapter {
    private ProfilesDatabaseAdapter.DatabaseHelper profilesDatabaseHelper;
    private static final String DATABASE_NAME = "dba_profiles";
    private static final int DATABASE_VERSION = 1;
    private final Context pDAContext;
    private SQLiteDatabase profilesDatabase;

    private static final String TABLE_PROFILE = "tbl_prf";

    private static final String COL_PROFILE_ID = "prf_id";
    private static final String COL_PROFILE_NAME = "prf_nme";
    private static final String COL_PROFILE_PPU = "prf_ppu";
    private static final String COL_PROFILE_WEIGHT = "prf_wgt";
    private static final String COL_PROFILE_GENDER = "prf_gdr";
    private static final String COL_PROFILE_BREED = "prf_brd";
    private static final String COL_PROFILE_DOB = "prf_dob";

    private static final int INDEX_PROFILE_ID = 0;
    private static final int INDEX_PROFILE_NAME = INDEX_PROFILE_ID + 1;
    private static final int INDEX_PROFILE_PPU = INDEX_PROFILE_ID + 2;
    private static final int INDEX_PROFILE_WEIGHT = INDEX_PROFILE_ID + 3;
    private static final int INDEX_PROFILE_GENDER = INDEX_PROFILE_ID + 4;
    private static final int INDEX_PROFILE_BREED = INDEX_PROFILE_ID + 5;
    private static final int INDEX_PROFILE_DOB = INDEX_PROFILE_ID + 6;

    private static final String CREATE_TABLE_PROFILE =
            "CREATE TABLE if not exists " + TABLE_PROFILE + " ( " +
                    COL_PROFILE_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_PROFILE_NAME + " TEXT, " +
                    COL_PROFILE_PPU + " TEXT, " +
                    COL_PROFILE_WEIGHT + " INTEGER, " +
                    COL_PROFILE_GENDER + " INTEGER, " +
                    COL_PROFILE_BREED + " TEXT, " +
                    COL_PROFILE_DOB + " TEXT );";

    private static final String TABLE_USER = "tbl_usr";

    private static final String COL_USER_ID = "_id";
    private static final String COL_USER_NAME = "usr_nme";

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE if not exists " + TABLE_USER + " ( " +
                    COL_USER_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_USER_NAME + " INTEGER );";

    protected ProfilesDatabaseAdapter(Context pDAContext) {
        this.pDAContext = pDAContext;
    }


    protected void open() throws SQLException {
        profilesDatabaseHelper = new ProfilesDatabaseAdapter.DatabaseHelper(pDAContext);
        profilesDatabase = profilesDatabaseHelper.getWritableDatabase();
    }

    protected   void updateProfile(int profileId, String nProfileName, int nProfileWeight, int nProfileGender, String nProfileBreed, String nProfileDob){

        ContentValues profileValues = new ContentValues();

        profileValues.put(COL_PROFILE_NAME, nProfileName);
        profileValues.put(COL_PROFILE_WEIGHT, nProfileWeight);
        profileValues.put(COL_PROFILE_GENDER, nProfileGender);
        profileValues.put(COL_PROFILE_BREED, nProfileBreed);
        profileValues.put(COL_PROFILE_DOB, nProfileDob);

        profilesDatabase.update(TABLE_PROFILE, profileValues,
                COL_PROFILE_ID + "=?", new String[]{String.valueOf(profileId)});
    }

    protected   void updateProfilePicture(int profileId, String nProfilePicture){

        ContentValues profileValues = new ContentValues();

        profileValues.put(COL_PROFILE_PPU, nProfilePicture);

        profilesDatabase.update(TABLE_PROFILE, profileValues,
                COL_PROFILE_ID + "=?", new String[]{String.valueOf(profileId)});

    }

    protected void deleteProfile(int profileId) {
        profilesDatabase.delete(TABLE_PROFILE, COL_PROFILE_ID + "=?", new String[]{String.valueOf(profileId)});
    }

    protected int createProfile(String name, String profilePictureUri, int weight, int gender, String breed, String dateOfBirth) {

        ContentValues profileValues = new ContentValues();

        profileValues.put(COL_PROFILE_NAME, name);
        profileValues.put(COL_PROFILE_PPU, profilePictureUri);
        profileValues.put(COL_PROFILE_WEIGHT, weight);
        profileValues.put(COL_PROFILE_GENDER, gender);
        profileValues.put(COL_PROFILE_BREED, breed);
        profileValues.put(COL_PROFILE_DOB, dateOfBirth);

        profilesDatabase.insert(TABLE_PROFILE, null, profileValues);

        int newProfileId= fetchProfiles().get(0).getId();
        return newProfileId;
    }

    protected List<Profile> fetchProfiles() {

        Profile profile;
        List<Profile> profileList =new ArrayList<>();

        Cursor profileTableCursor = profilesDatabase.query(TABLE_PROFILE, new String[]{COL_PROFILE_ID,
                        COL_PROFILE_NAME,
                        COL_PROFILE_PPU,
                        COL_PROFILE_WEIGHT,
                        COL_PROFILE_GENDER,
                        COL_PROFILE_BREED,
                        COL_PROFILE_DOB},
                null, null, null, null, COL_PROFILE_NAME +" ASC",null
        );


        if (profileTableCursor != null){
            while (profileTableCursor.moveToNext()){

                int id = profileTableCursor.getInt(INDEX_PROFILE_ID);
                String name = profileTableCursor.getString(INDEX_PROFILE_NAME);
                String profilePictureUri = profileTableCursor.getString(INDEX_PROFILE_PPU);
                int weight = profileTableCursor.getInt(INDEX_PROFILE_WEIGHT);
                int gender = profileTableCursor.getInt(INDEX_PROFILE_GENDER);
                String breed = profileTableCursor.getString(INDEX_PROFILE_BREED);
                String dateOfBirth = profileTableCursor.getString(INDEX_PROFILE_DOB);
                int age = getAgeFromDateOfBirth(dateOfBirth);

                profile= new Profile(id, name, profilePictureUri, weight, gender, breed, dateOfBirth, age);

                profileList.add(profile);}
        }
        if (profileTableCursor != null){
            profileTableCursor.close();
        }

        return profileList;
    }

    protected int getAgeFromDateOfBirth(String dob){

        long dobMills = getTIMFromDS(dob);
        long nowTIM = getNowTIM();
        long ageInMills = nowTIM - dobMills;
        int ageInDays = (int) (ageInMills/ (1000*60*60*24));
        int ageInYears = (int) (ageInDays/ (365));

        return ageInYears;
    }

    public long getTIMFromDS(String reminderDTS){
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date=dateFormat.parse(reminderDTS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long reminderTIM = 0;

        if (date != null) {
            reminderTIM = date.getTime();
        }

        return reminderTIM;
    }

    protected long getNowTIM(){
        Date currentDateTime =new Date();
        long currentDateTimeInMills = currentDateTime.getTime();
        return currentDateTimeInMills;
    }

    protected void createUser(int userName) {

        ContentValues userValues = new ContentValues();

        userValues.put(COL_USER_NAME, userName);

        profilesDatabase.insert(TABLE_USER, null, userValues);
    }

    protected boolean thereIsAUserAccnt(){
        Cursor usrLstCursor = profilesDatabase.query(TABLE_USER, new String[]{COL_USER_ID},
                null, null, null, null, null
        );
        int count = usrLstCursor.getCount();
        usrLstCursor.close();
        return count > 0;
    }




    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_PROFILE);
            db.execSQL(CREATE_TABLE_USER);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            onCreate(db);
        }
    }

}
