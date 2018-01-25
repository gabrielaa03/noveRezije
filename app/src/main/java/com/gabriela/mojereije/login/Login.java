package com.gabriela.mojereije.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


import com.gabriela.mojereije.forgPass.ForgottenPass;
import com.gabriela.mojereije.listOfBills.ListOfBills;
import com.gabriela.mojereije.model.data_models.User;
import com.gabriela.mojereije.registration.Registration;
import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.SharedPrefs;
import com.gabriela.mojereije.R;
import com.gabriela.mojereije.utils.WidgetUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {
    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.et_password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharedPrefs.getSharedPrefs("isLogged", this).equals("in")) {
            startActivity(new Intent(this, ListOfBills.class));
        } else if (SharedPrefs.getSharedPrefs("isLogged", this).equals("out")) {
            setContentView(R.layout.activity_login);
            setTitle(R.string.logIn);
            ButterKnife.bind(this);
        }
    }


    @OnClick(R.id.btn_login1)
    public void logIn() {
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            WidgetUtils.setToast(this, R.string.wrongPasswordOrUsername);
        } else {
            User user = RealmUtils.checkIfUserExists("username", username.getText().toString());
            if (user != null) {
                if (user.getPass().equals(password.getText().toString())) {
                    SharedPrefs.setSharedPrefs("username", username.getText().toString(), this);
                    SharedPrefs.setSharedPrefs("password", password.getText().toString(), this);
                    SharedPrefs.setSharedPrefs("isLogged", "in", this);
                    startActivity(new Intent(this, ListOfBills.class));
                } else {
                    WidgetUtils.setToast(this, R.string.wrongPasswordOrUsername);
                }
            } else {
                WidgetUtils.setToast(this, R.string.wrongPasswordOrUsername);
            }
        }
    }

    @OnClick(R.id.btn_register)
    public void register() {
        startActivity(new Intent(this, Registration.class));
    }

    @OnClick(R.id.tv_forgotten_pass)
    public void getYourPass() {
        startActivity(new Intent(this, ForgottenPass.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//                startActivity(new Intent(this, Upute.class));
        return super.onOptionsItemSelected(item);
    }
}
