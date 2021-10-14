package net.sistransito.mobile.util;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;


public class User implements InstanceCreator<User> {

    private String employeeName;
    private String companyName;
    private String companyCode;
    private String registration;
    private String status;
    private String success;
    private String profileImage;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public User createInstance(Type type) {
        return new User();
    }

    @Override
    public String toString() {
        return employeeName +" "+ registration +" "+ companyName +" "+ companyCode +" "+status+" "+success+" "+ profileImage;
    }
}
