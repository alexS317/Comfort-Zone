package com.example.ws2022creativecodelab3

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, dbName, null, 1) {

    companion object {
        private const val dbName = "DBApp"
        private const val characterTable = "characters"
        private const val characterId = "c_id"
        private const val characterImage = "characterImage"
        private const val characterName = "characterName"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $characterTable($characterId INTEGER PRIMARY KEY, $characterImage BLOB, $characterName TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE $characterTable")
        onCreate(db)
    }
}