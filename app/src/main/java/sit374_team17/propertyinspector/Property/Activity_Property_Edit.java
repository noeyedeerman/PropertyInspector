package sit374_team17.propertyinspector.Property;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import sit374_team17.propertyinspector.Note.Activity_Note_Edit;
import sit374_team17.propertyinspector.Note.Note;
import sit374_team17.propertyinspector.R;

public class Activity_Property_Edit extends AppCompatActivity implements Listener_Property_Edit {
    int count = 0;

    protected CognitoCachingCredentialsProvider credentialsProvider;
    protected DynamoDBMapper mapper;
    private String IDENTITY_POOL_ID = "ap-southeast-2:da48cacc-60b6-41ee-8dc6-4ae3c3abf13a";
    private String MY_BUCKET = "propertyinspector-userfiles-mobilehub-4404653";
    private String OBJECT_KEY = "uploads/propertyinspector_image" + SystemClock.currentThreadTimeMillis();
    private File MY_FILE;
    private Property mProperty;
    private FragmentManager fm;
    private Button button_save, button_continue;
    public static String propertyId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        propertyId="";
        //  final FragmentManager fm = getSupportFragmentManager();
        fm = getSupportFragmentManager();
        mProperty = new Property();
        // Initialises fragment
        button_continue = (Button) findViewById(R.id.button_continue);
        button_save = (Button) findViewById(R.id.button_save);
        button_save.setVisibility(View.GONE);

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (fm.getBackStackEntryCount()) {
                    case 0:
                        goTo_PropertyEditFragment2();
                        break;
                    case 1:
                        goTo_PropertyEditFragment3();
                        break;

                }
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProperty();
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        goTo_PropertyEditFragment1();


