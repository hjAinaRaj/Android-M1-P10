package mada.android.commons.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;

import mada.android.R;
import mada.android.tools.ConfigUtilities;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConfigUtilities.setLocale(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void replaceFragment(int idFragmentContainer, Fragment fragment){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(idFragmentContainer, fragment.getClass(), null)
                .commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {


        ConfigUtilities.setLocale(newBase);

        super.attachBaseContext(newBase);
    }
}