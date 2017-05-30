package sit374_team17.propertyinspector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Regions;

public class Activity_Create extends AppCompatActivity implements SignUpHandler{

    Spinner date_of_birth,month_of_birth,year_of_birth;
    ArrayAdapter dateAdapter,monthAdapter,yearAdapter;
    String [] date_range=new String[31];
    String [] year_range=new String[100];
    int tempYear=1917;
    Button submitToSignUp ;
    EditText edt_firstName,edt_surName,edt_email,edt_password,edt_mobile;
    private static String LOG_TAG="PropertyInspector";
    protected CognitoUserPool userPool;
    /**
     * Cognito Your Identity Pool ID
     */
    protected final String userPoolId="ap-southeast-2_e4nCxiblG";
    /**
     * Client ID created for your pool {@code userPoolId}.
     */
    protected final String clientId="7urpmpfrej7qj5msrpju7hagum";

    /**
     * Client secret generated for this {@code clientId}, this may be {@code null} if a secret is not
     * generated for the {@code clientId}.
     */
    protected final String clientSecret="1vtjn8qulev7dg5c51jb0kq31jo27h208uqa9grj1agmr8uvckd7";




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

    //View are initialize and operated below
    void initViews()
    {
        date_of_birth=(Spinner)findViewById(R.id.date_of_birth);
        month_of_birth=(Spinner)findViewById(R.id.month_of_birth);
        year_of_birth=(Spinner)findViewById(R.id.year_of_birth);

        edt_firstName=(EditText)findViewById(R.id.edt_firstName);
        edt_surName=(EditText)findViewById(R.id.edt_surName);
        edt_email=(EditText)findViewById(R.id.edt_email);
        edt_password=(EditText)findViewById(R.id.edt_password);
        edt_mobile=(EditText)findViewById(R.id.edt_mobile);

        submitToSignUp=(Button)findViewById(R.id.submitToSignUp);
        submitToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty(edt_firstName)||checkEmpty(edt_surName)||checkEmpty(edt_email)||checkEmpty(edt_password)||checkEmpty(edt_mobile))
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty ",Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(getApplicationContext(),"Processing your request.. ",Toast.LENGTH_LONG).show();
                    ClientConfiguration clientConfiguration = new ClientConfiguration();
                    /**
                     * This represents a user-pool in a Cognito identity provider account.
                     */
                    userPool = new CognitoUserPool(Activity_Create.this,userPoolId ,clientId ,clientSecret, clientConfiguration, Regions.AP_SOUTHEAST_2);
                    // Create a CognitoUserAttributes object and add user attributes
                    CognitoUserAttributes userAttributes = new CognitoUserAttributes();
                    // Add the user attributes. Attributes are added as key-value pairs
                    // Adding user's given name.
                    // Note that the key is "given_name" which is the OIDC claim for given name
                    userAttributes.addAttribute("given_name", edt_firstName.getText().toString().concat(" ").concat(edt_surName.getText().toString()));
                    // Adding user's phone number
                    userAttributes.addAttribute("phone_number", edt_mobile.getText().toString());
                    // Adding user's email address
                    userAttributes.addAttribute("email", edt_email.getText().toString());
                    // Adding user's family_name
                    userAttributes.addAttribute("family_name", edt_firstName.getText().toString().concat(" ").concat(edt_surName.getText().toString()));
                    // Adding user's address
                    userAttributes.addAttribute("address", "None");

                    userPool.signUpInBackground( edt_email.getText().toString(),edt_password.getText().toString(), userAttributes, null, Activity_Create.this);
                }
            }
        });
        addValues();
    }

    protected void addValues()
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

    protected static boolean checkEmpty(EditText editText)
    {
        return editText.getText().toString().equals("");
    }

    /**
     * Implemented methods of SignUpHandler
     */
    @Override
    public void onSuccess(CognitoUser cognitoUser, boolean b, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
        // Sign-up was successful
        Log.d(LOG_TAG,"Sign-up was successful");
        Toast.makeText(getApplicationContext(),"Sign-up successfully and confirmation code was sent to your mail",Toast.LENGTH_SHORT).show();
        // Check if this user (cognitoUser) needs to be confirmed
        if(!b) {
            // This user must be confirmed and a confirmation code was sent to the user
            // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
            // Get the confirmation code from user
            Log.d(LOG_TAG,"This user must be confirmed and a confirmation code was sent to the user");
        }
        else {
            // The user has already been confirmed
            Log.d(LOG_TAG,"The user has already been confirmed");
        }
        finish();
    }

    @Override
    public void onFailure(Exception e) {
        Log.e(LOG_TAG,e.getMessage());
    }
}
