package mada.android.commons.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import mada.android.R;
import mada.android.administrator.activities.home.HomeAdminActivity;
import mada.android.commons.activities.AuthActivity;
import mada.android.models.config.Constant;
import mada.android.models.users.User;
import mada.android.models.users.UserToken;
import mada.android.services.UserService;
import mada.android.tools.token.SharedPreferencesUtilities;
import mada.android.tools.token.TokenUtilities;
import mada.android.tools.ws.ErrorModel;
import mada.android.visitor.activities.home.HomeVisitorActivity;
import mada.android.visitor.fragments.settings.SettingConnectedVisitorFragment;
import mada.android.visitor.fragments.settings.SettingUnknownVisitorFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends BaseFragment {
    private UserService userService;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignin;
    public LoginFragment() {
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
                try {
                    User user = new User();
                    user.setEmail(editTextEmail.getText().toString());
                    user.setPassword(editTextPassword.getText().toString());
                    Call<UserToken> call = userService.login(user);
                    call.enqueue(new Callback<UserToken>() {
                        @Override
                        public void onResponse(Call<UserToken> call, Response<UserToken> response) {

                            if(response.code() != 200){
                                try{
                                    String errorBodyStr = response.errorBody().string();
                                    Gson gson = new Gson();
                                    ErrorModel errorModel = gson.fromJson(errorBodyStr, ErrorModel.class);

                                    Toast.makeText(view.getContext(), errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }catch(Exception e){
                                    Toast.makeText(view.getContext(), "Error when dealing with login error", Toast.LENGTH_SHORT).show();
                                }


                                return;
                            }

                            UserToken userToken = response.body();
                            SharedPreferencesUtilities.saveData(
                                    getContext(),
                                    TokenUtilities.USER_TOKEN_KEY,
                                    userToken.getToken()
                            );
                            SharedPreferencesUtilities.saveData(
                                    getContext(),
                                    TokenUtilities.USER_NAME,
                                    userToken.getUser().getFirstName() + " "
                                    + userToken.getUser().getLastName()
                            );
                            Activity startingActivity = new HomeVisitorActivity();
                            if(userToken.getUser().getRoleId() == Constant.ROLE_ADMIN){
                                startingActivity = new HomeAdminActivity();
                            }
                            String userTokenLoaded = SharedPreferencesUtilities.loadData(
                                    getContext(),
                                    TokenUtilities.USER_TOKEN_KEY,
                                    ""
                            );
                            startNewActivity(v, startingActivity);
                        }

                        @Override
                        public void onFailure(Call<UserToken> call, Throwable t) {
                            Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}