package com.example.scanner;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.intelligencetest.R;
import com.example.intelligencetest.library.Library;

public class GridMenu extends Fragment
{
LinearLayout CustomButton1;
LinearLayout CustomButton2;
LinearLayout CustomButton3;
GridView gridView;
GridView gridView2;
    @Override
    public View onCreateView(LayoutInflater inflater,
    	    ViewGroup container, Bundle savedInstanceState) {
    	        View view = inflater.inflate(
    	            R.layout.gridview, container, false);
    	         
        gridView = (GridView)view.findViewById(R.id.gridview);
        gridView2 = (GridView)view.findViewById(R.id.gridview2);
        gridView.setAdapter(new MyAdapter(getActivity()));
        gridView2.setAdapter(new MyAdapter2(getActivity()));
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               // Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                switch(position) {
                case 0: Intent i = new Intent(getActivity(), ScanActivity.class);
        		startActivity(i);
                break;
                case 1: Intent i2 = new Intent(getActivity(), Library.class);
        		startActivity(i2);
                break;
                
                }
            }
        });
        gridView2.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               // Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                switch(position) {
                case 0: Intent a = new Intent(getActivity(), ScanActivity.class);
        		startActivity(a);
                break;
                
                case 1: Intent b = new Intent(getActivity(), ScanActivity.class);
        		startActivity(b);
                break;
                
                case 2: Intent c = new Intent(getActivity(), ScanActivity.class);
        		startActivity(c);
                break;
                
                }
            }
        });
		return view;
    }

    private class MyAdapter extends BaseAdapter {
        private List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;
        
        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            gridView.setNumColumns(2);
            items.add(new Item("Scan", R.drawable.sample_0));
            items.add(new Item("Library", R.drawable.sample_1));   
        }
        
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            ImageView picture;
            TextView name;

            if(v == null) {
               v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
               v.setTag(R.id.picture, v.findViewById(R.id.picture));
               v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView)v.getTag(R.id.picture);
            name = (TextView)v.getTag(R.id.text);

            Item item = (Item)getItem(i);

            picture.setImageResource(item.drawableId);
            name.setText(item.name);
            return v;
        }

        private class Item {
            final String name;
            final int drawableId;

            Item(String name, int drawableId) {
                this.name = name;
                this.drawableId = drawableId;
            }
        }
        
    }
    
    private class MyAdapter2 extends BaseAdapter {
        private List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;
        
        public MyAdapter2(Context context) {
            inflater = LayoutInflater.from(context);
            
            items.add(new Item("Call", R.drawable.sample_2));
            items.add(new Item("Chemical", R.drawable.sample_3));
            items.add(new Item("Swag 16", R.drawable.sample_4));
            
        }
        
      //gridView.setNumColumns(3);
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            ImageView picture;
            TextView name;

            if(v == null) {
               v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
               v.setTag(R.id.picture, v.findViewById(R.id.picture));
               v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView)v.getTag(R.id.picture);
            name = (TextView)v.getTag(R.id.text);

            Item item = (Item)getItem(i);

            picture.setImageResource(item.drawableId);
            name.setText(item.name);
            return v;
        }

        private class Item {
            final String name;
            final int drawableId;

            Item(String name, int drawableId) {
                this.name = name;
                this.drawableId = drawableId;
            }
        }
        
    }
    
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    
        CustomButton1 = (LinearLayout) getActivity().findViewById(R.id.customButton1);
        CustomButton2 = (LinearLayout) getActivity().findViewById(R.id.customButton2);
        CustomButton3 = (LinearLayout) getActivity().findViewById(R.id.customButton3);
    
        /**CustomButtons*/
        CustomButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	System.out.println("YOLOSWAG1");
                Intent i = new Intent(getActivity(), ScanActivity.class);
        		startActivity(i);
            }
        });
        CustomButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	System.out.println("YOLOSWAG2");
            	Intent i = new Intent(getActivity(), ScanActivity.class);
        		startActivity(i);
            }
        });
        CustomButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	System.out.println("YOLOSWAG3");
            }
        });
        
    }  
    
    
}
