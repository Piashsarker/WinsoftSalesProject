package com.example.administrator.winsoftsalesproject.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.winsoftsalesproject.ErrorDialog;
import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.adapter.ProductAdapter;
import com.example.administrator.winsoftsalesproject.list.SalesList;
import com.example.administrator.winsoftsalesproject.model.SalesView;
import com.example.administrator.winsoftsalesproject.retrofit.ApiService;
import com.example.administrator.winsoftsalesproject.retrofit.RetroClient;
import com.example.administrator.winsoftsalesproject.session.SessionManger;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesListActivity extends AppCompatActivity {
    ProductAdapter adapter;
    SessionManger sessionManger ;
    HashMap<String , String> user;
    ImageView profile ;
    ErrorDialog errorDialog;
    private RecyclerView recyclerView;
    private ArrayList<SalesView> productArrayList;
    private FloatingActionButton btnSale;
    private TextView personName, designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbarSetup();
        initializeViews();
        initCollapsingToolbar();
        errorDialog = new ErrorDialog(this);
        sessionManger = new SessionManger(this);
        user = sessionManger.getUserDetails();
        prepareCustomerList();



        btnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesListActivity.this, SalesActivity.class);
                startActivity(intent);
            }
        });


    }
    private void prepareCustomerList() {


        prepareCustomer();
        try {
            byte[] decodeString = Base64.decode(user.get(sessionManger.KEY_EMPLOYEE_PHOTO).getBytes(),Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
            profile.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }


        personName.setText(user.get(SessionManger.KEY_EMPLOYEE_NAME));
        designation.setText(user.get(SessionManger.KEY_DEPARTMENT));

    }

    private void prepareCustomer() {

        if (isNetworkConnected()) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(this);
            dialog.setTitle("Wait!");
            dialog.setMessage("Loading......");
            dialog.show();

            ApiService api = RetroClient.getApiService();

            Call<SalesList> call = api.getSalesItem(user.get(sessionManger.KEY_KEY), "GETSALESINFO", user.get(sessionManger.KEY_USER_ID));
            call.enqueue(new Callback<SalesList>() {
                @Override
                public void onResponse(Call<SalesList> call, Response<SalesList> response) {
                    dialog.dismiss();

                    if (response.isSuccess()) {

                        productArrayList = new ArrayList<SalesView>();
                        productArrayList = response.body().getSalesView();
                        adapter = new ProductAdapter(getApplicationContext(), productArrayList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);


                    } else {
                        Toast.makeText(SalesListActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<SalesList> call, Throwable t) {
                    dialog.dismiss();
                    Log.d("Server Error:", t.toString());
                    errorDialog.showDialog("Error!", "Server Error, Try Again Later.");
                }
            });


        } else {
            errorDialog.showDialog("No Internet", "Please Enable Wifi or Mobile Data");
        }

    }


    private void initializeViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnSale = (FloatingActionButton) findViewById(R.id.btnAddNewSale);
        personName = (TextView) findViewById(R.id.txt_person_name);
        designation = (TextView) findViewById(R.id.txt_designation);
        profile = (ImageView) findViewById(R.id.backdrop);
    }

    private void toolbarSetup() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Home");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    }
