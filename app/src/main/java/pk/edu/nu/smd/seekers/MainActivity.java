package pk.edu.nu.smd.seekers;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    NavigationView mNavigationView;

    RecyclerView recyclerView;
    PostCardAdapter adapter;

    List<PostCard> postCardList;

    boolean loginStatus = false;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    private Button loginButtonMainScreen;


    private Toolbar mToolbar;

    private ImageButton notification_btn;
    private ImageButton search_btn;

    Menu _menu = null;

    MenuItem profileButton;
    MenuItem logoutButton;


    MenuItem new_request;
    MenuItem active_requests;
    MenuItem previous_requests;
    MenuItem funded_by;
    MenuItem backed_by;
    MenuItem message;
    MenuItem settings;
    TextView balance_tv;
    TextView balance_desc_tv;

    ConstraintLayout LoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginRegister = findViewById(R.id.constraintLayoutLoginRegister);

        SharedPreferences prefs = getSharedPreferences("login_status", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("login", false);
        if(loggedIn == true) {
            final float scale = getBaseContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (0 * scale + 0.5f);
            LoginRegister.setMaxHeight(pixels);
        }


        mNavigationView = findViewById(R.id.main_navigation_menu);

        _menu = mNavigationView.getMenu();
        profileButton = _menu.findItem(R.id.login);
        logoutButton = _menu.findItem(R.id.logout);
        new_request = _menu.findItem(R.id.new_request);
        active_requests = _menu.findItem(R.id.active_requests);
        previous_requests = _menu.findItem(R.id.previous_requests);
        funded_by = _menu.findItem(R.id.funded_by);
        backed_by = _menu.findItem(R.id.backed_by);
        message = _menu.findItem(R.id.message);
        settings = _menu.findItem(R.id.settings);
        balance_tv = findViewById(R.id.balance_tv);
        balance_desc_tv = findViewById(R.id.balance_desc_tv);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        loginButtonMainScreen = findViewById(R.id.loginButtonMainScreen);

        loginButtonMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginScreen();
            }
        });




        postCardList = new ArrayList<>();

        recyclerView = findViewById(R.id.postcard_recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postCardList.add(new PostCard("Nouman Arshad"));
        postCardList.add(new PostCard("Muhammad Shahnawaz"));
        postCardList.add(new PostCard("Zain Shahid"));
        postCardList.add(new PostCard("user4"));
        postCardList.add(new PostCard("user5"));

        adapter = new PostCardAdapter(this, postCardList);
        recyclerView.setAdapter(adapter);


        notification_btn = findViewById(R.id.notification_btn);
        search_btn = findViewById(R.id.search_btn);




        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Notification button pressed", Toast.LENGTH_LONG).show();
            }
        });


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Search button pressed", Toast.LENGTH_LONG).show();
            }
        });

        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        setTitle("Requests");
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);


        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        _menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void showLoginScreen(){
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        //finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.login && !loginStatus) {
            showLoginScreen();
        }
        if(id == R.id.new_request){
            Intent i = new Intent(MainActivity.this, NewRequestActivity.class);
            startActivity(i);
        }

        if(id == R.id.logout){

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Logging you out");
            progressDialog.show();

            SharedPreferences.Editor editor = getSharedPreferences("login_status", MODE_PRIVATE).edit();
            editor.putBoolean("login", false);
            editor.apply();

            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("login_status", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("login", false);
        if(loggedIn == true){
            SharedPreferences userPrefs = getSharedPreferences("user_details", MODE_PRIVATE);
            String user_fullname = userPrefs.getString("fullname", "User");
            Log.d("login_status", Boolean.toString(loggedIn));
            Log.d("user", user_fullname);
            profileButton.setTitle(user_fullname);

            logoutButton.setVisible(true);

            loginStatus = true;


            /*
             new_request = _menu.findItem(R.id.new_request);
        active_requests = _menu.findItem(R.id.active_requests);
        previous_requests = _menu.findItem(R.id.previous_requests);
        funded_by = _menu.findItem(R.id.funded_by);
        backed_by = _menu.findItem(R.id.backed_by);
        message = _menu.findItem(R.id.message);
        settings = _menu.findItem(R.id.settings);
        balance_tv = findViewById(R.id.balance_tv);
             */

            boolean option = true;
            new_request.setVisible(option);
            active_requests.setVisible(option);
            previous_requests.setVisible(option);
            funded_by.setVisible(option);
            backed_by.setVisible(option);
            message.setVisible(option);
            settings.setVisible(option);
            balance_tv.setVisibility(View.VISIBLE);
            balance_desc_tv.setVisibility(View.VISIBLE);


        }
        else{
            final float scale = getBaseContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (171 * scale + 0.5f);
            LoginRegister.setMaxHeight(pixels);

            logoutButton.setVisible(false);

            loginStatus = false;

            boolean option = false;
            new_request.setVisible(option);
            active_requests.setVisible(option);
            previous_requests.setVisible(option);
            funded_by.setVisible(option);
            backed_by.setVisible(option);
            message.setVisible(option);
            settings.setVisible(option);
            balance_tv.setVisibility(View.INVISIBLE);
            balance_desc_tv.setVisibility(View.INVISIBLE);

        }
    }
}
