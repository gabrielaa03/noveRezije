package com.gabriela.mojereije.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.TextView;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.utils.DateUtils;
import com.gabriela.mojereije.utils.NotificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Settings extends AppCompatActivity {

    @BindView(R.id.switch1)
    Switch upali_ugasi;

    @BindView(R.id.promjenaPodsjetnika)
    TextView promjenaPodsjetnika;

    @BindView(R.id.promjenaLozz)
    TextView promjenaLozz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        ButterKnife.bind(this);
        upali_ugasi.isChecked();
    }

    @OnClick(R.id.promjenaLozz)
    public void promjeniLozinku() {
        Intent intent = new Intent(this, Promjena.class);
        intent.putExtra("tip", "lozinka");
        startActivity(intent);
    }

    @OnClick(R.id.promjenaPodsjetnika)
    public void promjeniPodsjetnik() {
        Intent intent = new Intent(this, Promjena.class);
        intent.putExtra("tip", "podsjetnik");
        startActivity(intent);
    }
}
