package sit374_team17.propertyinspector;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;



public class Fragment_Property extends Fragment {

    private static final String ARG_PROPERTY = "property";

    private Property mProperty;

    View mView;
    TextView mStreetNumber, mStreetName, mCity, mState, mPostCode, mBedrooms, mBathrooms, mCars, mPrice, mDescription;

    private PropertyListener mListener;

    public Fragment_Property() {}

    public interface PropertyListener {
        void onFragmentInteraction(Uri uri);
    }

    public static Fragment_Property newInstance(Property property ) {
        Fragment_Property fragment = new Fragment_Property();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROPERTY, property);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable(ARG_PROPERTY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_property, container, false);

        mStreetNumber = (TextView) mView.findViewById(R.id.textView_streetNumber);
        mStreetName = (TextView) mView.findViewById(R.id.textView_streetName);
        mCity = (TextView) mView.findViewById(R.id.textView_city);
        mState = (TextView) mView.findViewById(R.id.textView_state);
        mPostCode = (TextView) mView.findViewById(R.id.textView_postCode);
        mBedrooms = (TextView) mView.findViewById(R.id.textView_bedrooms);
        mBathrooms = (TextView) mView.findViewById(R.id.textView_bathrooms);
        mCars = (TextView) mView.findViewById(R.id.textView_cars);
       // mPrice = (TextView) mView.findViewById(R.id.textView_price);


        mStreetNumber.setText(mProperty.getStreetNumber());
        mStreetName.setText(mProperty.getStreetName());
        mCity.setText(mProperty.getCity());
        mState.setText(mProperty.getState());
        mPostCode.setText(mProperty.getPostCode());
        mBedrooms.setText(mProperty.getBedrooms());
        mBathrooms.setText(mProperty.getBathrooms());
        mCars.setText(mProperty.getCars());
      //  mPrice.setText(mProperty.getPrice());
        return mView;
    }



//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof PropertyListener) {
//            mListener = (PropertyListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement PropertyListenerListener");
//        }
//    }
//
//

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
