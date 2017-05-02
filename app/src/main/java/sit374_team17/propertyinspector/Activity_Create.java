package sit374_team17.propertyinspector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Activity_Create extends AppCompatActivity {

    Spinner date_of_birth,month_of_birth,year_of_birth;
    ArrayAdapter dateAdapter,monthAdapter,yearAdapter;
    String [] date_range=new String[31];
    String [] year_range=new String[100];
    int tempYear=1917;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Hides the Toolbar in this activity
        getSupportActionBar().hide();

        initViews();
    }

    void initViews()
    {
        date_of_birth=(Spinner)findViewById(R.id.date_of_birth);
        month_of_birth=(Spinner)findViewById(R.id.month_of_birth);
        year_of_birth=(Spinner)findViewById(R.id.year_of_birth);

        addValues();
    }

    void addValues()
    {
        //Add int values up to 31
        for (int i=0;i<31;i++)
            date_range[i]=String.valueOf(i+1);

        for (int i=0;i<100;i++)
        {
            tempYear=tempYear+1;
            year_range[i]=String.valueOf(tempYear);
        }

        //Initialize the adapter and attach it to the spinner
        dateAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, date_range);
        date_of_birth.setAdapter(dateAdapter);
        monthAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.months));
        month_of_birth.setAdapter(monthAdapter);
        yearAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, year_range);
        year_of_birth.setAdapter(yearAdapter);
    }

}
