package com.example.administrator.winsoftsalesproject;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by pt on 11/19/16.
 */

public class ErrorDialog {
    private Context context ;

    public ErrorDialog(Context context ){
        this.context = context;
    }

    public void showDialog( String title , String message ){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setIcon(R.drawable.warning);
        builder.setMessage(message);


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog alertDialog = builder.create();


        alertDialog.show();

    }
}
