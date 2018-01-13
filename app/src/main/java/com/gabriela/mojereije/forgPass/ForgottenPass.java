package com.gabriela.mojereije.forgPass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;


import com.gabriela.mojereije.R;
import com.gabriela.mojereije.login.Login;
import com.gabriela.mojereije.model.data_models.User;
import com.gabriela.mojereije.utils.Credentials;
import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.WidgetUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgottenPass extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.et_name)
    EditText name;
    @BindView(R.id.et_email)
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_pass);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_getMyPass)
    public void getPass() {
        if (Credentials.checkCredentials(username) || Credentials.checkCredentials(name) || Credentials.checkCredentials(email)) {
            WidgetUtils.setToast(this, R.string.elementsArentEntered );
        } else {
            User user = RealmUtils.checkIfUserExists("username", username.getText().toString());
            assert user != null;
            if (RealmUtils.getName(user).equals(name.getText().toString()) && RealmUtils.getEmail(user).equals(email.getText().toString())) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, RealmUtils.getEmail(user));
                intent.putExtra(Intent.EXTRA_SUBJECT, R.string.pass);
                intent.putExtra(Intent.EXTRA_TEXT, R.string.mailtext + RealmUtils.getPass(user));
                try {
                    startActivity(Intent.createChooser(intent, "Pošalji lozinku"));
                } catch (android.content.ActivityNotFoundException ex) {
                    WidgetUtils.setToast(this,  R.string.email_error);
                }
                startActivity(new Intent(this, Login.class));
            } else {
                WidgetUtils.setToast(this, R.string.kriviPodaci);
            }
        }
    }
}
