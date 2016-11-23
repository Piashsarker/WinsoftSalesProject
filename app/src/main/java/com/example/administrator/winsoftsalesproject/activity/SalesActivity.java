package com.example.administrator.winsoftsalesproject.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.adapter.ItemAdapter;
import com.example.administrator.winsoftsalesproject.model.Item;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SalesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnSale;
    private ImageButton btnAddCustomer;
    private Button btnAddItem;
    private ArrayList<Item> productList = new ArrayList<>();
    private RecyclerView salesList;
    private EditText etDate, etReference, etCustomerCode, etItemCode, etBalance, etQuantity;
    private TextView txtCustomerName, txtItemName;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    private String minimumUnit , itemId;
    private ItemAdapter adapter;
    private String customerId, name, itemName;
    private Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        toolbarSetup();
        initializeView();
        hideBottomView();
        setDatePickerDialog();
        loadSpinnerData();




    }


    private void loadSpinnerData() {
        Spinner spinner = (Spinner) findViewById(R.id.minimum_unit_spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        String text = "10 PCS";
        ArrayList<String> typeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            typeList.add(text);
        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
        ;

        minimumUnit = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setDatePickerDialog() {

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void hideBottomView() {
        btnSale.setVisibility(View.INVISIBLE);
        salesList.setVisibility(View.INVISIBLE);
        etDate.setInputType(InputType.TYPE_NULL);

    }

    private void initializeView() {

        salesList = (RecyclerView) findViewById(R.id.sale_list);
        etDate = (EditText) findViewById(R.id.et_date);
        etReference = (EditText) findViewById(R.id.et_reference);
        etCustomerCode = (EditText) findViewById(R.id.et_customer_code);
        etItemCode = (EditText) findViewById(R.id.et_item_code);
        etBalance = (EditText) findViewById(R.id.et_balance);
        etQuantity = (EditText) findViewById(R.id.et_quantity);
        btnSale = (Button) findViewById(R.id.btnSale);
        txtItemName = (TextView) findViewById(R.id.txt_item_name);


    }

    private void toolbarSetup() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }



    public void itemScanOnClick(View view) {
        IntentIntegrator scanIntentIntegrator = new IntentIntegrator(this);
        scanIntentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode==RESULT_OK){
                customerId = data.getStringExtra("id");
                etCustomerCode.setText(customerId);

            }

        }
        if(requestCode ==2){
            if(resultCode==RESULT_OK){
                itemId = data.getStringExtra("item_id");
                etItemCode.setText(itemId);
                itemName = data.getStringExtra("item_name");
                txtItemName.setText(itemName);
            }

        }

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            etItemCode.setText(scanContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void btnAddCustomer(View view) {
        Intent intent = new Intent(SalesActivity.this, CustomerActivity.class);
        startActivityForResult(intent, 1);
    }

    public void btnDateOnClick(View view) {
        datePickerDialog.show();
    }

    public void btnAddItemOnClick(View view) {


        String reference = etReference.getText().toString();
        String date = etDate.getText().toString();
        String customerCode = etCustomerCode.getText().toString();
        String itemCode = etItemCode.getText().toString();
        String balance = etBalance.getText().toString();
        String quantity = etQuantity.getText().toString();

        if (!reference.isEmpty() && !date.isEmpty() && !customerCode.isEmpty() && !itemCode.isEmpty() && !balance.isEmpty() && !quantity.isEmpty()) {


            Item item = new Item(date, reference, customerCode, itemCode, minimumUnit, balance, quantity);
            productList.add(item);

            setSaleList(productList);


        } else {
            Toast.makeText(this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
        }

    }

    private void setSaleList(ArrayList<Item> productList) {

        salesList.setVisibility(View.VISIBLE);
        btnSale.setVisibility(View.VISIBLE);

        adapter = new ItemAdapter(getApplicationContext(), productList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        salesList.setLayoutManager(layoutManager);
        salesList.setAdapter(adapter);


    }


    public void btnAddSingleItem(View view) {

        Intent intent = new Intent(SalesActivity.this, ItemActivity.class);
        startActivityForResult(intent, 2);
    }
}
