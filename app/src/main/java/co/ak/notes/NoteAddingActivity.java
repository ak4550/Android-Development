package co.ak.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class NoteAddingActivity extends AppCompatActivity {

    NumberPicker numberPicker;
    EditText edtTitle, edtDescription;
    TextView txtPriority;

    public static final String NOTE_TITLE =
            "co.ak.notes.title";
    public static final String NOTE_DESCRIPTION =
            "co.ak.notes.description";
    public static final String NOTE_PRIORITY =
            "co.ak.notes.priority";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_adding);

        numberPicker = findViewById(R.id.numberPicker);
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);


        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.save_note:
                String title = edtTitle.getText().toString();
                String Description = edtDescription.getText().toString();
                int priority = numberPicker.getValue();
                if(title.trim().isEmpty() || Description.trim().isEmpty()){
                    Toast.makeText(this, "Please enter the title and description",
                            Toast.LENGTH_SHORT).show();

                }else{
                    Intent data = new Intent();
                    data.putExtra(NOTE_TITLE, title);
                    data.putExtra(NOTE_DESCRIPTION, Description);
                    data.putExtra(NOTE_PRIORITY, priority);
                    setResult(RESULT_OK, data);
                    finish();
                }
            //    Toast.makeText(this, "yo", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}