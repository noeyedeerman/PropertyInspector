package sit374_team17.propertyinspector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.util.List;

public class Fragment_CreateProperty extends Fragment {

    private static final String ARG_PROPERTY = "property";

    private Property mProperty;

    View mView;
    Button mButton_save;
    DB_PropertyHandler mDB_properties;
    List<Property> mPropertyList;
    EditText mEditText_streetNumber, mEditText_streetName, mEditText_city, mEditText_state, mEditText_postCode, mEditText_price, mEditText_description;
    NumberPicker mNumberPicker_bedrooms, mNumberPicker_bathrooms, mNumberPicker_cars;

    int pickerMin = 0;
    int pickerMax = 100;

    private Listener mListener;

    public Fragment_CreateProperty() {
    }

   // public interface CreatePropertyListener {
    //    void onSaveProperty();
   // }

    public static Fragment_CreateProperty newInstance(Property property) {
        Fragment_CreateProperty fragment = new Fragment_CreateProperty();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROPERTY, property);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDB_properties = new DB_PropertyHandler(getContext());
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable(ARG_PROPERTY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_create_property, container, false);
        setHasOptionsMenu(true);

        mEditText_streetNumber = (EditText) mView.findViewById(R.id.editText_streetNumber);
        mEditText_streetName = (EditText) mView.findViewById(R.id.editText_streetName);
        mEditText_city = (EditText) mView.findViewById(R.id.editText_city);
        mEditText_state = (EditText) mView.findViewById(R.id.editText_state);
        mEditText_postCode = (EditText) mView.findViewById(R.id.editText_postCode);
        mNumberPicker_bedrooms = (NumberPicker) mView.findViewById(R.id.editText_bedrooms);
        mNumberPicker_bathrooms = (NumberPicker) mView.findViewById(R.id.editText_bathrooms);
        mNumberPicker_cars = (NumberPicker) mView.findViewById(R.id.editText_cars);
        mEditText_price = (EditText) mView.findViewById(R.id.editText_price);
        mEditText_description = (EditText) mView.findViewById(R.id.editText_description);

        mNumberPicker_bedrooms.setMinValue(pickerMin);
        mNumberPicker_bedrooms.setMaxValue(pickerMax);
        mNumberPicker_bedrooms.setWrapSelectorWheel(false);

        mNumberPicker_bathrooms.setMinValue(pickerMin);
        mNumberPicker_bathrooms.setMaxValue(pickerMax);
        mNumberPicker_bathrooms.setWrapSelectorWheel(false);

        mNumberPicker_cars.setMinValue(pickerMin);
        mNumberPicker_cars.setMaxValue(pickerMax);
        mNumberPicker_cars.setWrapSelectorWheel(false);

        mButton_save = (Button) mView.findViewById(R.id.button_save);


        mButton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    saveProperty();
                }
            }
        });
        return mView;
    }

    private void saveProperty() {
        String streetNumber = mEditText_streetNumber.getText().toString();
        String streetName = mEditText_streetName.getText().toString();
        String city = mEditText_city.getText().toString();
        String state = mEditText_state.getText().toString();
        String postCode = mEditText_postCode.getText().toString();
        String bedrooms = String.valueOf(mNumberPicker_bedrooms.getValue());
        String bathrooms = String.valueOf(mNumberPicker_bathrooms.getValue());
        String garages = String.valueOf(mNumberPicker_cars.getValue());
        String price = mEditText_price.getText().toString();

        String empty = "--";

        if (mProperty != null) {
            if (!streetNumber.isEmpty() && !streetName.isEmpty()) {
                if (mProperty.getId() < 0) {
                    mProperty.setStreetNumber(streetNumber);
                    mProperty.setStreetName(streetName);

                    if (city.isEmpty()) city = empty;
                    if (state.isEmpty()) state = empty;
                    if (postCode.isEmpty()) postCode = empty;
                    if (bedrooms.isEmpty()) bedrooms = empty;
                    if (bathrooms.isEmpty()) bathrooms = empty;
                    if (garages.isEmpty()) garages = empty;
                    if (price.isEmpty()) price = empty;

                    mProperty.setCity(city);
                    mProperty.setState(state);
                    mProperty.setPostCode(postCode);
                    mProperty.setBedrooms(bedrooms);
                    mProperty.setBathrooms(bathrooms);
                    mProperty.setCars(garages);
                    mProperty.setPrice(price);
                   // mProperty.setPhoto(ContextCompat.getDrawable(getContext(), R.drawable.house1));
                    mDB_properties.addProperty(mProperty);
                } else {
                  //  mProperty.set_address(address);
                  //  mDB_properties.updateProperty(mProperty);
                }
                mListener.onSaveProperty();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CreatePropertyListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

//        MenuItem camera = menu.findItem(R.id.action_camera);
//        camera.setVisible(true);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);


    }

}
