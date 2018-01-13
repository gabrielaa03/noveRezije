package com.gabriela.mojereije.newBill;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


import com.gabriela.mojereije.listOfBills.ListOfBills;
import com.gabriela.mojereije.model.data_models.Bill;
import com.gabriela.mojereije.utils.Credentials;
import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.SharedPrefs;
import com.gabriela.mojereije.utils.WidgetUtils;
import com.gabriela.mojereije.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gabriela.mojereije.utils.RealmUtils.getUsersBills;


public class AddNewBill extends AppCompatActivity {
    @BindView(R.id.spiner_naziv)
    Spinner vrsta;
    @BindView(R.id.et_tvrtka)
    EditText tvrtka;
    @BindView(R.id.et_brojRacuna)
    EditText brojRacuna;
    @BindView(R.id.et_mjesec)
    EditText mjesec;
    @BindView(R.id.et_iznos)
    EditText iznos;
    @BindView(R.id.rb_placen)
    RadioButton rb_placen;
    @BindView(R.id.rb_neplacen)
    RadioButton rb_neplacen;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    String barcodeData;

    Bill bill;
    private int CAMERA_PIC_REQUEST = 9000;
    private Calendar myCalendar;
    private SimpleDateFormat sdf;
    private DatePickerDialog dpd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_bill);
        ButterKnife.bind(this);
        setWidgets();
    }

    private void setWidgets() {
        rb_neplacen.setChecked(true);
        WidgetUtils.setSpinner(vrsta, this);
        myCalendar = Calendar.getInstance();
    }

    @OnClick(R.id.btn_potvrdi)
    public void addBill() {
        if (!rb_neplacen.isChecked() && !rb_placen.isChecked()) {
            WidgetUtils.setToast(this, R.string.stanjeRacuna);
        } else if (!rb_neplacen.isChecked() && rb_placen.isChecked()) {
                if (Credentials.checkCredentialsSpinner(vrsta) || Credentials.checkCredentials(tvrtka) || Credentials.checkCredentials(brojRacuna) ||
                        Credentials.checkCredentials(mjesec) || Credentials.checkCredentials(iznos)) {
                    WidgetUtils.setToast(this, R.string.elementsArentEntered);
                } else {
                    bill = new Bill(SharedPrefs.getSharedPrefs("username", this), mjesec.getText().toString(), brojRacuna.getText().toString(), vrsta.getSelectedItem().toString(), tvrtka.getText().toString(), iznos.getText().toString(), "rb_placen");
                }

        } else {
            if (Credentials.checkCredentialsSpinner(vrsta) || Credentials.checkCredentials(tvrtka) || Credentials.checkCredentials(brojRacuna) ||
                    Credentials.checkCredentials(mjesec) || Credentials.checkCredentials(iznos)) {
                WidgetUtils.setToast(this, R.string.elementsArentEntered);
            } else {
                bill = new Bill(SharedPrefs.getSharedPrefs("username", this), mjesec.getText().toString(), brojRacuna.getText().toString(), vrsta.getSelectedItem().toString(), tvrtka.getText().toString(), iznos.getText().toString(), "rb_neplacen");
            }
        }
        startDialog();

}

    private void startDialog() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(R.string.potvrdiZaSpremanje)
                .setMessage(R.string.spremanjeRacuna)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        List<Bill> bills = RealmUtils.getUsersBills(SharedPrefs.getSharedPrefs("username", getApplicationContext()));
                        List<Bill> bills1 = new ArrayList<>();
                        bills1.addAll(bills);
                        bills1.add(bill);
                        RealmUtils.saveUsersBills(bills1, SharedPrefs.getSharedPrefs("username", getApplicationContext()));
                        Intent intent = new Intent(getApplicationContext(), ListOfBills.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @OnClick(R.id.ib_camera_btn)
    public void openCamera() {

        startActivityForResult(CameraActivity.getLaunchIntent(this), CAMERA_PIC_REQUEST);
    }

    @OnClick(R.id.et_mjesec)
    public void pickMonth() {
        createDialogWithoutDateField().show();
    }
    private void updateLabel() {
        String myFormat = "yy/MM"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        mjesec.setText(sdf.format(myCalendar.getTime()));
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            barcodeData = (String) data.getExtras().get(CameraActivity.KEY_EXTRA_DATA);
            assert barcodeData != null;
            String[] parts = barcodeData.split("\n");
            Double f = Double.parseDouble(parts[2]);
            f /= 100;
            String ff = String.format("%.2f", f);
            iznos.setText(ff);
            tvrtka.setText(parts[6] + ", " + parts[7] + ", " + parts[8]);
            rb_neplacen.isChecked();
            brojRacuna.setText(parts[13]);
            String[] string = parts[11].split("-");
            mjesec.setText(new StringBuilder(string[1]).insert(string[1].length() - 2, "/").toString());
        }
    }

    private DatePickerDialog createDialogWithoutDateField() {
        dpd = new DatePickerDialog(this, date, 2017, 1, 24);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return dpd;
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
}

