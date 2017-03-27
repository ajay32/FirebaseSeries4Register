package com.hackingbuzz.firebaseseries4register.activites;


/**
 * Created by Avi Mehta on 3/26/2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackingbuzz.firebaseseries4register.R;
import com.hackingbuzz.firebaseseries4register.helper.InputValidation;

public class MainActivity extends AppCompatActivity {


    // here what we are doing is...we as user signup we are storing his email and password in Authentication Section...n his other details in database.. Database Section...cool ?

    Context context;

    private EditText mEmail, mPassword, mName, mConfirmPassword;
    private Button mRegister;
    private FirebaseAuth mAuth;  // this is the object that controls our Authenication Section for Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;   //listener listen to the state of user... through mAuth (user logged in or not)
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private InputValidation inputValidation;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        context = MainActivity.this;

        initViews();
        initObejcts();

        // setting listener on login button
        loginListener();


    }  // end of onCreate method




    private void initObejcts() {

        mAuth = FirebaseAuth.getInstance();  // getting firebase auth object.


        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait...");


        inputValidation = new InputValidation(context);  // always try to send context in constructor ..otherwise there would be chances that your program will crash... its also better you dont have to get it (return method ) or access (field) everywhere...

    }  // end of initObjects


    private void loginListener() {

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void initViews() {

        mName = (EditText) findViewById(R.id.et_name);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mConfirmPassword = (EditText) findViewById(R.id.et_confirm_pass);
        mRegister = (Button) findViewById(R.id.btn_register);
    }


    // click on a Login button will fire up this method...
    private void register() {

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        final String name = mName.getText().toString().trim();
        final String confimPass = mConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {

            Toast.makeText(MainActivity.this, "Enter your Name", Toast.LENGTH_LONG).show();

            return;
        }

// basically checking if email or passwword is empty .. then print Toast else sign in  with the help of auth object
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(MainActivity.this, "Enter email or password", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(confimPass)) {

            Toast.makeText(MainActivity.this, "Confirm your Password..", Toast.LENGTH_LONG).show();
            return;
        } else if (!inputValidation.isEditTextMatches(mPassword, mConfirmPassword)) {
            Toast.makeText(MainActivity.this, "Password does not Match!", Toast.LENGTH_LONG).show();
            return;
        } else {



            dialog.show();  // showing dialog at the time creating account..

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {


// well ...this database section is not working now....so i am commenthing it for now...unitl the solution is figured out ....so .till then ...we just sigining up the user... not strong his details...in database...just email n password in authentication section..


                     /*   database = FirebaseDatabase.getInstance();
                        myRef = database.getReference("Users");   // get referece means root directory ..or main databse object..under which we creating Users as a child..


                        // as user signed up..we will retrive his id ..n store their details accordingly..
                        String userId = mAuth.getCurrentUser().getUid();

                       DatabaseReference current_user = myRef.child(userId);  // so it just like creating tree of a prticular user  --  Databse --> Users --> UId --> name , age and what ever ..basically in Users we gonna divide every user by its uid.. and in uid ..we have user detials..like name,age

                        current_user.child("Name").setValue(name);
                        current_user.child("Age").setValue("Not set yet");
                        current_user.child("Image").setValue("Not set yet");

*/

                        dialog.dismiss(); // dismissing at the time signed up successfully n going to login screen...for login..
                       emptyEditText();
                        Toast.makeText(getApplicationContext(), "You Sucessfully Signed up", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }   // end of register method


    // after sucessfully putting user in database clear text field ..so that user get to know..he successfully created account
    private void emptyEditText() {
        mName.setText(null);
        mEmail.setText(null);
        mPassword.setText(null);
        mConfirmPassword.setText(null);
    }

    public void login(View view) {

        startActivity(new Intent(MainActivity.this, LoginScreen.class));
    }
    }
}