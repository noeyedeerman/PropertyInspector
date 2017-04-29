package sit374_team17.propertyinspector;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static sit374_team17.propertyinspector.Adapter_Properties.*;
import static sit374_team17.propertyinspector.Fragment_CreateProperty.newInstance;


public class Fragment_Home extends Fragment implements PropertyItemListener, SearchView.OnQueryTextListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View mView;

    private RecyclerView mRecyclerView;
    private Adapter_Properties mPropertyAdapter;
    FloatingActionButton mFab;
    DB_PropertyHandler mDB_properties;
    List<Property> mPropertiesList;

    private HomeListener mListener;

    public Fragment_Home() {
    }

    public void searchResults(String query) {

//        query = query.toLowerCase();
//        List<Property> filteredList = new ArrayList<>();
//        for (Property property : mPropertiesList) {
//            String address = property.get_address().toLowerCase();
//            if (address.contains(query)) {
//                filteredList.add(property);
//            }
//        }
//        mPropertyAdapter.setFilter(filteredList);

    }


    public interface HomeListener {
        void onHomeInteraction();
    }

    @Override
    public void onItemClicked(Property property) {
        goTo_PropertyFragment(property);
        mFab.hide();
    }

    public void goTo_PropertyFragment(Property property) {
        Fragment_Property fragment = Fragment_Property.newInstance(property);
        replaceFragment(fragment, "Fragment_Property");
    }

    private void replaceFragment(Fragment fragment, String tag) {
        try {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            if (tag == "Fragment_CreateProperty") {
                fragmentTransaction.setCustomAnimations(R.anim.enter_up, R.anim.exit_down, R.anim.exit_up, R.anim.enter_down);
                fragmentTransaction.addToBackStack(null);
            } else if (tag == "Fragment_Property") {
                fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right, R.anim.exit_left, R.anim.enter_right);
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.replace(R.id.content_main, fragment, tag);
            fragmentTransaction.commit();

        } catch (Exception e) {
            Log.d(tag, e.toString());
        }
    }

    public static Fragment_Home newInstance(String param1, String param2) {
        Fragment_Home fragment = new Fragment_Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            mListener = (HomeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDB_properties = new DB_PropertyHandler(getContext());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        initViews();


        mPropertiesList = mDB_properties.getAllProperties();


        if (mPropertiesList.size() >= 0) {
            mPropertyAdapter = new Adapter_Properties(this);
            mPropertyAdapter.setPropertyList(mPropertiesList);
            mRecyclerView.setAdapter(mPropertyAdapter);

        }


        mFab = ((MainActivity) getActivity()).getFab();
        return mView;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onHomeInteraction();
        }
    }

    private ArrayList<Property> populateList() {
        ArrayList<Property> propertyList = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            Property property = new Property(i, "Address " + i, "", "", "", "", "", "");
            propertyList.add(property);
        }

        //   Property propertyTest = new Property(20, "Test", "");
        // propertyList.add(propertyTest);
        return propertyList;
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        query = query.toLowerCase();

        final List<Property> filteredModelList = new ArrayList<>();
        for (Property property : mPropertiesList) {
            final String text = property.get_address().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(property);
            }
        }
        mPropertyAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }



/*
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,             int after) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPropertyAdapter.setFilter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mPropertyAdapter.setFilter(newText);
        return true;
    }
*/


    //    private void search(SearchView searchView) {
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                mPropertyAdapter.getFilter().filter(newText);
//                return true;
//            }
//        });
//    }

//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//
//        inflater.inflate(R.menu.main, menu);
//
//       // MenuItem item = menu.findItem(R.id.action_search);
//      //  SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//     //  searchView.setOnQueryTextListener(this);
//
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String query) {
//        query = query.toLowerCase();
//        List<Property> filteredList = new ArrayList<>();
//        for (Property property : mPropertiesList) {
//            String address = property.get_address().toLowerCase();
//            if (address.contains(query)) {
//                filteredList.add(property);
//            }
//        }
//        mPropertyAdapter.setFilter(filteredList);
//        return true;
//    }
}


//
//
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        menu.clear();
//        inflater.inflate(R.menu.main, menu);
//        MenuItem item = menu.findItem(R.id.search);
//        SearchView searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
//        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
//        MenuItemCompat.setActionView(item, searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        searchView.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View v) {
//
//                                          }
//                                      }
//        );
//    }
//



