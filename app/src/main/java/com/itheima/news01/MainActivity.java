package com.itheima.news01;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.itheima.news01.Fragment.MainFragment1;
import com.itheima.news01.Fragment.MainFragment2;
import com.itheima.news01.Fragment.MainFragment3;
import com.itheima.news01.Fragment.MainFragment4;
import com.itheima.news01.Fragment.MainFragment5;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg01);
        initViewPager();
        initNavigationView();
        initToolBar();
        initDrawerLayout();

    }
    private ActionBarDrawerToggle toggle;
    private void initDrawerLayout() {

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initToolBar() {
        toolbar= (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("广交院实训");

        toolbar.setNavigationIcon(R.drawable.btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initNavigationView() {
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView= (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               showToast(""+item.getTitle());
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MainFragment1());
        fragments.add(new MainFragment2());
        fragments.add(new MainFragment3());
        fragments.add(new MainFragment4());
        fragments.add(new MainFragment5());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

    }

    @Override
    public void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_01:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_02:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_03:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_04:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.rb_05:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRadioGroup.check(R.id.rb_01);
                        break;
                    case 1:
                        mRadioGroup.check(R.id.rb_02);
                        break;
                    case 2:
                        mRadioGroup.check(R.id.rb_03);
                        break;
                    case 3:
                        mRadioGroup.check(R.id.rb_04);
                        break;
                    case 4:
                        mRadioGroup.check(R.id.rb_05);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.item_01){
            showToast("item 01");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
