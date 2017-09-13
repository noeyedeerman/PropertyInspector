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

public class Fragment_Walkthrough_7_Moval extends Fragment {

    Listener_Walkthrough mListener;

    public Fragment_Walkthrough_7_Moval() {
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

    public static Fragment_Walkthrough_7_Moval newInstance() {
        Fragment_Walkthrough_7_Moval fragment = new Fragment_Walkthrough_7_Moval();
        Bundle args = new Bundle();
       // args.putParcelable();
      //  fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_walkthrough_7_moval, container, false);

        CardView cardView_no = (CardView) mView.findViewById(R.id.cardView_no);

//        cardView_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.goTo_5_gas();
//            }
//        });

        ArrayList<Moval> mList = new ArrayList<>();

        Moval moval1 = new Moval("0", getString(R.string.moval_name_1), getResources().getDrawable(R.drawable.ic_logo_traversal), getString(R.string.moval_pitch_1), getString(R.string.moval_info_1));
        Moval moval2 = new Moval("1", getString(R.string.moval_name_2), getResources().getDrawable(R.drawable.ic_logo_roadrunner), getString(R.string.moval_pitch_2), getString(R.string.moval_info_2));
        Moval moval3 = new Moval("2", getString(R.string.moval_name_3), getResources().getDrawable(R.drawable.ic_logo_fantastic), getString(R.string.moval_pitch_3), getString(R.string.moval_info_3));

        mList.add(moval1);
        mList.add(moval2);
        mList.add(moval3);

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

        Adapter_Moval mAdapter = new Adapter_Moval(mListener, getContext());
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);



        return mView;


    }
}
