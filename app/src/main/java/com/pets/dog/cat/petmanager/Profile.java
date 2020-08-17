package com.pets.dog.cat.petmanager;

public class Profile {
    private int id;
    private String name;
    private String profilePictureUri;
    private int weight;
    private int gender;
    private String breed;
    private String dateOfBirth;
    private int age;

    public Profile(int id, String name, String profilePictureUri, int weight, int gender, String breed, String dateOfBirth, int age) {
        this.id = id;
        this.name = name;
        this.profilePictureUri = profilePictureUri;
        this.weight = weight;
        this.gender = gender;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePictureUri() {
        return profilePictureUri;
    }

    public void setProfilePictureUri(String profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
