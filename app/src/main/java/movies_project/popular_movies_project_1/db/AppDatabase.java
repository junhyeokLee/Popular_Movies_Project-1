package movies_project.popular_movies_project_1.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import movies_project.popular_movies_project_1.model.Movie;

@Database(entities = {Movie.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_Name = "movies";
    private static AppDatabase instance;
    private static final Object OBJECT = new Object();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (OBJECT){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_Name).build();
                }
            }
        }
        return instance;
    }

    public abstract MovieDAO movieDAO();
}
