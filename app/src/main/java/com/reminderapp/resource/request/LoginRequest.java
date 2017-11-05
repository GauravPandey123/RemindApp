package com.reminderapp.resource.request;

import com.reminderapp.web.BaseRequest;

public class LoginRequest extends BaseRequest {
    /**
     * email : jeevan@editsoft.in
     * password : 12345
     */

    private String email;
    private String password;
    private String gcmId;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }
}
