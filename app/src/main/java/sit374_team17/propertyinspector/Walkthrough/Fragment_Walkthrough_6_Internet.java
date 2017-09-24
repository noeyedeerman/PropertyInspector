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

public class Fragment_Walkthrough_6_Internet extends Fragment {

    private View mView;
    Listener_Walkthrough mListener;

    public Fragment_Walkthrough_6_Internet() {
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

    public static Fragment_Walkthrough_6_Internet newInstance() {
        Fragment_Walkthrough_6_Internet fragment = new Fragment_Walkthrough_6_Internet();
        Bundle args = new Bundle();
       // args.putParcelable();
      //  fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_walkthrough_6_internet, container, false);

        CardView cardView_no = (CardView) mView.findViewById(R.id.cardView_no);

        cardView_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goTo_7_moval();
            }
        });

        ArrayList<Internet> mList = new ArrayList<>();

        Internet internet1 = new Internet("0", getString(R.string.internet_name_1), getResources().getDrawable(R.drawable.ic_logo_bell), getString(R.string.internet_pitch_1), getString(R.string.internet_info_1));
        Internet internet2 = new Internet("1", getString(R.string.internet_name_2), getResources().getDrawable(R.drawable.ic_logo_rocket), getString(R.string.internet_pitch_2), getString(R.string.internet_info_2));
        Internet internet3 = new Internet("2", getString(R.string.internet_name_3), getResources().getDrawable(R.drawable.ic_logo_betternet), getString(R.string.internet_pitch_3), getString(R.string.internet_info_3));

        mList.add(internet1);
        mList.add(internet2);
        mList.add(internet3);

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

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mRecyclerView.setLayoutManager(layoutManager);

        Adapter_Internet mAdapter = new Adapter_Internet(mListener, getContext());
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);


        return  mView;


    }
}
