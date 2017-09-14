package sit374_team17.propertyinspector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.NewPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentityprovider.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException;

import java.util.Map;

import sit374_team17.propertyinspector.Main.MainActivity;

public class Activity_Login extends AppCompatActivity implements AuthenticationHandler {


    protected CognitoUser cognitoUser;
    protected CognitoUserPool userPool;
    public static String LOG_TAG="PropertyInspector";
    private String idToken="";
    private EditText edt_email,edt_password;
    /**
     * Cognito Your Identity Pool ID
     */
    public final static String userPoolId="ap-southeast-2_e4nCxiblG";
    /**
     * Client ID created for your pool {@code userPoolId}.
     */
    public final static String clientId="7urpmpfrej7qj5msrpju7hagum";

    /**
     * Client secret generated for this {@code clientId}, this may be {@code null} if a secret is not
     * generated for the {@code clientId}.
     */
    public final static String clientSecret="1vtjn8qulev7dg5c51jb0kq31jo27h208uqa9grj1agmr8uvckd7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_email=(EditText) findViewById(R.id.editText_username);
        edt_password=(EditText) findViewById(R.id.editText_password);
        Button button_goToLoginActivity = (Button) findViewById(R.id.button_goToMainActivity);
        button_goToLoginActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (checkEmpty(edt_email)||checkEmpty(edt_email)) {
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty ",Toast.LENGTH_LONG).show();
                }else
                {
                    ClientConfiguration clientConfiguration = new ClientConfiguration();
                    // Create a CognitoUserPool object to refer to your user pool
                    userPool = new CognitoUserPool(Activity_Login.this, userPoolId, clientId, clientSecret, clientConfiguration, Regions.AP_SOUTHEAST_2);
                    // Create a CognitoUserPool object to refer to your user pool
                    cognitoUser = userPool.getUser();
                    cognitoUser.getSessionInBackground(Activity_Login.this);
                }
            }

        });
        //Instance of the "Create Account" labeled TextView created and on click method opens the Activity_Create activity
        TextView text_goToCreateActivity = (TextView) findViewById(R.id.text_goToCreateActivity);
        text_goToCreateActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login.this, Activity_Create.class);
                startActivity(intent);
            }

        });
    }

    // Implement callback handler for getting details
    GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {
        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            // The user detail are in cognitoUserDetails
            Map mDetails=cognitoUserDetails.getAttributes().getAttributes();
            Log.d(LOG_TAG,"Phone Number"+mDetails.get("phone_number"));
            Log.d(LOG_TAG,"Email "+mDetails.get("email"));
            Log.d(LOG_TAG,"Family Name"+mDetails.get("family_name"));
            Intent intent = new Intent(Activity_Login.this, MainActivity.class);
            intent.putExtra("tokens",idToken);
            intent.putExtra("password",edt_password.getText().toString());
            startActivity(intent);
        }

        @Override
        public void onFailure(Exception exception) {
            // Fetch user details failed, check exception for the cause
            Log.e(LOG_TAG,"Failed"+exception.toString());
        }
    };

    @Override
    public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice cognitoDevice) {
        // Get id token from CognitoUserSession.
        idToken = cognitoUserSession.getIdToken().getJWTToken();
        // Fetch the user details
        cognitoUser.getDetailsInBackground(getDetailsHandler);

    }

    @Override
    public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String s) {
        Log.d(LOG_TAG,"Authentication is required and submitted successfully");
        Toast.makeText(getApplicationContext(),"Checking credentials please wait..  ",Toast.LENGTH_LONG).show();
      //  AuthenticationDetails authenticationDetails = new AuthenticationDetails(edt_email.getText().toString(), edt_password.getText().toString(), null);
        AuthenticationDetails authenticationDetails = new AuthenticationDetails("John", "JohnSmith12", null);
        // Pass the user sign-in credentials to the continuation
        authenticationContinuation.setAuthenticationDetails(authenticationDetails);
        // Allow the sign-in to continue
        authenticationContinuation.continueTask();
    }

    @Override
    public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
        // Multi-factor authentication is required; get the verification code from user;
        //currently disable in this case
    }

    @Override
    public void authenticationChallenge(ChallengeContinuation challengeContinuation) {
        if (challengeContinuation.getParameters().get("requiredAttributes").length()>2)
        {
            challengeContinuation.setChallengeResponse("userAttributes.family_name", "None");
            challengeContinuation.setChallengeResponse("userAttributes.given_name", "None");
            challengeContinuation.setChallengeResponse("userAttributes.address", "None");
        }
        if ("NEW_PASSWORD_REQUIRED".equals(challengeContinuation.getChallengeName())) {
            NewPasswordContinuation newPasswordContinuation = (NewPasswordContinuation) challengeContinuation;
            newPasswordContinuation.setPassword( edt_password.getText().toString());
            challengeContinuation.continueTask();
        }

    }

    @Override
    public void onFailure(Exception e) {
        if (e instanceof UserNotConfirmedException)
            Toast.makeText(getApplicationContext(),"User is not confirmed.Please confirm your account to proceed.",Toast.LENGTH_LONG).show();
        else if (e instanceof NotAuthorizedException)
            Toast.makeText(getApplicationContext(),"Either username or password is wrong ",Toast.LENGTH_LONG).show();

    }
    protected static boolean checkEmpty(EditText editText)
    {
        return editText.getText().toString().equals("");
    }


}

