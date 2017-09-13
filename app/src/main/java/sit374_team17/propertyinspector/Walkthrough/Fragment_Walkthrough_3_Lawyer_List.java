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
public class Fragment_Walkthrough_3_Lawyer_List extends Fragment {

    private View mView;
    Listener_Walkthrough mListener;
    private ArrayList<Lawyer> mList;
    private RecyclerView mRecyclerView;
    private Adapter_Lawyer mAdapter;

    public Fragment_Walkthrough_3_Lawyer_List() {
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

    public static Fragment_Walkthrough_3_Lawyer_List newInstance() {
        Fragment_Walkthrough_3_Lawyer_List fragment = new Fragment_Walkthrough_3_Lawyer_List();
        Bundle args = new Bundle();
       // args.putParcelable();
      //  fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_walkthrough_3_lawyer_list, container, false);

        mList = new ArrayList<>();


        Lawyer lawyer1 = new Lawyer("0", getString(R.string.lawyer_name_1), getString(R.string.lawyer_pitch_1), getString(R.string.lawyer_info_1));
        Lawyer lawyer2 = new Lawyer("1", getString(R.string.lawyer_name_2), getString(R.string.lawyer_pitch_2), getString(R.string.lawyer_info_2));

        mList.add(lawyer1);
        mList.add(lawyer2);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_lawyer);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new Adapter_Lawyer(mListener, getContext());
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);



        return  mView;


    }
}
