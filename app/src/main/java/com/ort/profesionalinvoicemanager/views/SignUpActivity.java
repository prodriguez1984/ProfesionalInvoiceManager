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
    private EditText etPasssword;
    private TextInputLayout tiloUsername;
    private TextInputLayout tiloPassword;
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
        etPasssword = (EditText) findViewById(R.id.etPasswordSignUp);
        tiloUsername = (TextInputLayout) findViewById(R.id.tiloUsernameSignUp);
        tiloPassword = (TextInputLayout) findViewById(R.id.tiloPasswordSignUp);
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton = findViewById(R.id.sign_in_buttonLogin);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validar si tengo internet aca tener un try por si no hay...
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail = String.valueOf(etUserName.getText());
                String password = String.valueOf(etPasssword.getText());
                if (validateFields(eMail, password)){
                    if (userExist(eMail)){
                        showSignUpError();
                    }else{
                        Intent i = new Intent(getApplicationContext(), IndustryActivity.class);
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


    public boolean validateFields(String userName, String password) {
        Boolean error = false;
//        User user = getUserByMail(userName);
        if (ValidateHelper.validateEmptyString(userName)) {
            tiloUsername.setError(StringConstant.USER_NOT_EMPTY);
            error = true;
        } else if (!ValidateHelper.isEmailValid(userName)) {
            tiloUsername.setError(StringConstant.INVALID_EMAIL);
            error = true;
        } /*else if (!userName.equals(getUser().getUserName())) {
            tiloUsername.setError("El usuario es incorrecto");
            error = true;
        }*/

        if (ValidateHelper.validateEmptyString(password)) {
            tiloPassword.setError(StringConstant.PASSWORD_NOT_EMPTY);
            error = true;
        } else if (ValidateHelper.passwordLength(password, LENGTH_PASSWORD)) {
            tiloPassword.setError(StringConstant.LENGTH_PASSWORD);
            error = true;
        }/* else if (!getUser().getPassword().equals((password))) {
            tiloPassword.setError("El password es incorrecto");
            error = true;
        }*/
        return  error;
    }

    private void showSignUpError() {
        Toast.makeText(this.getApplicationContext(),
                StringConstant.USER_EXIST, Toast.LENGTH_SHORT).show();
    }


}
