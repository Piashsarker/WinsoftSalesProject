package com.example.administrator.winsoftsalesproject.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.list.LoginList;
import com.example.administrator.winsoftsalesproject.model.LoginTable;
import com.example.administrator.winsoftsalesproject.retrofit.ApiService;
import com.example.administrator.winsoftsalesproject.retrofit.RetroClient;
import com.example.administrator.winsoftsalesproject.session.SessionManger;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    SessionManger sessionManager;
    String userName, password;
    private Intent intent;
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private TextView txtGroupName;
    private ArrayList<LoginTable> loginList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeToolbar();
        initializeViews();
    }

    private void initializeViews() {

        txtGroupName = (TextView) findViewById(R.id.txtGroupName);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
    }

    private void initializeToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);

    }

    public void loginOnClick(View view) {

        if (!validateEmail() && !validatePassword()) {
            return;
        } else {

            userName = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            checkLogin( userName ,  password);

        }


    }

    private void checkLogin(String userName, String password) {

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Loading....");
            progressDialog.show();

            ApiService apiService = RetroClient.getApiService();

            Call<LoginList> call = apiService.getLoginDetails("fr_pt_2016", userName, password);

            call.enqueue(new Callback<LoginList>() {
                @Override
                public void onResponse(Call<LoginList> call, Response<LoginList> response) {
                    progressDialog.dismiss();

                    if (response.isSuccess()) {
                        loginList = response.body().getLoginTable();

                        /**
                         * Binding that List to Adapter
                         */
                        authenticateUser(loginList);


                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginList> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void authenticateUser(ArrayList<LoginTable> loginList) {
        if (!loginList.isEmpty()) {

            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String emi = mngr.getDeviceId();
            if (userName.equalsIgnoreCase(loginList.get(0).getUserName()) && password.equalsIgnoreCase(loginList.get(0).getUserPassword())) {

                if (loginList.get(0).getEmiNumber().equals(emi)) {
                    sessionManager = new SessionManger(getApplicationContext());


                    sessionManager.createLoginSession("fr_pt_2016", loginList.get(0).getEmployeeId().toString(), loginList.get(0).getEmployeeName().toString(), loginList.get(0).getEmployeePhoto().toString()
                            , loginList.get(0).getApprovedBy().toString(), loginList.get(0).getReportingTo().toString(),
                            loginList.get(0).getDepartmentName(), loginList.get(0).getEmployeeCode(), loginList.get(0).getMobileNo(), loginList.get(0).getReportingMobileNo(), loginList.get(0).getLeaveReqMsg(), loginList.get(0).getUserId().toString(), loginList.get(0).getMsgUserName(), loginList.get(0).getMsgUserPass(), loginList.get(0).getMsgBrandName(), loginList.get(0).getLeaveRejMsg(), loginList.get(0).getLeaveAprMsg(),
                            loginList.get(0).getTourReqMsg(), loginList.get(0).getTourAprMsg(), loginList.get(0).getTourRejMsg(), loginList.get(0).getODReqMsg(), loginList.get(0).getODAprMsg(), loginList.get(0).getODRejMsg(), loginList.get(0).getUserGroupId().toString(), loginList.get(0).getBranchId());
                    Intent intent = new Intent(getApplicationContext(), NavHomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Emi Don't Match", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG)
                        .show();
            }


        } else {
            Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty()) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