        credentialsProvider = new CognitoCachingCredentialsProvider(this, IDENTITY_POOL_ID, Regions.AP_SOUTHEAST_2);
        // Set up as a credentials provider.
        Map<String, String> logins = new HashMap<String, String>();
        logins.put("cognito-idp.ap-southeast-2.amazonaws.com/ap-southeast-2_e4nCxiblG", this.getIntent().getStringExtra("tokens"));
        credentialsProvider.setLogins(logins);
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
        mapper = new DynamoDBMapper(ddbClient);
    }


    private void saveProperty() {

        Log.e("Property Inspector","Property Saved"+mProperty.getCategory()+mProperty.getAddress());
        if (propertyId.equals("")) {
            if (mProperty.getCity().equals("") || mProperty.getState().isEmpty() || mProperty.getPostCode() == 0)
                Toast.makeText(getApplicationContext(), "Enter proper address", Toast.LENGTH_LONG).show();
            else if (mProperty.getPrice().get(0) == 0)
                Toast.makeText(getApplicationContext(), "Enter proper price", Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(getApplicationContext(), "Saving property please wait", Toast.LENGTH_LONG).show();
                Runnable runnable = new Runnable() {
                    public void run() {
                        //DynamoDB calls go here
                        try {
                            mapper.save(mProperty);
                            propertyId = mProperty.getId();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showAlert();
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to create property", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                };
                Thread myThread = new Thread(runnable);
                myThread.start();
            }
        }
    }

    public void showAlert()
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Message");
        alertDialog.setMessage("Property created successfully. Do you want to create notes ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }

    // Passes "CreateGroupFragment" fragment to the "replaceFragment" method.
    public void goTo_PropertyEditFragment1() {
        Fragment_Property_Edit_1 fragment = Fragment_Property_Edit_1.newInstance(mProperty);
        replaceFragment(fragment, "Fragment_Property_Edit_1");
    }

    // Passes "CreateGroupFragment" fragment to the "replaceFragment" method.
    public void goTo_PropertyEditFragment2() {
        Fragment_Property_Edit_1 fragment_property_edit_1 = (Fragment_Property_Edit_1) fm.findFragmentById(R.id.content_property_edit);
        fragment_property_edit_1.getDetails_1();
        Fragment_Property_Edit_2 fragment = Fragment_Property_Edit_2.newInstance(mProperty);
        replaceFragment(fragment, "Fragment_Property_Edit_2");
    }

    // Passes "CreateGroupFragment" fragment to the "replaceFragment" method.
    public void goTo_PropertyEditFragment3() {
        Fragment_Property_Edit_2 fragment_property_edit_2 = (Fragment_Property_Edit_2) fm.findFragmentById(R.id.content_property_edit);
        fragment_property_edit_2.getDetails_2();
        Fragment_Property_Edit_3 fragment = Fragment_Property_Edit_3.newInstance(mProperty);
        replaceFragment(fragment, "Fragment_Property_Edit_3");
        button_continue.setVisibility(View.GONE);
        button_save.setVisibility(View.VISIBLE);
    }

    @Override
    public void goTo_NoteEditActivity(Note note, String openCamera) {
        Intent intent = new Intent(Activity_Property_Edit.this, Activity_Note_Edit.class);
        if (openCamera.equals("do"))
            note.setCommentType("photo");
        else
            note.setCommentType("text");
        intent.putExtra("property", mProperty);
        intent.putExtra("note", note);
        intent.putExtra("methodName", openCamera);
        startActivityForResult(intent, 1);
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
            if (tag == "Fragment_Property_Edit_2" || tag == "Fragment_Property_Edit_3") {
                fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right, R.anim.exit_left, R.anim.enter_right);
                fragmentTransaction.addToBackStack(null);

//                if (tag == "Fragment_Property_Edit_3") {
//                    Bundle args = new Bundle();
//                    args.putParcelable("PROPERTY", mProperty);
//                    fragment.setArguments(args);
//                }
            }

            fragmentTransaction.replace(R.id.content_property_edit, fragment, tag);
            fragmentTransaction.commit();

        } catch (Exception e) {
            Log.d(tag, e.toString());
        }
    }

    @Override
    public void goBackTo_Details() {
        onBackPressed();
        onBackPressed();
    }

    @Override
    public void goBackTo_Address() {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (fm.getBackStackEntryCount() <= 1) {
            button_continue.setVisibility(View.VISIBLE);
            button_save.setVisibility(View.GONE);
        }
    }

    //
//    Integer streetNumber;
//    private void saveProperty() {
//        if (!mEditText_streetNumber.getText().toString().equals(""))
//            streetNumber = Integer.parseInt(mEditText_streetNumber.getText().toString());
//        String streetName = mEditText_streetName.getText().toString();
//        String city = mEditText_city.getText().toString();
//        List<String> state = new ArrayList<>();
//        if (!mEditText_state.getText().toString().equals(""))
//            state.add(mEditText_state.getText().toString());
//        Integer postCode = Integer.parseInt(mEditText_postCode.getText().toString());
//        List<String> bedrooms =  new ArrayList<>();
//        bedrooms.add(String.valueOf(mNumberPicker_bedrooms.getValue()));
//        List<String>  bathrooms = new ArrayList<>();
//        bathrooms.add(String.valueOf(mNumberPicker_bathrooms.getValue()));
//        List<String> garages =  new ArrayList<>();
//        garages.add(String.valueOf(mNumberPicker_cars.getValue()));
//        List<Integer> price = new ArrayList<>();
//        price.add(Integer.parseInt(mEditText_price.getText().toString()));
//        String description=mEditText_description.getText().toString();
//
//        String empty = "--";
//        if (streetNumber>0 && !streetName.isEmpty()) {
//            mProperty.setStreetNumber(streetNumber);
//            mProperty.setStreetName(streetName);
//
//            if (city.isEmpty()) city ="None";
//            if (state.isEmpty()) state.add("None");
//            if (postCode==0) postCode = 0;
//            if (bedrooms.isEmpty()) bedrooms.add("None");
//            if (bathrooms.isEmpty()) bathrooms.add("None");
//            if (garages.isEmpty()) garages .add("None");
//            if (description.isEmpty())description="None";
//            if (price.isEmpty()) price.add(0);
//
//            mProperty.setCity(city);
//            mProperty.setState(state);
//            mProperty.setPostCode(postCode);
//            mProperty.setBedrooms(bedrooms);
//            mProperty.setBathrooms(bathrooms);
//            mProperty.setCars(garages);
//            mProperty.setPrice(price);
//            mProperty.setUnitNumber(0);
//            mProperty.setDescription(description);
//            // mProperty.setPhoto(ContextCompat.getDrawable(getContext(), R.drawable.house1));
//            Runnable runnable = new Runnable() {
//                public void run() {
//                    //DynamoDB calls go here
//                    try {
//                        mapper.save(mProperty);
//                        mPhotos.setPropertyId((mProperty.getId()));
//                        if (MY_FILE != null)
//                            mapper.save(mPhotos);
//                    }catch (Exception e){e.printStackTrace();}
//
//
//                }
//            };
//            Thread mythread = new Thread(runnable);
//            mythread.start();
//            //  mListener.onSaveProperty();
//        }
//    }


    @Override
    public void onContinue() {
        // Fragment_Property_Edit_1 fragment_property_edit_1 = (Fragment_Property_Edit_1) fm.findFragmentById(R.id.content_property_edit);
        // fragment_property_edit_1.getDetails_1();
    }

    @Override
    public void save() {

    }

    @Override
    public void setDetails_1(Property property) {
        mProperty.setDescription(property.getDescription());
        mProperty.setPrice(property.getPrice());
        mProperty.setBedrooms(property.getBedrooms());
        mProperty.setBathrooms(property.getBathrooms());
        mProperty.setCars(property.getCars());
        mProperty.setCategory(property.getCategory());
    }

    @Override
    public void setDetails_2(Property property) {
        mProperty.setStreetNumber(property.getStreetNumber());
        mProperty.setStreetName(property.getStreetName());
        mProperty.setCity(property.getCity());
        mProperty.setState(property.getState());
        mProperty.setPostCode(property.getPostCode());
        mProperty.setUnitNumber(property.getUnitNumber());
        mProperty.setAddress(property.getAddress());
    }



}
