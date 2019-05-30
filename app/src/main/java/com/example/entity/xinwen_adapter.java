package com.example.entity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynews.LoginActivity;
import com.example.mynews.R;
import com.example.mynews.talklist;
import com.example.util.ImageLoader;
import com.example.util.Url;

public class xinwen_adapter extends BaseAdapter {
	private List<xinwen> l;
	private Context c;
	User k;

	public xinwen_adapter(List<xinwen> list,Context con,User u){
		this.c=con;
		this.l=list;
		this.k=u;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(l==null)
		return 0;
		else
			return l.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return l.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		v=LayoutInflater.from(c).inflate(R.layout.xinwen_item, null);
		TextView title=(TextView)v.findViewById(R.id.title);
		TextView msg=(TextView)v.findViewById(R.id.msg);
		TextView time=(TextView)v.findViewById(R.id.time);
		TextView pl=(TextView)v.findViewById(R.id.pl);
		title.setText(l.get(arg0).getTitle());
		msg.setText(l.get(arg0).getMsg());
		time.setText(l.get(arg0).getTime());
		pl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View args) {
				// TODO Auto-generated method stub
				if(k==null){
					c.startActivity(new Intent(c,LoginActivity.class));
				}else{
					Intent i=new Intent(c,talklist.class);
					Bundle b=new Bundle();
					b.putSerializable("u",k);
					b.putString("id",String.valueOf( l.get(arg0).getId()));
					i.putExtras(b);
					c.startActivity(i);
				}
					
			}
		});
		ImageView img=(ImageView)v.findViewById(R.id.img);
		ImageLoader imageLoader=new ImageLoader(c);
		imageLoader.DisplayImage(Url.url()+"/upload/"+l.get(arg0).getImg(), img);
		return v;
	}

}
