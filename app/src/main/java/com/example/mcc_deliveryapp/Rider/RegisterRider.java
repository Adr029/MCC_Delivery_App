package com.example.mcc_deliveryapp.Rider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mcc_deliveryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RegisterRider extends AppCompatActivity {

    FirebaseAuth mAuth;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootie;

    EditText etCode;

    DatePickerDialog datePickerDialog;

    Dialog regRiderStep1;
    Dialog regRiderStep2;
    Dialog regRiderStep3;
    Dialog regRiderStep4;
    Dialog regRiderStep5;

    Boolean hasError = false;

    String verificationCodeBySystem;

    String vehiclebrandandmodel;

    EditText password, pwConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register_rider);

        EditText etPhoneNum = findViewById(R.id.editTextPhoneNumDriver);

        password = findViewById(R.id.pwfield);
        pwConfirm = findViewById(R.id.pwConfirm);

        Button btnReg = findViewById(R.id.btnRegRider);

        CheckBox checkRider = findViewById(R.id.checkBoxAgreeRider);

        Spinner spinCity = findViewById(R.id.spinnerCityDriver);
        Spinner spinVehicle = findViewById(R.id.spinnerVehicleDriver);
        Spinner spinBrand =  findViewById(R.id.spinnerVehicleBrand);

        //Getting City Rider Item List
        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this, R.array.cityRider,R.layout.spinner_items_1);
        adapterCity.setDropDownViewResource(R.layout.spinner_items_1);
        spinCity.setAdapter(adapterCity);

        //Getting Vehicle Rider Type Item List
        ArrayAdapter<CharSequence> adapterVehicle = ArrayAdapter.createFromResource (this,R.array.ridervehicletype,R.layout.spinner_items_1);
        adapterVehicle.setDropDownViewResource(R.layout.spinner_items_1);
        spinVehicle.setAdapter(adapterVehicle);

        //Spinner City
        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                if(i > 0){
                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    TextView tv = (TextView) view;
                    if(i == 0 )
                    {
                        tv.setTextColor(Color.GRAY);
                    }
                    else
                    {
                        tv.setTextColor(Color.BLACK);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner Rider Vehicle Type
        spinVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);

                if(i == 1)
                {
                    ArrayAdapter<CharSequence> adapterBrand = ArrayAdapter.createFromResource(getApplicationContext(), R.array.ridervehiclebrandmotorcycle,R.layout.spinner_items_1);
                    adapterBrand.setDropDownViewResource(R.layout.spinner_items_1);
                    spinBrand.setAdapter(adapterBrand);
                    spinBrand.setVisibility(View.VISIBLE);
                }
                else if(i == 2)
                {
                    ArrayAdapter<CharSequence> adapterBrand = ArrayAdapter.createFromResource(getApplicationContext(), R.array.ridervehiclebrandsuvcycle,R.layout.spinner_items_1);
                    adapterBrand.setDropDownViewResource(R.layout.spinner_items_1);
                    spinBrand.setAdapter(adapterBrand);
                    spinBrand.setVisibility(View.VISIBLE);
                }
                else if(i == 3)
                {
                    ArrayAdapter<CharSequence> adapterBrand = ArrayAdapter.createFromResource(getApplicationContext(), R.array.ridervehiclebrandvancycle,R.layout.spinner_items_1);
                    adapterBrand.setDropDownViewResource(R.layout.spinner_items_1);
                    spinBrand.setAdapter(adapterBrand);
                    spinBrand.setVisibility(View.VISIBLE);
                }

                if(i > 0){
                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                }
                else {
                    TextView tv = (TextView) view;
                    if (i == 0) {
                        tv.setTextColor(Color.GRAY);
                        spinBrand.setVisibility(View.GONE);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EditText etModelVehicle = findViewById(R.id.editTextVehicleModel);
        EditText etBrandVehicle = findViewById(R.id.editTextVehicleBrand);
        //Spinner Vehicle Brand

        spinBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);

                if(i==5)
                {
                    etBrandVehicle.setVisibility(View.VISIBLE);

                }
                else
                {
                    etBrandVehicle.setVisibility(View.GONE);
                }

                if(i > 0){
                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                    etModelVehicle.setVisibility(View.VISIBLE);
                }
                else {
                    TextView tv = (TextView) view;
                    if (i == 0) {
                        tv.setTextColor(Color.GRAY);
                        etModelVehicle.setVisibility(View.GONE);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Dialog VerifyNum = new Dialog(RegisterRider.this);
        VerifyNum.setContentView(R.layout.fragment_rider_phonenum_verify);
        VerifyNum.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        VerifyNum.setCancelable(false);
        VerifyNum.getWindow().getAttributes().windowAnimations = R.style.animation;


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean clear = true;
                if(TextUtils.isEmpty(etPhoneNum.getText().toString()))
                {
                    etPhoneNum.setError("Required");
                    etPhoneNum.setBackgroundResource(R.drawable.error_border_edittext);
                    clear = false;
                    return;

                }

                else  if(spinCity.getSelectedItemId() == 0)
                {
                    spinCity.setBackgroundResource(R.drawable.error_border_edittext);
                    clear = false;
                    return;

                }
                else  if(spinVehicle.getSelectedItemId() == 0)
                {
                    spinVehicle.setBackgroundResource(R.drawable.error_border_edittext);
                    clear = false;
                    return;

                }
                else  if(!checkRider.isChecked())
                {
                    Toast.makeText(RegisterRider.this,"Please agree on the terms and condition.",Toast.LENGTH_SHORT).show();
                    clear = false;
                    return;

                }
                else if(TextUtils.isEmpty(etModelVehicle.getText().toString()))
                {
                    etModelVehicle.setBackgroundResource(R.drawable.error_border_edittext);
                    clear = false;
                    return;

                }
                else if(etBrandVehicle.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etBrandVehicle.getText().toString()))
                {
                    etBrandVehicle.setBackgroundResource(R.drawable.error_border_edittext);
                    clear = false;
                    return;

                }
                if (password.length() == 0)
                {
                    ((EditText)findViewById(R.id.pwfield)).setError("Required", null);
                    findViewById(R.id.pwfield).setBackgroundResource(R.drawable.error_border_edittext);
                }
                if (pwConfirm.length() == 0)
                {
                    ((EditText)findViewById(R.id.pwConfirm)).setError("Required", null);
                    findViewById(R.id.pwConfirm).setBackgroundResource(R.drawable.error_border_edittext);
                }
                else
                {
                    etPhoneNum.setBackgroundResource(R.drawable.graphics_edittext_1);
                    etModelVehicle.setBackgroundResource(R.drawable.graphics_edittext_1);
                    spinVehicle.setBackgroundResource(R.drawable.graphics_edittext_1);
                    etPhoneNum.setBackgroundResource(R.drawable.graphics_edittext_1);
                    etBrandVehicle.setBackgroundResource(R.drawable.graphics_edittext_1);
                    findViewById(R.id.pwConfirm).setBackgroundResource(R.drawable.graphics_edittext_1);
                    findViewById(R.id.pwfield).setBackgroundResource(R.drawable.graphics_edittext_1);
                    clear = true;
                }

                if(etBrandVehicle.getVisibility() == View.VISIBLE)
                {
                    vehiclebrandandmodel = etBrandVehicle.getText().toString() + " " + etModelVehicle.getText().toString();
                }
                else {
                    vehiclebrandandmodel = spinBrand.getSelectedItem().toString() + " " + etModelVehicle.getText().toString();
                }

                String password = ((EditText)findViewById(R.id.pwfield)).getText().toString();
                String pwConfirm = ((EditText)findViewById(R.id.pwConfirm)).getText().toString();
                boolean uppercase = !password.equals(password.toLowerCase());
                boolean lowercase = !password.equals(password.toUpperCase());
                boolean min6  = password.length() > 5;
                boolean PWgood = false;

                int digits = 0;
                int upper = 0;

                for (int i = 0; i < password.length(); i++) {
                    char ch = password.charAt(i);
                    if (ch >= 48 && ch <= 57)
                        digits++;
                    else if(ch>='A' && ch<='Z'){
                        upper++;
                    }
                }
                //check if password satisfies conditions
                if(!uppercase || !lowercase || !min6 || digits == 0)
                {
                    ((EditText)findViewById(R.id.pwfield)).setError("Password most have at least 6 characters, one uppercase, lowercase, and number.", null);
                    findViewById(R.id.pwfield).setBackgroundResource(R.drawable.error_border_edittext);

                }

                // add confirm password function
                else if (min6 && uppercase && lowercase && digits >=1)
                {

                    if (password.equals(pwConfirm))
                    {
                        findViewById(R.id.pwfield).setBackgroundResource(R.drawable.graphics_edittext_1);
                        findViewById(R.id.pwConfirm).setBackgroundResource(R.drawable.graphics_edittext_1);
                        PWgood = true;
                    }
                    else {
                        ((EditText)findViewById(R.id.pwConfirm)).setError("Passwords do not match", null);
                        findViewById(R.id.pwfield).setBackgroundResource(R.drawable.error_border_edittext);
                        findViewById(R.id.pwConfirm).setBackgroundResource(R.drawable.error_border_edittext);
                    }

                }

                if (PWgood && clear)
                    {
                        sendVerificationCodeToUser(etPhoneNum.getText().toString());
                        VerifyNum.show();
                    }

            }
        });


