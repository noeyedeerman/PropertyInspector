package sit374_team17.propertyinspector.Walkthrough;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sit374_team17.propertyinspector.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_Walkthrough_3_Lawyer extends Fragment {

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

        View mView = inflater.inflate(R.layout.fragment_walkthrough_3_lawyer, container, false);

        CardView cardView_no = (CardView) mView.findViewById(R.id.cardView_no);

        cardView_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goTo_4_electrical();
            }
        });

        ArrayList<Lawyer> mList = new ArrayList<>();

        Lawyer lawyer1 = new Lawyer("0", getString(R.string.lawyer_name_1), getString(R.string.lawyer_pitch_1), getString(R.string.lawyer_info_1));
        Lawyer lawyer2 = new Lawyer("1", getString(R.string.lawyer_name_2), getString(R.string.lawyer_pitch_2), getString(R.string.lawyer_info_2));

        mList.add(lawyer1);
        mList.add(lawyer2);

        RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        int columnCount = 2;

        // Changes column count when device rotated
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnCount = 2;
        }

        layoutManager = new GridLayoutManager(getContext(), columnCount) {

            @Override
            public int getOrientation() {
                return super.getOrientation();
            }
        };

        mRecyclerView.setLayoutManager(layoutManager);

        Adapter_Lawyer mAdapter = new Adapter_Lawyer(mListener, getContext());
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);

        return mView;


    }
}
