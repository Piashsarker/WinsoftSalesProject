package com.example.administrator.winsoftsalesproject.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.activity.CustomerActivity;
import com.example.administrator.winsoftsalesproject.activity.SalesListActivity;
import com.example.administrator.winsoftsalesproject.activity.LoginActivity;
import com.example.administrator.winsoftsalesproject.activity.SalesActivity;
import com.example.administrator.winsoftsalesproject.session.SessionManger;

import java.util.HashMap;

/**
 * Created by pt on 11/15/16.
 */

public class Home extends Fragment {
    private Intent intent;
    private TextView userName , groupName;
    private Button saleList , saleProduct,customerList,logout, exits;
    private   String usrName,employeeId , employeePhoto;
    private SessionManger sessionManager  ;
    private HashMap<String , String> user ;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        sessionManager = new SessionManger(getContext());
        groupName = (TextView) rootView.findViewById(R.id.txtGroupName);
        groupName.setText(sessionManager.getGroupOfCompany());
        saleList = (Button) rootView.findViewById(R.id.btn_sale_list);
        saleProduct = (Button) rootView.findViewById(R.id.btn_sale_product);
        logout = (Button) rootView.findViewById(R.id.btnLogout);
        exits = (Button)rootView.findViewById(R.id.btnExits);
        customerList = (Button) rootView.findViewById(R.id.btn_customer_list);


        sessionManager.checkLogin();
        user = sessionManager.getUserDetails();




        saleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), SalesListActivity.class);
                startActivity(intent);

            }
        });

        saleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), SalesActivity.class);
                startActivity(intent);
            }
        });
        customerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), CustomerActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutDialog();

            }
        });


        exits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exitDialog();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }







    public void makeToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void exitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do You Want To Exit ?")
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
                sessionManager.logoutUser();
                getActivity().finish();
                startActivity(homeIntent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void logOutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do You Want To Logout ? ")
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
                sessionManager.logoutUser();
                intent = new Intent(getActivity(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
