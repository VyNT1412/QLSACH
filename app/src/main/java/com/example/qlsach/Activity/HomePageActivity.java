package com.example.qlsach.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qlsach.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        anhXa();
        actionBar();
        actionViewFlipper();
}

    private void actionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://newshop.vn/public/uploads/news/52999962-994063307450168-3207249248986857472-n.png");
        mangquangcao.add("https://marketingai.mediacdn.vn/zoom/700_438/wp-content/uploads/2019/06/sach.jpg");
        mangquangcao.add("https://bloganchoi.com/wp-content/uploads/2020/10/phuong-nam-sale-sach.jpg");
        for(int i = 0; i < mangquangcao.size(); i++){
            //lấy context
            ImageView imageView = new ImageView(getApplicationContext());
            //Glide.with(getApplicationContext()): Đây là cách bạn bắt đầu một yêu cầu tải ảnh với Glide.
            //load(mangquangcao.get(i)): Đây là phần mà bạn chỉ định nguồn dữ liệu bạn muốn tải.
            //.into(imageView): Đây là phần mà bạn chỉ định ImageView mà bạn muốn hiển thị ảnh đã tải.
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            //thiết lập hiển thị hình ảnh
            //FIT_XY: Thay đổi kích thước của ảnh để nó phù hợp với kích thước
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        //set thời gian chuyển động. 3000 tương đương với 3s
        viewFlipper.setFlipInterval(3000);
        //tự động chuyển đổi hình
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void actionBar() {
        //thiết lập một thanh công cụ toolbar hành động tuỳ chỉnh
        setSupportActionBar(toolbar);
        //hiển thị nút "Up" (hoặc mũi tên quay lại) trên thanh công cụ toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //thiết lập sự kiện ấn nút sẽ hiện lên trang navigation
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //drawLayout được sử dụng để quản lý thanh đựng và nội dung chính của giao diện người dùng.
                //Phương thức openDrawer(GravityCompat.START) được gọi để mở thanh đựng từ vị trí bên trái của giao diện người dùng.
                // Tham số GravityCompat.START chỉ ra rằng thanh đựng sẽ được mở từ phía bên trái của giao diện người dùng.
                // GravityCompat.START tương đương với Gravity.LEFT, chỉ rõ vị trí bên trái của màn hình.
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbarHomePage);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerView = findViewById(R.id.recycleview);
        navigationView = findViewById(R.id.navigationview);
        listView = findViewById(R.id.listview);
        drawerLayout = findViewById(R.id.drawlayout);
    }
}