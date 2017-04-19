package sit374_team17.propertyinspector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Button button_goToLoginActivity = (Button) findViewById(R.id.button_goToLoginActivity);
        button_goToLoginActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(Activity_SplashScreen.this, Activity_Login.class);
                startActivity(intent);
            }

        });

    }
}
