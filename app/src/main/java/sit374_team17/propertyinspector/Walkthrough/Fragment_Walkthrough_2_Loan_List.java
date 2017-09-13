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
public class Fragment_Walkthrough_2_Loan_List extends Fragment {

    private View mView;
    Listener_Walkthrough mListener;
    private RecyclerView mRecyclerView;
    private ArrayList<Loan> mList;
    private Adapter_Loan mAdapter;

    public Fragment_Walkthrough_2_Loan_List() {
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

    public static Fragment_Walkthrough_2_Loan_List newInstance() {
        Fragment_Walkthrough_2_Loan_List fragment = new Fragment_Walkthrough_2_Loan_List();
        Bundle args = new Bundle();
        // args.putParcelable();
        //  fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_walkthrough_2_loan_list, container, false);

        mList = new ArrayList<>();

        Loan loan1 = new Loan("0", getString(R.string.loan_name_1), getString(R.string.loan_pitch_1), getString(R.string.loan_info_1));
        Loan loan2 = new Loan("1", getString(R.string.loan_name_2), getString(R.string.loan_pitch_2), getString(R.string.loan_info_2));
        Loan loan3 = new Loan("2", getString(R.string.loan_name_3), getString(R.string.loan_pitch_3), getString(R.string.loan_info_3));

        mList.add(loan1);
        mList.add(loan2);
        mList.add(loan3);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_loans);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new Adapter_Loan(mListener, getContext());
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);


        return mView;


    }
}
