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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.R;

public class Fragment_Property_Edit_1 extends Fragment {

    private static final String ARG_PROPERTY = "property";

    private Property mProperty;
    private Photo mPhotos=new Photo();
    TextView file_path;
    View mView;
    Button mButton_save,mButton_upload;
    //DB_PropertyHandler mDB_properties;
    List<Property> mPropertyList;
    EditText mEditText_address, mEditText_streetName, mEditText_city, mEditText_state, mEditText_postCode, mEditText_price, mEditText_description;
    EditText mEditText_bedrooms, mEditText_bathrooms, mEditText_cars;
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
    //private Listener mListener;
//private CreatePropertyListener mListener;

    public Fragment_Property_Edit_1() {
    }

    public void getDetails_1() {

        String description=mEditText_description.getText().toString();

        String priceRaw = mEditText_price.getText().toString();

        List<Integer> price = new ArrayList<>();
        if (!priceRaw.isEmpty() && priceRaw !="") {
            String priceTemp = priceRaw.replace(",","");
            price.add(Integer.parseInt(priceTemp));
        }
        List<String> bedrooms =  new ArrayList<>();
        bedrooms.add(String.valueOf(mEditText_bedrooms.getText()));
        List<String>  bathrooms = new ArrayList<>();
        bathrooms.add(String.valueOf(mEditText_bathrooms.getText()));
        List<String> garages =  new ArrayList<>();
        garages.add(String.valueOf(mEditText_cars.getText()));

        if (description.isEmpty())description="None";
        if (price.isEmpty()) price.add(0);
        if (bedrooms.isEmpty()) bedrooms.add("None");
        if (bathrooms.isEmpty()) bathrooms.add("None");
        if (garages.isEmpty()) garages .add("None");

        mProperty = new Property();
        mProperty.setDescription(description);
        mProperty.setPrice(price);
        mProperty.setBedrooms(bedrooms);
        mProperty.setBathrooms(bathrooms);
        mProperty.setCars(garages);

      //  mProperty.setUnitNumber(0);


  mListener.setDetails_1(mProperty);


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


    public static Fragment_Property_Edit_1 newInstance(Property property) {
        Fragment_Property_Edit_1 fragment = new Fragment_Property_Edit_1();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROPERTY, property);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mDB_properties = new DB_PropertyHandler(getContext());
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable(ARG_PROPERTY);
        }
    }
    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_property_edit_1, container, false);
        setHasOptionsMenu(true);

        final RadioButton radioButton_sale = (RadioButton) mView.findViewById(R.id.radioButton_sale);

        radioButton_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  onRadioButtonClicked(radioButton_sale);
            }
        });
        final RadioButton radioButton_auction = (RadioButton) mView.findViewById(R.id.radioButton_auction);

        radioButton_auction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  onRadioButtonClicked(radioButton_auction);
            }
        });

        RadioButton radioButton_lease = (RadioButton) mView.findViewById(R.id.radioButton_lease);

        RadioGroup radioGroup = (RadioGroup) mView.findViewById(R.id.radioGroup_category);
radioButton_sale.setChecked(true);

//        Button button_continue = (Button) mView.findViewById(R.id.button_continue);
//button_continue.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        mListener.onContinue1();
//    }
//});

      //  mEditText_streetNumber = (EditText) mView.findViewById(R.id.editText_streetNumber);
      //  mEditText_address = (EditText) mView.findViewById(R.id.editText_address);
     //   mEditText_streetName = (EditText) mView.findViewById(R.id.editText_streetName);
       // mEditText_city = (EditText) mView.findViewById(R.id.editText_city);
      //  mEditText_state = (EditText) mView.findViewById(R.id.editText_state);
      //  mEditText_postCode = (EditText) mView.findViewById(R.id.editText_postCode);
      //  mNumberPicker_bedrooms = (NumberPicker) mView.findViewById(R.id.editText_bedrooms);
      //  mNumberPicker_bathrooms = (NumberPicker) mView.findViewById(R.id.editText_bathrooms);
      //  mNumberPicker_cars = (NumberPicker) mView.findViewById(R.id.editText_cars);

        mEditText_description = (EditText) mView.findViewById(R.id.editText_description);
        mEditText_price = (EditText) mView.findViewById(R.id.editText_price);
        mEditText_bedrooms = (EditText) mView.findViewById(R.id.editText_bedrooms);
        mEditText_bathrooms = (EditText) mView.findViewById(R.id.editText_bathrooms);
        mEditText_cars = (EditText) mView.findViewById(R.id.editText_cars);
        //  mEditText_description = (EditText) mView.findViewById(R.id.editText_description);
      //  file_path = (TextView) mView.findViewById(R.id.file_path);

