package com.brand.store.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_MOBILE TEXT," +
                "$COLUMN_DOB TEXT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_PASSWORD TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(user: User): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_MOBILE, user.mobile)
            put(COLUMN_DOB, user.dob)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase"
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_MOBILE = "mobile"
        const val COLUMN_DOB = "dob"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
    }
    // ... (previous code)
        // ... (previous code)
    fun getUserByEmail(email: String): User? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        var user: User? = null
        try {
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndex(COLUMN_ID)
                val usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME)
                val mobileIndex = cursor.getColumnIndex(COLUMN_MOBILE)
                val dobIndex = cursor.getColumnIndex(COLUMN_DOB)
                val passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD)

                val id = cursor.getInt(idIndex)
                val username = cursor.getString(usernameIndex)
                val mobile = cursor.getString(mobileIndex)
                val dob = cursor.getString(dobIndex)
                val password = cursor.getString(passwordIndex)

                user = User(id, username, mobile, dob, email, password)
            }
        } catch (e: Exception) {
            // Handle any exceptions that might occur while querying the database
            e.printStackTrace()
        } finally {
            cursor.close()
            db.close()
        }

        return user
    }



    fun updatePassword(email: String, newPassword: String) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_PASSWORD, newPassword)
            }
            db.update(TABLE_NAME, values, "$COLUMN_EMAIL = ?", arrayOf(email))
            db.close()
        }
    }


