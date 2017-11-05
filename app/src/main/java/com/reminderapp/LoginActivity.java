package com.reminderapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

;

import com.reminderapp.helper.Prefs;
import com.reminderapp.resource.request.LoginRequest;
import com.reminderapp.resource.request.response.LoginResponse;
import com.reminderapp.resource.request.service.LoginService;
import com.reminderapp.web.BaseApiCallback;

import in.editsoft.api.exception.APIException;

/**
 * Created by Gaurav on 05/11/17.
 */

public class LoginActivity extends AppCompatActivity {


    // Declare all views here
    EditText etUserEmail;
    EditText etUserPass;
    TextView btnUserLogin;

    Context context;
    LinearLayout linearLayoutSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        context=this;
        initView();
        initListener();
    }

    private void initListener() {
        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUserEmail.getText().toString();
                String pass = etUserPass.getText().toString();
                if (validateDetails(email, pass)) {
                    if (!Utility.isConnected()) {
                        Utility.showToast(R.string.msg_disconnected);
                    } else {
                        submitLoginRequest(email, pass);
                    }
                }
            }
        });

        linearLayoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,UserSignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        etUserEmail = (EditText) findViewById(R.id.et_user_email);
        etUserPass = (EditText) findViewById(R.id.et_user_pass);
        btnUserLogin = (TextView) findViewById(R.id.btn_user_login);
       linearLayoutSignUp=(LinearLayout)findViewById(R.id.linearLayoutSignUp);
    }

    private void submitLoginRequest(String email, String pass) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(pass);
        LoginService loginService = new LoginService();
        loginService.executeService(loginRequest, new BaseApiCallback<LoginResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull LoginResponse response) {
                super.onSuccess(response);
                LoginResponse.DataBean data = response.getData();
                Prefs.putStringPrefs(AppConstants.NAME, data.getName());
                Prefs.putStringPrefs(AppConstants.EMAIL, data.getEmail());
                context.startActivity(new Intent(context, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });
    }

    private boolean validateDetails(String email, String pass) {
        // Utility.hideSoftKeyboard(mA);
        if (!Utility.validateString(email)) {
            Utility.showToast(R.string.msg_enter_email);
            return false;
        } else if (!Utility.validateEmail(email)) {
            Utility.showToast(R.string.msg_email_error);
            return false;
        } else if (!Utility.validateString(pass)) {
            Utility.showToast(R.string.msg_enter_pass1);
            return false;
        } else if (pass.length() < 6) {
            Utility.showToast(R.string.msg_pass_error);
            return false;
        } else {
            return true;
        }
    }

}
