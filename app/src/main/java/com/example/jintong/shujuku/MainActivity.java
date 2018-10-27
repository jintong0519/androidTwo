package com.example.jintong.shujuku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.ContextMenu.ContextMenuInfo;

public class MainActivity extends AppCompatActivity {
    List<shuju> shujuList;
    List<shuju> shujuchaxunList;
    shujuDBhelper sjhelper;
    SQLiteDatabase db;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnCreateContextMenuListener(this);
        shujuList = new ArrayList<shuju>();
        shujuchaxunList = new ArrayList<shuju>();
        // 创建MyOpenHelper实例
        sjhelper = new shujuDBhelper(this);
        // 得到数据库
        db = sjhelper.getWritableDatabase();
        // 查询数据
        Query();
        // 创建MyAdapter实例
        myAdapter = new MyAdapter(this);
        // 向listview中添加Adapter
        lv.setAdapter(myAdapter);
    }

    // 创建MyAdapter继承BaseAdapter
    class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return shujuList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            shuju sj = shujuList.get(position);
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.geshi, null);
                viewHolder.tv_title = (TextView) convertView
                        .findViewById(R.id.tv_title);
                viewHolder.tv_date = (TextView) convertView
                        .findViewById(R.id.tv_date);
                viewHolder.tv_message = (TextView) convertView
                        .findViewById(R.id.tv_message);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //向TextView中插入数据
            viewHolder.tv_title.setText(sj.getTitle());
            viewHolder.tv_date.setText(sj.getDate());
            viewHolder.tv_message.setText(sj.getMessage());

            return convertView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // 插入数据
    public void Insert(String title, String date, String message) {
        String sql = "insert into shuju(title,date,message) values(?,?,?)";
        SQLiteDatabase db = openOrCreateDatabase("Shuju.db", Context.MODE_PRIVATE, null);
        db.execSQL(sql, new String[]{title,date,message});
    }
    public void InsertDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert,null);
        new AlertDialog.Builder(this)
                .setTitle("新增日志")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strTitle = ((EditText)tableLayout.findViewById(R.id.textTitle)).getText().toString();
                        String strDate = ((EditText)tableLayout.findViewById(R.id.textDate)).getText().toString();
                        String strMessage = ((EditText)tableLayout.findViewById(R.id.textMessage)).getText().toString();
                        Insert(strTitle,strDate,strMessage);
                        Query();
                        Intent ii = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(ii);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }
    //查找部分数据
    /*private ArrayList<Map<String, String>> Search(String strtitleSearch) {
        SQLiteDatabase db = openOrCreateDatabase("Shuju.db", Context.MODE_PRIVATE, null);
        String sql = "select * from shuju where title like ? order by title desc";
        Cursor c = db.rawQuery(sql,new String[] {"%"+strtitleSearch+"%"});
        return Query(c);
    }*/

    private void SearchDialog() {
           final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.searchterm,null);
           new AlertDialog.Builder(this)
                   .setTitle("查找标题")
                   .setView(tableLayout)
                   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           String txtSearchTitle=((EditText)tableLayout.findViewById(R.id.txtSearchTitle)).getText().toString();
                           //ArrayList<Map<String,String>> items=null;

                           //items=
                           int num = Query1(txtSearchTitle);
                           //if(items.size()>0) {
                           if(num>0){
                               //Bundle bundle = new Bundle();
                               //bundle.putSerializable("result",items);
                               Log.e("search","111155555");
                               //String temp = shujuchaxunList.toString();
                               Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                               intent.putExtra("result",(Serializable)shujuchaxunList);
                               startActivity(intent);
                           } else
                               Toast.makeText(MainActivity.this, "没有找到", Toast.LENGTH_LONG).show();
                       }
                   })
                   .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                       }
                   })
                   .create()
                   .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.chazhao:
                SearchDialog();
                return true;
            case R.id.xinzeng:
                InsertDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class ViewHolder {
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_message;
    }

    // 查询数据
    public void Query() {
        Cursor cursor = db.query("Shuju", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String date = cursor.getString(2);
            String message = cursor.getString(3);
            shuju sj = new shuju(id, title, date, message);
            shujuList.add(sj);
        }
    }
    public int Query1(String strtitleSearch) {  //ArrayList<Map<String, String>>
        String[] selectionArgs ={"%" + strtitleSearch + "%"};
        String[] projection = {
                "id","title","date","message"
        };
        String selection = "title" + " LIKE ?";
        String sortOrder = "title" + " DESC";

        Cursor cursor = db.query("Shuju", projection, selection, selectionArgs, null, null, sortOrder);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String date = cursor.getString(2);
            String message = cursor.getString(3);
            shuju sj = new shuju(id, title, date, message);
            shujuchaxunList.add(sj);
        }

        int num = cursor.getCount();
        return num;
        //return null;
    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.add(1, 1001, 1, "修改");
        menu.add(1, 1002, 2, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public void delete(int id){
        //删除数据
        SQLiteDatabase db = openOrCreateDatabase("Shuju.db", Context.MODE_PRIVATE, null);
        String sql = "delete from shuju where id = ?";
        db.execSQL(sql, new Integer[]{id});
        Cursor c;
        c = db.rawQuery("SELECT * FROM shuju",null);
        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("id"));
            String title1 = c.getString(c.getColumnIndex("title"));
            String message1 = c.getString(c.getColumnIndex("message"));
            String date1=c.getString(c.getColumnIndex("date"));
            Log.i("db", "id=>" + id + ", title=>" + title1 + ", date=>" + date1 + ", message=>" + message1);
        }
    }

    public void update(String id, String title, String date, String message){
        //修改数据
        SQLiteDatabase db = openOrCreateDatabase("Shuju.db", Context.MODE_PRIVATE, null);
        String sql = "update shuju set title=?,date=?,message=? where id=?";
        db.execSQL(sql, new String[]{title,date,message, id});
    }
    private void UpdateDialog(final String strId,final String strTitle,final String strDate,final String strMessage){
        final TableLayout tableLayout = (TableLayout)getLayoutInflater().inflate(R.layout.insert,null);
        ((EditText)tableLayout.findViewById(R.id.textTitle)).setText(strTitle);
        ((EditText)tableLayout.findViewById(R.id.textDate)).setText(strDate);
        ((EditText)tableLayout.findViewById(R.id.textMessage)).setText(strMessage);

        new AlertDialog.Builder(this).setTitle("修改便签").setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        String strNewTitle = ((EditText)tableLayout.findViewById(R.id.textTitle)).getText().toString();
                        String strNewDate = ((EditText)tableLayout.findViewById(R.id.textDate)).getText().toString();
                        String strNewMessage = ((EditText)tableLayout.findViewById(R.id.textMessage)).getText().toString();
                        Log.d("ddd","12345");
                        update(strId, strNewTitle, strNewDate, strNewMessage);
                        Query();
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }
        @Override
    public boolean onContextItemSelected(MenuItem item) {

        TextView textId = null;
        TextView texttitle = null;
        TextView textdate = null;
        TextView textmessage = null;
        AdapterView.AdapterContextMenuInfo info = null;
        View itemview = null;
        info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String _id = String.valueOf(info.position);
        int id1=Integer.parseInt(_id);
        Query();
        shuju sj = shujuList.get(id1);
        int id=Integer.parseInt(sj.getId());
        switch (item.getItemId()) {
            case 1001:
                itemview = info.targetView;
                textId = (TextView)itemview.findViewById(R.id.tv_id);
                texttitle = (TextView)itemview.findViewById(R.id.tv_title);
                textdate = (TextView)itemview.findViewById(R.id.tv_date);
                textmessage = (TextView)itemview.findViewById(R.id.tv_message);
                if(textId!=null && texttitle!=null && textdate!=null && textmessage!=null){
                    Log.d("hhh","1233333"+id+texttitle.getText().toString());
                    String strtitle=texttitle.getText().toString();
                    String strdate=textdate.getText().toString();
                    String strmessage=textmessage.getText().toString();
                    UpdateDialog(String.valueOf(id), strtitle, strdate, strmessage);
                }
                break;
            case 1002:
               System.out.println("id="+id);
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                delete(id);
                Intent j = new Intent(MainActivity.this, MainActivity.class);
                startActivity(j);
                return true;
            default:
                break;

        }

        boolean b = super.onContextItemSelected(item);
        Log.d("111111", b+"");

        return super.onContextItemSelected(item);
    }
}
