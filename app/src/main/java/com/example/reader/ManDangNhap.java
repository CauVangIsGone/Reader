package com.example.reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.reader.database.databasedoctruyen;

public class ManDangNhap extends AppCompatActivity {
    //tạo biến cho màn đăng nhập
    EditText edtTaiKhoan,edtMatKhau;
    Button btnDangNhap, btnDangKy;

    //tạo đối tượng cho dtb
    databasedoctruyen databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_nhap);

        AnhXa();

        databasedoctruyen = new databasedoctruyen(this);

        // sự kiện click btn chuyển sang mhinh dangky
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManDangNhap.this, ManDangKy.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentaikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                Cursor cursor = databasedoctruyen.getData();

                while (cursor.moveToNext()){
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);

                    if (datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau)){
                        int phanquyen = cursor.getInt(4);
                        int idd = cursor.getInt(0);
                        String email = cursor.getString(3);
                        String tentk = cursor.getString(1);

                        Intent intent = new Intent(ManDangNhap.this,MainActivity.class);

                        //gửi dữ liệu sang Mainactivity (a1)
                        intent.putExtra("phanq",phanquyen);
                        intent.putExtra("idd",idd);
                        intent.putExtra("email",email);
                        intent.putExtra("tentaikhoan",tentaikhoan);

                        startActivity(intent);
                    }
                }
                cursor.moveToFirst();
                cursor.close();
            }
        });
    }

    private void AnhXa() {
        edtMatKhau = findViewById(R.id.matkhau);
        edtTaiKhoan = findViewById(R.id.taikhoan);
        btnDangKy = findViewById(R.id.dangky);
        btnDangNhap = findViewById(R.id.dangnhap);
    }
}