package co.ak.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static co.ak.notes.NoteAddingActivity.NOTE_DESCRIPTION;
import static co.ak.notes.NoteAddingActivity.NOTE_PRIORITY;
import static co.ak.notes.NoteAddingActivity.NOTE_TITLE;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    public static final int REQUEST_CODE = 100;
    NoteViewModel viewModel;
    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        container = findViewById(R.id.container);

        fab.setOnClickListener(v -> loadNoteAddingActivity());

        // Setting up recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);


        viewModel = new ViewModelProvider(this)
                .get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this,
                new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        adapter.setList(notes);
                    }
                });

    }

    private void loadNoteAddingActivity(){
        Intent intent = new Intent(this, NoteAddingActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch(requestCode){
//            case REQUEST_CODE:
//                if(resultCode == RESULT_OK){
//                    String title = data.getStringExtra(NOTE_TITLE);
//                    String description = data.getStringExtra(NOTE_DESCRIPTION);
//                    int priority = data.getIntExtra(NOTE_PRIORITY, 1);
//                    Note note = new Note(title, description, priority);
//                    if(note == null){
//                        Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
//                    }else {
//                    viewModel.insert(note);
//                    Snackbar bar = Snackbar.make(container,
//                            "Note Saved", Snackbar.LENGTH_SHORT);
//                    bar.show();}
//                }
//        }

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(NOTE_TITLE);
            String description = data.getStringExtra(NOTE_DESCRIPTION);
            int priority = data.getIntExtra(NOTE_PRIORITY, 1);
            Note note = new Note(title, description, priority);
            viewModel.insert(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
      }
}