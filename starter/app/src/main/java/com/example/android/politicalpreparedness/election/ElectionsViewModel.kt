package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDataSource
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class ElectionsViewModel(
    private val electionRepository: ElectionDataSource
) : ViewModel() {

    private val _upcomingElectionsList = MutableLiveData<List<Election>>()
    val upcomingElectionsList: LiveData<List<Election>>
        get() = _upcomingElectionsList

    init {
        getElections()
    }

    private fun getElections() {
        viewModelScope.launch {
            try {
                val elections = electionRepository.getElections()
                _upcomingElectionsList.value = elections
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("network error", it) }
            }
        }
    }

}
