package sit374_team17.propertyinspector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button_goToLoginActivity = (Button) findViewById(R.id.button_goToMainActivity);
        button_goToLoginActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                startActivity(intent);
            }

        });

        //Instance of the "Create Account" labeled TextView created and on click method opens the Activity_Create activity
        TextView text_goToCreateActivity = (TextView) findViewById(R.id.text_goToCreateActivity);
        text_goToCreateActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(Activity_Login.this, Activity_Create.class);
                startActivity(intent);
            }

        });

    }

}
