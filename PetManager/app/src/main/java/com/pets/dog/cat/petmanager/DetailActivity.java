package com.pets.dog.cat.petmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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


        weightTvw.setText(weight +getString(R.string.klgrms));

        if(gender==1){
            genderTvw.setText(R.string.fml);
        }
        else {
            genderTvw.setText(R.string.mle);
        }

        breedTvw.setText(breed);

        int day = Integer.valueOf(dob.substring(0,2));
        String monthN = dob.substring(3,5);
        String monthIT = getMonthInText(monthN);
        String year = dob.substring(6,10);

        String longDob = day+" "+monthIT+" "+year;

        dobTvw.setText(longDob);

        ageTvw.setText(age +getString(R.string.yrs));

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
        String monthInText = getString(R.string.jan);

        switch (dString) {
            case "01":
                monthInText = getString(R.string.jan);
                break;
            case "02":
                monthInText = getString(R.string.feb);
                break;
            case "03":
                monthInText = getString(R.string.mrch);
                break;
            case "04":
                monthInText = getString(R.string.aprl);
                break;
            case "05":
                monthInText = getString(R.string.my);
                break;
            case "06":
                monthInText = getString(R.string.jne);
                break;
            case "07":
                monthInText = getString(R.string.jly);
                break;
            case "08":
                monthInText = getString(R.string.agst);
                break;
            case "09":
                monthInText = getString(R.string.sptmb);
                break;
            case "10":
                monthInText = getString(R.string.oct);
                break;
            case "11":
                monthInText = getString(R.string.nov);
                break;
            case "12":
                monthInText = getString(R.string.dec);
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
