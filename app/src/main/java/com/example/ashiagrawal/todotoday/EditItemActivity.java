package com.example.ashiagrawal.todotoday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String item = getIntent().getStringExtra("text");
        position = getIntent().getIntExtra("position", 0);
        setContentView(R.layout.activity_edit_item);
        TextView text = (TextView) findViewById(R.id.textEdit);
        text.append(item);
    }

    public void onEdit(View view){
        EditText etName = (EditText) findViewById(R.id.textEdit);
        Intent data = new Intent();
        data.putExtra("text", etName.getText().toString());
        data.putExtra("pos", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
