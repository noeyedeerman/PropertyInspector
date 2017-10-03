package sit374_team17.propertyinspector.User;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

import sit374_team17.propertyinspector.R;

public class Activity_User_Edit_old extends AppCompatActivity implements Listener_User_Edit, SignUpHandler {

    Spinner date_of_birth, month_of_birth, year_of_birth;
    ArrayAdapter dateAdapter, monthAdapter, yearAdapter;
    String[] date_range = new String[31];
    String[] year_range = new String[100];
    int tempYear = 1917;
    Button submitToSignUp;
    EditText edt_firstName, edt_surName, edt_email, edt_password, edt_mobile;
    private static String LOG_TAG = "PropertyInspector";
    protected CognitoUserPool userPool;

    private User mUser;
    private Button button_continue, button_save;
    private FragmentManager fm;

    /**
     * Cognito Your Identity Pool ID
     */
    protected final String userPoolId = "ap-southeast-2_e4nCxiblG";
    /**
     * Client ID created for your pool {@code userPoolId}.
     */
    protected final String clientId = "7urpmpfrej7qj5msrpju7hagum";

    /**
     * Client secret generated for this {@code clientId}, this may be {@code null} if a secret is not
     * generated for the {@code clientId}.
     */
    protected final String clientSecret = "1vtjn8qulev7dg5c51jb0kq31jo27h208uqa9grj1agmr8uvckd7";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Hides the Toolbar in this activity
        getSupportActionBar().hide();
        mUser = new User();

        fm = getSupportFragmentManager();
        button_continue = (Button) findViewById(R.id.button_continue);
        button_save = (Button) findViewById(R.id.button_save);
        button_save.setVisibility(View.GONE);

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mListener.onContinue1();

                switch (fm.getBackStackEntryCount()) {
                    case 0:
                        goTo_UserEditFragment2();
                        break;
                    case 1:
                        goTo_UserEditFragment3();
                        break;
                }

