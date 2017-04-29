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
    TextView mInfo;

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

        mInfo = (TextView) mView.findViewById(R.id.textView_propertyInfo);

        mInfo.setText(mProperty.get_address());


        return mView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
