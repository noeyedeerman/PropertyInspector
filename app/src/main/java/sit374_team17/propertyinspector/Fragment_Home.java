package sit374_team17.propertyinspector;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
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


public class Fragment_Home extends Fragment implements SearchView.OnQueryTextListener {

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

    private Listener mListener;


    public Fragment_Home() {
    }


    public interface HomeListener {
        void onHomeInteraction();
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
        if (context instanceof Listener) {
            mListener = (Listener) context;
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

        // mDB_properties.resetTable();
//mDB_properties.onCreate();
        mPropertiesList = mDB_properties.getAllProperties();

        mPropertiesList.addAll(populatePropertyList());


        if (mPropertiesList.size() >= 0) {
            mPropertyAdapter = new Adapter_Properties(mListener, getContext());
            mPropertyAdapter.setPropertyList(mPropertiesList);
            mRecyclerView.setAdapter(mPropertyAdapter);
        }


        return mView;
    }

    private List<Property> populatePropertyList() {
        List<Property> propertyList = new ArrayList<>();


        Property property1 = new Property(0, "311/67", "Spencer Street", "Melbourne", "VIC", "3000", "2", "1", "1", "$840,000", ContextCompat.getDrawable(getContext(), R.drawable.spencer));
        Property property2 = new Property(1, "89", "Armstrong Street", "Middle Park", "VIC", "3206", "3", "2", "1", "Auction", ContextCompat.getDrawable(getContext(), R.drawable.armstrong));
        Property property3 = new Property(2, "8", "Ferrars Place", "South Melbourne", "VIC", "3205", "4", "2", "1", "Auction", ContextCompat.getDrawable(getContext(), R.drawable.ferrars));
        Property property4 = new Property(4, "23", "Wakefield Street", "Kensington", "VIC", "3031", "3", "2", "1", "$950,000", ContextCompat.getDrawable(getContext(), R.drawable.wake));
        Property property5 = new Property(5, "199", "Clarke Street", "Northcote", "VIC", "3070", "3", "1", "1", "$700,000", ContextCompat.getDrawable(getContext(), R.drawable.clarke));

        propertyList.add(property1);
        propertyList.add(property2);
        propertyList.add(property3);
        propertyList.add(property4);
        propertyList.add(property5);
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

        //  MenuItem camera = menu.findItem(R.id.action_camera);
        //  camera.setVisible(false);

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
            final String text = property.getText().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(property);
            }
        }
        mPropertyAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

