package mada.android.commons.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mada.android.R;
import mada.android.commons.activities.AuthActivity;
import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.users.User;
import mada.android.models.users.UserToken;
import mada.android.services.UserService;
import mada.android.tools.token.TokenUtilities;
import mada.android.visitor.activities.home.HomeVisitorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninFragment extends BaseFragment {
    private UserService userService;
    private EditText editTextName, editTextFirstname, editTextEmail, editTextPassword, editTextPasswordConfirm;
    private Button buttonSignin;
    private TextView textViewToLogin;
    public SigninFragment() {
        // Required empty public constructor
        if(userService == null){
            userService = new UserService();
        }
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
                try {
                    User user = new User();
                    user.setFirstName(editTextFirstname.getText().toString());
                    user.setLastName(editTextName.getText().toString());
                    user.setEmail(editTextEmail.getText().toString());
                    user.setPassword(editTextPassword.getText().toString());
                    user.setConfirmPassword(editTextPasswordConfirm.getText().toString());
                    Call<MessageResponse> call = userService.signin(user);
                    call.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            MessageResponse messageResponse = response.body();
                            Toast.makeText(v.getContext(), messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            startNewActivity(v, new HomeVisitorActivity());
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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