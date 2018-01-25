package com.gabriela.mojereije.listOfBills;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.broadcast.MyReceiver;
import com.gabriela.mojereije.graph.Graph;
import com.gabriela.mojereije.listOfBills.adapter.CardAdapter;
import com.gabriela.mojereije.login.Login;
import com.gabriela.mojereije.model.data_models.User;
import com.gabriela.mojereije.newBill.AddNewBill;
import com.gabriela.mojereije.settings.Settings;
import com.gabriela.mojereije.userManual.UserManual;
import com.gabriela.mojereije.utils.DateUtils;
import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.SharedPrefs;
import com.gabriela.mojereije.utils.WidgetUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListOfBills extends AppCompatActivity {

    @BindView(R.id.paid_recycler)
    RecyclerView paidRecycler;

    @BindView(R.id.not_paid_recycler)
    RecyclerView notPaidRecycler;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    CardAdapter paidadapter;
    CardAdapter unpaidadapter;
//
//    @BindView(R.id.swiperefresh)
//    SwipeRefreshLayout mySwipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bills);
        ButterKnife.bind(this);
        fab.bringToFront();

        paidadapter = new CardAdapter("paid", this);
        unpaidadapter = new CardAdapter("unpaid", this);

        paidRecycler.setLayoutManager(new LinearLayoutManager(this));
        notPaidRecycler.setLayoutManager(new LinearLayoutManager(this));

        paidRecycler.setHasFixedSize(true);
        notPaidRecycler.setHasFixedSize(true);

        paidadapter.setBillList(RealmUtils.getUsersPaidBills(SharedPrefs.getSharedPrefs("username", this)));
        unpaidadapter.setBillList(RealmUtils.getUsersUnPaidBills(SharedPrefs.getSharedPrefs("username", this)));

        paidRecycler.setAdapter(paidadapter);
        notPaidRecycler.setAdapter(unpaidadapter);

        AlarmManager alarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        MyReceiver receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent("ALARM_ACTION");
        intent.putExtra("param", "My scheduled action");
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (alarms != null && DateUtils.isTodaysDate(this) && (RealmUtils.getUsersUnPaidBills(SharedPrefs.getSharedPrefs("username", this)).size() > 0)) {
            alarms.set(AlarmManager.RTC_WAKEUP, new Date().getTime(), operation);
        }

    }

    @OnClick(R.id.fab)
    public void addNew() {
        startActivity(new Intent(this, AddNewBill.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_of_bills, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.upali_ugasi_podsjetnik:
                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.graph:
                startActivity(new Intent(this, Graph.class));
                break;
            case R.id.action_upute:
                startActivity(new Intent(this, UserManual.class));
                break;
            case R.id.logout:
                SharedPrefs.setSharedPrefs("isLogged", "out", this);
                startActivity(new Intent(this, Login.class));
                Toast.makeText(this, R.string.odjava, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    public void refreshRecyclerViews(){
        paidadapter.setBillList(RealmUtils.getUsersPaidBills(SharedPrefs.getSharedPrefs("username", this)));
        unpaidadapter.setBillList(RealmUtils.getUsersUnPaidBills(SharedPrefs.getSharedPrefs("username", this)));
        WidgetUtils.setToast(this, R.string.listepromijenjene);
    }
}
