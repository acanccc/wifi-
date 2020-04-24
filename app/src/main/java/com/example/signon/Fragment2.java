package com.example.signon;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    private List<qunzhu> qunzhuList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getinfo=getView().findViewById(R.id.getWiFiinfo);
//        setinfo=getView().findViewById(R.id.setWiFiinfo);
//        getinfo.setOnClickListener(this);c
        Toolbar toolbar1=getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar1);
        initqunzhus();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        qunzhuAdapter adapter = new qunzhuAdapter(qunzhuList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//加上这句话，menu才会显示出来

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear();//这句话没用，不必加
        inflater.inflate(R.menu.toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    // 设置监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                jiaruqunzhu.actionStart(getActivity());
                break;
            case R.id.create:
                xinjianqunliao.actionStart(getActivity());
                break;
            default:
                break;
        }
        return true;
    }
    private void initqunzhus() {
        for(int i =0;i<3;i++){
            qunzhu qunzhu1 =new qunzhu("群组1",R.mipmap.qunzu);
            qunzhuList.add(qunzhu1);
            qunzhu qunzhu2 =new qunzhu("群组2",R.mipmap.qunzu);
            qunzhuList.add(qunzhu2);
            qunzhu qunzhu3 =new qunzhu("群组3",R.mipmap.qunzu);
            qunzhuList.add(qunzhu3);
            qunzhu qunzhu4 =new qunzhu("群组4",R.mipmap.qunzu);
            qunzhuList.add(qunzhu4);
            qunzhu qunzhu5 =new qunzhu("群组5",R.mipmap.qunzu);
            qunzhuList.add(qunzhu5);
            qunzhu qunzhu6 =new qunzhu("群组6",R.mipmap.qunzu);
            qunzhuList.add(qunzhu6);
            qunzhu qunzhu7 =new qunzhu("群组7",R.mipmap.qunzu);
            qunzhuList.add(qunzhu7);


        }

    }
}
