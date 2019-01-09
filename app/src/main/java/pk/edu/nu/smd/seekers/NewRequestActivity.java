package pk.edu.nu.smd.seekers;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewRequestActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseUsers;


    EditText title_et;
    EditText description_et;
    EditText requiredFunds_et;
    EditText targetDays_et;
    CheckBox terms_checkbox;
    Button post_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);


        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("posts");
        progressDialog = new ProgressDialog(this);


        title_et = findViewById(R.id.editText4);
        description_et = findViewById(R.id.editText6);
        requiredFunds_et = findViewById(R.id.editText7);
        targetDays_et = findViewById(R.id.editText8);
        terms_checkbox = findViewById(R.id.checkBox);
        post_btn = findViewById(R.id.postButton);


        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_post();
            }
        });

    }

    void new_post(){

    }



}
