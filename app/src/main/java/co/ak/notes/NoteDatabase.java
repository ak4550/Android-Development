package co.ak.notes;

import android.content.Context;

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

    private static volatile NoteDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    private static final String DB_NAME = "note_db";

    static final ExecutorService databaseWriterExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback mnCallbacks =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    databaseWriterExecutor.execute(() -> {
                        NoteDao noteDao = INSTANCE.noteDao();
                        noteDao.deleteAllNotes();
                        Note note = new Note("Title-1",
                                "Description-1", 10);
                        noteDao.insert(note);
                        Note note2 = new Note("Title-2",
                                "Description-2", 1);
                        noteDao.insert(note2);
                        Note note3 = new Note("Title-3Title-3Title-3Title-3Title-3Title-3Title-3Title-3Title-3",
                                "Description-3", 4);
                        noteDao.insert(note3);
                    });
                }
            };

//    static synchronized NoteDatabase getDatabaseInstance(Context context){
//        if(INSTANCE == null){
//            INSTANCE = Room.databaseBuilder(context,
//                    NoteDatabase.class, DB_NAME)
//                    .fallbackToDestructiveMigration()
//                    .addCallback(mnCallbacks)
//                    .build();
//
//        }
//        return INSTANCE;
//    }

    static NoteDatabase getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, DB_NAME)
                            .addCallback(mnCallbacks)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
