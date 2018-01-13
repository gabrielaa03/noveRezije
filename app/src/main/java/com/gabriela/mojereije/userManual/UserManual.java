package com.gabriela.mojereije.userManual;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import com.gabriela.mojereije.utils.WidgetUtils;
import com.gabriela.mojereije.R;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.example.gabrielaangebrandt.mojerezije.utils.WidgetUtils;

public class UserManual extends AppCompatActivity {
    @BindView(R.id.iv_registrationscreenshot)
    ImageView registration;
    @BindView(R.id.iv_registrationscreenshot2)
    ImageView registration2;
    @BindView(R.id.iv_loginscreenshot)
    ImageView login;
    @BindView(R.id.iv_billscreenshot)
    ImageView bill;
    @BindView(R.id.iv_newbillscreenshot)
    ImageView newbill;
    @BindView(R.id.iv_graph)
    ImageView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_usermanual);
        ButterKnife.bind(this);
        WidgetUtils.setImages(registration, registration2, login, bill, newbill, graph);
    }
}