//etong part na to yung sa fragment
        regRiderStep1 = new Dialog(RegisterRider.this);
        regRiderStep1.setContentView(R.layout.fragment_signup_rider_step1);
        regRiderStep1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        regRiderStep1.setCancelable(true);
        regRiderStep1.getWindow().getAttributes().windowAnimations = R.style.animation;

        regRiderStep2 = new Dialog(RegisterRider.this);
        regRiderStep2.setContentView(R.layout.fragment_signup_rider_step2);
        regRiderStep2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        regRiderStep2.setCancelable(true);
        regRiderStep2.getWindow().getAttributes().windowAnimations = R.style.animation;

        regRiderStep3 = new Dialog(RegisterRider.this);
        regRiderStep3.setContentView(R.layout.fragment_signup_rider_step3);
        regRiderStep3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        regRiderStep3.setCancelable(true);
        regRiderStep3.getWindow().getAttributes().windowAnimations = R.style.animation;

        regRiderStep4 = new Dialog(RegisterRider.this);
        regRiderStep4.setContentView(R.layout.fragment_signup_rider_step4);
        regRiderStep4.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        regRiderStep4.setCancelable(true);
        regRiderStep4.getWindow().getAttributes().windowAnimations = R.style.animation;

        regRiderStep5 = new Dialog(RegisterRider.this);
        regRiderStep5.setContentView(R.layout.fragment_signup_rider_step5);
        regRiderStep5.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        regRiderStep5.setCancelable(true);
        regRiderStep5.getWindow().getAttributes().windowAnimations = R.style.animation;

        Dialog successfullyRegistered = new Dialog(RegisterRider.this);
        successfullyRegistered.setContentView(R.layout.success_dialog);
        successfullyRegistered.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        successfullyRegistered.setCancelable(false);
        successfullyRegistered.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button btnSuccessOkay = successfullyRegistered.findViewById(R.id.btnSuccessRegRider);

        btnSuccessOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterRider.this, riderLogin.class);
                startActivity(intent);
            }
        });

        EditText etVerifyCode =  VerifyNum.findViewById(R.id.etVerify);
        etCode =  VerifyNum.findViewById(R.id.etVerify);
        Button verify =  VerifyNum.findViewById(R.id.btnVerify);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = etVerifyCode.getText().toString();
                if(TextUtils.isEmpty(etVerifyCode.getText().toString()))
                {
                    etVerifyCode.setError("Required");
                    Toast.makeText(RegisterRider.this,"Please Enter The Code.",Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyCode(code);
                VerifyNum.dismiss();
            }
        });

        Button nextstep1 = regRiderStep1.findViewById(R.id.nextstep1);
        Button nextstep2 = regRiderStep2.findViewById(R.id.nextstep2);
        Button nextstep3 = regRiderStep3.findViewById(R.id.nextstep3);
        Button nextstep4 =  regRiderStep4.findViewById(R.id.nextstep4);
        Button register = regRiderStep5.findViewById(R.id.register);

        EditText etDatePicker =  regRiderStep2.findViewById(R.id.etRiderDateofBirth);

        etDatePicker.setText(getTodaysDate());

        etDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = makeDateString(day,month,year);
                        etDatePicker.setText(date);
                    }
                };
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int style = AlertDialog.THEME_HOLO_LIGHT;

                datePickerDialog = new DatePickerDialog(RegisterRider.this,style,dateSetListener,year,month,day);
                datePickerDialog.show();
            }
        });

        nextstep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmptyEditText(regRiderStep1.findViewById(R.id.etNameRider));
                if(hasError)
                {
                    hasError = false;
                    return;
                }

                else
                {
                    regRiderStep2.show();
                    regRiderStep1.dismiss();
                }

            }
        });

        nextstep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmptyEditText(regRiderStep2.findViewById(R.id.etRiderDateofBirth));
                checkEmptyEditText(regRiderStep2.findViewById(R.id.etRiderDriverLicense));
                checkEmptyEditText(regRiderStep2.findViewById(R.id.etRiderDriverLicenseExpiry));
                checkEmptyEditText(regRiderStep2.findViewById(R.id.etRiderCurrentAddress));
                if(hasError)
                {
                    hasError = false;
                    return;
                }

                else
                {
                    regRiderStep3.show();
                    regRiderStep2.dismiss();
                }

                    }
                });

        nextstep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmptyEditText(regRiderStep3.findViewById(R.id.etRiderVehicleNumber));
                checkEmptyEditText(regRiderStep3.findViewById(R.id.etRiderManufactureYear));
                if(hasError)
                {
                    hasError = false;
                    return;
                }
                else
                {
                    regRiderStep4.show();
                    regRiderStep3.dismiss();
                }
            }
        });

        nextstep4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    regRiderStep5.show();
                    regRiderStep4.dismiss();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String riderName  = getTextFromEditText(regRiderStep1.findViewById(R.id.etNameRider));
                String riderEmail = getTextFromEditText(regRiderStep1.findViewById(R.id.etEmailRider));
                String riderDateofBirth = getTextFromEditText(regRiderStep2.findViewById(R.id.etRiderDateofBirth));
                String riderDriverLicenseNumber = getTextFromEditText(regRiderStep2.findViewById(R.id.etRiderDriverLicense));
                String riderDriverLicenseExpiry = getTextFromEditText(regRiderStep2.findViewById(R.id.etRiderDriverLicenseExpiry));
                String riderCurrentAddress = getTextFromEditText(regRiderStep2.findViewById(R.id.etRiderCurrentAddress));
                String riderVehiclePlateNumber = getTextFromEditText(regRiderStep3.findViewById(R.id.etRiderVehicleNumber));
                String riderVehicleManufacturerYear = getTextFromEditText(regRiderStep3.findViewById(R.id.etRiderManufactureYear));
                String riderEMPerson = getTextFromEditText(regRiderStep5.findViewById(R.id.etEmergencyPerson));
                String riderEMNumber = getTextFromEditText(regRiderStep5.findViewById(R.id.etEmergencyNumber));
                String phonenum = getTextFromEditText(findViewById(R.id.editTextPhoneNumDriver));
                String riderpass = getTextFromEditText(findViewById(R.id.pwfield));
                checkEmptyEditText(regRiderStep5.findViewById(R.id.etEmergencyPerson));
                checkEmptyEditText(regRiderStep5.findViewById(R.id.etEmergencyNumber));
                if(hasError)
                {
                    hasError = false;
                    return;
                }
                else
                {
                    FirebaseUser userCurrent = mAuth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(String.valueOf(riderName)).build();
                    userCurrent.updateProfile(profileUpdates);
                    rootie = db.getReference();

                    HashMap riderInfo = new HashMap<>();
                    riderInfo.put("city",spinCity.getSelectedItem().toString());
                    riderInfo.put("vehicletype",spinVehicle.getSelectedItem().toString());
                    riderInfo.put("riderphone",phonenum);
                    riderInfo.put("name",riderName);
                    riderInfo.put("email",riderEmail);
                    riderInfo.put("riderpass",riderpass);
                    riderInfo.put("dateofbirth",riderDateofBirth);
                    riderInfo.put("driverlicensenumber",riderDriverLicenseNumber);
                    riderInfo.put("driverlicenseexpiry",riderDriverLicenseExpiry);
                    riderInfo.put("currentaddress",riderCurrentAddress);
                    riderInfo.put("vehicleplatenumber",riderVehiclePlateNumber);
                    riderInfo.put("manufactureryear",riderVehicleManufacturerYear);
                    riderInfo.put("emergencyperson",riderEMPerson);
                    riderInfo.put("emergencynumber",riderEMNumber);
                    riderInfo.put("vehiclebrandandmodel",vehiclebrandandmodel);

                    rootie.child("riders").child(phonenum).setValue(riderInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            successfullyRegistered.show();
                            regRiderStep5.dismiss();
                        }
                    });
                }

            }
        });


    }

