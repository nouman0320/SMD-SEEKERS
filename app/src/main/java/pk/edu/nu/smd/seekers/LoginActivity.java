package pk.edu.nu.smd.seekers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import mehdi.sakout.fancybuttons.FancyButton;
import mehdi.sakout.fancybuttons.Utils;
import pk.edu.nu.smd.seekers.Models.User;

public class LoginActivity extends AppCompatActivity {

    FancyButton facebookLoginBtn;
    FancyButton googleplusLoginBtn;
    FancyButton emailSignupBtn;

    ImageButton backButton;


    EditText email_et;
    EditText password_et;
    Button login_btn;

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        progressDialog = new ProgressDialog(this);

        setContentView(R.layout.activity_login);

        email_et = findViewById(R.id.editText2);
        password_et = findViewById(R.id.editText3);
        login_btn = findViewById(R.id.loginButton);

        backButton = findViewById(R.id.backImageButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        facebookLoginBtn = findViewById(R.id.btn_facebook);
        googleplusLoginBtn = findViewById(R.id.btn_googleplus);
        emailSignupBtn = findViewById(R.id.btn_email_signup);

        facebookLoginBtn.setText("      Continue with Facebook");
        facebookLoginBtn.setBackgroundColor(Color.parseColor("#3b5998"));
        facebookLoginBtn.setFocusBackgroundColor(Color.parseColor("#5474b8"));
        facebookLoginBtn.setTextSize(17);
        facebookLoginBtn.setRadius(5);
        facebookLoginBtn.setIconResource("\uf082");
        facebookLoginBtn.setIconPosition(FancyButton.POSITION_LEFT);
        facebookLoginBtn.setFontIconSize(30);


        googleplusLoginBtn.setText("      Continue with Google+");
        googleplusLoginBtn.setBackgroundColor(Color.parseColor("#dd4b39"));
        googleplusLoginBtn.setFocusBackgroundColor(Color.parseColor("#D67064"));
        googleplusLoginBtn.setTextSize(17);
        googleplusLoginBtn.setRadius(5);
        googleplusLoginBtn.setIconResource("\uf0d5");
        googleplusLoginBtn.setIconPosition(FancyButton.POSITION_LEFT);
        googleplusLoginBtn.setFontIconSize(30);


        emailSignupBtn.setText("      Sign up with Email");
        emailSignupBtn.setBackgroundColor(Color.parseColor("#58A5E1"));
        emailSignupBtn.setFocusBackgroundColor(Color.parseColor("#8BBEE6"));
        emailSignupBtn.setTextSize(17);
        emailSignupBtn.setRadius(5);
        emailSignupBtn.setIconResource("\uf0e0");
        emailSignupBtn.setIconPosition(FancyButton.POSITION_LEFT);
        emailSignupBtn.setFontIconSize(30);


        emailSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();


            }
        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    void login(){
        if(TextUtils.isEmpty(email_et.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(password_et.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isEmailValid(email_et.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email_et.getText().toString().trim(), password_et.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    SharedPreferences.Editor editor = getSharedPreferences("login_status", MODE_PRIVATE).edit();
                    editor.putBoolean("login", true);
                    editor.apply();

                    databaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                                User user = userSnapshot.getValue(User.class);

                                if(user.getEmail().equals(email_et.getText().toString().trim())){
                                    Log.d("User", user.getEmail());

                                    SharedPreferences.Editor editor = getSharedPreferences("user_details", MODE_PRIVATE).edit();
                                    editor.putString("fullname", user.getFullname());
                                    editor.putString("gender", user.getGender());
                                    editor.putString("id", user.getUserID());
                                    editor.putString("email", user.getEmail());
                                    editor.apply();

                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    progressDialog.dismiss();
                                    progressDialog.dismiss();
                                    startActivity(i);
                                    finish();

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });
    }
}
