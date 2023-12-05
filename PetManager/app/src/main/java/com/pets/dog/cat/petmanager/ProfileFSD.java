package com.pets.dog.cat.petmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ERIC on 09/07/2020.
 */

public class ProfileFSD extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextInputLayout newProfileNameTEIL;
    private TextInputLayout newProfileWeightTEIL;
    private TextInputLayout newProfileBreedTEIL;
    private TextInputEditText newProfileNameTEIET;
    private TextInputEditText newProfileWeightTEIET;
    private MaterialAutoCompleteTextView genderSpinner;
    private String[] genderArray;
    private TextInputEditText newProfileBreedTEIET;
    private int gender = 0;
    private Button nPDSaveButton;
    private LinearLayout nPBirthdayLayout;
    private TextView nPBLDateTvw;

    private int setDay = 0;
    private int setMonth = 0;
    private int setYear = 0;
    private Calendar setCalendar;

    private DatePickerDialog nPDatePickerDialog;

    private ProfileInterface profileInterface;
    private String profilePictureUri;

    private ImageButton aBCloseButton;
    private TextView titleTvw;
    private int id;
    private String name;
    private int weight;
    private String breed;
    private String dateOfBirth;
    private int profilePosition;
    private boolean isEditMode;


    public static ProfileFSD newInstance(String profilePictureUri, int id, String name, int weight, int gender, String breed, String dateOfBirth, int profilePosition, boolean isEditMode) {
        ProfileFSD profileFSD = new ProfileFSD();
        Bundle params = new Bundle();

        params.putString("profilePictureUri", profilePictureUri);
        params.putInt("id", id);
        params.putString("name", name);
        params.putInt("weight", weight);
        params.putInt("gender", gender);
        params.putString("breed", breed);
        params.putString("dateOfBirth", dateOfBirth);
        params.putInt("profilePosition", profilePosition);
        params.putBoolean("isEditMode", isEditMode);

        profileFSD.setArguments(params);
        return profileFSD;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profilePictureUri = getArguments().getString("profilePictureUri");
        id = getArguments().getInt("id");
        name = getArguments().getString("name");
        weight = getArguments().getInt("weight");
        isEditMode = getArguments().getBoolean("isEditMode");

        if(isEditMode){
            gender = getArguments().getInt("gender");
        }
        gender = getArguments().getInt("gender");

        breed = getArguments().getString("breed");
        dateOfBirth = getArguments().getString("dateOfBirth");
        profilePosition = getArguments().getInt("profilePosition");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.profile_fsd, container, false);

        titleTvw = rootView.findViewById(R.id.np_title_tvw);

        if (isEditMode) {
            titleTvw.setText(R.string.edit_profile);
        }
        else {
            titleTvw.setText(R.string.new_profile);
        }

        aBCloseButton = rootView.findViewById(R.id.button_close);

        aBCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(rootView);
                dismiss();
            }
        });

        nPDSaveButton = rootView.findViewById(R.id.npd_save_button);

        nPDSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = newProfileNameTEIET.getText().toString();
                String weightString = newProfileWeightTEIET.getText().toString();
                String breed = newProfileBreedTEIET.getText().toString();
                String dateOfBirth = getDS(setCalendar.getTimeInMillis());


                if(!dialogTextIsSpaces(name) && !dialogTextIsSpaces(weightString) && !dialogTextIsSpaces(breed)){

                    int weight = Integer.valueOf(weightString);

                    if(!isEditMode){
                        profileInterface.addProfile(name, profilePictureUri, weight, gender, breed, dateOfBirth);
                    }
                    else {
                        profileInterface.updateProfile(id, name, weight, gender, breed, dateOfBirth, profilePosition);
                    }

                    hideKeyboard(rootView);
                    dismiss();
                }


                else {

                    if(dialogTextIsSpaces(name)){
                        newProfileNameTEIL.setErrorEnabled(true);
                        newProfileNameTEIL.setError(getResources().getString(R.string.teit_error_text));
                    }


                    newProfileNameTEIET.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String editTextText=newProfileNameTEIET.getText().toString();
                            boolean textIsSpaces= dialogTextIsSpaces(editTextText);

                            if(textIsSpaces){
                                newProfileNameTEIL.setErrorEnabled(true);
                                newProfileNameTEIL.setError(getResources().getString(R.string.teit_error_text));
                            }
                            else {
                                newProfileNameTEIL.setErrorEnabled(false);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });


                    if(dialogTextIsSpaces(weightString)){
                        newProfileWeightTEIL.setErrorEnabled(true);
                        newProfileWeightTEIL.setError(getResources().getString(R.string.teit_error_text));
                    }

                    newProfileWeightTEIET.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String editTextText=newProfileWeightTEIET.getText().toString();
                            boolean textIsSpaces= dialogTextIsSpaces(editTextText);

                            if(textIsSpaces){
                                newProfileWeightTEIL.setErrorEnabled(true);
                                newProfileWeightTEIL.setError(getResources().getString(R.string.teit_error_text));
                            }
                            else {
                                newProfileWeightTEIL.setErrorEnabled(false);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    if(dialogTextIsSpaces(breed)){
                        newProfileBreedTEIL.setErrorEnabled(true);
                        newProfileBreedTEIL.setError(getResources().getString(R.string.teit_error_text));
                    }

                    newProfileBreedTEIET.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String editTextText=newProfileBreedTEIET.getText().toString();
                            boolean textIsSpaces= dialogTextIsSpaces(editTextText);

                            if(textIsSpaces){
                                newProfileBreedTEIL.setErrorEnabled(true);
                                newProfileBreedTEIL.setError(getResources().getString(R.string.teit_error_text));
                            }
                            else {
                                newProfileBreedTEIL.setErrorEnabled(false);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });



                }




            }
        });

        profileInterface = (ProfileInterface)getActivity();

        newProfileNameTEIL = rootView.findViewById(R.id.pname_til);
        newProfileWeightTEIL = rootView.findViewById(R.id.pweight_til);
        newProfileBreedTEIL = rootView.findViewById(R.id.pbreed_til);

        newProfileNameTEIET = rootView.findViewById(R.id.pname_tiet);
        newProfileWeightTEIET = rootView.findViewById(R.id.pweight_tiet);
        genderSpinner = rootView.findViewById(R.id.gender_spinner);
        newProfileBreedTEIET = rootView.findViewById(R.id.pbreed_tiet);
        ProfileTIETSuffixDrawable profileTIETSuffixDrawable = new ProfileTIETSuffixDrawable(getResources(),getString(R.string.klgrms), newProfileWeightTEIET);
        newProfileWeightTEIET.setCompoundDrawablesWithIntrinsicBounds(null, null, profileTIETSuffixDrawable, null);

        genderSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender=position;
            }
        });


        genderArray = new String[] {getString(R.string.mle), getString(R.string.fml)};
        ArrayAdapter<String> genderSAdapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.gnder_spnnr_adptr_itm,
                        genderArray);

        nPBirthdayLayout = rootView.findViewById(R.id.new_profile_birthday_layout);
        nPBLDateTvw = rootView.findViewById(R.id.npbl_date_value_tvw);
        String nowDS = getDS(getNowTIM());
        nPBLDateTvw.setText(nowDS);
        setCalendar = Calendar.getInstance();
        initializeDatePickerDialogs();
        nPBirthdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nPDatePickerDialog.show();
            }
        });

        profileInterface.hideActionBar();

        if(isEditMode){
            setEditMode();
        }


        genderSpinner.setAdapter(genderSAdapter);

        return rootView;
    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setEditMode(){
        newProfileNameTEIET.setText(name);
        newProfileWeightTEIET.setText(String.valueOf(weight));
        genderSpinner.setText(genderArray[gender]);
        newProfileBreedTEIET.setText(breed);
        nPBLDateTvw.setText(dateOfBirth);
    }

    protected boolean dialogTextIsSpaces(String dialogText){
        int i=0;
        int noOfSpaces=0;
        int dialogTextLength=dialogText.length();

        while (i >= 0 && i <= (dialogTextLength-1)){
            String dialogTextCharacter= String.valueOf(dialogText.charAt(i));
            if(dialogTextCharacter.equals(" ")){
                noOfSpaces=noOfSpaces+1;
            }
            ++i;
        }

        if(dialogTextLength==noOfSpaces){
            return true;
        }

        else {
            return false;
        }

    }

    private long getTIMFDS(String dateOfBirth){
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date date= new Date();
        try {
            date = dateFormat.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.getTime();
    }

    protected void initializeDatePickerDialogs(){
        if(!isEditMode) {
            setCalendar.setTimeInMillis(getNowTIM());
        }
        else {
            long setTIM = getTIMFDS(dateOfBirth);
            setCalendar.setTimeInMillis(setTIM);
        }

        int nCYear=setCalendar.get(Calendar.YEAR);
        int nCMonth= setCalendar.get(Calendar.MONTH);
        int nCDay=setCalendar.get(Calendar.DAY_OF_MONTH);

        setYear = nCYear;
        setMonth = nCMonth;
        setDay = nCDay;

        nPDatePickerDialog=new DatePickerDialog(getContext(), this, nCYear, nCMonth, nCDay);

        nPDatePickerDialog.setCanceledOnTouchOutside(true);

        nPDatePickerDialog.setTitle(getString(R.string.i_b_dte));
    }

    protected long getNowTIM(){
        Date nowDate =new Date();
        long nowTIM = nowDate.getTime();
        return nowTIM;
    }

    protected String getDS(long timeInMills){
        Date date= new Date(timeInMills);
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String dateTimeString=  dateFormat.format(date);
        return dateTimeString;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        profileInterface.showActionBar();

        if(isEditMode){
            profileInterface.showEditCSnackBar();
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog profileFSDialog = super.onCreateDialog(savedInstanceState);
        profileFSDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return profileFSDialog;
    }

    private void updateNPBTvw() {
        setCalendar.set(setYear,setMonth,setDay);
        String setBirthday = getDS(setCalendar.getTimeInMillis());
        nPBLDateTvw.setText(setBirthday);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        setYear =i;
        setMonth = i1;
        setDay =i2;
        updateNPBTvw();
    }
}
