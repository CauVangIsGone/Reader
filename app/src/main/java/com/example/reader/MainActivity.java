package com.example.reader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.reader.adapter.adapterchuyenmuc;
import com.example.reader.adapter.adapterthongtin;
import com.example.reader.database.databasedoctruyen;
import com.example.reader.model.TaiKhoan;
import com.example.reader.model.Truyen;
import com.example.reader.model.chuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.example.reader.adapter.adapterTruyen;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView,listViewNew,listViewThongTin;
    DrawerLayout drawerLayout;

    String email;
    String tentaikhoan;

    ArrayList<Truyen> TruyenArraylist;
    adapterTruyen adapterTruyen;
    ArrayList<chuyenmuc> chuyenmucArrayList;
    ArrayList<TaiKhoan> taiKhoanArrayList;

    databasedoctruyen databasedoctruyen;

    adapterchuyenmuc adapterchuyenmuc;

    adapterthongtin adapterthongtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasedoctruyen = new databasedoctruyen(this);

        //nhận dữ liệu từ màn đăng nhập(a1)
        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phang",0);
        int idd = intentpq.getIntExtra("idd",0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        AnhXa();
        ActionBar();
        ActionViewFlipper();

        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ManNoiDung.class);

                String tent = TruyenArraylist.get(position).getTenTruyen();
                String noidungt = TruyenArraylist.get(position).getNoiDung();
                intent.putExtra("tentruyen", tent);
                intent.putExtra("noidung", noidungt);
                startActivity(intent);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Đăng Bài
                if (position == 0){
                    if (position == 2 ){
                        Intent intent = new Intent(MainActivity.this,ManAdmin.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(MainActivity.this,"Bạn không có quyền đăng bài", Toast.LENGTH_SHORT).show();
                        Log.e("Đăng Bài: " , "Bạn không có quyền");
                    }
                }

                else if(position == 1){
                    Intent intent = new Intent(MainActivity.this,ManThongTin.class);
                    startActivity(intent);

                    //Đăng xuất
                } else if (position ==2) {
                    finish();
                }
            }
        });
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //Quảng cáo
    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();

        //ảnh quảng cáo
        mangquangcao.add("https://i.pinimg.com/736x/8b/5d/36/8b5d363aba0ba42c6997d477b4001ca0.jpg");
        mangquangcao.add("https://i.pinimg.com/736x/8b/5d/36/8b5d363aba0ba42c6997d477b4001ca0.jpg");
        mangquangcao.add("https://i.pinimg.com/736x/8b/5d/36/8b5d363aba0ba42c6997d477b4001ca0.jpg");
        mangquangcao.add("https://i.pinimg.com/736x/8b/5d/36/8b5d363aba0ba42c6997d477b4001ca0.jpg");

        for (int i=0; i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        //chạy viewflipper trong 4s
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        //gọi animation
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);
    }

    //phương thức ánh xạ
    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        listViewNew = findViewById(R.id.listviewNew);
        listView = findViewById(R.id.listviewmanhinhchinh);
        listViewThongTin = findViewById(R.id.listviewthongtin);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        TruyenArraylist = new ArrayList<>();

        Cursor cursor1 = databasedoctruyen.getData1();
        while (cursor1.moveToNext()){
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArraylist.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

            adapterTruyen = new adapterTruyen(getApplicationContext(),TruyenArraylist);
            listView.setAdapter(adapterTruyen);

        }

        cursor1.moveToFirst();
        cursor1.close();

        //
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan,email));

        adapterthongtin = new adapterthongtin(this,R.layout.navigation_thongtin,taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin);

        chuyenmucArrayList = new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("Đăng bài",R.drawable.baseline_post_add_24));
        chuyenmucArrayList.add(new chuyenmuc("Thông Tin",R.drawable.baseline_contact_page_24));
        chuyenmucArrayList.add(new chuyenmuc("Đăng Xuất",R.drawable.baseline_logout_24));

        adapterchuyenmuc = new adapterchuyenmuc(this,R.layout.chuyenmuc,chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Nếu click vào icon tìm kiếm chuyển sang mh tìm kiếm
        int itemId = item.getItemId();
        if (itemId == R.id.menu1){
            Intent intent = new Intent(MainActivity.this,ManTimKiem.class);
            startActivity(intent);
        }

        //switch (item.getItemId()){
       //     case R.id.menu1:
        //        break;

        //    default:
          //      break;
       // }

        return super.onOptionsItemSelected(item);
    }
}