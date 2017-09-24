package sit374_team17.propertyinspector.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.io.File;
import java.util.List;

import sit374_team17.propertyinspector.Property.Photo;
import sit374_team17.propertyinspector.Property.Property;
import sit374_team17.propertyinspector.R;

public class Fragment_User_Edit_1 extends Fragment {

    private static final String ARG_USER = "user";

    private User mUser;
    private Photo mPhotos=new Photo();
    TextView file_path;
    View mView;
    Button mButton_save,mButton_upload;
    //DB_PropertyHandler mDB_properties;
    List<Property> mPropertyList;
    EditText mEditText_email, mEditText_password1, mEditText_password2;
    protected CognitoCachingCredentialsProvider credentialsProvider ;
    protected DynamoDBMapper mapper ;
    private String IDENTITY_POOL_ID="ap-southeast-2:da48cacc-60b6-41ee-8dc6-4ae3c3abf13a";
    private String MY_BUCKET="propertyinspector-userfiles-mobilehub-4404653";
    private String OBJECT_KEY="uploads/propertyinspector_image"+ SystemClock.currentThreadTimeMillis();
    private File MY_FILE;
    int pickerMin = 0;
    int pickerMax = 100;
    int SELECT_IMAGE = 103;
Listener_User_Edit mListener;
    //private Listener mListener;
//private CreatePropertyListener mListener;

    public Fragment_User_Edit_1() {
    }

    public void getDetails_1() {
        String email = mEditText_email.getText().toString();

        String password1 = mEditText_password1.getText().toString();
        String password2 = mEditText_password2.getText().toString();

        if (email.isEmpty() || email == "") email = "None";
        if (password1.isEmpty() || password1 == "") password1 = "None";
        if (password2.isEmpty() || password2 == "") password2 = "None";

        mUser = new User();
        mUser.setEmail(email);
        mUser.setPassword(password2);

        mListener.setDetails_1(mUser);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener_User_Edit) {
            mListener = (Listener_User_Edit) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener_User_Edit");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public static Fragment_User_Edit_1 newInstance(User user) {
        Fragment_User_Edit_1 fragment = new Fragment_User_Edit_1();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mDB_properties = new DB_PropertyHandler(getContext());
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARG_USER);
        }
    }
    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_user_edit_1, container, false);
        setHasOptionsMenu(true);


        mEditText_email = (EditText) mView.findViewById(R.id.editText_email);
        mEditText_password1 = (EditText) mView.findViewById(R.id.editText_password1);
        mEditText_password2 = (EditText) mView.findViewById(R.id.editText_password2);

//
//        credentialsProvider = new CognitoCachingCredentialsProvider(getActivity(), IDENTITY_POOL_ID, Regions.AP_SOUTHEAST_2);
//        // Set up as a credentials provider.
//        Map<String, String> logins = new HashMap<String, String>();
//        logins.put("cognito-idp.ap-southeast-2.amazonaws.com/ap-southeast-2_e4nCxiblG", getActivity().getIntent().getStringExtra("tokens"));
//        credentialsProvider.setLogins(logins);
//        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
//        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
//        mapper = new DynamoDBMapper(ddbClient);
        return mView;
    }

//    private void onRadioButtonClicked(RadioButton button) {
//
//        // Is the button now checked?
//        boolean checked = ((RadioButton) button).();
//
//        // Check which radio button was clicked
//        switch(button.getId()) {
//            case R.id.radioButton_sale:
//                if (checked)
//                    // Pirates are the best
//                button.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
//
//                    break;
//            case R.id.radioButton_auction:
//                if (checked)
//                    button.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
//
//                    break;
//        }
//    }




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
//          //  mListener.onSaveProperty();
//        }
//    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

//        MenuItem camera = menu.findItem(R.id.action_camera);
//        camera.setVisible(true);

      //  MenuItem searchItem = menu.findItem(R.id.action_search);
//        searchItem.setVisible(false);


    }


}
