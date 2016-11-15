package com.example.administrator.winsoftsalesproject.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.adapter.CustomerAdapter;
import com.example.administrator.winsoftsalesproject.model.Customer;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private ArrayList<Customer> customerArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        toolbarSetup();
        initializeViews();
        prepareCustomerList();

    }

    private void initializeViews() {
        recyclerView = (RecyclerView) findViewById(R.id.customer_list);

    }

    private void prepareCustomerList() {
        customerArrayList = new ArrayList<>();
        customerArrayList = prepareCustomer();

        adapter= new CustomerAdapter(CustomerActivity.this,customerArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    private ArrayList<Customer> prepareCustomer() {

        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        Customer customer = new Customer("Piash Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[0]);
        customerArrayList.add(customer);

        customer = new Customer("PT Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[1]);
        customerArrayList.add(customer);

        customer = new Customer("Titu Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[2]);
        customerArrayList.add(customer);

        customer = new Customer("Deepa Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[3]);
        customerArrayList.add(customer);

        customer = new Customer("Anita Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[4]);
        customerArrayList.add(customer);

        customer = new Customer("Aupo Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[5]);
        customerArrayList.add(customer);
        customer = new Customer("Partho Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[6]);
        customerArrayList.add(customer);
        customer = new Customer("Piash Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[7]);
        customerArrayList.add(customer);
        customer = new Customer("Piash Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[8]);
        customerArrayList.add(customer);
        customer = new Customer("Piash Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[9]);
        customerArrayList.add(customer);
        customer = new Customer("Piash Sarker", "Android Developer", "44,Kolabagan, Dhaka-1208", covers[10]);
        customerArrayList.add(customer);

       return  customerArrayList;
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
            final String text = customer.getName().toLowerCase();
            if(text.contains(newText)){
                filteredModelList.add(customer);
            }
        }
        return filteredModelList;
    }

}
