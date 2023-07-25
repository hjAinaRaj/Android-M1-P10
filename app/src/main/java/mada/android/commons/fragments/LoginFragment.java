package mada.android.commons.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mada.android.R;
import mada.android.visitor.activities.home.HomeVisitorActivity;

public class LoginFragment extends BaseFragment {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignin;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        this.initWidget(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initWidget(View view){
        this.editTextEmail = (EditText) view.findViewById(R.id.editTextEmailLogin);
        this.editTextPassword = (EditText) view.findViewById(R.id.editTextPasswordLogin);
        this.textViewSignin = (TextView) view.findViewById(R.id.textViewToSignin);
        this.buttonLogin = (Button) view.findViewById(R.id.buttonLogin);

        this.textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(R.id.fragmentContainerViewAuth, new SigninFragment());
            }
        });

        this.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(v, new HomeVisitorActivity());
            }
        });
    }
}