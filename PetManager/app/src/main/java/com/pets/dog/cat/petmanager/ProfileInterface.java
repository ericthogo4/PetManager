package com.pets.dog.cat.petmanager;

/**
 * Created by ERIC on 15/07/2020.
 */

public interface ProfileInterface {

    public void hideActionBar();
    public void showActionBar();
    public void addProfile(final String name, final String profilePictureUri, final int weight, final int gender, final String breed, final String dateOfBirth);
    public void updateProfile(final int profileId, final String nProfileName, final int nProfileWeight, final int nProfileGender, final String nProfileBreed, final String nProfileDob, final int profilePosition);
    public void showEditCSnackBar();
}
