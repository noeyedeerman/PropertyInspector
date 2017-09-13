package sit374_team17.propertyinspector.Walkthrough;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import sit374_team17.propertyinspector.R;

public class Activity_Walkthrough extends AppCompatActivity implements Listener_Walkthrough{

    private FragmentTransaction fragmentTransaction;
    private Loan mLoan;
    private Lawyer mLawyer;
    private Gas mGas;
    private Internet mInternet;
    private Moval mMoval;
    private Electrical mElectrical;

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

    @Override
    public void goTo_2_loan() {
        Fragment_Walkthrough_2_Loan fragment = Fragment_Walkthrough_2_Loan.newInstance();
        replaceFragment(fragment, "fragment_2_loan");
    }

    @Override
    public void goTo_2_loan_list() {
        Fragment_Walkthrough_2_Loan_List fragment = Fragment_Walkthrough_2_Loan_List.newInstance();
        replaceFragment(fragment, "fragment_2_loan_list");
    }

    @Override
    public void goTo_2_loan_details(Loan loan) {
        Fragment_Walkthrough_2_Loan_Details fragment = Fragment_Walkthrough_2_Loan_Details.newInstance(loan);
        replaceFragment(fragment, "fragment_2_loan_details");
    }

    @Override
    public void goTo_3_lawyer() {
        Fragment_Walkthrough_3_Lawyer fragment = Fragment_Walkthrough_3_Lawyer.newInstance();
        replaceFragment(fragment, "fragment_3_lawyer");
    }

    @Override
    public void goTo_3_lawyer_list() {
        Fragment_Walkthrough_3_Lawyer_List fragment = Fragment_Walkthrough_3_Lawyer_List.newInstance();
        replaceFragment(fragment, "fragment_3_lawyer_list");
    }

    @Override
    public void goTo_3_lawyer_details(Lawyer lawyer) {
        Fragment_Walkthrough_3_Lawyer_Details fragment = Fragment_Walkthrough_3_Lawyer_Details.newInstance(lawyer);
        replaceFragment(fragment, "fragment_3_lawyer_details");
    }

    @Override
    public void goTo_4_electrical() {
        Fragment_Walkthrough_4_Electrical fragment = Fragment_Walkthrough_4_Electrical.newInstance();
        replaceFragment(fragment, "fragment_4_electrical");
    }

    @Override
    public void goTo_4_electrical_list() {
        Fragment_Walkthrough_4_Electrical_List fragment = Fragment_Walkthrough_4_Electrical_List.newInstance();
        replaceFragment(fragment, "fragment_4_electrical_list");
    }

    @Override
    public void goTo_4_electrical_details(Electrical electrical) {
        Fragment_Walkthrough_4_Electrical_Details fragment = Fragment_Walkthrough_4_Electrical_Details.newInstance(electrical);
        replaceFragment(fragment, "fragment_4_electrical_details");
    }

    @Override
    public void goTo_5_gas() {
        Fragment_Walkthrough_5_Gas fragment = Fragment_Walkthrough_5_Gas.newInstance();
        replaceFragment(fragment, "fragment_5_gas");
    }

    @Override
    public void goTo_5_gas_list() {
        Fragment_Walkthrough_5_Gas_List fragment = Fragment_Walkthrough_5_Gas_List.newInstance();
        replaceFragment(fragment, "fragment_5_gas_list");
    }

    @Override
    public void goTo_5_gas_details(Gas gas) {
        Fragment_Walkthrough_5_Gas_Details fragment = Fragment_Walkthrough_5_Gas_Details.newInstance(gas);
        replaceFragment(fragment, "fragment_5_gas_details");
    }

    @Override
    public void goTo_6_internet() {
        Fragment_Walkthrough_6_Internet fragment = Fragment_Walkthrough_6_Internet.newInstance();
        replaceFragment(fragment, "fragment_6_internet");
    }

    @Override
    public void goTo_6_internet_list() {
        Fragment_Walkthrough_6_Internet_List fragment = Fragment_Walkthrough_6_Internet_List.newInstance();
        replaceFragment(fragment, "fragment_6_internet_list");
    }

    @Override
    public void goTo_6_internet_details(Internet internet) {
        Fragment_Walkthrough_6_Internet_Details fragment = Fragment_Walkthrough_6_Internet_Details.newInstance(internet);
        replaceFragment(fragment, "fragment_6_internet_details");
    }

    @Override
    public void goTo_7_moval() {
        Fragment_Walkthrough_7_Moval fragment = Fragment_Walkthrough_7_Moval.newInstance();
        replaceFragment(fragment, "fragment_7_moval");
    }

    @Override
    public void goTo_7_moval_details(Moval moval) {
        Fragment_Walkthrough_7_Moval_Details fragment = Fragment_Walkthrough_7_Moval_Details.newInstance(moval);
        replaceFragment(fragment, "fragment_7_moval_details");
    }

    private void replaceFragment(Fragment fragment, String tag) {
        try {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (tag != "fragment_1_start"){
                fragmentTransaction.addToBackStack(null);

                if (tag == "fragment_2_loan" || tag == "fragment_3_lawyer" || tag == "fragment_4_electrical" || tag == "fragment_5_gas" || tag == "fragment_6_internet" || tag == "fragment_7_moval") {
                    fragmentTransaction.setCustomAnimations(R.anim.enter_up, R.anim.exit_down, R.anim.exit_up, R.anim.enter_down);
                } else {
                    fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right, R.anim.exit_left, R.anim.enter_right);
                }
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

    @Override
    public void onClick_electrical(Electrical electrical) {
        mElectrical = electrical;
        goTo_4_electrical_details(electrical);
    }

    @Override
    public void onClick_gas(Gas gas) {
        mGas = gas;
        goTo_5_gas_details(gas);
    }

    @Override
    public void onClick_internet(Internet internet) {
        mInternet = internet;
        goTo_6_internet_details(internet);
    }

    @Override
    public void onClick_moval(Moval moval) {
        mMoval = moval;
        goTo_7_moval_details(moval);
    }






}
