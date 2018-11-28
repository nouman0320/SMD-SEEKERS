package pk.edu.nu.smd.seekers;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends AppCompatActivity {

    FancyButton facebookLoginBtn;
    FancyButton googleplusLoginBtn;
    FancyButton emailSignupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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

    }
}
