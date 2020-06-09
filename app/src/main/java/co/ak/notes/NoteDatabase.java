package co.ak.notes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    private static NoteDatabase instance;

    public static synchronized NoteDatabase getDatabaseInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,
                    NoteDatabase.class, "myDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallbacks)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallbacks = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;
       private PopulateAsyncTask(NoteDatabase db){
           noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
           noteDao.insert(new Note("title-1", "Description-1", 10));
            return null;
        }
    }
}
