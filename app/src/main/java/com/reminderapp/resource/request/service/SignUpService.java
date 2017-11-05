package com.reminderapp.resource.request.service;



import com.reminderapp.resource.request.SignUpRequest;
import com.reminderapp.resource.request.response.SignUpResponse;
import com.reminderapp.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public class SignUpService extends BaseService<SignUpService.SignUpApi, SignUpRequest, SignUpResponse> {


    @Override
    public Call<SignUpResponse> onExecute(SignUpApi api, SignUpRequest request) {
        return api.signUp(request);
    }

    @Override
    public Class<SignUpApi> getAPI() {
        return SignUpApi.class;
    }

    public interface SignUpApi {
        @POST("/api/users")
        Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);
    }

}
