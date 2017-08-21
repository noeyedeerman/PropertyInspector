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
       viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));

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

    private class sliderAdapter extends FragmentPagerAdapter {

        final  String tabTitles[]={"Public", "Private"};
        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:

                    //return new Fragment_PublicComments.newInstance(property);
                case 1:
                    return new Fragment_PrivateComments();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabTitles[position];
//        }
    }
}


