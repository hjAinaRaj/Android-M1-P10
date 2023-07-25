package mada.android.commons.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import mada.android.R;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}