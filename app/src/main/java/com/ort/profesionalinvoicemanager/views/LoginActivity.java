package com.ort.profesionalinvoicemanager.views;

//if (alreadyloggedAccount != null) {
//package com.ort.profesionalinvoicemanager.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;
import com.ort.profesionalinvoicemanager.views.Utils.ValidateHelper;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "AndroidClarified";
    private static final int LENGTH_PASSWORD = 6;
    private GoogleSignInClient googleSignInClient;
   // private SignInButton googleSignInButton;
    private GoogleSignInOptions gso;
    private Button btnLogin;
    private EditText etUserName;
    private EditText etPasssword;
    private TextInputLayout tiloUsername;
    private TextInputLayout tiloPassword;
    private Button btnToSignUp;
    private Button btnToPasswordRecovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Prueba de cometario
        ApplicationContext.getInstance().init(getApplicationContext());

        setContentView(R.layout.activity_login);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        ApplicationContext.getInstance().setLoggedUser(UserDAO.getInstance().getUserByMail("pablorodri1984@gmail.com"));
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        configView();
    }

    public boolean validateFields(String userName, String password) {
        Boolean error = false;
        if (ValidateHelper.validateEmptyString(userName)) {
            tiloUsername.setError(StringConstant.USER_NOT_EMPTY);
            error = true;
        } else if (!ValidateHelper.isEmailValid(userName)) {
            tiloUsername.setError(StringConstant.INVALID_EMAIL);
            error = true;
        }

        return  !error;
    }

    private void configView() {
        btnLogin = findViewById(R.id.btnLogin);
        btnToSignUp = findViewById(R.id.btnToSignUp);
        btnToPasswordRecovery = findViewById(R.id.btnToPasswordRecovery);
        etUserName = (EditText) findViewById(R.id.etUsernameLogin);
        etPasssword = (EditText) findViewById(R.id.etPasswordLogin);

        tiloUsername = (TextInputLayout) findViewById(R.id.tiloUsernameLogin);
        tiloPassword = (TextInputLayout) findViewById(R.id.tiloPasswordLogin);

       /* googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton = findViewById(R.id.sign_in_buttonLogin);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validar si tengo internet aca tener un try por si no hay...
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });*/

        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnToPasswordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordRecoveryDialog p =new PasswordRecoveryDialog();
                p.show(getSupportFragmentManager(), "dialog");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                String password = etPasssword.getText().toString();
                if (validateFields(userName,password)){
                    User u = UserDAO.getInstance().getUserByMail(userName);
                    if (u!=null && password.equals(u.getPassword())) {
                        ApplicationContext.getInstance().setLoggedUser(u);
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("email", tiloUsername.getEditText().getText());
                        startActivity(i);
                    }else{
                        showLoginError();
                    }
                }

                //ResultData resultUsername = loginViewModel.setResult(etUserName.getText().toString());
            }


        });
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
