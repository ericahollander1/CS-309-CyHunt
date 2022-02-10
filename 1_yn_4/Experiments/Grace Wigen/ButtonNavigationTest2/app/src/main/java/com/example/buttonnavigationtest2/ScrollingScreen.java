package com.example.buttonnavigationtest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class ScrollingScreen extends AppCompatActivity {
    private ListView leaderboard;
    private List leaders = new ArrayList();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_screen);
        leaderboard = (ListView)findViewById(R.id.leaderboardList);
        leaders.add("q8weufkshn");
        leaders.add("287 fy8w7feih");
        leaders.add("wfuiq2cwes");
        leaders.add("4tyfn38 ce");
        leaders.add("cw98ryfh");
        leaders.add("3qatnhmfd");
        leaders.add("c83q7 4wyhf");
        leaders.add("c2oijk3whetg");
        leaders.add("209w5vm9cf e");
        leaders.add("4v5 yet7ncom");
        leaders.add("2qo8nwy54htcg");
        leaders.add("v4etcfd");
        leaders.add("i1uqwnl f");
        leaders.add("q3w4a 8fy");
        leaders.add("q38v wya4iucr");
        leaders.add("q7vw45y nctr2w");
        leaders.add("2q87i4 yrc8i3");

        adapter = new ArrayAdapter(ScrollingScreen.this, android.R.layout.simple_list_item_1, leaders);
        leaderboard.setAdapter(adapter);
    }
}