package com.example.sharingparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sharingparking.R;
import com.example.sharingparking.SysApplication;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private Button btnRegister;
    private Button btnLogin;
    private TextView txtForgetPsw;

    private CardView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //添加活动到ActivityList中
        SysApplication.getInstance().addActivity(this);


        //初始化控件
        initView();


        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login_register:
                /*//只支持5.0以上
                //Button和TextView要强制转换为View，只支持View
                Pair<View,String> registerPair = Pair.create((View) btnRegister,getString(R.string.register));
                Pair<View,String> loginPair = Pair.create((View)btnLogin,getString(R.string.login));
                Pair<View,String> forgetPsw = Pair.create((View)txtForgetPsw,getString(R.string.forget_password));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //登录按钮消失
                    btnLogin.setVisibility(View.GONE);
                    txtForgetPsw.setVisibility(View.GONE);
                    //注册按钮的过度动画
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,registerPair,loginPair,forgetPsw);
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {

                }*/
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                //explode: 转场特效
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat aoc = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent,aoc.toBundle());

                break;
        }
    }

    private void initView(){

        etLoginUsername = (EditText) findViewById(R.id.et_login_username);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        btnRegister = (Button) findViewById(R.id.btn_login_register);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtForgetPsw = (TextView) findViewById(R.id.txt_forget_psw);
        cv = (CardView) findViewById(R.id.login_card);

    }


}












