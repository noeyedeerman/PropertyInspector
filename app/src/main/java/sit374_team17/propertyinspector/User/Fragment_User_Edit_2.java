package sit374_team17.propertyinspector.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.io.File;
import java.util.List;

import sit374_team17.propertyinspector.Property.Photo;
import sit374_team17.propertyinspector.Property.Property;
import sit374_team17.propertyinspector.R;

public class Fragment_User_Edit_2 extends Fragment {

    private static final String ARG_USER = "user";

    private User mUser;
    private Photo mPhotos=new Photo();
    TextView file_path;
    View mView;
    Button mButton_save,mButton_upload;
   // DB_PropertyHandler mDB_properties;
    List<Property> mPropertyList;
    EditText mEditText_firstName, mEditText_lastName, mEditText_phone;
    NumberPicker mNumberPicker_bedrooms, mNumberPicker_bathrooms, mNumberPicker_cars;
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

    public Fragment_User_Edit_2() {
    }

    public boolean getDetails_2() {
        String firstName = mEditText_firstName.getText().toString();

        String lastName = mEditText_lastName.getText().toString();
        String phone = mEditText_phone.getText().toString();

        if (firstName.isEmpty() || firstName == "") {
            Toast.makeText(getActivity(),"First Name cannot be empty",Toast.LENGTH_LONG).show();
            return false;
        }
        if (lastName.isEmpty() || lastName == "") {
            Toast.makeText(getActivity(),"Last Name cannot be empty",Toast.LENGTH_LONG).show();
            return false;
        }
        if (phone.isEmpty() || phone == "") {
            Toast.makeText(getActivity(),"Phone number cannot be empty",Toast.LENGTH_LONG).show();
            return false;
        }
        if (phone.length()<11|| !phone.contains("+61")) {
            Toast.makeText(getActivity(),"Invalid phone number or add country code",Toast.LENGTH_LONG).show();
            return false;
        }

        mUser = new User();
        mUser.setFirstName(firstName);
        mUser.setLastName(lastName);
        mUser.setPhone(phone);

        mListener.setDetails_2(mUser);

        return true;
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




    public static Fragment_User_Edit_2 newInstance(User user) {
        Fragment_User_Edit_2 fragment = new Fragment_User_Edit_2();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mDB_properties = new DB_PropertyHandler(getContext());
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARG_USER);
        }
    }
    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user_edit_2, container, false);

        mEditText_firstName = (EditText) mView.findViewById(R.id.editText_firstName);
        mEditText_lastName = (EditText) mView.findViewById(R.id.editText_lastName);
        mEditText_phone = (EditText) mView.findViewById(R.id.editText_phone);

        try{
            mEditText_firstName.setText(mUser.getFirstName());
            mEditText_lastName.setText(mUser.getLastName());
            mEditText_phone.setText(mUser.getPhone());
        }catch(NullPointerException e){Log.e("","");};

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

    Integer streetNumber;
//    private void saveProperty() {
//
//      //  if (!mEditText_streetNumber.getText().toString().equals(""))
//         //   streetNumber = Integer.parseInt(mEditText_streetNumber.getText().toString());
//       // String streetName = mEditText_streetName.getText().toString();
//        String city = mEditText_city.getText().toString();
//        List<String> state = new ArrayList<>();
//        if (!mEditText_state.getText().toString().equals(""))
//            state.add(mEditText_state.getText().toString());
//        Integer postCode = Integer.parseInt(mEditText_post.getText().toString());
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
