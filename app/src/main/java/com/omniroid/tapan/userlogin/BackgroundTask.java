package com.omniroid.tapan.userlogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hp on 12/13/2016.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {

    Context ctx;
    Activity activity;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
        activity = (Activity) ctx;
    }



    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please Wait ..");
        progressDialog.setMessage("Connecting to server...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }



    @Override
    protected String doInBackground(String... params) {

        String method = params[0];



        Log.d("asdasdasdasdasd",method);

        if (method.equals("register")){

            try {

                URL url = new URL(AppConfig.URL_REGISTER);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream,"UTF-8")
                );

                String name = params[1];
                String email = params[2];
                String password = params[3];
                String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream)
                );
                StringBuilder stringBuilder = new StringBuilder();
                String line =  "";
                while ((line=bufferedReader.readLine())!= null){

                    stringBuilder.append(line+"\n");

                }

                httpURLConnection.disconnect();
                Thread.sleep(5000);

                Log.d("asasasasasa",stringBuilder.toString());

                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }else if (method.equals("login")){

            try {
                URL login_url = new URL(AppConfig.URL_LOGIN);
                HttpURLConnection httpURLConnection = (HttpURLConnection) login_url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream,"UTF-8")
                );

                String email,password;
                email = params[1];
                password = params[2];

                String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream)
                );
                StringBuilder stringBuilder = new StringBuilder();
                String line =  "";
                while ((line=bufferedReader.readLine())!= null){

                    stringBuilder.append(line+"\n");

                }

                httpURLConnection.disconnect();
                Thread.sleep(5000);

                Log.d("asasasasasa",stringBuilder.toString());

                return stringBuilder.toString().trim();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


        return null;
    }




    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }




    @Override
    protected void onPostExecute(String json) {

        //json = "";

        try {

            if ((progressDialog != null) && progressDialog.isShowing()) {
                progressDialog.dismiss();
        }

            JSONObject jsonObject = new JSONObject(json) ;
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            Log.i("json array responce",jsonArray.toString());
            JSONObject jo = jsonArray.getJSONObject(0);
            String code = jo.getString("code");
            Toast.makeText(ctx,code,Toast.LENGTH_SHORT).show();
            String message = jo.getString("message");

            if (code.equals("reg_true")){
                showDialog("Registration Success",message,code);
            }else if (code.equals("reg_false")){
                showDialog("Registration Failed",message,code);
            }else if (code.equals("login_true")){
                Intent intent = new Intent(activity,HomeActivity.class);
                intent.putExtra("message",message);
                activity.startActivity(intent);
            }else if (code.equals("login_false")){
                showDialog("Login error",message,code);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void showDialog(String title,String message,String code){


        builder.setTitle(title);
        if (code.equals("reg_true") || code.equals("reg_false")){
            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    activity.finish();
                }
            });

        }else if (code.equals("login_false")){


            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText email = (EditText) activity.findViewById(R.id.et_email);
                    EditText password = (EditText) activity.findViewById(R.id.et_pass);
                    email.setText("");
                    password.setText("");
                    dialogInterface.dismiss();
                }
            });

        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
