package com.example.administrator.winsoftsalesproject.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.fragment.Home;
import com.example.administrator.winsoftsalesproject.fragment.LicenseFragment;
import com.example.administrator.winsoftsalesproject.session.SessionManger;

import java.util.HashMap;

public class NavHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManger sessionManger ;

    private Home home ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManger = new SessionManger(this);
        sessionManger.checkLogin();
        HashMap<String , String> user = sessionManger.getUserDetails();
        home = new Home();
        fragmentTransaction(home);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView headerName  = (TextView) header.findViewById(R.id.nav_name);
        TextView designation = (TextView) header.findViewById(R.id.nav_designation);
        ImageView headerImage = (ImageView) header.findViewById(R.id.nav_image);

        headerName.setText(user.get(sessionManger.KEY_EMPLOYEE_NAME).toUpperCase());
        designation.setText(user.get(sessionManger.KEY_DEPARTMENT).toUpperCase());

        byte[] decodeString = Base64.decode(user.get(sessionManger.KEY_EMPLOYEE_PHOTO).getBytes(),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
        headerImage.setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openLisence();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openLisence() {
        LicenseFragment licensesFragment = new LicenseFragment();
        licensesFragment.show(getSupportFragmentManager().beginTransaction(), "dialog_licenses");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_item_home:
                fragmentTransaction(home);
                return true;

            // For rest of the options we just show a toast on click

            case R.id.nav_item_sale_list:
                Intent intent = new Intent(getApplicationContext(),SalesListActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_item_sale_product:
                intent = new Intent(getApplicationContext(),SalesActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_item_customer_list:
                intent = new Intent(getApplicationContext(),CustomerActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_item_logout:
                logOutDialog();
                return true;
            case R.id.nav_item_exit:
                exitDialog();

                return true ;

            default:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

        }


    }
    public void fragmentTransaction(Fragment f){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame,f);
        fragmentTransaction.commit();

    }
    public void exitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do You Want To Exit?")
                .setTitle("Exit App");


        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                sessionManger.logoutUser();
                NavHomeActivity.this.finish();
                startActivity(homeIntent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void logOutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage("Do You Want To Logout?")
                .setTitle("Logout");


        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManger.logoutUser();
                Intent intent = new Intent( getApplicationContext(), LoginActivity.class);
                startActivity( intent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
