package com.example.ws2022creativecodelab3

import android.content.ContentValues
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
        private const val affirmationTable = "affirmations"
        private const val affirmationId = "a_id"
        private const val affirmationText = "affirmationText"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $characterTable($characterId INTEGER PRIMARY KEY, $characterImage BLOB, $characterName TEXT);")
        db?.execSQL("CREATE TABLE IF NOT EXISTS $affirmationTable($affirmationId INTEGER PRIMARY KEY, $affirmationText TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE $characterTable")
        db?.execSQL("DROP TABLE $affirmationTable")
        onCreate(db)
    }

    fun addCharacter(image: ByteArray, name: String) {
        val db = this.writableDatabase
        val ctv = ContentValues()
        ctv.put(characterImage, image)
        ctv.put(characterName, name)

        db.insert(characterTable, characterId, ctv)
    }

    fun updateCharacter(id: String, image: ByteArray, name: String): Int {
        val db = this.writableDatabase
        val ctv = ContentValues()
        ctv.put(characterImage, image)
        ctv.put(characterName, name)

        return db.update(characterTable, ctv, "$characterId = ?", arrayOf(id))
    }

    fun deleteCharacter(id: String): Int {
        val db = this.writableDatabase
        return db.delete(characterTable, "$characterId = ?", arrayOf(id))
    }

    data class Characters(
        val ids: MutableList<String>,
        val images: MutableList<ByteArray>,
        val names: MutableList<String>
    )

    fun getAllCharacters(): Characters {
        val db = this.readableDatabase
        val allIds = mutableListOf<String>()
        val allImages = mutableListOf<ByteArray>()
        val allNames = mutableListOf<String>()

        val cursor = db.rawQuery("SELECT * FROM $characterTable", null)

        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(characterId)
            val imageIndex = cursor.getColumnIndex(characterImage)
            val nameIndex = cursor.getColumnIndex(characterName)

            if (idIndex >= 0) {
                allIds.add(cursor.getString(idIndex))
                allImages.add(cursor.getBlob(imageIndex))
                allNames.add(cursor.getString(nameIndex))
            }
        }

        cursor.close()
        return Characters(allIds, allImages, allNames)
    }

    data class Character(
        val image: ByteArray,
        val name: String
    )

    fun getOneCharacter(id: String): Character {
        val db = this.readableDatabase
        var image = byteArrayOf()
        var name = toString()

        val cursor = db.rawQuery("SELECT * FROM $characterTable WHERE $characterId = $id", null)

        while (cursor.moveToNext()) {
            val imageIndex = cursor.getColumnIndex(characterImage)
            val nameIndex = cursor.getColumnIndex(characterName)

            if (imageIndex >= 0) {
                image = cursor.getBlob(imageIndex)
                name = cursor.getString(nameIndex)
            }
        }

        cursor.close()
        return Character(image, name)
    }

    fun addAffirmation(text: String) {
        val db = this.writableDatabase
        val ctv = ContentValues()
        ctv.put(affirmationText, text)

        db.insert(affirmationTable, affirmationId, ctv)
    }

    fun deleteAffirmation(id: String): Int {
        val db = this.writableDatabase
        return db.delete(affirmationTable, "$affirmationId = ?", arrayOf(id))
    }

    fun getAllAffirmations(): Pair<MutableList<String>, MutableList<String>> {
        val db = this.readableDatabase
        val allIds = mutableListOf<String>()
        val allTexts = mutableListOf<String>()

        val cursor = db.rawQuery("SELECT * FROM $affirmationTable", null)

        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(affirmationId)
            val textIndex = cursor.getColumnIndex(affirmationText)

            if (textIndex >= 0) {
                allIds.add(cursor.getString(idIndex))
                allTexts.add(cursor.getString(textIndex))
            }
        }

        cursor.close()
        return Pair(allIds, allTexts)
    }
}