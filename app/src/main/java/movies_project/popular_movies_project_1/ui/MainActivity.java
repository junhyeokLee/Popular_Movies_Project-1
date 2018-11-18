package movies_project.popular_movies_project_1.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;
import movies_project.popular_movies_project_1.R;
import movies_project.popular_movies_project_1.adapter.TabPagerAdapter;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter tabPagerAdapter;
    private android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.popular);
        tabLayout.getTabAt(1).setText(R.string.top_rated);
        tabLayout.getTabAt(2).setText(R.string.favorite);
//        tabLayout.getTabAt(2).setText(R.string.favorite);

        if(!internetConnected()){
            Toast.makeText(MainActivity.this, "Please ensure your internet is connection and try again..", Toast.LENGTH_LONG).show();
            networkDialog();
        }

    }

    private void networkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Connect to Internet")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private boolean internetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        return networkInfo != null && networkInfo.isConnected();
        }
    }
