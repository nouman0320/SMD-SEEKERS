package pk.edu.nu.smd.seekers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pk.edu.nu.smd.seekers.Models.User;

public class RegisterActivity extends AppCompatActivity {

    ImageButton backButton;

    Button registerButton;
    EditText fullnameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    RadioGroup genderRadioGroup;

    FirebaseAuth firebaseAuth;

    DatabaseReference databaseUsers;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        registerButton = findViewById(R.id.postButton);
        fullnameEditText = findViewById(R.id.fullnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);


        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        backButton = findViewById(R.id.backRegisterImageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("login_status", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("login", false);

        Log.d("login_status", Boolean.toString(loggedIn));

        if(loggedIn == true){

            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    void registerUser(){




        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();
        final String fullname = fullnameEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();

        } else if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this,"Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else {

            if(!isEmailValid(email)){
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            int genderID = genderRadioGroup.getCheckedRadioButtonId();
            RadioButton radioSexButton =findViewById(genderID);
            final String userGender = radioSexButton.getText().toString();

            progressDialog.setMessage("Registering user..");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        final String id = databaseUsers.push().getKey();
                        User user = new User(id, fullname, email, userGender, password, 50000);

                        try{
                            databaseUsers.child(id).setValue(user).addOnSuccessListener(new OnSuccessListener<Object>() {
                                @Override
                                public void onSuccess(Object o) {

                                    SharedPreferences.Editor editor = getSharedPreferences("login_status", MODE_PRIVATE).edit();
                                    editor.putBoolean("login", true);
                                    editor.apply();

                                    editor = getSharedPreferences("user_details", MODE_PRIVATE).edit();
                                    editor.putString("fullname", fullname);
                                    editor.putString("gender", userGender);
                                    editor.putString("id", id);
                                    editor.putString("email", email);
                                    editor.putInt("balance", 50000);
                                    editor.apply();

                                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                    progressDialog.dismiss();
                                    startActivity(i);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                }
                            });



                        }catch (Exception e){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Unable to register", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }

    }
}
