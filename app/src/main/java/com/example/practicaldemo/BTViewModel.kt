package com.example.practicaldemo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.practicaldemo.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class BTViewModel() : ViewModel() {

//    val btItemList = mutableListOf<BTItems>()

    val btItemList = MutableLiveData<List<BTItems>>()


    fun getbt(context: Context) {
        val executor: Executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val result =
                AppDatabase
                    .getDatabase(context).btDao().getBTList()
            Log.d("<>", "getbt: "+result.size)
            btItemList.postValue(result)
//            btItemList.addAll(result)

        }
    }

    fun insert(btItemsList: List<BTItems>, context: Context) {
        for (bt in btItemsList) {
            AppDatabase.getDatabase(context).btDao().insertBT(bt)
        }
    }
}

