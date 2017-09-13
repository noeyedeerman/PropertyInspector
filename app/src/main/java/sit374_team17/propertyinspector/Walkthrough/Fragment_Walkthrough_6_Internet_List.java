package sit374_team17.propertyinspector.Walkthrough;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sit374_team17.propertyinspector.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_Walkthrough_6_Internet_List extends Fragment {

    Listener_Walkthrough mListener;

    public Fragment_Walkthrough_6_Internet_List() {
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

    public static Fragment_Walkthrough_6_Internet_List newInstance() {
        Fragment_Walkthrough_6_Internet_List fragment = new Fragment_Walkthrough_6_Internet_List();
        Bundle args = new Bundle();
        // args.putParcelable();
        //  fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_walkthrough_6_internet_list, container, false);

        ArrayList<Internet> mList = new ArrayList<>();

        Internet internet1 = new Internet("0", getString(R.string.internet_name_1), getString(R.string.internet_pitch_1), getString(R.string.internet_info_1));
        Internet internet2 = new Internet("1", getString(R.string.internet_name_2), getString(R.string.internet_pitch_2), getString(R.string.internet_info_2));
        Internet internet3 = new Internet("2", getString(R.string.internet_name_3), getString(R.string.internet_pitch_3), getString(R.string.internet_info_3));

        mList.add(internet1);
        mList.add(internet2);
        mList.add(internet3);

        RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_internet);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        Adapter_Internet mAdapter = new Adapter_Internet(mListener, getContext());
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);


        return mView;


    }
}
