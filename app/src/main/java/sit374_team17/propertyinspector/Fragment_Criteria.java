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

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBSaveExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sit374_team17.propertyinspector.Main.Fragment_Home;
import sit374_team17.propertyinspector.Main.Listener;
import sit374_team17.propertyinspector.Note.Fragment_Note_List;
import sit374_team17.propertyinspector.Property.Inspection;
import sit374_team17.propertyinspector.Property.Property;


public class Fragment_Criteria extends Fragment implements Inspection.Actions {




    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private Listener mListener;

    private View mView;
    // public interface CreatePropertyListener {
    //    void onSaveProperty();
   // }

    public Fragment_Criteria() {
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

    public static Fragment_Criteria newInstance(Property property) {
        Fragment_Criteria fragment = new Fragment_Criteria();
        Bundle args = new Bundle();
       // args.putParcelable(ARG_PROPERTY, property);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.fragment_criteria);

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
    public List<Inspection> inspectionList;
    public static HashMap<String,List<String>> customInspection=new HashMap<>();
    List<Inspection> createInspections;
    DynamoDBMapper mapper;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_criteria, container, false);

        expandableListView = (ExpandableListView) mView.findViewById(R.id.expandableListView);

        //java.util.Collections.sort(expandableListTitle);



        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
        mapper = new DynamoDBMapper(ddbClient);
        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                scanExpression.addFilterCondition("PropertyID", new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue().withS(Fragment_Note_List.PROPERTY_ID)));
                        inspectionList = mapper.scan(Inspection.class, scanExpression);
                if (inspectionList.size() >= 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            expandableListDetail = ExpandableListDataPump.getData(inspectionList);
                            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                            expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail,Fragment_Criteria.this,customInspection);
                            expandableListView.setAdapter(expandableListAdapter);
                        }
                    });
                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        return mView;


    }


    @Override
    public void addLikeDislike(int position, int values, String conditions) {
        int prev_value=0;
        try{
            prev_value=Integer.parseInt(customInspection.get(conditions).get(position));
            customInspection.get(conditions).set(position,String.valueOf(values+prev_value));
        }catch (Exception e){
            customInspection.get(conditions).set(position,String.valueOf(values));
        }
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail,Fragment_Criteria.this,customInspection);
        expandableListView.setAdapter(expandableListAdapter);
        updateInspections();

    }

    void updateInspections()
    {
        final Inspection inspections_result=new Inspection();
        inspections_result.setAttic_Basement(customInspection.get("Attic/ Basement"));
        inspections_result.setBackyard(customInspection.get("Backyard"));
        inspections_result.setBathroom(customInspection.get("Bathrooms"));
        inspections_result.setExterior(customInspection.get("Exterior Surface"));
        inspections_result.setInterior(customInspection.get("Interior Rooms"));
        inspections_result.setGrounds(customInspection.get("Grounds"));
        inspections_result.setKitchen(customInspection.get("Kitchen"));
        inspections_result.setMiscellaneous(customInspection.get("Miscellaneous"));
        inspections_result.setRoof(customInspection.get("Roof"));
        inspections_result.setStructure(customInspection.get("Structure"));
        inspections_result.setWindows_Doors_Trim(customInspection.get("Windows, Doors and Wood Trim"));

        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
                try {
                    AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
                    ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
                    DynamoDBSaveExpression scanExpression = new DynamoDBSaveExpression();
                    mapper = new DynamoDBMapper(ddbClient);

                    mapper.save(scanExpression);
                    expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail,Fragment_Criteria.this,customInspection);
                    expandableListView.setAdapter(expandableListAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();

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
        public static HashMap<String, List<String>> getData(List<Inspection> inspections) {
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

            expandableListDetail.put("Attic/ Basement", J_attic);
            expandableListDetail.put("Backyard", I_bathroom);
            expandableListDetail.put("Bathrooms", I_bathroom);
            expandableListDetail.put("Exterior Surface", C_exterior);
            expandableListDetail.put("Grounds", A_grounds);
          //  expandableListDetail.put("Heating/Cooling System", N_heating);
            expandableListDetail.put("Interior Rooms", E_interior);
            expandableListDetail.put("Kitchen", H_kitchen);
            expandableListDetail.put("Miscellaneous", H_kitchen);
         //   expandableListDetail.put("Plumbing/Electrical", L_plumbing);
            expandableListDetail.put("Roof", F_roof);
            expandableListDetail.put("Structure", B_structure);
            expandableListDetail.put("Windows, Doors and Wood Trim", G_windows);
         /*   expandableListDetail.put("Garage", K_garage);
            expandableListDetail.put("Electrical", M_electrical);
            expandableListDetail.put("Garden", O_garden);
            expandableListDetail.put("Other", other);)*/
            customInspection.put("Attic/ Basement", inspections.get(0).getAttic_Basement());
            customInspection.put("Backyard", inspections.get(0).getBackyard());
            customInspection.put("Bathrooms", inspections.get(0).getBathroom());
            customInspection.put("Exterior Surface", inspections.get(0).getExterior());
            customInspection.put("Grounds", inspections.get(0).getGrounds());
            customInspection.put("Heating/Cooling System", inspections.get(0).getHeating_Cooling());
            customInspection.put("Interior Rooms", inspections.get(0).getInterior());
            customInspection.put("Kitchen", inspections.get(0).getKitchen());
            customInspection.put("Miscellaneous", inspections.get(0).getMiscellaneous());
            customInspection.put("Plumbing/Electrical", inspections.get(0).getPlumbing_Electrical());
            customInspection.put("Roof", inspections.get(0).getRoof());
            customInspection.put("Structure", inspections.get(0).getStructure());
            customInspection.put("Windows, Doors and Wood Trim", inspections.get(0).getWindows_Doors_Trim());

            return expandableListDetail;
        }
    }





}


