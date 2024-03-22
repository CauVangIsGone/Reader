package com.example.reader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ManThongTin extends AppCompatActivity {

    TextView txtThongtinapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_thong_tin);

        txtThongtinapp = findViewById(R.id.textviewthongtin);

        String thongtin = "ứng dụng được phát hành bởi Nhóm 4\n" + "Uy tín hàng đầu" + "Ok";
        txtThongtinapp.setText(thongtin);
    }
}