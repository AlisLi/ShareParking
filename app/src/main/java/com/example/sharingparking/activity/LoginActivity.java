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
import android.widget.Toast;

import com.example.sharingparking.R;
import com.example.sharingparking.SysApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.example.sharingparking.common.Constans.NET_URL_HEADER;

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
                startActivity(new Intent(this,RegisterActivity.class));

                break;
            case R.id.btn_login:
                //explode: 转场特效
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat aoc = ActivityOptionsCompat.makeSceneTransitionAnimation(this);

                OkHttpUtils
                        .post()
                        .url(NET_URL_HEADER + "UserServlet")
                        .addParams("phoneNumber",etLoginUsername.getText().toString())
                        .addParams("password",etLoginPassword.getText().toString())
                        .addParams("method","login")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(LoginActivity.this,"登录异常!",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if("没有注册".equals(response)){
                                    Toast.makeText(LoginActivity.this,"未注册，请先注册！",Toast.LENGTH_LONG).show();
                                }else if("密码错误".equals(response)){
                                    Toast.makeText(LoginActivity.this,"密码错误！",Toast.LENGTH_LONG).show();
                                }else if("登录成功".equals(response)){
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    /**
                                     * 用户名暂时用手机号代替
                                     * 传入手机号
                                     */
                                    bundle.putString("userName",etLoginUsername.getText().toString());
                                    startActivity(intent,aoc.toBundle());
                                }else {
                                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

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












