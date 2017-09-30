package sit374_team17.propertyinspector;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.transition.Transition;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.HashMap;

import sit374_team17.propertyinspector.User.Activity_Login;

public class Activity_SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    private static int SPLASH_FADE_DURATION = 300;
    private Transition mFadeTransition;

    private SavedPreference savedPreference;
    private HashMap<String,String> credentials=new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        savedPreference=new SavedPreference(this);
        credentials=savedPreference.getCredentials();
        if (!credentials.get("username").equals(""))
        {
            Intent intent = new Intent(Activity_SplashScreen.this, Activity_Login.class);
            intent.putExtra("username",credentials.get("username"));
            intent.putExtra("password",credentials.get("password"));
            startActivity(intent);
            finish();
        }

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            Transition mFadeTransition = TransitionInflater.from(this).inflateTransition(R.transition.fade_transition);
//        }
      //   Transition mFadeTransition = new Fade();


       
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            mFadeTransition = TransitionInflater.from(this).inflateTransition(R.transition.fade_transition);
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
//        }

//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(Activity_SplashScreen.this, Activity_Login.class);
//
//
//                  //  intent.putExtra("fade", mFadeTransition);
//                 Fade enterTransition = new Fade();
//                    enterTransition.setDuration(SPLASH_FADE_DURATION);
//                  //  getWindow().setEnterTransition(enterTransition);
//
//
//                startActivity(intent);
//              //  TransitionManager.go(Activity_Login.class, mFadeTransition);
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
//



        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout_splash);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_SplashScreen.this, Activity_Login.class);
           //     Fade enterTransition = new Fade();
             //   enterTransition.setDuration(SPLASH_FADE_DURATION);
                startActivity(intent);
            }
        });
    }


}



