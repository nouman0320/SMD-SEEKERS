package pk.edu.nu.smd.seekers;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    PostCardAdapter adapter;

    List<PostCard> postCardList;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;



    private Toolbar mToolbar;

    private ImageButton notification_btn;
    private ImageButton search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
