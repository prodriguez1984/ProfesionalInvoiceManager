        if (alreadyloggedAccount != null) {
package com.ort.profesionalinvoicemanager.views;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.model.user.User;
import com.ort.profesionalinvoicemanager.viewmodel.LoginViewModel;
import com.ort.profesionalinvoicemanager.model.base.SQLiteManager;
import com.ort.profesionalinvoicemanager.viewmodel.ResultData;
import com.ort.profesionalinvoicemanager.views.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "AndroidClarified";
    private GoogleSignInClient googleSignInClient;
    private SignInButton googleSignInButton;
    private Button btnSignUp;
    private GoogleSignInOptions gso;
    private LoginViewModel loginViewModel;
    private Button btnLogin;
    private EditText etUserName;
    private EditText etPasssword;
    private TextView tvResultUsername;
    private TextView tvResultPassword;
    private ActivityLoginBinding binding;
    private TextInputLayout tiloUsername;
    private TextInputLayout tiloPassword;
    private UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Prueba de cometario
        setContentView(R.layout.activity_login);
        getUser();
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        configView();
        SQLiteManager d = new SQLiteManager(getApplicationContext());
        final SQLiteDatabase db = d.getReadableDatabase();
    }

    public boolean validateFields(String userName, String password) {
        Boolean error = false;

        if (TextUtils.isEmpty(Objects.requireNonNull(userName))) {
            tiloUsername.setError("El usuario no debe estar vacío");
            error = true;
        } else if (!isEmailValid(userName)) {
            tiloUsername.setError("No es un email válido");
            error = true;
        } else if (!userName.equals(getUser().getUserName())) {
            tiloUsername.setError("El usuario es incorrecto");
            error = true;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(password))) {
            tiloPassword.setError("El password no debe estar vacío");
            error = true;
        } else if (password.length() < 6) {
            tiloPassword.setError("El password debe tener 6 caracteres");
            error = true;
        } else if (!getUser().getPassword().equals((password))) {
            tiloPassword.setError("El password es incorrecto");
            error = true;
        }
        return  error;
    }

    private void configView() {
        btnLogin = findViewById(R.id.btnHardcodeLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUpLogin);
        etUserName = (EditText) findViewById(R.id.etUsernameLogin);
        etPasssword = (EditText) findViewById(R.id.etPasswordLogin);

        tiloUsername = (TextInputLayout) findViewById(R.id.tiloUsernameLogin);
        tiloPassword = (TextInputLayout) findViewById(R.id.tiloPasswordLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });
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
            String userName = String.valueOf(etUserName.getText());
            String password = String.valueOf(etPasssword.getText());
            @Override
            public void onClick(View v) {
                if (validateFields(userName,password)){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("user", "Pablo Rodriguez");
                    i.putExtra("mail", "pablorodri1984@gmail.com");
                    startActivity(i);
                }

                //ResultData resultUsername = loginViewModel.setResult(etUserName.getText().toString());
            }


        });
    }

    public boolean isEmailValid(String username) {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    public User getUser() {
        userDAO = new UserDAO();
        return userDAO.mockGet();
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
}
