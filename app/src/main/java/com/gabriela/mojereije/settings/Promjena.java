package com.gabriela.mojereije.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.model.data_models.User;
import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.SharedPrefs;
import com.gabriela.mojereije.utils.WidgetUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Promjena extends AppCompatActivity {
    private String tip;

    @BindView(R.id.text1)
    TextView tv1;

    @BindView(R.id.text2)
    TextView tv2;

    @BindView(R.id.edittext1)
    EditText et1;

    @BindView(R.id.edittext2)
    EditText et2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promjena_layout);
        ButterKnife.bind(this);
        tip = getIntent().getStringExtra("tip");
        if (tip.equals("lozinka")) {
            tv1.setText(R.string.staraLoz);
            tv2.setText(R.string.novaLoz);
        } else {
            tv2.setVisibility(View.GONE);
            User user =  RealmUtils.checkIfUserExists("username", SharedPrefs.getSharedPrefs("username", this));
            tv1.setText("Trenutno se podsjetnik javlja " + RealmUtils.getDatumPodsjetnika(user) +  ". u mjesecu \n" + "Unesite novi datum podsjetnika.");
            et2.setVisibility(View.GONE);
            et1.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    @OnClick(R.id.b_potvrdiPromjenu)
    public void spremiPodatke() {
        switch (tip) {
            case "lozinka":
                final String novaLozz = et2.getText().toString();
                String staraLozz = et1.getText().toString();
                if (!novaLozz.isEmpty() && !staraLozz.isEmpty() && !(novaLozz.equals(staraLozz))) {
                    String pass = RealmUtils.getPass(RealmUtils.checkIfUserExists("username",  SharedPrefs.getSharedPrefs("username", getApplicationContext())));
                    if (!(pass.equals(novaLozz))) {
                        User user = RealmUtils.checkIfUserExists("username", SharedPrefs.getSharedPrefs("username", getApplicationContext()));
                        if(user != null){
                           RealmUtils.setPass(user, novaLozz);
                           RealmUtils.saveUser(user);
                        }
                        WidgetUtils.setToast(getApplicationContext(), R.string.pohranaPromjene);
                        startActivity(new Intent(this, Settings.class));
                    } else {
                        WidgetUtils.setToast(getApplicationContext(), R.string.isteLozinke);
                    }
                } else {
                    WidgetUtils.setToast(this, R.string.notAll);
                }
                break;

            case "podsjetnik":
                String noviDatum = et1.getText().toString();
                User user = RealmUtils.checkIfUserExists("username", SharedPrefs.getSharedPrefs("username", this));
                String stariDatum = RealmUtils.getDatumPodsjetnika(user);
                if (stariDatum.equals(noviDatum)) {
                    WidgetUtils.setToast(this, R.string.istiDatum);
                } else {
                    if (user != null) {
                        RealmUtils.setDatumPodsjetnika(user, noviDatum);
                        RealmUtils.saveUser(user);
                    }
                    WidgetUtils.setToast(getApplicationContext(), R.string.pohranaPromjene);
                    startActivity(new Intent(this, Settings.class));
                }
                break;
        }
    }
}

