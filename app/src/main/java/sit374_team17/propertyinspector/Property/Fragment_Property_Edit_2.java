package sit374_team17.propertyinspector.Property;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
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
import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.R;

public class Fragment_Property_Edit_2 extends Fragment {

    private static final String ARG_PROPERTY = "property";

    private Property mProperty;
    private Photo mPhotos=new Photo();
    TextView file_path;
    View mView;
    Button mButton_save,mButton_upload;
   // DB_PropertyHandler mDB_properties;
    List<Property> mPropertyList;
    EditText mEditText_address, mEditText_streetName, mEditText_city, mEditText_state, mEditText_post, mEditText_price, mEditText_description;
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

    Listener_Property_Edit mListener;

    public Fragment_Property_Edit_2() {
    }

    public void getDetails_2() {
        mProperty = new Property();

      //  if (!mEditText_streetNumber.getText().toString().equals(""))
         //   streetNumber = Integer.parseInt(mEditText_streetNumber.getText().toString());
        //String streetName = mEditText_streetName.getText().toString();
        String address = mEditText_address.getText().toString();
        String city = mEditText_city.getText().toString();
        List<String> state = new ArrayList<>();
        if (!mEditText_state.getText().toString().equals(""))
            state.add(mEditText_state.getText().toString());

        String postCodeString = mEditText_post.getText().toString();
        Integer postCode = 0;
        if (!postCodeString.isEmpty() && postCodeString !="") {
            postCode = Integer.parseInt(postCodeString);
        }


        String empty = "--";
        //if (streetNumber>0 && !streetName.isEmpty()) {
      //  String address = editText_address;
       // if (
           // mProperty.setStreetNumber(streetNumber);
           // mProperty.setStreetName(streetName);

        if (address.isEmpty() || address == "") address = "None";
            if (city.isEmpty() || city == "") city = "None";
            if (state.isEmpty()) state.add("None");
            if (postCode <= 0) postCode = 0;

            mProperty.setAddress(address);
            mProperty.setCity(city);
            mProperty.setState(state);
            mProperty.setPostCode(postCode);
            mProperty.setUnitNumber(0);



        mListener.setDetails_2(mProperty);
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener_Property_Edit) {
            mListener = (Listener_Property_Edit) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener_Property_Edit");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public static Fragment_Property_Edit_2 newInstance(Property property) {
        Fragment_Property_Edit_2 fragment = new Fragment_Property_Edit_2();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROPERTY, property);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mDB_properties = new DB_PropertyHandler(getContext());
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable(ARG_PROPERTY);
        }
    }
    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_property_edit_2, container, false);

          //mEditText_streetNumber = (EditText) mView.findViewById(R.id.editText_streetNumber);
        mEditText_address = (EditText) mView.findViewById(R.id.editText_address);
          // mEditText_streetName = (EditText) mView.findViewById(R.id.editText_streetName);
         mEditText_city = (EditText) mView.findViewById(R.id.editText_city);
          mEditText_state = (EditText) mView.findViewById(R.id.editText_state);
          mEditText_post = (EditText) mView.findViewById(R.id.editText_post);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {     if (requestCode == SELECT_IMAGE) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    if (Build.VERSION.SDK_INT < 20)
                        MY_FILE = new File(getPath(selectedImageUri));
                    else
                        MY_FILE = new File(PathFromURI(selectedImageUri));
                    file_path.setText(MY_FILE.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } else if (resultCode == Activity.RESULT_CANCELED)
        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @SuppressLint("NewApi")
    public String PathFromURI( Uri contentURI){
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String path = cursor.getString(idx);
            cursor.close();
            return path;
        }
    }


}
