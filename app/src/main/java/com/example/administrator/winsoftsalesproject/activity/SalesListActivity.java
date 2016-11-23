package com.example.administrator.winsoftsalesproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.adapter.ProductAdapter;
import com.example.administrator.winsoftsalesproject.model.Product;
import com.example.administrator.winsoftsalesproject.session.SessionManger;

import java.util.ArrayList;
import java.util.HashMap;

public class SalesListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<Product> productArrayList;
    private FloatingActionButton btnSale;
    SessionManger sessionManger ;
    HashMap<String , String> user;
    private TextView personName, designation;
    ImageView profile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbarSetup();
        initializeViews();
        initCollapsingToolbar();

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

        productArrayList = new ArrayList<>();
        adapter= new ProductAdapter(getApplicationContext(),productArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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


        Product product = new Product("Android Phone","123333","12/12/2016","34566 TK");
        productArrayList.add(product);
       product = new Product("Android Phone","123333","12/12/2016","34566 TK");
        productArrayList.add(product);
         product = new Product("Android Phone","123333","12/12/2016","34566 TK");
        productArrayList.add(product);

        adapter.notifyDataSetChanged();
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



    }
