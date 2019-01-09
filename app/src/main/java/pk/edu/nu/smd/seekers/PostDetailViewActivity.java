package pk.edu.nu.smd.seekers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import pk.edu.nu.smd.seekers.Models.Post;

public class PostDetailViewActivity extends AppCompatActivity {

    ImageButton backButton;


    TextView title_tv;
    TextView name_tv;
    TextView description_tv;

    ProgressBar progressBar;
    TextView pledge_tv;
    TextView backedby_tv;
    TextView days_tv;

    Button donate_btn;

    Post post;

    NotificationCompat.Builder notification;
    NotificationManager notificationManager;
    static int id=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_view);

        post = (Post) getIntent().getSerializableExtra("post");

        title_tv = findViewById(R.id.textView16);
        name_tv = findViewById(R.id.username_textview);
        description_tv = findViewById(R.id.textView9);
        progressBar = findViewById(R.id.progressBar);
        pledge_tv = findViewById(R.id.textView10);
        backedby_tv = findViewById(R.id.textView12);
        days_tv = findViewById(R.id.textView7);
        donate_btn = findViewById(R.id.postButton);


        title_tv.setText(post.getPostTitle());
        description_tv.setText(post.getPostDescription());

        name_tv.setText(post.getUserName());
        String amount_txt = "Rs. " + post.getRaisedAmount() + " Pledged of Rs. " + post.getTotalAmount();
        pledge_tv.setText(amount_txt);
        backedby_tv.setText(String.valueOf(post.getBackedBy()));
        days_tv.setText(String.valueOf(post.getDays()));
        double percentage = (post.getRaisedAmount()/post.getTotalAmount())*100;

        progressBar.setMax(100);
        progressBar.setProgress((int)percentage);


        donate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donate();
            }
        });



        backButton = findViewById(R.id.backDetailImageButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostDetailViewActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    void donate(){
        showAmountDialog();
    }


    void showAmountDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PostDetailViewActivity.this);
        alertDialog.setTitle("Donation");
        alertDialog.setMessage("Please enter amount to donate ");

        final EditText input = new EditText(PostDetailViewActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.round_attach_money_24);

        alertDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        int donation_amount = 0;
                        String amount_string = input.getText().toString();

                        if(TextUtils.isEmpty(amount_string)){
                            Toast.makeText(getApplicationContext(),"Please enter valid donation amount", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        try {
                            int num = Integer.parseInt(amount_string);
                            if(num <= 0){
                                Toast.makeText(getApplicationContext(),"Please enter valid donation amount", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            donation_amount = num;


                            SharedPreferences userPrefs = getSharedPreferences("user_details", MODE_PRIVATE);
                            int balance = userPrefs.getInt("balance", 50000);

                            if(donation_amount > balance){
                                Toast.makeText(getApplicationContext(),"Donation is greater than balance", Toast.LENGTH_SHORT).show();
                                return;
                            }else{
                                balance = balance - donation_amount;

                                post.setRaisedAmount(post.getRaisedAmount()+donation_amount);
                                post.setBackedBy(post.getBackedBy()+1);

                                String amount_txt = "Rs. " + post.getRaisedAmount() + " Pledged of Rs. " + post.getTotalAmount();
                                pledge_tv.setText(amount_txt);
                                backedby_tv.setText(String.valueOf(post.getBackedBy()));
                                days_tv.setText(String.valueOf(post.getDays()));
                                double percentage = (post.getRaisedAmount()/post.getTotalAmount())*100;

                                progressBar.setMax(100);
                                progressBar.setProgress((int)percentage);

                                SharedPreferences.Editor editor = getSharedPreferences("user_details", MODE_PRIVATE).edit();
                                editor.putInt("balance", balance);
                                editor.apply();

                                sendNotification(donation_amount);
                            }


                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(),"Please enter valid donation amount", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    void sendNotification(int amount){
        notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notification=new NotificationCompat.Builder(getApplicationContext());
        notification.setSmallIcon(R.drawable.round_attach_money_24);
        notification.setTicker("Donation confirmed");
        notification.setContentTitle("Thank you!");
        notification.setContentText("You have donated Rs. "+amount+ " to "+post.getUserName());
        notification.setChannelId("Donation");
        Intent intent=new Intent(getApplicationContext(),PostDetailViewActivity.class);
        intent.putExtra("post", post);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("SEEKERS", "SEEKERS Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            notification.setChannelId("SEEKERS");
        }

        notificationManager.notify(id,notification.build());
        id++;
    }
}
