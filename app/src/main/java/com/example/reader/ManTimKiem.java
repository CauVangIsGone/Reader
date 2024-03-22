package com.example.reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.reader.adapter.adapterTruyen;
import com.example.reader.database.databasedoctruyen;
import com.example.reader.model.Truyen;

import java.util.ArrayList;

public class ManTimKiem extends AppCompatActivity {

    ListView listView;
    EditText edt;
    ArrayList<Truyen> TruyenArrayList;
    adapterTruyen adapterTruyen;

    databasedoctruyen databasedoctruyen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_tim_kiem);

        listView = findViewById(R.id.listviewTimKiem);
        edt = findViewById(R.id.timkiem);

        initList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ManTimKiem.this,ManNoiDung.class);
                String tent = TruyenArrayList.get(position).getTenTruyen();
                String noidungt = TruyenArrayList.get(position).getNoiDung();
                intent.putExtra("tentruyen",tent);
                intent.putExtra("noidung",noidungt);
                startActivity(intent);
            }
        });
    }

    //Phương thức lấy dữ liệu
    private void initList() {
        TruyenArrayList = new ArrayList<>();

        //arrayList = new ArrayList<>();

        databasedoctruyen = new databasedoctruyen(this);

        Cursor cursor = databasedoctruyen.getData2();
        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String tentruyen = cursor.getString(1);
            String noidung = cursor.getString(2);
            String anh = cursor.getString(3);
            int id_tk = cursor.getInt(4);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

            adapterTruyen = new adapterTruyen(getApplicationContext(),TruyenArrayList);

            listView.setAdapter(adapterTruyen);
        }
        cursor.moveToFirst();
        cursor.close();


    }
}