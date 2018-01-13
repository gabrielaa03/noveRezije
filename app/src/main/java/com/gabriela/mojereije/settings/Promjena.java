package com.gabriela.mojereije.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.model.data_models.User;
import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.SharedPrefs;
import com.gabriela.mojereije.utils.WidgetUtils;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

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
            tv1.setText(R.string.unesiteNoviDatumPodsjetnika);
            tv2.setVisibility(View.GONE);
            et2.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.b_potvrdiPromjenu)
    public void spremiPodatke() {
        switch (tip) {
            case "lozinka":
                String novaLozz = et2.getText().toString();
                String staraLozz = et1.getText().toString();
                if (novaLozz.equals(staraLozz)) {
                    WidgetUtils.setToast(this, R.string.isteLozinke);
                } else {
                    String pass = RealmUtils.getPass(RealmUtils.checkIfUserExists("username", SharedPrefs.getSharedPrefs("username", getApplicationContext())));
                    if (pass.equals(novaLozz)) {
                        WidgetUtils.setToast(this, R.string.isteLozinke);
                    } else {
                        User user = RealmUtils.checkIfUserExists("username", SharedPrefs.getSharedPrefs("username", getApplicationContext()));
                        assert user != null;
                        user.setPass(novaLozz);
                        RealmUtils.saveUser(user);
                    }
                }
                WidgetUtils.setToast(getApplicationContext(), R.string.pohranaPromjene);
                startActivity(new Intent(this, Podsjetnik.class));
            case "podsjetnik":
                String noviDatum = et1.getText().toString();
                User user = RealmUtils.checkIfUserExists("username", SharedPrefs.getSharedPrefs("username", getApplicationContext()));
                String stariDatum = RealmUtils.getDatumPodsjetnika(user);
                if(stariDatum.equals(noviDatum)){
                    WidgetUtils.setToast(this, R.string.istiDatum);
                }else{
                    assert user != null;
                    user.setDatumPodsjetnika(noviDatum);
                    RealmUtils.saveUser(user);
                }
                WidgetUtils.setToast(getApplicationContext(), R.string.pohranaPromjene);
                startActivity(new Intent(this, Podsjetnik.class));
        }
    }
}