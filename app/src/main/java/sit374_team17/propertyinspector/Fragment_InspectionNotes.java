package sit374_team17.propertyinspector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_InspectionNotes extends Fragment {




    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private View mView;
    // public interface CreatePropertyListener {
    //    void onSaveProperty();
   // }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.fragment_inspection_notes);

        /*expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getActivity().getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });*/


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_inspection_notes, container, false);

        expandableListView = (ExpandableListView) mView.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        java.util.Collections.sort(expandableListTitle);

        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

//        MenuItem camera = menu.findItem(R.id.action_camera);
//        camera.setVisible(true);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);


    }

    public static class ExpandableListDataPump {
        public static HashMap<String, List<String>> getData() {
            HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


            List<String> grounds = new ArrayList<String>();
            grounds.add("Comments:");

            List<String> structure = new ArrayList<String>();
            structure.add("Comments:");

            List<String> exterior = new ArrayList<String>();
            exterior.add("Comments:");

            List<String> environment = new ArrayList<String>();
            environment.add("Comments:");

            List<String> interior = new ArrayList<String>();
            interior.add("Comments:");


            List<String> roof = new ArrayList<String>();
            roof.add("Comments:");


            List<String> windows = new ArrayList<String>();
            windows.add("Comments:");


            List<String> kitchen = new ArrayList<String>();
            kitchen.add("Comments:");


            List<String> bathroom = new ArrayList<String>();
            bathroom.add("Comments:");


            List<String> attic = new ArrayList<String>();
            attic.add("Comments:");


            List<String> garage = new ArrayList<String>();
            garage.add("Comments:");


            List<String> plumbing = new ArrayList<String>();
            plumbing.add("Comments:");


            List<String> electrical = new ArrayList<String>();
            electrical.add("Comments:");


            List<String> heating = new ArrayList<String>();
            heating.add("Comments:");


            List<String> garden = new ArrayList<String>();
            garden.add("Comments:");


            List<String> miscellaneous = new ArrayList<String>();
            miscellaneous.add("Comments:");


            expandableListDetail.put("Grounds", grounds);
            expandableListDetail.put("Structure", structure);
            expandableListDetail.put("Exterior Surface", exterior);
            expandableListDetail.put("Environment", environment);
            expandableListDetail.put("Interior Rooms", interior);
            expandableListDetail.put("Roof", roof);
            expandableListDetail.put("Windows, Doors and Wood Trim", windows);
            expandableListDetail.put("Kitchen", kitchen);
            expandableListDetail.put("Bathrooms", bathroom);
            expandableListDetail.put("Attic/ Basement", attic);
            expandableListDetail.put("Garage", garage);
            expandableListDetail.put("Plumbing", plumbing);
            expandableListDetail.put("Electrical", electrical);
            expandableListDetail.put("Heating/Cooling System", heating);
            expandableListDetail.put("Garden", garden);
            expandableListDetail.put("Miscellaneous", miscellaneous);
            return expandableListDetail;
        }
    }





}


