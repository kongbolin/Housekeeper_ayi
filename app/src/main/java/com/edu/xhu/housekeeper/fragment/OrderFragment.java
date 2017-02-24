package com.edu.xhu.housekeeper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.edu.xhu.housekeeper.R;

public class OrderFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_order, null);
     //   findview(contentView);
        return contentView;
    }

//    private void findview(View v) {
//        search= (ImageView) v.findViewById(R.id.type_search);
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity (new Intent(getActivity(),SearchActivity.class));
//            }
//        });
//        str = new String[]{"美妆护肤","秋装新品","数码商城","换季特卖","水果超市","数码商城","美妆护肤","服装城",
//                "美妆护肤","秋装新品","数码商城","换季特卖","水果超市","数码商城","美妆护肤","服装城"};
//        adapter1 = new TypeFragmentAdapterLeft(getActivity(), str);
//        lv1 = (ListView) v.findViewById(R.id.type_listview1);
//        lv1.setAdapter(adapter1);
//        lv1.setOnItemClickListener(this);
//        ifs = 1;
//        try {
//            businessList1 = DBHelper.getInstance(getActivity()).findAll(Selector.from(Business.class).where("type", "=", "美妆护肤"));
//            businessList2 = DBHelper.getInstance(getActivity()).findAll(Selector.from(Business.class).where("type", "=", "秋装新品"));
//            businessList3 = DBHelper.getInstance(getActivity()).findAll(Selector.from(Business.class).where("type", "=", "数码商城"));
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        adapter2 = new TypeFragmentAdapterRight(getActivity(),businessList1);
//        lv2 = (ListView) v.findViewById(R.id.type_listview2);
//        lv2.setAdapter(adapter2);
//        lv2.setOnItemClickListener(this);
//    }


}
