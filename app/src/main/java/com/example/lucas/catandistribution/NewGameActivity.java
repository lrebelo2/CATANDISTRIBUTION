package com.example.lucas.catandistribution;

import android.content.res.Resources;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class NewGameActivity extends AppCompatActivity {
    public static DataPoint[] rolls = new DataPoint[13];
    public GraphView graph;
    public ListView lv;
    public int lastRoll=0;
    public ArrayList<Integer> rollist = new ArrayList<Integer>();
    public CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRolls();
        setContentView(R.layout.activity_new_game);
        lv = (ListView) findViewById(R.id.lvdata);

        Button d1 = (Button) findViewById(R.id.d1);
        Button d2 = (Button) findViewById(R.id.d2);
        Button d3 = (Button) findViewById(R.id.d3);
        Button d4 = (Button) findViewById(R.id.d4);
        Button d5 = (Button) findViewById(R.id.d5);
        Button d6 = (Button) findViewById(R.id.d6);
        Button d7 = (Button) findViewById(R.id.d7);
        Button d8 = (Button) findViewById(R.id.d8);
        Button d9 = (Button) findViewById(R.id.d9);
        Button d10 = (Button) findViewById(R.id.d10);
        Button d11 = (Button) findViewById(R.id.d11);
        Button d12 = (Button) findViewById(R.id.d12);
        Button rem = (Button) findViewById(R.id.removelast);
        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLast();
            }
        });
        final List<Button> list = new ArrayList<Button>();
        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        list.add(d5);
        list.add(d6);
        list.add(d7);
        list.add(d8);
        list.add(d9);
        list.add(d10);
        list.add(d11);
        list.add(d12);
        for(Button x:list){
            x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDie(v);
                }
            });
        }
        graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(13);

        graph.removeAllSeries();

    }
    public void initRolls(){
        for(int i=0;i<rolls.length;i++){
            rolls[i]= new DataPoint(i,0);
        }
    }
    public void addDie(View view){
        int id = view.getId();
        int roll=0;
        DataPoint current;
        switch(id){
            case R.id.d1:
                roll=1;
                break;
            case R.id.d2:
                roll=2;
                break;
            case R.id.d3:
                roll=3;
                break;
            case R.id.d4:
                roll=4;
                break;
            case R.id.d5:
                roll=5;
                break;
            case R.id.d6:
                roll=6;
                break;
            case R.id.d7:
                roll=7;
                break;
            case R.id.d8:
                roll=8;
                break;
            case R.id.d9:
                roll=9;
                break;
            case R.id.d10:
                roll=10;
                break;
            case R.id.d11:
                roll=11;
                break;
            case R.id.d12:
                roll=12;
                break;
            default:
                roll=0;
                break;
        }
        if(roll!=0) {
            rollist.add(roll);
            current = rolls[roll];
            lastRoll=roll;
            DataPoint update = new DataPoint(roll, current.getY() + 1);
            rolls[roll] = update;
            Resources res = getResources();
            adapter = new CustomAdapter(this,rollist, res);
            lv.setAdapter(adapter);
            updateGraph();
            scrollMyListViewToBottom();
        }

    }
    private void scrollMyListViewToBottom() {
        lv.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lv.setSelection(adapter.getCount() - 1);
            }
        });
    }
    public void removeLast(){
        if(rollist.size()!=0) {
            //update for graph
            double curr = rolls[lastRoll].getY();
            DataPoint update = new DataPoint(lastRoll, curr - 1);
            rolls[lastRoll] = update;
            updateGraph();
            //update for list
            rollist.remove(rollist.size() - 1);
            if(rollist.size()!=0){
            lastRoll = rollist.get(rollist.size() - 1);
            Resources res = getResources();
            adapter = new CustomAdapter(this, rollist, res);
            lv.setAdapter(adapter);
                scrollMyListViewToBottom();
            }else{
                lastRoll=0;
                Resources res = getResources();
                adapter = new CustomAdapter(this, rollist, res);
                lv.setAdapter(adapter);
            }
        }
    }
    public void updateGraph(){

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(rolls);
        graph.removeAllSeries();
        graph.addSeries(series);
        // styling
        series.setColor(Color.BLUE);
        series.setSpacing(5);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);

    }

}
