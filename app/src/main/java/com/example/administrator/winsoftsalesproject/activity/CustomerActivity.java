package com.example.administrator.winsoftsalesproject.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.winsoftsalesproject.ErrorDialog;
import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.adapter.CustomerAdapter;
import com.example.administrator.winsoftsalesproject.list.CustomerList;
import com.example.administrator.winsoftsalesproject.model.Customer;
import com.example.administrator.winsoftsalesproject.retrofit.ApiService;
import com.example.administrator.winsoftsalesproject.retrofit.RetroClient;
import com.example.administrator.winsoftsalesproject.session.SessionManger;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private ArrayList<Customer> customerArrayList;
    ErrorDialog errorDialog ;
    SessionManger sessionManger ;
    private HashMap<String , String> user ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        errorDialog = new ErrorDialog(this);
        sessionManger = new SessionManger(this);
        sessionManger.checkLogin();
        user = sessionManger.getUserDetails();
        toolbarSetup();
        initializeViews();
        prepareCustomerList();

    }

    private void initializeViews() {
        recyclerView = (RecyclerView) findViewById(R.id.customer_list);

    }

    private void prepareCustomerList() {
        customerArrayList  = new ArrayList<>();
        prepareCustomer();




    }

    private void prepareCustomer() {

             if(isNetworkConnected()){
                 final ProgressDialog dialog ;
                 dialog = new ProgressDialog(this);
                 dialog.setTitle("Wait!");
                 dialog.setMessage("Loading......");
                 dialog.show();

                 ApiService api = RetroClient.getApiService();

                 Call<CustomerList> call = api.getCustomerDetails(user.get(sessionManger.KEY_KEY),"GETCUSTOMER");
                 call.enqueue(new Callback<CustomerList>() {
                     @Override
                     public void onResponse(Call<CustomerList> call, Response<CustomerList> response) {
                         dialog.dismiss();

                         if(response.isSuccess()){

                             customerArrayList = response.body().getCustomerView();
                             adapter= new CustomerAdapter(CustomerActivity.this,customerArrayList);
                             RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                             recyclerView.setLayoutManager(layoutManager);
                             recyclerView.setAdapter(adapter);

                             adapter.setOnLongItemClickListener(new CustomerAdapter.onLongItemClickListener() {
                                 @Override
                                 public void onLongItemClick(View v, final int position) {

                                     ArrayList<String> entrys = new ArrayList<String>();
                                     entrys.add(getString(R.string.dialog_add));

                                     final CharSequence[] items = entrys.toArray(new CharSequence[entrys.size()]);


                                     // File delete confirm
                                     AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivity.this);
                                     builder.setTitle(getString(R.string.dialog_title_choose));
                                     builder.setItems(items, new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int item) {
                                             if (item == 0) {
                                                 sendResultBack(position);
                                             }
                                         }
                                     });
                                     builder.setCancelable(true);
                                     builder.setNegativeButton(getString(R.string.dialog_action_cancel),
                                             new DialogInterface.OnClickListener() {
                                                 public void onClick(DialogInterface dialog, int id) {
                                                     dialog.cancel();
                                                 }
                                             });

                                     AlertDialog alert = builder.create();
                                     alert.show();

                                 }
                             });

                         }
                         else{
                             Toast.makeText(CustomerActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                         }

                     }

                     @Override
                     public void onFailure(Call<CustomerList> call, Throwable t) {
                          dialog.dismiss();
                         Log.d("Server Error:",t.toString());
                         errorDialog.showDialog("Error!","Server Error, Try Again Later.");
                     }
                 });


             }
        else{
               errorDialog.showDialog("No Internet", "Please Enable Wifi or Mobile Data");
             }

    }

    private void sendResultBack(int position) {
        Intent intent = new Intent();
        intent.putExtra("id", customerArrayList.get(position).getCustomerId().toString());
        setResult(RESULT_OK, intent);
        finish();
    }


    private void toolbarSetup() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                adapter.setFilter(customerArrayList);
                break ;
            case R.id.refresh:
                prepareCustomerList();
                break ;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Customer> filterContactList = filterContact(newText , customerArrayList);
                adapter.setFilter(filterContactList);
                customerArrayList = filterContactList;
                return true;
            }
        });

        return true;
    }
    private ArrayList<Customer> filterContact(String newText, ArrayList<Customer> customerArrayList) {
        newText = newText.toLowerCase();
        final ArrayList<Customer> filteredModelList = new ArrayList<>();

        for(Customer customer: customerArrayList){
            final String text = customer.getCustomerName().toLowerCase();
            if(text.contains(newText)){
                filteredModelList.add(customer);
            }
        }
        return filteredModelList;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
