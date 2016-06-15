package com.example.ashiagrawal.todotoday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    Random randNum = new Random();
    final int REQUEST_CODE = randNum.nextInt(100);
    TodoItemDatabase todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        todoList = new TodoItemDatabase(MainActivity.this);
        items = new ArrayList<String>();
//        SQLiteDatabase db = todoList.getReadableDatabase();
//        Cursor cursor = db.query("todolist", null, null, null,
//                null, null, null);
//        while (cursor.moveToNext()){
//            items.add(cursor.getString(0));
//        }
        //readFile();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        todoList.addTodo(itemText);
        //writeItems();
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                String toDelete = items.get(pos);
                todoList.deleteTodo(toDelete);
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                //writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        Intent edit = new Intent(MainActivity.this, EditItemActivity.class);
                        edit.putExtra("text", items.get(pos));
                        edit.putExtra("position", pos);
                        edit.putExtra("code", REQUEST_CODE);
                        startActivityForResult(edit, REQUEST_CODE);
                    }
                });
    }

//    private void readItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            items = new ArrayList<String>(FileUtils.readLines(todoFile));
//        } catch (IOException e) {
//            items = new ArrayList<String>();
//        }
//    }
//
//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            FileUtils.writeLines(todoFile, items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String text = data.getStringExtra("text");
            int pos = data.getIntExtra("pos", 0);
            String previous = items.get(pos);
            items.set(pos, text);
            itemsAdapter.notifyDataSetChanged();
            todoList.editTodo(text,previous);
            //writeItems();
        }
    }
}
