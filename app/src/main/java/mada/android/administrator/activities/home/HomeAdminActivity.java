package mada.android.administrator.activities.home;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import mada.android.R;
import mada.android.administrator.fragments.home.HomeAdminDestinationListFragment;
import mada.android.administrator.fragments.home.SettingAdminFragment;
import mada.android.commons.activities.BaseActivity;

public class HomeAdminActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        this.initWidget();
    }

    public void initWidget(){
        int fragmentContainerViewId = R.id.fragmentContainerViewAdmin;
        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewAdmin);
        this.bottomNavigationView.setOnItemSelectedListener(item -> {
            /*if(item.getItemId() == R.id.accueilMenuAdmin){
                replaceFragment(fragmentContainerViewId, new HomeAdminFragment());
            }*/
            if(item.getItemId() == R.id.destinationMenuAdmin){
                replaceFragment(fragmentContainerViewId, new HomeAdminDestinationListFragment());
            }
            /*else if(item.getItemId() == R.id.hotelMenuAdmin){
                replaceFragment(fragmentContainerViewId, new HomeAdminHotelListFragment());
            }
            else if(item.getItemId() == R.id.actualityMenuAdmin){
                replaceFragment(fragmentContainerViewId, new HomeAdminActualityListFragment());
            }*/
            else if(item.getItemId() == R.id.settingMenuAdmin){
                replaceFragment(fragmentContainerViewId, new SettingAdminFragment());
            }

            return true;
        });
    }
}