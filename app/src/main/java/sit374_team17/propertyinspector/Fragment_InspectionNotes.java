package sit374_team17.propertyinspector;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
    private Listener mListener;

    private View mView;
    // public interface CreatePropertyListener {
    //    void onSaveProperty();
   // }

    public Fragment_InspectionNotes() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
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

    public static Fragment_InspectionNotes newInstance(DB_Property property) {
        Fragment_InspectionNotes fragment = new Fragment_InspectionNotes();
        Bundle args = new Bundle();
       // args.putParcelable(ARG_PROPERTY, property);
        fragment.setArguments(args);
        return fragment;
    }


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
        //java.util.Collections.sort(expandableListTitle);

        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        return mView;


    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
////        MenuItem camera = menu.findItem(R.id.action_camera);
////        camera.setVisible(true);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        searchItem.setVisible(false);
//
//
//    }


    public static class ExpandableListDataPump {
        public static HashMap<String, List<String>> getData() {
            HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


            List<String> A_grounds = new ArrayList<String>();
            A_grounds.add("Comments:");

            List<String> B_structure = new ArrayList<String>();
            B_structure.add("Comments:");

            List<String> C_exterior = new ArrayList<String>();
            C_exterior.add("Comments:");

            List<String> D_environment = new ArrayList<String>();
            D_environment.add("Comments:");

            List<String> E_interior = new ArrayList<String>();
            E_interior.add("Comments:");


            List<String> F_roof = new ArrayList<String>();
            F_roof.add("Comments:");


            List<String> G_windows = new ArrayList<String>();
            G_windows.add("Comments:");


            List<String> H_kitchen = new ArrayList<String>();
            H_kitchen.add("Comments:");


            List<String> I_bathroom = new ArrayList<String>();
            I_bathroom.add("Comments:");


            List<String> J_attic = new ArrayList<String>();
            J_attic.add("Comments:");


            List<String> K_garage = new ArrayList<String>();
            K_garage.add("Comments:");


            List<String> L_plumbing = new ArrayList<String>();
            L_plumbing.add("Comments:");


            List<String> M_electrical = new ArrayList<String>();
            M_electrical.add("Comments:");


            List<String> N_heating = new ArrayList<String>();
            N_heating.add("Comments:");


            List<String> O_garden = new ArrayList<String>();
            O_garden.add("Comments:");


            List<String> other = new ArrayList<String>();
            other.add("Comments:");


            expandableListDetail.put("Grounds", A_grounds);
            expandableListDetail.put("Structure", B_structure);
            expandableListDetail.put("Exterior Surface", C_exterior);
            expandableListDetail.put("Environment", D_environment);
            expandableListDetail.put("Interior Rooms", E_interior);
            expandableListDetail.put("Roof", F_roof);
            expandableListDetail.put("Windows, Doors and Wood Trim", G_windows);
            expandableListDetail.put("Kitchen", H_kitchen);
            expandableListDetail.put("Bathrooms", I_bathroom);
            expandableListDetail.put("Attic/ Basement", J_attic);
            expandableListDetail.put("Garage", K_garage);
            expandableListDetail.put("Plumbing", L_plumbing);
            expandableListDetail.put("Electrical", M_electrical);
            expandableListDetail.put("Heating/Cooling System", N_heating);
            expandableListDetail.put("Garden", O_garden);
            expandableListDetail.put("Other", other);
            return expandableListDetail;
        }
    }





}


