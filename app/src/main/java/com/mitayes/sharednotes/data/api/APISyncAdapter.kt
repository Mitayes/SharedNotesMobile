package com.mitayes.sharednotes.data.api

import com.mitayes.sharednotes.StaticVariables
import com.mitayes.sharednotes.domain.ISyncServerAdapter
import com.mitayes.sharednotes.domain.RootNote
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class APISyncAdapter : ISyncServerAdapter {
    private val client = OkHttpClient()
    private val baseUrl: String = StaticVariables.SYNC_SERVER_URL
    override fun test(): Observable<Response> {
        val jsonRequest = "some request"
        val JSON = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = jsonRequest.toRequestBody(JSON)
        val fullUrl: String = baseUrl + StaticVariables.SYNC_SERVER_TEST_ROUTE + "get_all"
        val request = Request.Builder().url(fullUrl).post(body).build()

        val result = Observable.fromCallable() {
            client.newCall(request).execute()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return result
    }

    override fun addNote(note: RootNote): Completable {
        val request = getRequest(note, StaticVariables.SYNC_SERVER_NOTE_ROUTE, "add")

        val result = Completable.fromCallable() {
            client.newCall(request).execute()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return result
    }

    override fun editNote(note: RootNote): Completable {
        val request = getRequest(note, StaticVariables.SYNC_SERVER_NOTE_ROUTE, "edit")

        val result = Completable.fromCallable() {
            client.newCall(request).execute()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return result
    }

    override fun removeNote(note: RootNote): Completable {
        val request = getRequest(note, StaticVariables.SYNC_SERVER_NOTE_ROUTE, "remove")

        val result = Completable.fromCallable {
            client.newCall(request).execute()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return result
    }

    override fun syncNotes(notesList: MutableList<RootNote>): Completable {
        TODO("Not yet implemented")
    }

    private fun getRequest(note: RootNote, route: String, action: String): Request {
        var noteObject = APIRootNote(note, StaticVariables.SYNC_SERVER_USER_UUID)
        val jsonRequest = noteObject.toJsonString()
        val jsonData = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = jsonRequest.toRequestBody(jsonData)
        val fullUrl: String = baseUrl + route + action
        return Request.Builder().url(fullUrl).post(body).build()
    }

}