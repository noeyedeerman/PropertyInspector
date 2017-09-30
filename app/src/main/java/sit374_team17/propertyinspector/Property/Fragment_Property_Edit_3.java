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
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

import sit374_team17.propertyinspector.Note.Note;
import sit374_team17.propertyinspector.R;

public class Fragment_Property_Edit_3 extends Fragment {

    private static final String ARG_PROPERTY = "property";

    private Property mProperty;
    private Photo mPhotos=new Photo();
    TextView file_path;
    View mView;
    Button mButton_save,mButton_upload;
    //DB_PropertyHandler mDB_properties;
    List<Property> mPropertyList;
    TextView mTextView_address, mTextView_streetName, mTextView_city, mTextView_state, mTextView_postCode, mTextView_price, mTextView_description,
    mTextView_bedrooms, mTextView_bathrooms, mTextView_cars;

    protected CognitoCachingCredentialsProvider credentialsProvider ;
    protected DynamoDBMapper mapper ;
    private String IDENTITY_POOL_ID="ap-southeast-2:da48cacc-60b6-41ee-8dc6-4ae3c3abf13a";
    private String MY_BUCKET="propertyinspector-userfiles-mobilehub-4404653";
    private String OBJECT_KEY="uploads/propertyinspector_image"+ SystemClock.currentThreadTimeMillis();
    private File MY_FILE;
    int pickerMin = 0;
    int pickerMax = 100;
    int SELECT_IMAGE = 103;
    Note mNote;

    Listener_Property_Edit mListener;

    public Fragment_Property_Edit_3() {
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


    // public interface CreatePropertyListener {
    //    void onSaveProperty();
    // }

    public static Fragment_Property_Edit_3 newInstance(Property property) {
        Fragment_Property_Edit_3 fragment = new Fragment_Property_Edit_3();
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

        mView = inflater.inflate(R.layout.fragment_property_edit_3, container, false);
        setHasOptionsMenu(true);

        ImageButton imageButton_textNote = mView.findViewById(R.id.imageButton_textNote);
        ImageButton imageButton_ohotoNote = mView.findViewById(R.id.imageButton_photoNote);

        ConstraintLayout layout_details = mView.findViewById(R.id.layout_details);
        ConstraintLayout layout_address = mView.findViewById(R.id.layout_address);

        imageButton_textNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!Activity_Property_Edit.propertyId.equals(""))
                   mListener.goTo_NoteEditActivity(new Note(), "don't");
               else Toast.makeText(getActivity(),"Save the property first",Toast.LENGTH_SHORT).show();
            }
        });

        imageButton_ohotoNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Activity_Property_Edit.propertyId.equals(""))
                    mListener.goTo_NoteEditActivity(new Note(), "do");
                else Toast.makeText(getActivity(),"Save the property first",Toast.LENGTH_SHORT).show();
            }
        });



        layout_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBackTo_Details();
            }
        });

        layout_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBackTo_Address();
            }
        });
//        final RadioButton radioButton_sale = (RadioButton) mView.findViewById(R.id.radioButton_sale);
//
//        radioButton_sale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  onRadioButtonClicked(radioButton_sale);
//            }
//        });
//        final RadioButton radioButton_auction = (RadioButton) mView.findViewById(R.id.radioButton_auction);
//
//        radioButton_auction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  onRadioButtonClicked(radioButton_auction);
//            }
//        });
//        RadioButton radioButton_lease = (RadioButton) mView.findViewById(R.id.radioButton_lease);
//
//        RadioGroup radioGroup = (RadioGroup) mView.findViewById(R.id.radioGroup_category);


        //mEditText_streetNumber = (EditText) mView.findViewById(R.id.editText_streetNumber);
        mTextView_description = (TextView) mView.findViewById(R.id.textView_description);
        mTextView_price = (TextView) mView.findViewById(R.id.textView_price);
        mTextView_bedrooms = (TextView) mView.findViewById(R.id.textView_bedrooms);
        mTextView_bathrooms = (TextView) mView.findViewById(R.id.textView_bathrooms);
        mTextView_cars = (TextView) mView.findViewById(R.id.textView_cars);
        mTextView_address = (TextView) mView.findViewById(R.id.textView_address);
        mTextView_city = (TextView) mView.findViewById(R.id.textView_city);
        mTextView_state = (TextView) mView.findViewById(R.id.textView_state);
        mTextView_postCode = (TextView) mView.findViewById(R.id.textView_postCode);

        if (mProperty.getDescription() != null && !Objects.equals(mProperty.getDescription(), "None"))
            mTextView_description.setText(mProperty.getDescription());

        if (mProperty.getPrice() != null && mProperty.getPrice().get(0) != 0) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            numberFormat.setMaximumFractionDigits(0);
            ((DecimalFormat) numberFormat).setDecimalFormatSymbols(decimalFormatSymbols);
            mTextView_price.setText(numberFormat.format(mProperty.getPrice().get(0)));
        }

        if (mProperty.getBedrooms() != null && mProperty.getBedrooms().size() > 0 && !Objects.equals(mProperty.getBedrooms().get(0), ""))
            mTextView_bedrooms.setText(String.valueOf(mProperty.getBedrooms().get(0)));

        if (mProperty.getBathrooms() != null && mProperty.getBathrooms().size() > 0 && !Objects.equals(mProperty.getBathrooms().get(0), ""))
            mTextView_bathrooms.setText(String.valueOf(mProperty.getBathrooms().get(0)));

        if (mProperty.getCars() != null && mProperty.getCars().size() > 0 && !Objects.equals(mProperty.getCars().get(0), ""))
            mTextView_cars.setText(String.valueOf(mProperty.getCars().get(0)));


        if (mProperty.getAddress() != null && !Objects.equals(mProperty.getAddress(), "None"))
            mTextView_address.setText(mProperty.getAddress() + ",");

        if (mProperty.getCity() != null && !Objects.equals(mProperty.getCity(), "None"))
            mTextView_city.setText(mProperty.getCity() + ",");

        if (mProperty.getState().size() > 0 && !Objects.equals(mProperty.getState().get(0), "None"))
            mTextView_state.setText(mProperty.getState().get(0) + ",");

        if (mProperty.getPostCode() != null && mProperty.getPostCode() > 0)
            mTextView_postCode.setText(String.valueOf(mProperty.getPostCode()));


      //  mEditText_description = (EditText) mView.findViewById(R.id.editText_description);
      //  file_path = (TextView) mView.findViewById(R.id.file_path);

//
//        Spinner spinner = (Spinner) mView.findViewById(R.id.spinner_bedrooms);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.number_array, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

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

//
//    public void goTo_NoteEditActivity(String openCamera) {
//        mNotes=new Note();
//        Intent intent = new Intent(getContext(), Activity_Note_Edit.class);
//        if (openCamera.equals("do"))
//            mNotes.setCommentType("photo");
//        else
//            mNotes.setCommentType("text");
//        intent.putExtra("note",mNotes);
//        intent.putExtra("property", mProperty);
//        intent.putExtra("methodName", openCamera);
//        startActivity(intent);
//
//    }


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
//            mListener.onSaveProperty();
//        }
//    }
//


//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof Listener) {
//            mListener = (Listener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement CreatePropertyListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
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
