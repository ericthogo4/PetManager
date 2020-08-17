package com.pets.dog.cat.petmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;


public class DetailActivity extends AppCompatActivity {

    private String name;
    private String profilePictureUri;
    private int weight;
    private int gender;
    private String breed;
    private String dob;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        profilePictureUri = extras.getString("profilePictureUri");
        weight = extras.getInt("weight");
        gender = extras.getInt("gender");
        breed = extras.getString("breed");
        dob = extras.getString("dob");
        age = extras.getInt("age");

        Toolbar toolbar = (Toolbar) findViewById(R.id.p_toolbar);

        ImageView profileImageView = findViewById(R.id.profile_imageview);
        TextView weightTvw = findViewById(R.id.weight_tvw);
        TextView genderTvw = findViewById(R.id.gender_tvw);
        TextView breedTvw = findViewById(R.id.breed_tvw);
        TextView dobTvw = findViewById(R.id.dob_tvw);
        TextView ageTvw = findViewById(R.id.age_tvw);


        weightTvw.setText(String.valueOf(weight)+"Kgs");

        if(gender==1){
            genderTvw.setText("Female");
        }
        else {
            genderTvw.setText("Male");
        }

        breedTvw.setText(breed);

        int day = Integer.valueOf(dob.substring(0,2));
        String monthN = dob.substring(3,5);
        String monthIT = getMonthInText(monthN);
        String year = dob.substring(6,10);

        String longDob = day+" "+monthIT+" "+year;

        dobTvw.setText(longDob);

        ageTvw.setText(String.valueOf(age)+"Yrs");

        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            Bitmap profilePBitmap = decodeUri(this,Uri.parse(profilePictureUri),300);
                    profileImageView.setImageBitmap(profilePBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getMonthInText(String dString){
        String monthInText = "January";

        switch (dString) {
            case "01":
                monthInText = "January";
                break;
            case "02":
                monthInText = "February";
                break;
            case "03":
                monthInText = "March";
                break;
            case "04":
                monthInText = "April";
                break;
            case "05":
                monthInText = "May";
                break;
            case "06":
                monthInText = "June";
                break;
            case "07":
                monthInText = "July";
                break;
            case "08":
                monthInText = "August";
                break;
            case "09":
                monthInText = "September";
                break;
            case "10":
                monthInText = "October";
                break;
            case "11":
                monthInText = "November";
                break;
            case "12":
                monthInText = "December";
                break;

            default:

        }

        return monthInText;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return false;
        }
    }

    public  Bitmap decodeUri(Context context, Uri uri, final int requiredSize) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, o2);
    }
}
