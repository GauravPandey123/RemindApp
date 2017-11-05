package com.reminderapp.resource.request.service;



import com.reminderapp.resource.request.LoginRequest;
import com.reminderapp.resource.request.response.LoginResponse;
import com.reminderapp.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public class LoginService extends BaseService<LoginService.LoginApi, LoginRequest, LoginResponse> {

    @Override
    public Call<LoginResponse> onExecute(LoginApi api, LoginRequest request) {
        return api.login(request);
    }

    @Override
    public Class<LoginApi> getAPI() {
        return LoginApi.class;
    }

    interface LoginApi {
        @POST("/api/authenticate")
        Call<LoginResponse> login(@Body LoginRequest loginRequest);
    }
}
