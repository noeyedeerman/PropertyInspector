package sit374_team17.propertyinspector;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity_Old extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        Button advanceToLoginActivity = (Button) findViewById(R.id.loginButton);
        advanceToLoginActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity_Old.this, Activity_Login.class);
                startActivity(intent);
            }

        });

        Button advanceToSearchActivity = (Button) findViewById(R.id.searchButton);
        advanceToSearchActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity_Old.this, Activity_Search.class);
                startActivity(intent);
            }

        });
        Button advanceToCreateActivity = (Button) findViewById(R.id.createButton);
        advanceToCreateActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity_Old.this, Activity_Create.class);
                startActivity(intent);
            }

        });
        Button advanceToSavedActivity = (Button) findViewById(R.id.savedButton);
        advanceToSavedActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity_Old.this, Activity_Saved.class);
                startActivity(intent);
            }

        });


    }
}