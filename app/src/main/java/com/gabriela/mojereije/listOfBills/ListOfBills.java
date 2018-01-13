package com.gabriela.mojereije.listOfBills;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.graph.Graph;
import com.gabriela.mojereije.listOfBills.adapter.CardAdapter;
import com.gabriela.mojereije.login.Login;
import com.gabriela.mojereije.newBill.AddNewBill;
import com.gabriela.mojereije.settings.Podsjetnik;
import com.gabriela.mojereije.userManual.UserManual;
import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.SharedPrefs;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bills);
        ButterKnife.bind(this);
        fab.bringToFront();

        CardAdapter paidadapter = new CardAdapter("paid");
        CardAdapter unpaidadapter = new CardAdapter("unpaid");

        paidRecycler.setLayoutManager(new LinearLayoutManager(this));
        notPaidRecycler.setLayoutManager(new LinearLayoutManager(this));

        paidRecycler.setHasFixedSize(true);
        notPaidRecycler.setHasFixedSize(true);

        paidadapter.setBillList(RealmUtils.getUsersPaidBills(SharedPrefs.getSharedPrefs("username", this)));
        unpaidadapter.setBillList(RealmUtils.getUsersUnPaidBills(SharedPrefs.getSharedPrefs("username", this)));

        paidRecycler.setAdapter(paidadapter);
        notPaidRecycler.setAdapter(unpaidadapter);
    }

    @OnClick(R.id.fab)
    public void addNew() {
        startActivity(new Intent(this, AddNewBill.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_of_bills, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.upali_ugasi_podsjetnik:
                startActivity(new Intent(this, Podsjetnik.class));
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
        // Do Here what ever you want do on back press;
    }
}
