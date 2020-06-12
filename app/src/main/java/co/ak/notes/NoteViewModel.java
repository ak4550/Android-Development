package co.ak.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mNoteRepository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mNoteRepository = new NoteRepository(application);
        allNotes = mNoteRepository.getAllNotes();
    }

    LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    public void insert(Note note){
        mNoteRepository.insert(note);
    }

    public void delete(Note note){
        mNoteRepository.delete(note);
    }
}
