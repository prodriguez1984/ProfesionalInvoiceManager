package com.ort.profesionalinvoicemanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.views.ui.ClientList.ClientListFragment;
import com.ort.profesionalinvoicemanager.views.ui.mail.MailFragment;
import com.ort.profesionalinvoicemanager.views.ui.products.ProductAdapter;
import com.ort.profesionalinvoicemanager.views.ui.products.ProductCreate;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    public static final String GOOGLE_ACCOUNT = "google_account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //le paso this, haciendo alución a la interfaz OnNavigationItemSelectedListener
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home_custom,
                R.id.nav_industry, R.id.nav_clientList,R.id.nav_ProductList,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.getMenu().getItem(0).setChecked(true);

        TextView user = (TextView) navigationView.getHeaderView(0).findViewById(R.id.lblName);
        //user.setText(ApplicationContext.getInstance().getLoggedUser().getUserName());

        TextView mail = navigationView.getHeaderView(0).findViewById(R.id.lblMail);
        //mail.setText(ApplicationContext.getInstance().getLoggedUser().getMail());

      Button btnPrueba = findViewById(R.id.btnPrueba);
        btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), IndustryFragment.class);
                startActivity(i);
            }
        });
        btnPrueba.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home_custom:
                Fragment homeFragment = new HomeCustomFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_custom, homeFragment)
                        .commit();
                break;
            case R.id.nav_industry:
                Intent intent = new Intent(getApplicationContext(), IndustryActivity.class);
                intent.putExtra("EXTRA_INSUTRY", UserDAO.getInstance().);
                startActivity(intent);
                break;
            case R.id.nav_clientList:
                Fragment clientListFragment = new ClientListFragment();
                getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_host_fragment, clientListFragment)
                                            .commit();
                break;
            case R.id.nav_billing:
                Fragment billingFragment = new BillingFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, billingFragment)
                        .commit();
                break;
            case R.id.nav_mail:
                MailFragment mailFragment = new MailFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, mailFragment)
                        .commit();
                break;
        }
        return true;
    }

}
