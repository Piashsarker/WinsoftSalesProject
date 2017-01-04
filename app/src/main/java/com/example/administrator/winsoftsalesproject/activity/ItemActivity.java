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
import com.example.administrator.winsoftsalesproject.adapter.SaleItemAdapter;
import com.example.administrator.winsoftsalesproject.list.ItemList;
import com.example.administrator.winsoftsalesproject.model.ItemView;
import com.example.administrator.winsoftsalesproject.retrofit.ApiService;
import com.example.administrator.winsoftsalesproject.retrofit.RetroClient;
import com.example.administrator.winsoftsalesproject.session.SessionManger;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends AppCompatActivity {

    ErrorDialog errorDialog;
    SessionManger sessionManger;
    private RecyclerView recyclerView;
    private SaleItemAdapter adapter;
    private ArrayList<ItemView> itemArrayList;
    private HashMap<String , String> user ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        errorDialog = new ErrorDialog(this);
        sessionManger = new SessionManger(this);
        sessionManger.checkLogin();
        user = sessionManger.getUserDetails();
        toolbarSetup();
        initializeViews();
        prepareItemList();

    }

    private void initializeViews() {
        recyclerView = (RecyclerView) findViewById(R.id.item_list);

    }

    private void prepareItemList() {
        itemArrayList  = new ArrayList<>();
        prepareItem();




    }

    private void prepareItem() {

        if(isNetworkConnected()){
            final ProgressDialog dialog ;
            dialog = new ProgressDialog(this);
            dialog.setTitle("Wait!");
            dialog.setMessage("Loading......");
            dialog.show();

            ApiService api = RetroClient.getApiService();

            Call<ItemList> call = api.getItemDetails(user.get(sessionManger.KEY_KEY),"GETITEM");
            call.enqueue(new Callback<ItemList>() {
                @Override
                public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                    dialog.dismiss();

                    if(response.isSuccess()){

                        itemArrayList = response.body().getItemView();
                        adapter= new SaleItemAdapter(ItemActivity.this,itemArrayList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);

                        adapter.setOnLongItemClickListener(new SaleItemAdapter.onLongItemClickListener() {
                            @Override
                            public void onLongItemClick(View v, final int position) {

                                ArrayList<String> entrys = new ArrayList<String>();
                                entrys.add(getString(R.string.dialog_add_item));

                                final CharSequence[] items = entrys.toArray(new CharSequence[entrys.size()]);


                                // File delete confirm
                                AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
                                builder.setTitle(getString(R.string.dialog_title_choose_item));
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
                        Toast.makeText(ItemActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ItemList> call, Throwable t) {
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
        intent.putExtra("item_id", itemArrayList.get(position).getItemCode().toString());
        intent.putExtra("item_name",itemArrayList.get(position).getItemName().toString());

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
                adapter.setFilter(itemArrayList);
                break ;
            case R.id.refresh:
                prepareItemList();
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
                ArrayList<ItemView> filterContactList = filterContact(newText , itemArrayList);
                adapter.setFilter(filterContactList);
                itemArrayList = filterContactList;
                return true;
            }
        });

        return true;
    }
    private ArrayList<ItemView> filterContact(String newText, ArrayList<ItemView> customerArrayList) {
        newText = newText.toLowerCase();
        final ArrayList<ItemView> filteredModelList = new ArrayList<>();

        for(ItemView customer: customerArrayList){
            final String text = customer.getItemName().toLowerCase();
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
