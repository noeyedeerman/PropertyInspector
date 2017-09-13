package sit374_team17.propertyinspector.Walkthrough;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import sit374_team17.propertyinspector.R;

import static sit374_team17.propertyinspector.R.id.view;

public class Activity_Walkthrough extends AppCompatActivity implements Listener_Walkthrough{

    private FragmentTransaction fragmentTransaction;
    private Loan mLoan;
    private Lawyer mLawyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Property Buying Walkthrough");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        goTo_1_start();
    }



    public void goTo_1_start() {
        Fragment_Walkthrough_1 fragment = Fragment_Walkthrough_1.newInstance();
        replaceFragment(fragment, "fragment_1_start");
    }

    public void goTo_2_loan() {
        Fragment_Walkthrough_2_Loan fragment = Fragment_Walkthrough_2_Loan.newInstance();
        replaceFragment(fragment, "fragment_2_loan");
    }


    public void goTo_2_loan_list() {
        Fragment_Walkthrough_2_Loan_List fragment = Fragment_Walkthrough_2_Loan_List.newInstance();
        replaceFragment(fragment, "fragment_2_loan_list");
    }

    public void goTo_2_loan_details(Loan loan) {
        Fragment_Walkthrough_2_Loan_Details fragment = Fragment_Walkthrough_2_Loan_Details.newInstance(loan);
        replaceFragment(fragment, "fragment_2_loan_details");
    }



    public void goTo_3_lawyer() {
        Fragment_Walkthrough_3_Lawyer fragment = Fragment_Walkthrough_3_Lawyer.newInstance();
        replaceFragment(fragment, "fragment_3_lawyer");
    }


    public void goTo_3_lawyer_list() {
        Fragment_Walkthrough_3_Lawyer_List fragment = Fragment_Walkthrough_3_Lawyer_List.newInstance();
        replaceFragment(fragment, "fragment_3_lawyer_list");
    }

    @Override
    public void goTo_3_lawyer_details(Lawyer lawyer) {
        Fragment_Walkthrough_3_Lawyer_Details fragment = Fragment_Walkthrough_3_Lawyer_Details.newInstance(lawyer);
        replaceFragment(fragment, "fragment_3_lawyer_details");
    }



    private void replaceFragment(Fragment fragment, String tag) {
        try {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (tag != "fragment_1_start"){
                fragmentTransaction.addToBackStack(null);
            }

            if (tag == "fragment_2_loan" || tag == "fragment_3_lawyer") {
                fragmentTransaction.setCustomAnimations(R.anim.enter_up, R.anim.exit_down, R.anim.exit_up, R.anim.enter_down);
            }

            if (tag == "fragment_2_loan_list" || tag == "fragment_2_loan_details" || tag == "fragment_3_lawyer_list" || tag == "fragment_3_lawyer_details") {
                fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right, R.anim.exit_left, R.anim.enter_right);
            }

            fragmentTransaction.replace(R.id.content_walkthrough, fragment, tag);
            fragmentTransaction.commit();

        } catch (Exception e) {
            Log.d(tag, e.toString());
        }

    }

    @Override
    public void onClick_loan(Loan loan) {
        mLoan = loan;
        goTo_2_loan_details(loan);
    }

    @Override
    public void onClick_lawyer(Lawyer lawyer) {
        mLawyer = lawyer;
        goTo_3_lawyer_details(lawyer);
    }




}
