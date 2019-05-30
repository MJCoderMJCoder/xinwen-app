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
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.User;
import com.example.util.HttpUtils;
import com.example.util.Url;

import org.json.JSONException;
import org.json.JSONObject;

public class User_login extends Activity {
    private TextView regist;
    private EditText username;
    private EditText pass;
    private Button login;
    private Button tongbu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_login);
        login = (Button) findViewById(R.id.loginbtn);
        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (username.getText().equals("") || pass.getText().equals("")) {
                    Toast.makeText(User_login.this, "Can not null！", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });
        pass = (EditText) findViewById(R.id.pass);
        username = (EditText) findViewById(R.id.username);
        regist = (TextView) findViewById(R.id.regist);
        regist.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(new Intent(User_login.this, User_regist.class));
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
    }

    /**
     * 注册
     *
     * @author Administrator
     */
    public class userlogin extends AsyncTask<String, Void, String> {

        ProgressDialog p = new ProgressDialog(User_login.this,
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
            result = HttpUtils.doGet(Url.url() + "user/ulogin/" + arg0[0] + "-" + arg0[1]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            p.dismiss();
            if (result.equals("1"))
                Toast.makeText(User_login.this, "Login Failed!", Toast.LENGTH_SHORT).show();
            else {
                try {
                    JSONObject j = new JSONObject(result);
                    User u = new User();
                    u.setPass(j.getString("pass"));
                    u.setUsername(j.getString("username"));
                    u.setId(j.getString("id"));
                    Intent i = new Intent(User_login.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("u", u);
                    i.putExtras(b);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
