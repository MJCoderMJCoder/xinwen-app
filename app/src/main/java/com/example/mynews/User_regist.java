package com.example.mynews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.util.HttpUtils;
import com.example.util.Url;

public class User_regist extends Activity {
    private EditText username;
    private EditText pass;
    private EditText queding;
    private Button login;
    private EditText nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_regist);
        pass = (EditText) findViewById(R.id.pass);
        username = (EditText) findViewById(R.id.username);
        nickname = (EditText) findViewById(R.id.nickname);
        queding = (EditText) findViewById(R.id.queding);
        login = (Button) findViewById(R.id.tijiao);
        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (username.getText().equals("") || nickname.getText().equals("") || pass.getText().equals("") || queding.getText().equals("")) {
                    Toast.makeText(User_regist.this, "信息不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.getText().toString().equals(queding.getText().toString())) {
                        useradd u = new useradd();
                        u.execute(username.getText().toString(), pass.getText().toString(), nickname.getText().toString());
                    } else {
                        Toast.makeText(User_regist.this, "两次密码输入不一致！！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    /**
     * 注册
     *
     * @author Administrator
     */
    public class useradd extends AsyncTask<String, Void, String> {

        ProgressDialog p = new ProgressDialog(User_regist.this,
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
            result = HttpUtils.doGet(Url.url() + "user/addUser/" + arg0[0] + "-" + arg0[1] + "-" + arg0[2]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            p.dismiss();
            if (result.equals("success"))
                Toast.makeText(User_regist.this, "成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(User_regist.this, "失败", Toast.LENGTH_SHORT).show();
        }

    }
}
