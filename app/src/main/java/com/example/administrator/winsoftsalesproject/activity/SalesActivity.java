package com.example.administrator.winsoftsalesproject.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.winsoftsalesproject.ErrorDialog;
import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.adapter.ItemAdapter;
import com.example.administrator.winsoftsalesproject.list.BranchList;
import com.example.administrator.winsoftsalesproject.list.CompanyList;
import com.example.administrator.winsoftsalesproject.list.MeasureMentUnitList;
import com.example.administrator.winsoftsalesproject.model.Item;
import com.example.administrator.winsoftsalesproject.model.MeasurementUnitView;
import com.example.administrator.winsoftsalesproject.model.PermittedBranchView;
import com.example.administrator.winsoftsalesproject.model.PermittedCompanyView;
import com.example.administrator.winsoftsalesproject.model.PostingResponse;
import com.example.administrator.winsoftsalesproject.retrofit.ApiService;
import com.example.administrator.winsoftsalesproject.retrofit.RetroClient;
import com.example.administrator.winsoftsalesproject.session.SessionManger;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnSale;
    private ArrayList<Item> productList = new ArrayList<>();
    private RecyclerView salesList;
    private EditText etDate, etReference, etCustomerCode, etItemCode, etBalance, etQuantity;
    private TextView  txtItemName, txtOr;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    private String minimumUnit , itemId , companyName, companyId, branchName, branchId;
    private ItemAdapter adapter;
    private String customerId,  itemName;
    private Intent intent ;
    private SessionManger sessionManger;
    private HashMap<String, String> user;
    private Spinner companySpinner , branchSpinner;
    private TextInputLayout inputItemLayout;
    private ArrayList<MeasurementUnitView> list;
    private ArrayList<PermittedCompanyView> companyList;
    private ArrayList<PermittedBranchView> branchList;
    private ErrorDialog errorDialog;
    private  ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        sessionManger = new SessionManger(SalesActivity.this);
        user = sessionManger.getUserDetails();
        errorDialog = new ErrorDialog(this);
        dialog  = new ProgressDialog(this) ;
        toolbarSetup();
        initializeView();
        hideBottomView();
        setDatePickerDialog();
        loadCompanyData();




    }

    private void showProgressBar(){

        dialog.setTitle("Wait");
        dialog.setMessage("Data Loading......");
        dialog.show();
    }
    private void hideProgressBar(){
        dialog.dismiss();
    }

    private void loadCompanyData() {
        showProgressBar();
        companyList = new ArrayList<PermittedCompanyView>();
        if(isNetworkConnected()){

            ApiService apiService = RetroClient.getApiService();

            Call<CompanyList> call = apiService.getCompanyList(user.get(sessionManger.KEY_KEY),"GETPCOMPANY",user.get(sessionManger.KEY_USER_GROUP_ID),user.get(sessionManger.KEY_EMPLOYEE_ID),user.get(sessionManger.KEY_BRANCH_ID));


            call.enqueue(new Callback<CompanyList>() {

                @Override
                public void onResponse(Call<CompanyList> call, Response<CompanyList> response) {
                    hideProgressBar();
                    if(response.isSuccess()){
                        companyList = response.body().getPermittedCompanyView();
                        loadCompanySpinner(companyList);

                    }

                }

                @Override
                public void onFailure(Call<CompanyList> call, Throwable t) {

                    Toast.makeText(SalesActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

        }

        else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadCompanySpinner(ArrayList<PermittedCompanyView> companyList) {
        companySpinner.setOnItemSelectedListener(this);

        ArrayList<String> typeList = new ArrayList<>();
        for (int i = 0; i < companyList.size(); i++) {
            String name = companyList.get(i).getCompanyName();
            typeList.add(name);
        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        companySpinner.setAdapter(dataAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.spinner_company){
            companyName = adapterView.getItemAtPosition(i).toString();
            companyId =  companyList.get(i).getCompanyId().toString();

            Toast.makeText(this, companyId, Toast.LENGTH_SHORT).show();
            loadBranchSpinnerData(companyId);
        }
        if(adapterView.getId()==R.id.minimum_unit_spinner){
            minimumUnit = adapterView.getItemAtPosition(i).toString();
        }
        if(adapterView.getId()==R.id.spinner_branch){
            branchId = branchList.get(i).getBranchId().toString();
        }

    }

    private void loadBranchSpinnerData(String companyId) {

        branchList = new ArrayList<PermittedBranchView>();
        if(isNetworkConnected()){
            showProgressBar();
            ApiService apiService = RetroClient.getApiService();

            Call<BranchList> call = apiService.getBranchlist(user.get(sessionManger.KEY_KEY),"GETPBRANCH",user.get(sessionManger.KEY_USER_GROUP_ID),user.get(sessionManger.KEY_EMPLOYEE_ID),user.get(sessionManger.KEY_BRANCH_ID),companyId);


            call.enqueue(new Callback<BranchList>() {

                @Override
                public void onResponse(Call<BranchList> call, Response<BranchList> response) {
                    hideProgressBar();
                    if(response.isSuccess()){
                        branchList = response.body().getPermittedBranchView();
                        loadBranchSpinner(branchList);

                    }

                }

                @Override
                public void onFailure(Call<BranchList> call, Throwable t) {

                    Toast.makeText(SalesActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    hideProgressBar();

                }
            });

        }

        else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadBranchSpinner(ArrayList<PermittedBranchView> branchList) {

        branchSpinner.setOnItemSelectedListener(this);

        ArrayList<String> typeList = new ArrayList<>();
        for (int i = 0; i < branchList.size(); i++) {
            String name = branchList.get(i).getBranchName();
            typeList.add(name);
        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        branchSpinner.setAdapter(dataAdapter);


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
        inputItemLayout = (TextInputLayout) findViewById(R.id.input_layout_item_code);
        etItemCode.addTextChangedListener(new MyTextWatcher(etItemCode));

        etBalance = (EditText) findViewById(R.id.et_balance);
        etQuantity = (EditText) findViewById(R.id.et_quantity);
        btnSale = (Button) findViewById(R.id.btnSale);
        txtItemName = (TextView) findViewById(R.id.txt_item_name);
        txtOr = (TextView) findViewById(R.id.txt_or);
        companySpinner = (Spinner) findViewById(R.id.spinner_company);
        branchSpinner = (Spinner) findViewById(R.id.spinner_branch);



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
                txtItemName.setVisibility(View.VISIBLE);
                txtOr.setVisibility(View.GONE);
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
        String itemName = txtItemName.getText().toString();



        if (!reference.isEmpty() && !date.isEmpty() && !customerCode.isEmpty() && !itemCode.isEmpty() && !balance.isEmpty() && !quantity.isEmpty()) {

            int balanceValue = Integer.parseInt(balance);
            int quantityValue = Integer.parseInt(quantity);

            if (balanceValue>= quantityValue){
                Item item = new Item(date, reference,branchId, customerCode, itemCode, itemName,  minimumUnit, balance, quantity);
                productList.add(item);
                setSaleList(productList);

                etItemCode.setText("");
                txtItemName.setText("");
                etBalance.setText("");
                etQuantity.setText("");



            }
            else {
                Toast.makeText(this, "Not Enough Balance", Toast.LENGTH_SHORT).show();
            }



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


    public void saleOnClick(View view) {

        String saleId = "0";
        sentFirstSaleInfo(saleId , productList);




    }

    private void sentFirstSaleInfo(String saleId, final ArrayList<Item> productList) {
        if(isNetworkConnected()){
            showProgressBar();

            ApiService apiService = RetroClient.getApiService();

            Call<PostingResponse> call = apiService.postSelesItem(user.get(sessionManger.KEY_KEY),"POSTSALESINFO",saleId,productList.get(0).getDate(),productList.get(0).getReference(),productList.get(0).getBranchId(),productList.get(0).getCustomerCode(),productList.get(0).getItemCode(),productList.get(0).getMinimumUnit(),productList.get(0).getQuantity(),user.get(sessionManger.KEY_EMPLOYEE_ID));

            call.enqueue(new Callback<PostingResponse>() {
                @Override
                public void onResponse(Call<PostingResponse> call, Response<PostingResponse> response) {
                    hideProgressBar();
                    if(response.isSuccess()){
                        PostingResponse postingResponse = response.body();
                        sentAllSalesInfoWithResponseId(postingResponse.getResponse() , productList);
                        errorDialog.showDialog("Success!","Data Sent Successfully!");
                    }

                }

                @Override
                public void onFailure(Call<PostingResponse> call, Throwable t) {
                    hideProgressBar();
                    errorDialog.showDialog("Error","Server Error!");
                }
            });
        }
        else {
            errorDialog.showDialog("No Internet!","Please Enable WiFi or Mobile Data.");
        }
    }

    private void sentAllSalesInfoWithResponseId(String response, ArrayList<Item> productList) {
        if(isNetworkConnected()){

            for(int i=1 ; i<productList.size(); i++){
                ApiService apiService = RetroClient.getApiService();

                Call<PostingResponse> call = apiService.postSelesItem(user.get(sessionManger.KEY_KEY),"POSTSALESINFO",response,productList.get(i).getDate(),productList.get(i).getReference(),productList.get(i).getBranchId(),productList.get(i).getCustomerCode(),productList.get(i).getItemCode(),productList.get(i).getMinimumUnit(),productList.get(i).getQuantity(),user.get(sessionManger.KEY_EMPLOYEE_ID));

                call.enqueue(new Callback<PostingResponse>() {
                    @Override
                    public void onResponse(Call<PostingResponse> call, Response<PostingResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<PostingResponse> call, Throwable t) {

                        errorDialog.showDialog("Error","Server Error!");
                    }
                });
            }

            this.productList.clear();
            salesList.setVisibility(View.INVISIBLE);
            btnSale.setVisibility(View.INVISIBLE);

        }
        else {
            errorDialog.showDialog("No Internet!","Please Enable WiFi or Mobile Data.");
        }
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void setSpinnerData(ArrayList<MeasurementUnitView> spinnerData) {


        Spinner spinner = (Spinner) findViewById(R.id.minimum_unit_spinner);


        spinner.setOnItemSelectedListener(this);

        ArrayList<String> typeList = new ArrayList<>();
        for (int i = 0; i < spinnerData.size(); i++) {
            String name = spinnerData.get(i).getMeasurementUnit();
            typeList.add(name);
        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    private boolean validateItem() {
        String itemCode = etItemCode.getText().toString();


        getMeasurementUnit(itemCode);

        if (list.isEmpty()) {
            inputItemLayout.setError("Item Code Not Correct");
            requestFocus(etItemCode);
            return false;
        } else if (!list.isEmpty()) {
            inputItemLayout.setErrorEnabled(false);


        }
        return true;
    }

    private void getMeasurementUnit(String itemCode) {

        final ProgressDialog dialog ;
        dialog = new ProgressDialog(this);
        dialog.setTitle("Wait");
        dialog.setMessage("Data Loading.....");
        dialog.show();

        list = new ArrayList<>();
        if (isNetworkConnected()) {

            ApiService apiService = RetroClient.getApiService();

            Call<MeasureMentUnitList> call = apiService.getMeasurementList(user.get(sessionManger.KEY_KEY), "GETMUNIT", itemCode);

            call.enqueue(new Callback<MeasureMentUnitList>() {
                @Override
                public void onResponse(Call<MeasureMentUnitList> call, Response<MeasureMentUnitList> response) {
                    dialog.dismiss();

                    if (response.code() == 200) {

                        list = response.body().getMeasurementUnitView();
                        setSpinnerData(list);
                        if (!list.isEmpty()) {
                            inputItemLayout.setErrorEnabled(false);

                        }

                    }
                }

                @Override
                public void onFailure(Call<MeasureMentUnitList> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(SalesActivity.this, "Sever Error !", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(this, "NO Internet !!", Toast.LENGTH_SHORT).show();


        }


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private class MyTextWatcher implements TextWatcher

    {
        private View view;

        public MyTextWatcher(View etItemCode) {
            this.view = etItemCode;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {

                case R.id.et_item_code:
                    validateItem();
                    break;

            }
        }
    }
}
