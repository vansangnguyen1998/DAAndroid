package com.example.pc.daandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MainCallBack,TextWatcher {

    private DrawerLayout mDraw;
    private ActionBarDrawerToggle mItem;
    private NavigationView navView;
    AutoCompleteTextView autoCompleteTextView;
    TextView nameuser;

    private String[] TenTinh = {"ha noi", "ho chi minh", "Qui Nhon", "da lat", "binh dinh"};
    private String[] QuocGia={"Viet Nam"};
    private String[] Loai={"Biển", "Núi", "Tây Bắc", "Chùa Chiền", "Cao Nguyên","Nơi mát mẻ"};
    private String[] Tua={"Không có"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //txtview=(TextView) findViewById(R.id.txtView);
        mDraw = (DrawerLayout) findViewById(R.id.drawer);
        navView= (NavigationView) findViewById(R.id.navView);


        View header = navView.getHeaderView(0);

        nameuser = (TextView) header.findViewById(R.id.nameuser);

        navView.setNavigationItemSelectedListener(this);
        mItem= new ActionBarDrawerToggle(this,mDraw,R.string.open,R.string.close);
        mDraw.addDrawerListener(mItem);
        mItem.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main,
                new trangchu_class()).commit();
        navView.setCheckedItem(R.id.menutrangchu);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //View header = navView.inflateHeaderView(R.layout.header);



        switch (menuItem.getItemId()){
            case R.id.menudangnhap:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main,
                        new dangnhap_class()).commit();

                //Toast.makeText(MainActivity.this,"dang nhap",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menutrangchu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main,
                        new trangchu_class()).commit();
                Toast.makeText(MainActivity.this,"trangchu",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menutimkiemtheotinh:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                View view = getLayoutInflater().inflate(R.layout.dialog_tk_tinh,null);
                autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete);

                autoCompleteTextView.addTextChangedListener( MainActivity.this);

                autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_single_choice,TenTinh));

                // tìm kiếm theo tỉnh
                Button timkiem = (Button) view.findViewById(R.id.btnSearchTinh);

                timkiem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,ActivityChiTiet.class);
                        intent.putExtra("LoaiDuLich","TheoTinh");
                        intent.putExtra("TenTinh",autoCompleteTextView.getText().toString());
                        startActivity(intent);
                    }
                });

                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Toast.makeText(MainActivity.this,"tim kiem theo tinh",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menutimkiemnangcao:
                if(!(nameuser.getText().toString().equals("NoName"))) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    View view1 = getLayoutInflater().inflate(R.layout.dialog_tim_kiem_nang_cao, null);
                    Spinner spinner, spinner1, spinner2, spinner3;
                    spinner = (Spinner) view1.findViewById(R.id.spinerQuocGia);
                    spinner1 = (Spinner) view1.findViewById(R.id.spinerTenTinh);
                    spinner2 = (Spinner) view1.findViewById(R.id.spinerLoai);
                    spinner3 = (Spinner) view1.findViewById(R.id.spinerTuaDuLich);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_single_choice, QuocGia);
                    spinner.setAdapter(adapter);

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_single_choice, TenTinh);
                    spinner1.setAdapter(adapter1);

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_single_choice, Loai);
                    spinner2.setAdapter(adapter2);

                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_single_choice, Tua);
                    spinner3.setAdapter(adapter3);


                    builder1.setView(view1);
                    AlertDialog alertDialog1 = builder1.create();
                    alertDialog1.show();
                }else{
                    Toast.makeText(MainActivity.this,"Đăng nhập trước khi sử dụng chức năng",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menuvitrihientai:
                Toast.makeText(MainActivity.this,"@string/vitrihientai",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menungonngu:
                Toast.makeText(MainActivity.this,"ngon ngu",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menudiadiemyeuthich:
                Toast.makeText(MainActivity.this,"dia diem yeu thich",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuhuongdan:
                Toast.makeText(MainActivity.this,"huong dan",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menudangxuat:
                Toast.makeText(MainActivity.this,"dang xuat",Toast.LENGTH_SHORT).show();
                break;
        }
        mDraw.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mItem.onOptionsItemSelected(item)){
            return true;
        }
        return true;
    }

    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {
        if(sender.equals("_User")){
            nameuser.setText(strValue);
        }
        else if(sender.equals("TrangChu_CaNhan")) {
            if(nameuser.getText().toString().equals("NoName")) {
                Toast.makeText(this,"Đăng nhập trước khi vào cá nhân",Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main,
                        new dangnhap_class()).commit();

            }else{
                MainCaNhan mainCaNhan = MainCaNhan.newInstance("main");
                mainCaNhan.onMsgFromMainToFragment(nameuser.getText().toString());

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main,
                        mainCaNhan).commit();
            }
        }
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // hàm này sẽ là tự đọng nhận giá trị truyền vào liên tục
        //Toast.makeText(MainActivity.this,""+autoCompleteTextView.getText(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
