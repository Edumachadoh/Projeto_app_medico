import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.up.clinica_digital.Dao.DoctorDao
import com.up.clinica_digital.Dao.ForumCommentDao
import com.up.clinica_digital.Dao.ForumTopicDao
import com.up.clinica_digital.Dao.PatientDao
import com.up.clinica_digital.models.Doctor
import com.up.clinica_digital.models.Patient
import com.up.clinica_digital.models.ForumTopic
import com.up.clinica_digital.models.ForumComment

@Database(
    entities = [
        Doctor::class,
        Patient::class,
        ForumTopic::class,
        ForumComment::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun doctorDao(): DoctorDao
    abstract fun patientDao(): PatientDao
    abstract fun forumTopicDao(): ForumTopicDao
    abstract fun forumCommentDao(): ForumCommentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