                //  fm.getBackStackEntryCount()
                // goTo_CreatePropertyFragment2();
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        goTo_UserEditFragment1();
    }

    private void saveUser() {
        //   if (checkEmpty(edt_firstName)||checkEmpty(edt_surName)||checkEmpty(edt_email)||checkEmpty(edt_password)||checkEmpty(edt_mobile))
        //   Toast.makeText(getApplicationContext(),"Fields cannot be empty ",Toast.LENGTH_LONG).show();
        // else{
        Toast.makeText(getApplicationContext(), "Processing your request.. ", Toast.LENGTH_LONG).show();
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        /**
         * This represents a user-pool in a Cognito identity provider account.
         */
        userPool = new CognitoUserPool(Activity_User_Edit_old.this, userPoolId, clientId, clientSecret, clientConfiguration, Regions.AP_SOUTHEAST_2);
        // Create a CognitoUserAttributes object and add user attributes
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        // Add the user attributes. Attributes are added as key-value pairs
        // Adding user's given name.
        // Note that the key is "given_name" which is the OIDC claim for given name
        userAttributes.addAttribute("given_name", mUser.getFirstName());
        // Adding user's phone number
        userAttributes.addAttribute("phone_number", mUser.getPhone());
        // Adding user's email address
        userAttributes.addAttribute("email", mUser.getEmail());
        // Adding user's family_name
        userAttributes.addAttribute("family_name", mUser.getLastName());
        // Adding user's address
        userAttributes.addAttribute("address", "None");

        userPool.signUpInBackground(mUser.getEmail(), mUser.getPassword(), userAttributes, null, Activity_User_Edit_old.this);

        // finish();
        //  Intent intent = new Intent(Activity_User_Edit_old.this, MainActivity.class);
        //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // startActivity(intent);

        //    }

        //  addValues();
    }


    protected void addValues() {
        //Add int values up to 31
        for (int i = 0; i < 31; i++)
            date_range[i] = String.valueOf(i + 1);

        for (int i = 0; i < 100; i++) {
            tempYear = tempYear + 1;
            year_range[i] = String.valueOf(tempYear);
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

    protected static boolean checkEmpty(EditText editText) {
        return editText.getText().toString().equals("");
    }

    /**
     * Implemented methods of SignUpHandler
     */
    @Override
    public void onSuccess(CognitoUser cognitoUser, boolean b, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
        // Sign-up was successful
        Log.d(LOG_TAG, "Sign-up was successful");
        Toast.makeText(getApplicationContext(), "Sign-up successfully and confirmation code was sent to your mail", Toast.LENGTH_SHORT).show();
        // Check if this user (cognitoUser) needs to be confirmed
        if (!b) {
            // This user must be confirmed and a confirmation code was sent to the user
            // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
            // Get the confirmation code from user
            Log.d(LOG_TAG, "This user must be confirmed and a confirmation code was sent to the user");
        } else {
            // The user has already been confirmed
            Log.d(LOG_TAG, "The user has already been confirmed");
        }
        finish();
    }

    @Override
    public void onFailure(Exception e) {
        Log.e(LOG_TAG,e.getMessage());
        if (e.getMessage().contains("User already exists"))
            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
        if (e.getMessage().contains("Value at 'username' failed to satisfy constraint"))
            Toast.makeText(getApplicationContext(), "Username is invalid", Toast.LENGTH_SHORT).show();
    }

    // Passes "CreateGroupFragment" fragment to the "replaceFragment" method.
    public void goTo_UserEditFragment1() {
        Fragment_User_Edit_1 fragment = new Fragment_User_Edit_1();
        replaceFragment(fragment, "Fragment_User_Edit_1");
    }

    // Passes "CreateGroupFragment" fragment to the "replaceFragment" method.
    public void goTo_UserEditFragment2() {
        Fragment_User_Edit_1 fragment_user_edit_1 = (Fragment_User_Edit_1) fm.findFragmentById(R.id.content_user_edit);
        if (fragment_user_edit_1.getDetails_1())
        {
            Fragment_User_Edit_2 fragment = Fragment_User_Edit_2.newInstance(mUser);
            replaceFragment(fragment, "Fragment_User_Edit_2");
        }
    }

    // Passes "CreateGroupFragment" fragment to the "replaceFragment" method.
    public void goTo_UserEditFragment3() {
        Fragment_User_Edit_2 fragment_user_edit_2 = (Fragment_User_Edit_2) fm.findFragmentById(R.id.content_user_edit);
        if(fragment_user_edit_2.getDetails_2()) {
            Fragment_User_Edit_3 fragment = Fragment_User_Edit_3.newInstance(mUser);
            replaceFragment(fragment, "Fragment_User_Edit_3");
            button_continue.setVisibility(View.GONE);
            button_save.setVisibility(View.VISIBLE);
        }
    }
//
//    // Passes "AddPersonFragment" fragment to the "replaceFragment" method.
//    public void goTo_AddPersonFragment(Person person) {
//        Fragment_AddPerson fragment = Fragment_AddPerson.newInstance(person);
//        replaceFragment(fragment, "Fragment_AddPerson");
//    }

    // Receives fragment and starts transaction to replace fragments
    private void replaceFragment(android.support.v4.app.Fragment fragment, String tag) {
        try {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // Adds animation to the transaction and adds fragment to back stack
            // if (Objects.equals(tag, "Fragment_Property_Edit_2")) {
            if (tag == "Fragment_User_Edit_2" || tag == "Fragment_User_Edit_3") {
                fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right, R.anim.exit_left, R.anim.enter_right);
                fragmentTransaction.addToBackStack(null);

//                if (tag == "Fragment_Property_Edit_3") {
//                    Bundle args = new Bundle();
//                    args.putParcelable("PROPERTY", mProperty);
//                    fragment.setArguments(args);
//                }
            }

            fragmentTransaction.replace(R.id.content_user_edit, fragment, tag);
            fragmentTransaction.commit();

        } catch (Exception e) {
            Log.d(tag, e.toString());
        }
    }


    private void buttonVisability() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (fm.getBackStackEntryCount() <= 1) {
            button_continue.setVisibility(View.VISIBLE);
            button_save.setVisibility(View.GONE);
        }
    }

    @Override
    public void onContinue() {

    }

    @Override
    public void save() {

    }

    @Override
    public void setDetails_1(User user) {
        mUser.setEmail(user.getEmail());
        mUser.setPassword(user.getPassword());
    }

    @Override
    public void setDetails_2(User user) {
        mUser.setFirstName(user.getFirstName());
        mUser.setLastName(user.getLastName());
        mUser.setPhone(user.getPhone());
    }
}