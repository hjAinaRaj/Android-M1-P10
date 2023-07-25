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

public class SigninFragment extends BaseFragment {
    private EditText editTextName, editTextFirstname, editTextEmail, editTextPassword, editTextPasswordConfirm;
    private Button buttonSignin;
    private TextView textViewToLogin;
    public SigninFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        this.initWidget(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initWidget(View view){
        this.editTextName = (EditText) view.findViewById(R.id.editTextNameSignin);
        this.editTextFirstname = (EditText) view.findViewById(R.id.editTextFirstnameSignin);
        this.editTextEmail = (EditText) view.findViewById(R.id.editTextEmailSignin);
        this.editTextPassword = (EditText) view.findViewById(R.id.editTextTextPasswordSignin);
        this.editTextPasswordConfirm = (EditText) view.findViewById(R.id.editTextTextPasswordConfirmSignin);
        this.buttonSignin = (Button) view.findViewById(R.id.buttonSignin);
        this.textViewToLogin = (TextView) view.findViewById(R.id.textViewToLogin);

        this.buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(v, new HomeVisitorActivity());
            }
        });

        this.textViewToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(R.id.fragmentContainerViewAuth, new LoginFragment());
            }
        });
    }
}