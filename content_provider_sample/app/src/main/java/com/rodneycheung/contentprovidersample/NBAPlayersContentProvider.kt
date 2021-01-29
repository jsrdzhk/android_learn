package com.rodneycheung.contentprovidersample

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.rodneycheung.contentprovidersample.persist.NbaDbHelper

class NBAPlayersContentProvider : ContentProvider() {
    private lateinit var dbHelper: NbaDbHelper

    private val playerTable = 0
    private val authority = "com.rodneycheung.contentprovidersample.provider"

    private val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authority, "player", playerTable)
        matcher
    }

    override fun onCreate(): Boolean = context?.let {
        dbHelper = NbaDbHelper(it, "nba.db", 1)
        true
    } ?: false

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            playerTable -> "vnd.android.cursor.dir/vnd.com.rodneycheung.contentprovidersample.provider.player"
            else -> null
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (uriMatcher.match(uri)) {
            playerTable -> dbHelper.deletePlayer(selection, selectionArgs)
            else -> 0
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (uriMatcher.match(uri)) {
            playerTable -> {
                Uri.parse("content://$authority/player/${dbHelper.insertPlayer(values)}")
            }
            else -> null
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            playerTable -> dbHelper.queryPlayer(projection, selection, selectionArgs, sortOrder)
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return when (uriMatcher.match(uri)) {
            playerTable -> dbHelper.updatePlayer(values, selection, selectionArgs)
            else -> 0
        }
    }
}