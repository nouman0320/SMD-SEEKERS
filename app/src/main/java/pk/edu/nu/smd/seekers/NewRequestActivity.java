package pk.edu.nu.smd.seekers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pk.edu.nu.smd.seekers.Models.Post;

public class NewRequestActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference databasePosts;


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
        databasePosts = FirebaseDatabase.getInstance().getReference("posts");
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

        String title = title_et.getText().toString().trim();
        String description = description_et.getText().toString().trim();
        String requiedFunds = requiredFunds_et.getText().toString().trim();
        String targetDays = targetDays_et.getText().toString().trim();


        if(TextUtils.isEmpty(title)){
            Toast.makeText(this,"Please enter title", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(this,"Please enter description", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(requiedFunds)){
            Toast.makeText(this,"Please enter required funds", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(targetDays)){
            Toast.makeText(this,"Please enter target days", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!terms_checkbox.isChecked()){
            Toast.makeText(this,"Please accept terms and conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        int targetDays_int = 0;
        int requiredFunds_int = 0;

        try {
            int num = Integer.parseInt(requiedFunds);
            if(num <= 0){
                Toast.makeText(this,"Please enter valid required funds", Toast.LENGTH_SHORT).show();
                return;
            }
            requiredFunds_int = num;
        } catch (NumberFormatException e) {
            Toast.makeText(this,"Please enter valid required funds", Toast.LENGTH_SHORT).show();
            return;
        }




        try {
            int num = Integer.parseInt(targetDays);
            if(num <= 0){
                Toast.makeText(this,"Please enter valid target days", Toast.LENGTH_SHORT).show();
                return;
            }
            targetDays_int = num;
        } catch (NumberFormatException e) {
            Toast.makeText(this,"Please enter valid target days", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Post a new request..");
        progressDialog.show();

        final String id = databasePosts.push().getKey();

        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        String userId = prefs.getString("id", null);
        String userName = prefs.getString("fullname", null);

        Post post = new Post(id, title, userId, userName, description, "Islamabad", requiredFunds_int, 0, targetDays_int, 0);
        //    public Post(String postTitle, String userID, String userName, String postDescription, String location, int totalAmount, int raisedAmount, int days, int backedBy) {

        try{
            databasePosts.child(id).setValue(post).addOnSuccessListener(new OnSuccessListener<Object>() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(getApplicationContext(), "Posted!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(NewRequestActivity.this, MainActivity.class);
                    progressDialog.dismiss();
                    startActivity(i);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to post right now..", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }catch (Exception e){
            progressDialog.dismiss();
        }
    }



}
