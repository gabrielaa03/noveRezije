package com.gabriela.mojereije.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.login.Login;
import com.gabriela.mojereije.utils.Credentials;
import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.WidgetUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Registration extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText imeIPrezime;
    @BindView(R.id.et_addr)
    EditText adresa;
    @BindView(R.id.et_email)
    EditText email;
    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.et_password1)
    EditText pass;
    @BindView(R.id.et_password2)
    EditText pass2;
    @BindView(R.id.datumPodsjetnika)
    EditText placa;
    public static final String EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_register_user)
    public void saveIntoDB() {
        if (Credentials.checkCredentials(imeIPrezime) || Credentials.checkCredentials(adresa) || Credentials.checkCredentials(email)
                || Credentials.checkCredentials(username) || Credentials.checkCredentials(pass) || Credentials.checkCredentials(pass2)
                || Credentials.checkCredentials(placa)) {
            WidgetUtils.setToast(this, R.string.elementsArentEntered);
        } else {
            if (RealmUtils.checkIfUserExists("username", username.getText().toString()) != null) {
                WidgetUtils.setToast(this, R.string.userAlreadyExists);
            } else {
                if (!pass.getText().toString().equals(pass2.getText().toString())) {
                    WidgetUtils.setToast(this, R.string.error_passwordsDontMatch);
                } else {
//                    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                    Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(email.getText().toString());
                    if (matcher.matches()) {
                        RealmUtils.saveUser(RealmUtils.createUser(username, imeIPrezime, adresa, email, pass, placa));
                        startActivity(new Intent(this, Login.class));
                        WidgetUtils.setToast(this, R.string.successfullRegistration);
                        Toast.makeText(this, R.string.successfullRegistration, Toast.LENGTH_LONG).show();
                    } else {
                        WidgetUtils.setToast(this, R.string.emailIsNotValid);
                    }
                }
            }
        }
    }
}
