package com.example.jintong.shujuku;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    List<shuju> shujuchaxunList;
    List<shuju> shuju;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("search","111166666");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        shujuchaxunList = (List<shuju>)intent.getSerializableExtra("result");
        String result = shujuchaxunList.toString();
        TextView textView = (TextView)findViewById(R.id.shi);
        textView.setText(result);
    }

}
