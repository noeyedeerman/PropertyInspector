package sit374_team17.propertyinspector.Walkthrough;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sit374_team17.propertyinspector.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_Walkthrough_3_Lawyer extends Fragment {

    private View mView;
    Listener_Walkthrough mListener;

    public Fragment_Walkthrough_3_Lawyer() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener_Walkthrough) {
            mListener = (Listener_Walkthrough) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static Fragment_Walkthrough_3_Lawyer newInstance() {
        Fragment_Walkthrough_3_Lawyer fragment = new Fragment_Walkthrough_3_Lawyer();
        Bundle args = new Bundle();
       // args.putParcelable();
      //  fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_walkthrough_3_lawyer, container, false);

        Button button_no = (Button) mView.findViewById(R.id.button_no);
        Button button_yes = (Button) mView.findViewById(R.id.button_yes);

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   mListener.goTo_4_lawyer();
            }
        });

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goTo_3_lawyer_list();
            }
        });


        return  mView;


    }
}
