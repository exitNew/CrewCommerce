package com.crew.foundation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class signupactivity extends AppCompatActivity {

    EditText first_name, last_name, dob,pob,email, username, password, date;
    DatePickerDialog datePickerDialog;
    Button regis_button;
    DatabaseReference mDatabase,newdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);



        //mengambil id masing2 sesuai kebutuhan
        regis_button = findViewById(R.id.register_button);
        first_name = findViewById(R.id.register_first_name);
        last_name = findViewById(R.id.register_last_name);
        pob = findViewById(R.id.register_place_of_birth);
        dob = findViewById(R.id.register_date_of_birth);
        email = findViewById(R.id.register_email);
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_pass);
        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.register_date_of_birth);
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = 2000; //c.get(Calendar.YEAR); // current year
        int mMonth = 1;//c.get(Calendar.MONTH); // current month
        int mDay =  1;//c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(signupactivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


        //saat register button ditekan
        regis_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check firstname empty
                if(first_name.getText().toString().trim().isEmpty()){
                    first_name.setError("This field is required");
                }
                //check lastname empty
                if(last_name.getText().toString().trim().isEmpty()){
                    last_name.setError("This field is required");
                }
                //check email empty
                if(email.getText().toString().trim().isEmpty()){
                    email.setError("This field is required");

                }
                //check pob emty
                if(pob.getText().toString().trim().isEmpty()){
                    pob.setError("This field is required");
                }
                //check dob empty
                if(dob.getText().toString().trim().isEmpty()) {
                    dob.setError("This field is required");
                }
                //check username empty
                if(username.getText().toString().trim().isEmpty()){
                    username.setError("This field is required");;
                }
                //untuk constraint password
                String s = password.getText().toString();
                //check apakah ada number/uppercase/lowercase di string
                boolean uppercase_flag = false;
                boolean lowercase_flag = false;
                boolean number_flag = false;
                Character ch;
                for (int i = 0; i < s.length(); i++){
                    ch = s.charAt(i);
                    if(Character.isDigit(ch)){
                        number_flag = true;
                    }
                    else if(Character.isUpperCase(ch)){
                        uppercase_flag = true;
                    }
                    else if(Character.isLowerCase(ch)){
                        lowercase_flag = true;
                    }
                }
                //check password empty
                if(password.getText().toString().trim().isEmpty()){
                    password.setError("This field is required");
                }
                //constrain panjang password
                else if(s.length() < 8 ){
                    Log.d("tes", "ceklenght");
                    password.setError("Password must have at least 8 characters");
                }
                //constraint angka
                else if(number_flag == false){
                    Log.d("tes", "ceknumber");
                    password.setError("Password must contain at least one number digit");
                }
                //constrain upppercase
                else if(uppercase_flag == false){
                    Log.d("tes", "cekuppercase");
                    password.setError("Password must contain at least uppercase letter");
                }
                else if(lowercase_flag == false){
                    Log.d("tes", "ceklowercase");
                    password.setError("Password must contain at least lowercase letter");
                }
                if(first_name.getError() == null && last_name.getError() == null &&
                    email.getError() == null && pob.getError() == null &&
                    dob.getError() == null && username.getError() == null &&
                    password.getError() == null){
                    Users user = new Users();
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setFirst_name(first_name.getText().toString());
                    user.setLast_name(last_name.getText().toString());
                    user.setDob(dob.getText().toString());
                    user.setPob(pob.getText().toString());
                    user.setEmail(email.getText().toString());
                    //connect ke firebase
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("users").push();
                    mDatabase.setValue(user);
                    Intent intent = new Intent(signupactivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        });
    }
}
