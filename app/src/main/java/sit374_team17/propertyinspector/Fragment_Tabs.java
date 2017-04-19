package sit374_team17.propertyinspector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Callum on 12/04/2017.
 */


public class Fragment_Tabs extends Fragment {
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    private PagerAdapter viewPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabs, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
      //  viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));

       // viewPagerAdapter = new sliderAdapter(getChildFragmentManager());
       // viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }

//    private class sliderAdapter extends FragmentPagerAdapter {
//
//        final  String tabs[]={"Home", "Buy", "Rent", "Sold", "Lease", "News", "About"};
//        public sliderAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//
//            switch (position) {
//                case 0:
//                    Fragment_Home tab1 = new Fragment_Home();
//                    return tab1;
//                case 1:
//                    Fragment_Buy tab2 = new Fragment_Buy();
//                    return tab2;
//                case 2:
//                    Fragment_Rent tab3 = new Fragment_Rent();
//                    return tab3;
//                case 3:
//                    Fragment_Sold tab4 = new Fragment_Sold();
//                    return tab4;
//                case 4:
//                    Fragment_Lease tab5 = new Fragment_Lease();
//                    return tab5;
//                case 5:
//                    Fragment_News tab6 = new Fragment_News();
//                    return tab6;
//                case 6:
//                    Fragment_Property tab7 = new Fragment_Property();
//                    return tab7;
//
//                default:
//                    return null;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 7;
//        }
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabs[position];
//        }
//    }
}


