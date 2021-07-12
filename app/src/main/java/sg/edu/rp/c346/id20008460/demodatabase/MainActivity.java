package sg.edu.rp.c346.id20008460.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    Button btnInsert , btnGetTasks , btnAscend , btnDescend;
    TextView tvResults;

    EditText etDescription , etDate;

    ListView lvResults;
    ArrayAdapter<task> aa;
    ArrayList<task> al;

    boolean asc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);

        btnAscend = findViewById(R.id.btnAscend);
        btnDescend = findViewById(R.id.btnDescending);

        lvResults = findViewById(R.id.listView);

        etDate = findViewById(R.id.etDate);
        etDescription = findViewById(R.id.etDescription);

        al = new ArrayList<>();
        aa = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, al);
        lvResults.setAdapter(aa);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                db.insertTask(etDescription.getText().toString(),etDate.getText().toString());
            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                ArrayList<String> tasks = dbh.getTaskContent();

                String txt = "";
                for (int i = 0; i < tasks.size(); i ++) {
                    txt += i + ". " + tasks.get(i) + "\n";
                }
                tvResults.setText(txt);

                al.clear();
                al.addAll(dbh.getTasks(asc));

                asc = !asc;
                aa.notifyDataSetChanged();
            }
        });

    }
}