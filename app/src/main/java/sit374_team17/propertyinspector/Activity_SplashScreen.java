package sit374_team17.propertyinspector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;

import sit374_team17.propertyinspector.User.Activity_Login;

public class Activity_SplashScreen extends AppCompatActivity {

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

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_splash);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_SplashScreen.this, Activity_Login.class);
                startActivity(intent);
            }
        });
    }
}
