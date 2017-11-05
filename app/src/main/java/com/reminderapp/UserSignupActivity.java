package com.reminderapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.reminderapp.helper.Prefs;
import com.reminderapp.resource.request.SignUpRequest;
import com.reminderapp.resource.request.response.SignUpResponse;
import com.reminderapp.resource.request.service.SignUpService;
import com.reminderapp.web.BaseApiCallback;

import in.editsoft.api.exception.APIException;


public class UserSignupActivity extends AppCompatActivity {
    // Declare all views here
    EditText etUserName;
    EditText etUserEmail;
    EditText etUserPass1;
    EditText etUserPass2;
    TextView btnUserSignup;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_signup);
        context=this;
        initialization();
        setClickListeners();
    }

    private void initialization() {
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etUserEmail = (EditText) findViewById(R.id.et_user_email);
        etUserPass1 = (EditText) findViewById(R.id.et_user_pass1);
        etUserPass2 = (EditText) findViewById(R.id.et_user_pass2);
        btnUserSignup = (TextView) findViewById(R.id.btn_user_signup);
    }


    @Override
    public void onStart() {
        super.onStart();

    }
    private void setClickListeners() {
        btnUserSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etUserName.getText().toString();
                String email = etUserEmail.getText().toString();
                String pass1 = etUserPass1.getText().toString();
                String pass2 = etUserPass2.getText().toString();
                if (validateDetails(name, email, pass1, pass2)) {
                    if (!Utility.isConnected()) {
                        Utility.showToast(R.string.msg_disconnected);
                    } else {
                        submitSignupRequest(name, email, pass1);
                    }
                }
            }
        });
    }

    private void submitSignupRequest(String name, String email, String pass) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName(name);
        signUpRequest.setEmail(email);
        signUpRequest.setPassword(pass);
        signUpRequest.setGcmId("123456");
        signUpRequest.setLoginType(AppConstants.MANUAL_LOGIN);
        SignUpService signUpService = new SignUpService();
        signUpService.executeService(signUpRequest, new BaseApiCallback<SignUpResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull SignUpResponse response) {
                super.onSuccess(response);
                if (response.getStatus().getCode() == 200) {
                    SignUpResponse.DataBean data = response.getData();
                    Prefs.putStringPrefs(AppConstants.EMAIL, data.getEmail());
                    Prefs.putStringPrefs(AppConstants.NAME, data.getName());
                    context.startActivity(new Intent(context, MainActivity.class));
                    finish();
                } else {
                    Utility.showToast(response.getStatus().getDescription());
                }
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });
    }

    private boolean validateDetails(String name, String email, String pass1, String pass2) {
        if (!Utility.validateString(name)) {
            Utility.showToast(R.string.msg_enter_name);
            return false;
        } else if (name.length() < 4) {
            Utility.showToast(R.string.msg_short_name);
            return false;
        } else if (!Utility.validateString(email)) {
            Utility.showToast(R.string.msg_enter_email);
            return false;
        } else if (!Utility.validateEmail(email)) {
            Utility.showToast(R.string.msg_email_error);
            return false;
        } else if (!Utility.validateString(pass1)) {
            Utility.showToast(R.string.msg_enter_pass1);
            return false;
        } else if (pass1.length() < 6) {
            Utility.showToast(R.string.msg_pass_error);
            return false;
        } else if (!pass1.equals(pass2)) {
            Utility.showToast(R.string.msg_pass_match);
            return false;
        } else {
            return true;
        }
    }
}