//
//        Spinner spinner_currency = (Spinner) mView.findViewById(R.id.spinner_currency);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//              R.array.array_currency, R.layout.spinner_item);
//       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item,list);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(R.layout.spinner_item);
//// Apply the adapter to the spinner
//        spinner_currency.setAdapter(adapter);
//spinner_currency.setSelection(0);

     mEditText_price.addTextChangedListener(new NumberTextWatcherForThousand(mEditText_price));

//        mNumberPicker_bedrooms.setMinValue(pickerMin);
//        mNumberPicker_bedrooms.setMaxValue(pickerMax);
//        mNumberPicker_bedrooms.setWrapSelectorWheel(false);
//
//        mNumberPicker_bathrooms.setMinValue(pickerMin);
//        mNumberPicker_bathrooms.setMaxValue(pickerMax);
//        mNumberPicker_bathrooms.setWrapSelectorWheel(false);
//
//        mNumberPicker_cars.setMinValue(pickerMin);
//        mNumberPicker_cars.setMaxValue(pickerMax);
//        mNumberPicker_cars.setWrapSelectorWheel(false);

//        mButton_save = (Button) mView.findViewById(R.id.button_save);
//       // mButton_upload = (Button) mView.findViewById(R.id.button_upload);
//
//
//
//
//        mButton_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (MY_FILE!=null) {
//                    AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
//                    TransferUtility transferUtility = new TransferUtility(s3, getActivity());
//                    final TransferObserver observer = transferUtility.upload(
//                            MY_BUCKET,     /* The bucket to upload to */
//                            OBJECT_KEY.concat(MY_FILE.getName().substring(MY_FILE.getName().length()-4)),    /* The key for the uploaded object */
//                            MY_FILE,        /* The file where the data to upload exists */
//                            CannedAccessControlList.PublicRead
//                    );
//                    progress=new ProgressDialog(getActivity());
//                    progress.setMessage("Uploading Images");
//                    progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                    progress.setIndeterminate(false);
//                    progress.setProgress(0);
//                    progress.show();
//
//                    observer.setTransferListener(new TransferListener() {
//
//                        @Override
//                        public void onStateChanged(int id, TransferState state) {
//                            if (TransferState.COMPLETED.equals(state)) {
//                                progress.setProgress(100);
//                                if (progress.isShowing())progress.dismiss();
//                                Toast.makeText(getActivity(),"Image uploaded successfully!",Toast.LENGTH_SHORT).show();
//                                mPhotos.setPicDetails(OBJECT_KEY.concat(MY_FILE.getName().substring(MY_FILE.getName().length() - 4)));
//                                saveProperty();
//                            }
//                        }
//
//                        @Override
//                        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//                            int percentage = (int) (bytesCurrent / bytesTotal * 100);
//                            progress.setProgress(percentage);
//                            //Display percentage transferred to user
//                        }
//
//                        @Override
//                        public void onError(int id, Exception ex) {
//                            // do something
//                            if (progress.isShowing())progress.dismiss();
//                            Toast.makeText(getActivity(),"Image fail to upload",Toast.LENGTH_SHORT).show();
//                        }
//
//                    });
//
//                }else {
//                    saveProperty();
//                }
//            }
//        });
//        mButton_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);//
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
//            }
//        });
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
