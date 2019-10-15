package com.ort.profesionalinvoicemanager.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ort.profesionalinvoicemanager.model.user.User;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> userName;
    public  MutableLiveData<String> password;

    private User mockUser;

    private MutableLiveData<User> userMutableLiveData;

    public LoginViewModel() {
        userName = new MutableLiveData<>();
        password = new MutableLiveData<>();

    }

    public void setUserName(MutableLiveData<String> userName) {
        this.userName = userName;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public LiveData<String> getUsername() {
        return userName.getValue();
    }
    public LiveData<String> getPassword() {
        return password;
    }

    private void mockRepositorioUser(){


    }

}
