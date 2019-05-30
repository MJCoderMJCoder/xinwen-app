package com.example.mynews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.entity.User;
import com.example.util.HttpUtils;
import com.example.util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class talklist extends Activity {
    private ListView listView;
    private EditText talk;
    private Button fasong;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.talklist);
        u = (User) getIntent().getExtras().getSerializable("u");
        listView = (ListView) findViewById(R.id.listView);
        talk = (EditText) findViewById(R.id.talk);
        fasong = (Button) findViewById(R.id.fasong);
        fasong.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                fasong f = new fasong();
                try {
                    f.execute(getIntent().getExtras().getString("id"), URLEncoder.encode(talk.getText().toString(), "utf-8"), URLEncoder.encode(u.getUsername(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        getall g = new getall();
        g.execute(getIntent().getExtras().getString("id"));
    }

    public class fasong extends AsyncTask<String, Void, String> {

        ProgressDialog p = new ProgressDialog(talklist.this,
                ProgressDialog.STYLE_SPINNER);

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            p.setMessage("正在上传....");
            p.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String result = null;
            result = HttpUtils.doGet(Url.url() + "talk/addtalk/" + arg0[0] + "-" + arg0[1] + "-" + arg0[2]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            p.dismiss();
            talk.setText("");
            JSONArray json;
            try {
                json = new JSONArray(result);

                List<talk> list = new ArrayList<talk>();
                for (int i = 0; i < json.length(); i++) {
                    JSONObject j = json.getJSONObject(i);
                    talk t = new talk();
                    t.setId(j.getString("id"));
                    t.setMsg(j.getString("msg"));
                    t.setXid(j.getString("xid"));
                    t.setMname(j.getString("uname"));
                    list.add(t);
                }
                listView.setAdapter(new talk_adapter(talklist.this, list));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    public class getall extends AsyncTask<String, Void, String> {

        ProgressDialog p = new ProgressDialog(talklist.this,
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
            result = HttpUtils.doGet(Url.url() + "talk/findalltalk/" + arg0[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            p.dismiss();
            if (!result.equals("1")) {
                JSONArray json;
                try {
                    json = new JSONArray(result);

                    List<talk> list = new ArrayList<talk>();
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject j = json.getJSONObject(i);
                        talk t = new talk();
                        t.setId(j.getString("id"));
                        t.setMsg(j.getString("msg"));
                        t.setXid(j.getString("xid"));
                        t.setMname(j.getString("uname"));
                        list.add(t);
                    }
                    listView.setAdapter(new talk_adapter(talklist.this, list));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    public class talk_adapter extends BaseAdapter {

        private Context context;
        private List<talk> list;

        public talk_adapter(Context c, List<talk> l) {
            this.list = l;
            this.context = c;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (list != null) {
                return list.size();
            } else
                return 0;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(final int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub
            arg1 = LayoutInflater.from(context).inflate(R.layout.talk_item, null);
            TextView msg = (TextView) arg1.findViewById(R.id.msg);
            TextView uname = (TextView) arg1.findViewById(R.id.uname);
            msg.setText(list.get(arg0).getMsg());
            uname.setText(list.get(arg0).getMname() + "：  ");
            return arg1;
        }

    }
}
