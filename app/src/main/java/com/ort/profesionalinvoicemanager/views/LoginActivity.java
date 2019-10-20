package com.ort.profesionalinvoicemanager.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.model.base.ApplicationContext;
import com.ort.profesionalinvoicemanager.model.user.User;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "AndroidClarified";
    private GoogleSignInClient googleSignInClient;
    private SignInButton googleSignInButton;
    private GoogleSignInOptions gso;
    private Button btnLogin;
    private EditText etUserName;
    private EditText etPasssword;
    private TextInputLayout tiloUsername;
    private TextInputLayout tiloPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Prueba de cometario
        ApplicationContext.getInstance().init(getApplicationContext());
        setContentView(R.layout.activity_login);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        configView();

    }

    public boolean validateFields(String userName, String password) {
        Boolean error = false;

        if (TextUtils.isEmpty(Objects.requireNonNull(userName))) {
            tiloUsername.setError("El usuario no debe estar vacío");
            error = true;
        } else if (!isEmailValid(userName)) {
            tiloUsername.setError("No es un email válido");
            error = true;
        } /*else if (!userName.equals(getUser().getUserName())) {
            tiloUsername.setError("El usuario es incorrecto");
            error = true;
        }*/

        if (TextUtils.isEmpty(Objects.requireNonNull(password))) {
            tiloPassword.setError("El password no debe estar vacío");
            error = true;
        } else if (password.length() < 6) {
            tiloPassword.setError("El password debe tener 6 caracteres");
            error = true;
        }/* else if (!getUser().getPassword().equals((password))) {
            tiloPassword.setError("El password es incorrecto");
            error = true;
        }*/
        return  error;
    }

    private void configView() {
        btnLogin = findViewById(R.id.btnHardcodeLogin);
        etUserName = (EditText) findViewById(R.id.etUsernameLogin);
        etPasssword = (EditText) findViewById(R.id.etPasswordLogin);

        tiloUsername = (TextInputLayout) findViewById(R.id.tiloUsernameLogin);
        tiloPassword = (TextInputLayout) findViewById(R.id.tiloPasswordLogin);

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


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                String password = etPasssword.getText().toString();
                if (!validateFields(userName,password)){
                    User u = UserDAO.getInstance().getUserByMail(userName);
                    if (u!=null && password.equals(u.getPassword())) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("user", u.getUserName());
                        i.putExtra("mail", u.getMail());
                        startActivity(i);
                    }else{
                        showLoginError();
                    }
                }

                //ResultData resultUsername = loginViewModel.setResult(etUserName.getText().toString());
            }


        });
    }

    public boolean isEmailValid(String username) {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.GOOGLE_ACCOUNT, googleSignInAccount);

        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }
    }
    private void showLoginError() {
        Toast.makeText(this.getApplicationContext(),
                "Error al validar credenciales", Toast.LENGTH_SHORT).show();
    }
}
