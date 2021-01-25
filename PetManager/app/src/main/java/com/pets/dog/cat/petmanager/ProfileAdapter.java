package com.pets.dog.cat.petmanager;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>{

    private Context profileAdapterContext;
    private List<Profile> profileList;
    private List<Integer> selectedPositions = new ArrayList<>();

    public ProfileAdapter(Context profileAdapterContext, List<Profile> profileList) {
        this.profileAdapterContext = profileAdapterContext;
        this.profileList = profileList;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_list_row, parent, false);

        return new ProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {

        try {
            Bitmap profilePBitmap = decodeUri(profileAdapterContext, Uri.parse(profileList.get(position).getProfilePictureUri()), 40);
            holder.profilePictureImageIV.setImageBitmap(profilePBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.nameTvw.setText(profileList.get(position).getName());

        if (selectedPositions.contains(position)){
            holder.profileRowFL.setForeground(new ColorDrawable(ContextCompat.getColor(profileAdapterContext,R.color.recyclerViewItemSelectionColor)));
        }
        else {
            holder.profileRowFL.setForeground(new ColorDrawable(ContextCompat.getColor(profileAdapterContext,android.R.color.transparent)));
        }

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    protected class ProfileViewHolder extends RecyclerView.ViewHolder {
        protected ImageView profilePictureImageIV;
        protected TextView nameTvw;
        protected FrameLayout profileRowFL;

        public ProfileViewHolder(View view) {
            super(view);
            profilePictureImageIV = view.findViewById(R.id.profile_pic_imageview);
            nameTvw = view.findViewById(R.id.name_tvw);
            profileRowFL = view.findViewById(R.id.profile_list_row_frame_layout);
        }
    }


    public void setSelectedPositions(int previousPosition, List<Integer> selectedPositions) {
        this.selectedPositions = selectedPositions;

        if(previousPosition != -1){
            notifyItemChanged(previousPosition);
        }

        if(this.selectedPositions.size()>0){
            notifyItemChanged(this.selectedPositions.get(0));
        }

    }


    public Profile getItem(int position){
        return profileList.get(position);
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