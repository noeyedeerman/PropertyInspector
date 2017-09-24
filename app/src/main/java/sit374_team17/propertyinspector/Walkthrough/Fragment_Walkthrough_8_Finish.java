package sit374_team17.propertyinspector.Walkthrough;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import sit374_team17.propertyinspector.R;

public class Fragment_Walkthrough_8_Finish extends Fragment {

    private static int MAX_LENGTH = 8;
    Listener_Walkthrough mListener;
    private Moval mMoval;

    public Fragment_Walkthrough_8_Finish() {
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

    public static Fragment_Walkthrough_8_Finish newInstance() {
        Fragment_Walkthrough_8_Finish fragment = new Fragment_Walkthrough_8_Finish();
        //Bundle args = new Bundle();
        // args.putParcelable("moval", moval);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //  mMoval = getArguments().getParcelable("moval");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_walkthrough_8_finish, container, false);

        TextView textView_pi = (TextView) mView.findViewById(R.id.textView_pi);
        String pi = "PI-" + random() + "-" + random();

        textView_pi.setText(pi);

        Button button_finish = (Button) mView.findViewById(R.id.button_finish);
        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return mView;
    }


    public static String random() {
        Random rnd = new Random();
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
    String string = "";

        for (int n = 0; n < MAX_LENGTH; n++) {
            string += chars.charAt(rnd.nextInt(chars.length()));
        }

        return string;
    }
}