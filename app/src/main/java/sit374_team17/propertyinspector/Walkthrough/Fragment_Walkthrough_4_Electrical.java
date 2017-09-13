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
public class Fragment_Walkthrough_4_Electrical extends Fragment {

    private View mView;
    Listener_Walkthrough mListener;

    public Fragment_Walkthrough_4_Electrical() {
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

    public static Fragment_Walkthrough_4_Electrical newInstance() {
        Fragment_Walkthrough_4_Electrical fragment = new Fragment_Walkthrough_4_Electrical();
        Bundle args = new Bundle();
       // args.putParcelable();
      //  fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_walkthrough_4_electrical, container, false);

        CardView cardView_no = (CardView) mView.findViewById(R.id.cardView_no);

        cardView_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goTo_5_gas();
            }
        });




        ArrayList<Electrical> mList = new ArrayList<>();

        Electrical electrical1 = new Electrical("0", getString(R.string.electrical_name_1), getString(R.string.electrical_pitch_1), getString(R.string.electrical_info_1));
        Electrical electrical2 = new Electrical("1", getString(R.string.electrical_name_2), getString(R.string.electrical_pitch_2), getString(R.string.electrical_info_2));
        Electrical electrical3 = new Electrical("2", getString(R.string.electrical_name_3), getString(R.string.electrical_pitch_3), getString(R.string.electrical_info_3));

        mList.add(electrical1);
        mList.add(electrical2);
        mList.add(electrical3);

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

        Adapter_Electrical mAdapter = new Adapter_Electrical(mListener, getContext());
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);

        return  mView;


    }
}
