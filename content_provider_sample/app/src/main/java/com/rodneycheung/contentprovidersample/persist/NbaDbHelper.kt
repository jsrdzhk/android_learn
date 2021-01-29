package com.rodneycheung.contentprovidersample.persist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NbaDbHelper(private val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val TABLE_PLAYER = "player"

    private val COLUMN_NAME = "name"
    private val COLUMN_SCORE = "score"
    private val COLUMN_REBOUND = "rebound"
    private val COLUMN_ASSISTS = "assists"
    private val COLUMN_BLOCK = "block"
    private val COLUMN_STEALS = "steals"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_PLAYER ($COLUMN_NAME text primary key, $COLUMN_SCORE real,$COLUMN_REBOUND real,$COLUMN_ASSISTS real,$COLUMN_BLOCK real,$COLUMN_STEALS real)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $TABLE_PLAYER")
        onCreate(db)
    }

    fun insertPlayer(values: ContentValues?): Long {
        return writableDatabase.insert(TABLE_PLAYER, null, values)
    }

    fun updatePlayer(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return writableDatabase.update(TABLE_PLAYER, values, selection, selectionArgs)
    }

    fun deletePlayer(selection: String?, selectionArgs: Array<String>?): Int {
        return writableDatabase.delete(TABLE_PLAYER, selection, selectionArgs)
    }

    fun queryPlayer(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor {
        return readableDatabase.query(
            TABLE_PLAYER,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }
}