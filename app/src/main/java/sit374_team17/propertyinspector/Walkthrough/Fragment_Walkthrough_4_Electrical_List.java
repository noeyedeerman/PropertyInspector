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
public class Fragment_Walkthrough_4_Electrical_List extends Fragment {

    private View mView;
    Listener_Walkthrough mListener;
    private RecyclerView mRecyclerView;
    private ArrayList<Electrical> mList;
    private Adapter_Electrical mAdapter;

    public Fragment_Walkthrough_4_Electrical_List() {
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

    public static Fragment_Walkthrough_4_Electrical_List newInstance() {
        Fragment_Walkthrough_4_Electrical_List fragment = new Fragment_Walkthrough_4_Electrical_List();
        Bundle args = new Bundle();
        // args.putParcelable();
        //  fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_walkthrough_4_electrical_list, container, false);

        mList = new ArrayList<>();

        Electrical electrical1 = new Electrical("0", getString(R.string.electrical_name_1), getString(R.string.electrical_pitch_1), getString(R.string.electrical_info_1));
        Electrical electrical2 = new Electrical("1", getString(R.string.electrical_name_2), getString(R.string.electrical_pitch_2), getString(R.string.electrical_info_2));
        Electrical electrical3 = new Electrical("2", getString(R.string.electrical_name_3), getString(R.string.electrical_pitch_3), getString(R.string.electrical_info_3));

        mList.add(electrical1);
        mList.add(electrical2);
        mList.add(electrical3);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_loans);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new Adapter_Electrical(mListener, getContext());
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);


        return mView;


    }
}
