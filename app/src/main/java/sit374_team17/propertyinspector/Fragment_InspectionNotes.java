package sit374_team17.propertyinspector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.util.Log;



import java.util.List;

public class Fragment_InspectionNotes extends Fragment {




    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
   // public interface CreatePropertyListener {
    //    void onSaveProperty();
   // }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.fragment_inspection_notes);



        expandableListView = (ExpandableListView) getActivity().findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());

        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
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
            grounds.add("Evidence of damage");
            grounds.add("Evidence of risk");
            grounds.add("Likes");
            grounds.add("Dislikes");


            List<String> structure = new ArrayList<String>();
            structure.add("Evidence of damage");
            structure.add("Evidence of risk");
            structure.add("Likes");
            structure.add("Dislikes");


            List<String> exterior = new ArrayList<String>();
            exterior.add("Evidence of damage");
            exterior.add("Evidence of risk");
            exterior.add("Likes");
            exterior.add("Dislikes");

            List<String> environment = new ArrayList<String>();
            environment.add("Evidence of damage");
            environment.add("Evidence of risk");
            environment.add("Likes");
            environment.add("Dislikes");

            List<String> interior = new ArrayList<String>();
            interior.add("Evidence of damage");
            interior.add("Evidence of risk");
            interior.add("Likes");
            interior.add("Dislikes");

            List<String> roof = new ArrayList<String>();
            roof.add("Evidence of damage");
            roof.add("Evidence of risk");
            roof.add("Likes");
            roof.add("Dislikes");

            List<String> windows = new ArrayList<String>();
            windows.add("Evidence of damage");
            windows.add("Evidence of risk");
            windows.add("Likes");
            windows.add("Dislikes");

            List<String> kitchen = new ArrayList<String>();
            kitchen.add("Evidence of damage");
            kitchen.add("Evidence of risk");
            kitchen.add("Likes");
            kitchen.add("Dislikes");

            List<String> bathroom = new ArrayList<String>();
            bathroom.add("Evidence of damage");
            bathroom.add("Evidence of risk");
            bathroom.add("Likes");
            bathroom.add("Dislikes");

            List<String> attic = new ArrayList<String>();
            attic.add("Evidence of damage");
            attic.add("Evidence of risk");
            attic.add("Likes");
            attic.add("Dislikes");

            List<String> garage = new ArrayList<String>();
            garage.add("Evidence of damage");
            garage.add("Evidence of risk");
            garage.add("Likes");
            garage.add("Dislikes");

            List<String> plumbing = new ArrayList<String>();
            plumbing.add("Evidence of damage");
            plumbing.add("Evidence of risk");


            List<String> electrical = new ArrayList<String>();
            electrical.add("Evidence of damage");
            electrical.add("Evidence of risk");


            List<String> heating = new ArrayList<String>();
            heating.add("Evidence of damage");
            heating.add("Evidence of risk");


            List<String> garden = new ArrayList<String>();
            garden.add("Evidence of damage");
            garden.add("Evidence of risk");
            garden.add("Likes");
            garden.add("Dislikes");

            List<String> miscellaneous = new ArrayList<String>();
            miscellaneous.add("Evidence of damage");
            miscellaneous.add("Evidence of risk");
            miscellaneous.add("Likes");
            miscellaneous.add("Dislikes");


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


