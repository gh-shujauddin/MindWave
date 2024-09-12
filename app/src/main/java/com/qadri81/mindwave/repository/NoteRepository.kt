package com.qadri81.mindwave.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qadri81.mindwave.api.NoteApi
import com.qadri81.mindwave.models.NoteRequest
import com.qadri81.mindwave.models.NoteResponse
import com.qadri81.mindwave.util.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteApi: NoteApi) {
    private val _notesLivedata = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData: LiveData<NetworkResult<List<NoteResponse>>>
        get() = _notesLivedata

    private val _statusLivedata = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLivedata

    suspend fun getNotes() {
        _notesLivedata.postValue(NetworkResult.Loading())
        val response = noteApi.getNotes()
        try {
            if (response.isSuccessful && response.body() != null) {
                _notesLivedata.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorBody = JSONObject(response.errorBody()!!.charStream().readText())
                _notesLivedata.postValue(NetworkResult.Error(errorBody.getString("message")))
            } else {
                _notesLivedata.postValue(NetworkResult.Error("Something went wrong"))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            _notesLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun addNote(noteRequest: NoteRequest) {
        _statusLivedata.postValue(NetworkResult.Loading())
        val response = noteApi.addNote(noteRequest)
        handleResponse(response, "Note added")

    }

    suspend fun deleteNote(noteId: String) {
        _statusLivedata.postValue(NetworkResult.Loading())
        val response = noteApi.deleteNote(noteId)
        handleResponse(response, "Note deleted")
    }

    suspend fun updateNote(noteId: String, noteRequest: NoteRequest) {
        _statusLivedata.postValue(NetworkResult.Loading())
        val response = noteApi.updateNote(noteId, noteRequest)
        handleResponse(response, "Note updated")
    }

    private fun handleResponse(response: Response<NoteResponse>, message: String) {
        try {
            if (response.isSuccessful && response.body() != null) {
                _statusLivedata.postValue(NetworkResult.Success(message))
            } else {
                _statusLivedata.postValue(NetworkResult.Error("Something went wrong"))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            _statusLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}