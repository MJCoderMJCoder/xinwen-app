package com.example.mynews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.User;
import com.example.entity.xinwen;
import com.example.entity.xinwen_adapter;
import com.example.util.HttpUtils;
import com.example.util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private Context context;
    private List<Navigation> navs;
    private NavigationHorizontalScrollView mHorizontalScrollView;
    private TextView sz;
    private ListView listView;
    List<String> l;
    User u = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sz = (TextView) findViewById(R.id.sz);
        sz.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (u == null)
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                else {
                    Intent i = new Intent(MainActivity.this, user_update.class);
                    i.putExtras(getIntent().getExtras());
                    startActivity(i);
                }
            }
        });
        u = (User) getIntent().getExtras().getSerializable("u");
        getAllm g = new getAllm();
        g.execute();
        listView = (ListView) findViewById(R.id.listView);
    }

    private List<Navigation> buildNavigation(List<String> l) {
        List<Navigation> navigations = new ArrayList<Navigation>();
        for (int i = 0; i < l.size(); i++) {
            navigations.add(new Navigation(0, "url", l.get(i)));
        }
        return navigations;
    }

    class NavigationAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return navs.size();
        }

        @Override
        public Object getItem(int position) {
            return navs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.navigation_item, null);
            }
            ((TextView) convertView).setText(navs.get(position).getTitle());
            return convertView;
        }

    }

    /**
     * 获取所有的模块内容
     *
     * @author Administrator
     */
    public class getAllm extends AsyncTask<String, Void, String> {

        ProgressDialog p = new ProgressDialog(MainActivity.this,
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
            result = HttpUtils.doGet(Url.url() + "mokuai/findAlls/");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            p.dismiss();
            if (result.equals("1"))
                Toast.makeText(MainActivity.this, "内容为空", Toast.LENGTH_SHORT)
                        .show();
            else {
                try {
                    JSONArray json = new JSONArray(result);
                    l = new ArrayList<String>();
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject j = json.getJSONObject(i);
                        l.add(j.getString("title"));
                    }
                    navs = buildNavigation(l);
                    context = MainActivity.this;
                    mHorizontalScrollView = (NavigationHorizontalScrollView) findViewById(R.id.horizontal_scrollview);
                    mHorizontalScrollView.setImageView(
                            (ImageView) findViewById(R.id.iv_pre),
                            (ImageView) findViewById(R.id.iv_next));

                    mHorizontalScrollView
                            .setOnItemClickListener(new NavigationHorizontalScrollView.OnItemClickListener() {

                                @Override
                                public void click(int position) {
                                    // TODO Auto-generated method stub
                                    getAllx g = new getAllx();
                                    g.execute(l.get(position));
                                }
                            });

                    mHorizontalScrollView.setAdapter(new NavigationAdapter());
                    getAllx g = new getAllx();
                    g.execute(l.get(0));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 获取所有xinwen
     *
     * @author Administrator
     */
    public class getAllx extends AsyncTask<String, Void, String> {

        ProgressDialog p = new ProgressDialog(MainActivity.this,
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
            result = HttpUtils.doGet(Url.url() + "ssss/findallxinwenBym/"
                    + URLEncoder.encode(arg0[0]));
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            p.dismiss();
            if (result.equals("1")) {
                Toast.makeText(MainActivity.this, "内容为空", Toast.LENGTH_SHORT)
                        .show();
                listView.setAdapter(new xinwen_adapter(null,
                        MainActivity.this, null));
            } else {
                try {
                    JSONArray json = new JSONArray(result);
                    List<xinwen> l = new ArrayList<xinwen>();
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject j = json.getJSONObject(i);
                        xinwen w = new xinwen();
                        w.setId(j.getString("id"));
                        w.setImg(j.getString("img"));
                        w.setMname(j.getString("mname"));
                        w.setMsg(j.getString("msg"));
                        w.setTime(j.getString("time"));
                        w.setTitle(j.getString("title"));
                        l.add(w);
                    }
                    listView.setAdapter(new xinwen_adapter(l,
                            MainActivity.this, u));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
