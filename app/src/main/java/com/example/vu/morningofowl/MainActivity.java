package com.example.vu.morningofowl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.vu.morningofowl.adapter.Phim_Adapter;
import com.example.vu.morningofowl.model.Phim;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView lvPhim;
    private ArrayList<Phim> arrayList;
    private Phim_Adapter adapter;

    private ImageView imgHome;
    private ImageView imgFeed;
    private ImageView imgKidz;
    private ImageView imgLive;
    private ImageView imgMore;

    private TextView tvHome;
    private TextView tvFeed;
    private TextView tvKidz;
    private TextView tvLive;
    private TextView tvMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Init();
        lvPhim = (ListView)findViewById(R.id.lvPhim);
        arrayList = new ArrayList<>();
        adapter = new Phim_Adapter(MainActivity.this, R.layout.dong_layout,arrayList);
        lvPhim.setAdapter(adapter);


       arrayList.add(new Phim("Princess Mononoke","https://moowlcom.000webhostapp.com/Princess%20Mononoke%20-%20Official%20Trailer.mp4","https://c2.staticflickr.com/2/1865/42471550050_cdea85be44_o.jpg","Hoạt Hình","Matsuda Yōji","21548 Lượt Xem", 3.5f));
       arrayList.add(new Phim("The Last Airbender","","https://c2.staticflickr.com/2/1873/44280252771_1813bb322c_o.jpg","Hoạt Hình","Noah Ringer","35165 Lượt Xem", 3.5f));
       arrayList.add(new Phim("Back To The Jurassic","https://moowlcom.000webhostapp.com/Back%20To%20The%20Jurassic%20Official%20Main%20Trailer%20[HD].mp4","https://c2.staticflickr.com/2/1870/42472021840_4cc133e217_o.jpg","Hoạt Hình","Melanie Griffith","15846 Lượt Xem", 3.5f));
       arrayList.add(new Phim("Minions","https://moowlcom.000webhostapp.com/Minions%20Official%20Trailer%201%20(2015)%20-%20Despicable%20Me%20Prequel%20HD.mp4","https://c2.staticflickr.com/2/1884/43374535035_452843dddd_n.jpg","Hoạt Hình","","21548 Lượt Xem", 3.5f));
       arrayList.add(new Phim("Sing","https://moowlcom.000webhostapp.com/Sing%20TRAILER%201%20(2016)%20-%20Scarlett%20Johansson%20Matthew%20McConaughey%20Animated%20Movie%20HD.mp4","https://c2.staticflickr.com/2/1845/44231813242_62e9bc45e8_n.jpg","Hoạt Hình","","39842 Lượt Xem", 3.5f));

        adapter.notifyDataSetChanged();


        lvPhim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               final Phim phim = arrayList.get(position);
                Intent manhinhDetail = new Intent(MainActivity.this, DetailActivity.class);
                for(int i=0; i< arrayList.size(); i++){
                    manhinhDetail.putExtra("dulieu", (Serializable)phim);
                }
                startActivity(manhinhDetail);
            }
        });
    }


    public void clearClick(){
        imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home));
        imgFeed.setImageDrawable(getResources().getDrawable(R.drawable.feed));
        imgKidz.setImageDrawable(getResources().getDrawable(R.drawable.kid));
        imgLive.setImageDrawable(getResources().getDrawable(R.drawable.livestream));
        imgMore.setImageDrawable(getResources().getDrawable(R.drawable.menu));

        tvHome.setTextColor(getResources().getColor(R.color.notselected));
        tvFeed.setTextColor(getResources().getColor(R.color.notselected));
        tvKidz.setTextColor(getResources().getColor(R.color.notselected));
        tvLive.setTextColor(getResources().getColor(R.color.notselected));
        tvMore.setTextColor(getResources().getColor(R.color.notselected));
    }
    public void Init(){
        imgHome = (ImageView)findViewById(R.id.imgHome);
        imgFeed = (ImageView)findViewById(R.id.imgFeed);
        imgKidz = (ImageView)findViewById(R.id.imgKid);
        imgLive = (ImageView)findViewById(R.id.imgLive);
        imgMore= (ImageView)findViewById(R.id.imgMore);

        tvHome = (TextView)findViewById(R.id.tvHome);
        tvFeed = (TextView)findViewById(R.id.tvFeed);
        tvKidz = (TextView)findViewById(R.id.tvKid);
        tvLive = (TextView)findViewById(R.id.tvLive);
        tvMore = (TextView)findViewById(R.id.tvMore);
    }
    public void clickMenu(View view){
            switch (view.getId()){
                case R.id.imgHome:
                    clearClick();
                    imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home_selected));
                    tvHome.setTextColor(getResources().getColor(R.color.selected));
                    break;
                case R.id.imgFeed:
                    clearClick();
                    imgFeed.setImageDrawable(getResources().getDrawable(R.drawable.feed_selected));
                    tvFeed.setTextColor(getResources().getColor(R.color.selected));
                    break;
                case R.id.imgKid:
                    clearClick();
                    imgKidz.setImageDrawable(getResources().getDrawable(R.drawable.kid_selected));
                    tvKidz.setTextColor(getResources().getColor(R.color.selected));
                    break;
                case R.id.imgLive:
                    clearClick();
                    imgLive.setImageDrawable(getResources().getDrawable(R.drawable.livestream_selected));
                    tvLive.setTextColor(getResources().getColor(R.color.selected));
                    break;
                case R.id.imgMore:
                    clearClick();
                    imgMore.setImageDrawable(getResources().getDrawable(R.drawable.menu_selected));
                    tvMore.setTextColor(getResources().getColor(R.color.selected));
                    break;
            }
    }
//    private void fakeData() {
//        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.princess_mononoke);
//        for (int i = 0; i < 2; i++) {
//            Phim p= new Phim("Princess Mononoke","","https://c2.staticflickr.com/2/1889/30409125548_babd086b2c_o.jpg","Hoạt Hình","","21548 Lượt Xem", 3.5f);
//
//            arrayList.add(p);
//        }
//        //lưu ý: bất kì khi nào thay đổi mảng thì sẽ phải cập nhật lại giao diện
//        adapter.notifyDataSetChanged();
//    }
}
