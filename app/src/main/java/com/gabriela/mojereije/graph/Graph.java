package com.gabriela.mojereije.graph;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.model.App;
import com.gabriela.mojereije.model.data_models.Bill;
import com.gabriela.mojereije.utils.SharedPrefs;
import com.gabriela.mojereije.utils.WidgetUtils;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;


public class Graph extends AppCompatActivity {

    private LineGraphSeries<DataPoint> mSeries2;

    @BindView(R.id.spiner_naziv)
    Spinner vrsta;
    String text;
    static String year_split;
    static String year_from_spinner;
    HashMap billsmap = new HashMap<>();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);

        mSeries2 = new LineGraphSeries<>();
        WidgetUtils.setSpinner(vrsta, this);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        final Spinner spinYear = (Spinner)findViewById(R.id.spinner_years);
        spinYear.setAdapter(adapter);

        //final TextView ddd = (TextView)findViewById(R.id.ddd);
        text = vrsta.getSelectedItem().toString();
        final GraphView graph = (GraphView) findViewById(R.id.graph);

        vrsta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mSeries2.resetData(generateData());
                text = vrsta.getSelectedItem().toString();
                year_from_spinner = spinYear.getSelectedItem().toString();
                billsmap = getAllMonthsAndTheirValues(SharedPrefs.getSharedPrefs("username", getApplicationContext()), text);
                Map<Integer, String> treeMap = new TreeMap<Integer, String>(billsmap);
                //ddd.setText(treeMap.toString());
                Set keys = treeMap.keySet();
                for (Iterator i = keys.iterator(); i.hasNext(); ) {
                    Integer key = (Integer) i.next();
                    String value = (String) treeMap.get(key);
                    Double mm=Double.parseDouble(value.replace(",", "."));
                    mSeries2.appendData(new DataPoint(key, mm), true, 40);
                    graph.addSeries(mSeries2);
                    mSeries2.setDrawDataPoints(true);
                    mSeries2.setDataPointsRadius(10);
                }
                // set manual X bounds
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(1);
                graph.getViewport().setMaxX(12);
                graph.getGridLabelRenderer().setNumHorizontalLabels(12);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mSeries2.resetData(generateData());
                year_from_spinner = spinYear.getSelectedItem().toString();
                billsmap = getAllMonthsAndTheirValues(SharedPrefs.getSharedPrefs("username", getApplicationContext()), text);
                Map<Integer, String> treeMap = new TreeMap<Integer, String>(billsmap);
                //ddd.setText(treeMap.toString());
                Set keys = treeMap.keySet();
                for (Iterator i = keys.iterator(); i.hasNext(); ) {
                    Integer key = (Integer) i.next();
                    String value = (String) treeMap.get(key);
                    Double mm=Double.parseDouble(value.replace(",", "."));
                    mSeries2.appendData(new DataPoint(key, mm), true, 40);
                    graph.addSeries(mSeries2);
                    mSeries2.setDrawDataPoints(true);
                    mSeries2.setDataPointsRadius(10);
                }
                // set manual X bounds
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(1);
                graph.getViewport().setMaxX(12);
                graph.getGridLabelRenderer().setNumHorizontalLabels(12);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private DataPoint[] generateData() {
        DataPoint[] values = new DataPoint[0];
        return values;
    }

    public static HashMap<Integer, String> getAllMonthsAndTheirValues(String value, String text) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        List<Bill> bills = realm.copyFromRealm(realm.where(Bill.class).equalTo("user", value).equalTo("naziv", text).findAll());
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (Bill bill : bills) {
            String[] date = bill.getMjesec().split("/");
            String month = date[1];
            year_split = date[0];
            if((year_split.compareTo(year_from_spinner)==0) || (year_split.compareTo(year_from_spinner.substring(2))==0) ) {
                Integer x  = Integer.parseInt(month);
                if (month.startsWith("0")) {
                    month = month.substring(1);
                    x = Integer.parseInt(month);
                }
                hashMap.put(x, bill.getIznos());
            }
        }
        realm.commitTransaction();
        return hashMap;
    }
}
