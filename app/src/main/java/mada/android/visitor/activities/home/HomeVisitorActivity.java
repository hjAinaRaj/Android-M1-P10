package mada.android.visitor.activities.home;

import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import mada.android.R;
import mada.android.commons.MyApplication;
import mada.android.commons.activities.BaseActivity;
import mada.android.visitor.fragments.destination.DestinationListFragment;
import mada.android.visitor.fragments.home.HomeVisitorFragment;
import mada.android.visitor.fragments.quiz.QuizListFragment;
import mada.android.services.external.FirebaseTokenService;
import mada.android.services.external.MyFirebaseMessagingService;
import mada.android.tools.ConfigUtilities;
import mada.android.tools.token.SharedPreferencesUtilities;
import mada.android.visitor.fragments.home.SettingVisitorFragment;
import mada.android.visitor.fragments.settings.SettingUnknownVisitorFragment;

public class HomeVisitorActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView;
    private boolean isRecreatingCalled = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.initGlobalPreferences();
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_visitor);

        this.initWidget();
    }

    public void initWidget(){
        // For testing subscription an unsubscription 
        /*try {
            new MyFirebaseMessagingService().saveSavedToken();
            //new MyFirebaseMessagingService().unsubscribeSavedToken();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/
        int fragmentContainerViewId = R.id.fragmentContainerViewHomeVisitor;
        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        this.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.accueilMenu){
                replaceFragment(fragmentContainerViewId, new HomeVisitorFragment());
            }
            else if(item.getItemId() == R.id.destinationMenu){
                replaceFragment(fragmentContainerViewId, new DestinationListFragment());
            }
            else if(item.getItemId() == R.id.quizMenu){
                replaceFragment(fragmentContainerViewId, QuizListFragment.newInstance());
            }
            else if(item.getItemId() == R.id.hotelMenu){
                replaceFragment(fragmentContainerViewId, new DestinationListFragment());
            }
            /*else if(item.getItemId() == R.id.actualityMenu){
                replaceFragment(fragmentContainerViewId, new DestinationListFragment());
            }*/
            else if(item.getItemId() == R.id.settingsMenu){
                replaceFragment(fragmentContainerViewId, new SettingVisitorFragment());
            }

            return true;
        });
    }
}