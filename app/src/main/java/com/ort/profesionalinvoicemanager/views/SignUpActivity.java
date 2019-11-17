package com.ort.profesionalinvoicemanager.views;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.model.user.User;
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;
import com.ort.profesionalinvoicemanager.views.Utils.ValidateHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private static final int LENGTH_PASSWORD = 6;
    private GoogleSignInClient googleSignInClient;
    private SignInButton googleSignInButton;
    private Button btnSignUp;
    private GoogleSignInOptions gso;

    private EditText etUserName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etRepeatPass;

    private TextInputLayout tiloUsername;
    private TextInputLayout tiloEmail;
    private TextInputLayout tiloPassword;
    private TextInputLayout tiloRepeatPass;

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        configView();
    }

    private void configView() {
        btnSignUp = (Button) findViewById(R.id.idBtnSignUp);
        etUserName = (EditText) findViewById(R.id.etUsernameSignUp);
        etEmail = (EditText) findViewById(R.id.etEmailSignUp);
        etPassword = (EditText) findViewById(R.id.etPasswordSignUp);
        etRepeatPass = (EditText) findViewById(R.id.etRepeatPassSignUp);

        tiloUsername = (TextInputLayout) findViewById(R.id.tiloUsernameSignUp);
        tiloEmail = (TextInputLayout) findViewById(R.id.tiloEmailSignUp);
        tiloPassword = (TextInputLayout) findViewById(R.id.tiloPasswordSignUp);
        tiloRepeatPass = (TextInputLayout) findViewById(R.id.tiloRepeatPassSignUp);

//        googleSignInClient = GoogleSignIn.getClient(this, gso);
//        googleSignInButton = findViewById(R.id.sig);
//        googleSignInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //validar si tengo internet aca tener un try por si no hay...
//                Intent signInIntent = googleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, 101);
//            }
//        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(etUserName.getText());
                String eMail = String.valueOf(etEmail.getText());
                String password = String.valueOf(etPassword.getText());
                String repeatPass = String.valueOf(etRepeatPass.getText());

                if (validateFields(username, eMail,password,repeatPass)){
                    if (userExist(eMail)){
                        showSignUpError();
                    }else{
                        Intent i = new Intent(getApplicationContext(), IndustryActivity.class);
                        i.putExtra("username", username);
                        i.putExtra("email", eMail);
                        i.putExtra("password", password);
                        startActivity(i);
                    }
                };
            }

        });
    }

    public boolean userExist(String eMail) {
        boolean ok = true;
        User user = UserDAO.getInstance().getUserByMail(eMail);

        return user != null;
    }


    public boolean validateFields(String userName,String eMail,String password,  String repeatPass) {
        Boolean error = false;
//        User user = getUserByMail(userName);
        if (ValidateHelper.validateEmptyString(userName)) {
            tiloUsername.setError(StringConstant.USER_NOT_EMPTY);
            error = true;

        } else if (ValidateHelper.validateEmptyString(eMail)) {
            tiloEmail.setError(StringConstant.EMAIL_EMPTY);
            error = true;
        }
        else if (!ValidateHelper.isEmailValid(eMail)) {
            tiloUsername.setError(StringConstant.INVALID_EMAIL);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(password)) {
            tiloPassword.setError(StringConstant.PASSWORD_NOT_EMPTY);
            error = true;
        } else if (ValidateHelper.passwordLength(password, LENGTH_PASSWORD)) {
            tiloPassword.setError(StringConstant.LENGTH_PASSWORD);
            error = true;
        } if (ValidateHelper.validateEmptyString(repeatPass)) {
            tiloPassword.setError(StringConstant.REPEAT_PASSWORD_NOT_EMPTY);
            error = true;
        } else if (ValidateHelper.passwordLength(repeatPass, LENGTH_PASSWORD)) {
            tiloPassword.setError(StringConstant.LENGTH_PASSWORD);
            error = true;
        } else if (!ValidateHelper.comparatePass(password, repeatPass)) {
            tiloPassword.setError(StringConstant.DIFERENT_PASS);
            error = true;
        }
        return  !error;
    }

    private void showSignUpError() {
        Toast.makeText(this.getApplicationContext(),
                StringConstant.USER_EXIST, Toast.LENGTH_SHORT).show();
    }


}
