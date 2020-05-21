package com.paril.mlaclientapp.ui.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.MLARegisterUsers;
import com.paril.mlaclientapp.util.CommonUtils;
import com.paril.mlaclientapp.util.KeyManager;
import com.paril.mlaclientapp.util.PrefsManager;
import com.paril.mlaclientapp.webservice.Api;

import java.io.IOException;
import java.security.KeyStore;
import java.security.PublicKey;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class MLALoginActivity extends BaseActivity
{

    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    MLARegisterUsers register;
    private ProgressDialog progressDialog;
    private PrefsManager prefsManager;
    String alias;
    byte [] grpKey;
    byte [] privateGrpKey;
    PublicKey pk;
    byte [] encryptgk;
    String  defGrpKey;
    KeyManager keys;


    public void showProgressDialog(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = ProgressDialog.show(this, getString(R.string.app_name), message, true, false);

        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();

        }
    }

    void loadingUserInformation() {
        register.userId = prefsManager.getStringData("userId");
        register.userName = prefsManager.getStringData("userName");
        register.userType = prefsManager.getStringData("userType");
        register.publicKey = prefsManager.getStringData("publicKey");


    }

    public void showSnackBar(String message, View view) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    void savingUserInformation()
    {
        prefsManager.saveData("userId", register.userId);
        prefsManager.saveData("userName", register.userName);
        prefsManager.saveData("userType", register.userType);
        prefsManager.saveData("publicKey", register.publicKey);
    }
    KeyStore keyStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Sign In");
        prefsManager=new PrefsManager(this);
        register = new MLARegisterUsers();


        loadingUserInformation();
        if (register.userType != "" && register.userType != null) {
            Intent mlaActivity = new Intent();
            mlaActivity.setClass(MLALoginActivity.this, MLAHomeActivity.class);
            mlaActivity.putExtra("userId", register.userId);
            mlaActivity.putExtra("userName", register.userName);
            mlaActivity.putExtra("userType", register.userType);
            mlaActivity.putExtra("publicKey", register.publicKey);
            mlaActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mlaActivity);
            finish();
        }

        btnLogin = (Button) findViewById(R.id.mla_login_btnLogin);
        txtUserName = (EditText) findViewById(R.id.mla_login_txtUserName);
        txtPassword = (EditText) findViewById(R.id.mla_login_txtPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                if (TextUtils.isEmpty(txtPassword.getText().toString()) || TextUtils.isEmpty(txtUserName.getText().toString())) {

                    showSnackBar(getString(R.string.enter_all_fields), findViewById(R.id.activity_main_coordinatorLayout));

                } else {
                    if (CommonUtils.checkInternetConnection(MLALoginActivity.this)) {
                        MLALoginAPI authentication = new MLALoginAPI(getApplicationContext());
                        authentication.execute(txtUserName.getText().toString(), txtPassword.getText().toString());
                    } else {
                        showSnackBar(getString(R.string.check_connection), findViewById(R.id.activity_main_coordinatorLayout));
                    }
                }

            }
        });

    }


    class MLALoginAPI extends AsyncTask<String, Void, MLARegisterUsers> {
        Context appContext;
        String PrkGrpKey;

        public MLALoginAPI (Context context) {
            appContext = context;
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog("Verifying Credentials...");
        }

        @Override
        protected void onPostExecute(MLARegisterUsers registerArg) {
            hideProgressDialog();
            register = registerArg;
            if (register.userType != null) {
                Intent mlaActivity = new Intent();
                mlaActivity.setClass(MLALoginActivity.this, MLAHomeActivity.class);
                mlaActivity.putExtra("userId", register.userId);
                mlaActivity.putExtra("userName", register.userName);
                mlaActivity.putExtra("userType", register.userType);
                savingUserInformation();
                mlaActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mlaActivity);
                finish();
            } else {
                showSnackBar("User Name/Password is incorrect. Please enter correct credentials.", findViewById(R.id.activity_main_coordinatorLayout));
            }
        }

        @Override
        protected MLARegisterUsers doInBackground(String... params) {
            MLARegisterUsers register = new MLARegisterUsers();
            Call<List<MLARegisterUsers>> callAuth = Api.getClient().authenticate(params[0], params[1]);
            try {
                Response<List<MLARegisterUsers>> respAuth = callAuth.execute();
                if (respAuth != null && respAuth.isSuccessful() & respAuth.body() != null && respAuth.body().size() > 0)
                {
                    register = respAuth.body().get(0);

                    if (register.publicKey == null)
                    {
                        keys = new KeyManager(1024);
                        grpKey = keys.newGrpKeyGenerate();

                        try
                        {
                            KeyManager.Keys keyPair = KeyManager.getKeysFromKeyStore(register.userId,getApplicationContext());
                            pk = keyPair.getPublicKey();
                            byte[] encryptKeyByte = keys.encrypt_with_PubKey(grpKey,pk);
                            defGrpKey = keys.bTos(keys.encrypt_with_PubKey(grpKey,keyPair.getPublicKey()));
                            String pubKey = keys.bTos(keyPair.getPublicKey().getEncoded());
                            register.setPublicKey(pubKey);
                            privateGrpKey = keys.newGrpKeyGenerate();
                            byte[] encryptKey1 = keys.encrypt_with_PubKey(privateGrpKey,pk);
                            PrkGrpKey = keys.bTos(encryptKey1);

                            Call<String> setPk = Api.getClient().addPk(Integer.parseInt(register.userId),defGrpKey,pubKey,PrkGrpKey);
                            Response<String> respAuth1 = setPk.execute();
                            System.out.println(respAuth1);

                        }
                        catch (Exception e){
                            System.out.println(e);

                        }
                    }



                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return register;
        }

    }


}
