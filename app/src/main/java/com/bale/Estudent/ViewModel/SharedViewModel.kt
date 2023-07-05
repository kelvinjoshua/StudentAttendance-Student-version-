package com.bale.Estudent.ViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bale.Estudent.Models.lecturerUnitEntry
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
/*Ignore this class*/
class SharedViewModel: ViewModel(){
    val db = Firebase.firestore
    val allUnits = MutableLiveData<MutableList<lecturerUnitEntry>>()
   // val all:MutableList<lecturerUnitEntry>() = null

    fun getAllAvailableUnits(co:String,ca:String,ms:String){
        viewModelScope.launch {
            var l:MutableList<lecturerUnitEntry>? = null
            db.collection("Units by lecturer").whereEqualTo("cohort",co).whereEqualTo("campus",ca).whereEqualTo("studyMode",ms).get().addOnSuccessListener {
                    documents ->
                for (doc in documents){

                    val newObj = doc.toObject<lecturerUnitEntry>()
                    l!!.add(newObj)
                    allUnits.value = l
                    //Log.d("Result", doc.data.toString())
                  //  Log.d("Result", newObj.toString())
                    //allUnits?.add(newObj)
                   // Toast.makeText(this,newObj.lecturer, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}