/*
        String strength = "";

        //check for password strength
        if (good)
        {
            float lengthscore = 0;
            float UCscore = 0;
            float numscore = 0;
            float strengthscore = 0;
            if (password.length() == 6)
            {
                lengthscore = (float) 0.2;
            }
            else if (password.length()>= 7 && password.length() <= 9)
            {
                lengthscore = (float) 0.3;
            }
            else if (password.length() > 9)
            {
                lengthscore = (float) 0.5;
            }
            if(upper == 2)
            {
                UCscore = (float) 0.2;
            }
            else if(upper > 2)
            {
                UCscore = (float) 0.3;
            }
            if(digits == 2)
            {
                numscore = (float) 0.2;
            }
            else if(digits > 2)
            {
                numscore = (float) 0.3;
            }
            strengthscore = lengthscore + UCscore + numscore;

            if (strengthscore < 0.5)
            {
                strength = "Weak";
            }
            else if (strengthscore >= 0.5 && strengthscore < 1)
            {
                strength = "Moderate";
            }
            else if (strengthscore >=1 )
            {
                strength = "Strong";
            }
            ((TextView)findViewById(R.id.pwStrength)).setText("Password Strength: " + strength);
        }

        else {
            ((TextView)findViewById(R.id.pwStrength)).setText("Password does not satisfy conditions");
        }
    }
*/
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;

        return makeDateString(day,month,year);
    }

    private String getTextFromEditText(EditText et)
    {
        EditText ett = et;
        return ett.getText().toString();
    }

    private String makeDateString(int day,int month,int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";

        if(month == 2)
            return "FEB";

        if(month == 3)
            return "MAR";

        if(month == 4)
            return "APR";

        if(month == 5)
            return "MAY";

        if(month == 6)
            return "JUNE";

        if(month == 7)
            return "JUL";

        if(month == 8)
            return "AUG";

        if(month == 9)
            return "SEP";

        if(month == 10)
            return "OCT";

        if(month == 11)
            return "NOV";

        if(month == 12)
            return "DEC";

        return "JAN";

    }

    public void checkEmptyEditText(EditText et)
    {
        EditText ett = et;
        String etstring = ett.getText().toString();
        if(TextUtils.isEmpty(etstring))
        {
            ett.setError("Required");
            hasError = true;
        }
    }

    private void sendVerificationCodeToUser(String phoneNo)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+63" + phoneNo,60, TimeUnit.SECONDS, this,mCallBacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem = s;
            Toast.makeText(RegisterRider.this,"Code Sent.",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


            String code = phoneAuthCredential.getSmsCode();
            etCode.setText(code);
            if(code != null)
            {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(RegisterRider.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };


    private void verifyCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,code);

        signInByCredential(credential);


    }

    private void signInByCredential(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    regRiderStep1.show();

                }
                else
                {
                    Toast.makeText(RegisterRider.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}