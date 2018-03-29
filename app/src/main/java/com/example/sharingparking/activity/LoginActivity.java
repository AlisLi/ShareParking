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
import com.example.sharingparking.entity.User;
import com.example.sharingparking.utils.Utility;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static com.example.sharingparking.common.Common.LOGIN_ERROR;
import static com.example.sharingparking.common.Common.LOGIN_FAIL;
import static com.example.sharingparking.common.Common.LOGIN_NO_REGISTER;
import static com.example.sharingparking.common.Common.LOGIN_PASSWORD_MISTAKE;
import static com.example.sharingparking.common.Common.NET_URL_HEADER;


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
        //添加活动到ActivityList中(安全退出)
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

                //临时加入
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);

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
                                Toast.makeText(LoginActivity.this,LOGIN_ERROR,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                List<User> list = Utility.handleUserResponse(response);
                                if(LOGIN_NO_REGISTER.equals(response)){
                                    Toast.makeText(LoginActivity.this,LOGIN_NO_REGISTER,Toast.LENGTH_LONG).show();
                                }else if(LOGIN_PASSWORD_MISTAKE.equals(response)){
                                    Toast.makeText(LoginActivity.this,LOGIN_PASSWORD_MISTAKE,Toast.LENGTH_LONG).show();
                                }else if(list != null){
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    /**
                                     * 用户名暂时用手机号代替
                                     * 传入手机号
                                     */
                                    User user = list.get(0);
                                    intent.putExtra("userName",user.getUserName());
                                    intent.putExtra("userId",user.getUserId());
                                    startActivity(intent,aoc.toBundle());
                                }else {
                                    Toast.makeText(LoginActivity.this,LOGIN_FAIL,Toast.LENGTH_LONG).show();
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












