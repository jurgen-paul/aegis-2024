package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        AuditLogEntity::class,
        TelemetryPacketEntity::class,
        ThreatIncidentEntity::class,
        NeuralCommandEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AgisDatabase : RoomDatabase() {
    abstract fun agisDao(): AgisDao

    companion object {
        @Volatile
        private var INSTANCE: AgisDatabase? = null

        fun getDatabase(context: Context): AgisDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AgisDatabase::class.java,
                    "agis_2045_quantum_enclave.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
