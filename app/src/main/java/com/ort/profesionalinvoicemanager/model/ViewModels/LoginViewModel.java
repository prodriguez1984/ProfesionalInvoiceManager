package com.ort.profesionalinvoicemanager.model.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> userName;
    private MutableLiveData<String> password;

    public LoginViewModel() {
        userName = new MutableLiveData<>();
        userName = new MutableLiveData<>();
    }

    public void setUserName(MutableLiveData<String> userName) {
        this.userName = userName;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public LiveData<String> getUsername() {
        return userName;
    }
    public LiveData<String> getPassword() {
        return password;
    }

}
