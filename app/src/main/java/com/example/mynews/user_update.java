package com.example.mynews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.entity.User;
import com.example.util.HttpUtils;
import com.example.util.Url;

import java.net.URLEncoder;

public class user_update extends Activity {
    private LinearLayout back;
    private EditText pass;
    private EditText queding;
    private Button login;
    private EditText nickname;
    User us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_update);
        us = (User) getIntent().getExtras().getSerializable("u");
        pass = (EditText) findViewById(R.id.pass);
        nickname = (EditText) findViewById(R.id.nickname);
        nickname.setText(us.getNickname());
        queding = (EditText) findViewById(R.id.queding);
        login = (Button) findViewById(R.id.tijiao);
        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (nickname.getText().equals("") || pass.getText().equals("") || queding.getText().equals("")) {
                    Toast.makeText(user_update.this, "信息不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.getText().toString().equals(queding.getText().toString())) {
                        userup u = new userup();
                        u.execute(us.getId(), pass.getText().toString(), nickname.getText().toString());
                    } else {
                        Toast.makeText(user_update.this, "两次密码输入不一致！！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
    }

    /**
     * @author Administrator
     */
    public class userup extends AsyncTask<String, Void, String> {

        ProgressDialog p = new ProgressDialog(user_update.this,
                ProgressDialog.STYLE_SPINNER);

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            p.setMessage("Loading....");
            p.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String result = null;
            result = HttpUtils.doGet(Url.url() + "user/updateU/" + arg0[0] + "-" + arg0[1] + "-" + URLEncoder.encode(arg0[2]));
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            p.dismiss();
            if (result.equals("success")) {
                Toast.makeText(user_update.this, "成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(user_update.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(user_update.this, "失败", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
