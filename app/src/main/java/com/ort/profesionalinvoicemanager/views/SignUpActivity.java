
package com.ort.profesionalinvoicemanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.model.user.User;
import com.ort.profesionalinvoicemanager.viewmodel.LoginViewModel;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private GoogleSignInClient googleSignInClient;
    private SignInButton googleSignInButton;
    private Button btnSignUp;
    private GoogleSignInOptions gso;
    private LoginViewModel loginViewModel;
    private EditText etUserName;
    private EditText etPasssword;
    private TextInputLayout tiloUsername;
    private TextInputLayout tiloPassword;

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
                String userName = String.valueOf(etUserName.getText());
                String password = String.valueOf(etPasssword.getText());
                if (validateFields(userName, password)){
                    User user = new User("",userName,password);
                    //userDAO(user);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("user", "Pablo Rodriguez");
                    i.putExtra("mail", "pablorodri1984@gmail.com");
                    startActivity(i);
                };
                //ResultData resultUsername = loginViewModel.setResult(etUserName.getText().toString());
            }


        });
    }

    public boolean validateFields(String userName, String password) {
        Boolean error = false;

        if (TextUtils.isEmpty(Objects.requireNonNull(userName))) {
            tiloUsername.setError("El usuario no debe estar vacío");
            error = true;
        } else if (!isEmailValid(userName)) {
            tiloUsername.setError("No es un email válido");
            error = true;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(password))) {
            tiloPassword.setError("El password no debe estar vacío");
            error = true;
        } else if (password.length() < 6) {
            tiloPassword.setError("El password debe tener 6 caracteres");
            error = true;
        }
        return error;
    }
    public boolean isEmailValid(String username) {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

}
