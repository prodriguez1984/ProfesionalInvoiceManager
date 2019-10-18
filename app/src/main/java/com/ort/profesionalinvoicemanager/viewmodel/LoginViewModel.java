    }
package com.ort.profesionalinvoicemanager.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.model.user.User;

import java.util.Objects;

public class LoginViewModel extends ViewModel {
    public static final String ERROR_EMPTY_USERNAME = "Error el usuario no puede estar vacío";
    public MutableLiveData<String> userName;
    public  MutableLiveData<String> password;
    public MutableLiveData<ResultData> result;

    private UserDAO userDAO;
    private MutableLiveData<User> userMutableLiveData;
    private User mockUser;


    public LoginViewModel() {
        userDAO = new UserDAO();
        userName = new MutableLiveData<>();
        password = new MutableLiveData<>();
        result = new MutableLiveData<>();
    }

    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        userMutableLiveData.setValue(geMockUser());
        return userMutableLiveData;
    }

    public ResultData setResult(String userName) {
        ResultData resultData = new ResultData();
        if (TextUtils.isEmpty(Objects.requireNonNull(userName))) {
           resultData.setError(true);
           resultData.setMessagge(ERROR_EMPTY_USERNAME);
        }
        this.result.setValue(resultData);
        return resultData;
    }


    public LiveData<ResultData> getResult() {
        return result;
    }
    public LiveData<String> getPassword() {
        return password;
    }

    private User geMockUser(){
        return userDAO.mockGet();
    }

}
