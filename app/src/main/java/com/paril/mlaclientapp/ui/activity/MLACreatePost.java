package com.paril.mlaclientapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.util.CommonUtils;

/**
 * Created by Vaidehi Shah on 04-24-2020.
 */

public class MLACreatePost extends BaseActivity
{
    EditText p1;
    FloatingActionButton myFab;
    String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mla_create_post);

        p1 = (EditText)findViewById(R.id.post_message);
        myFab = (FloatingActionButton) findViewById(R.id.floatingSendPost);

        //intentService();

       // message = p1.getText().toString();
        // Toast.makeText(MLAAddPost.this, "POST MESSAGE IS....", Toast.LENGTH_LONG).show();
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"do someting here  ",Toast.LENGTH_LONG).show();
                message = p1.getText().toString();

                final Intent intent = new Intent(MLACreatePost.this, MLAAddPost.class);
                intent.putExtra("Post_message",message);
                startActivity(intent);
                finish();

            }
        });

    }


}
