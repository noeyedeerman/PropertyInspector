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
public class Fragment_Walkthrough_2_Loan extends Fragment {

    private View mView;
    Listener_Walkthrough mListener;
    private RecyclerView mRecyclerView;
    private ArrayList<Loan> mList;
    private Adapter_Loan mAdapter;


    public Fragment_Walkthrough_2_Loan() {
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

    public static Fragment_Walkthrough_2_Loan newInstance() {
        Fragment_Walkthrough_2_Loan fragment = new Fragment_Walkthrough_2_Loan();
        Bundle args = new Bundle();
       // args.putParcelable();
      //  fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_walkthrough_2_loan, container, false);

        CardView cardView_no = (CardView) mView.findViewById(R.id.cardView_no);

        cardView_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goTo_3_lawyer();
            }
        });

        mList = new ArrayList<>();

        Loan loan1 = new Loan("0", getString(R.string.loan_name_1), getResources().getDrawable(R.drawable.ic_logo_loremipsum), getString(R.string.loan_pitch_1), getString(R.string.loan_info_1));
        Loan loan2 = new Loan("1", getString(R.string.loan_name_2), getResources().getDrawable(R.drawable.ic_logo_tower), getString(R.string.loan_pitch_2), getString(R.string.loan_info_2));
        Loan loan3 = new Loan("2", getString(R.string.loan_name_3), getResources().getDrawable(R.drawable.ic_logo_falcon), getString(R.string.loan_pitch_3), getString(R.string.loan_info_3));

        mList.add(loan1);
        mList.add(loan2);
        mList.add(loan3);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
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

        mAdapter = new Adapter_Loan(mListener, getContext());
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);


        return  mView;


    }
}
