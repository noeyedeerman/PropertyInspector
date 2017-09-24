package sit374_team17.propertyinspector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import sit374_team17.propertyinspector.User.Activity_Login;

public class Activity_SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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
