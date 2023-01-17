package com.bale.Estudent.Views

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bale.Estudent.Models.Presence
import com.bale.Estudent.Models.Session
import com.bale.Estudent.databinding.ActivityMarkRegisterBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MarkRegister : AppCompatActivity() {
    lateinit var b:ActivityMarkRegisterBinding
    private val db = Firebase.firestore
    lateinit var tokenList:List<Session>
    lateinit var currentDay:String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        getAllsessionCodes()
        super.onCreate(savedInstanceState)
        b = ActivityMarkRegisterBinding.inflate(layoutInflater)
        setContentView(b.root)


        b.unitName.text = intent!!.extras?.getString("unit_Name")
        b.unitCode.text = intent!!.extras?.getString("unit_code")
        b.Lecturer.text = intent!!.extras?.getString("lecturer")
        b.submitToken.setOnClickListener {
            validateCode()
        }
        b.markPresent.setOnClickListener {
            submitAttendance()
            b.markRegisterProgress.visibility= View.VISIBLE
        }

    }

    private fun submitAttendance() {
        val accountPreference = getSharedPreferences(AccountInfo.PREFERENCE_FILE_NAME, MODE_PRIVATE)
        val studentAdm = accountPreference.getString(AccountInfo.STUDENT_ADMISSION,"")
        val studentName = accountPreference.getString(AccountInfo.STUDENT_NAME,"")

        val newRegisterEntry = Presence(unitCode = intent!!.extras?.getString("unit_code")
            , lecturerName = intent!!.extras?.getString("lecturer")
            ,studentName = studentName , admissionNumber = studentAdm , dateOfSubmission = currentDay, status = "Present" )

        db.collection(ATTENDANCE_LIST).add(newRegisterEntry).addOnSuccessListener {
            res ->
            b.markRegisterProgress.visibility = View.GONE
            Toast.makeText(this,"Succesful",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,RegisteredUNits::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateCode() {
        val res = b.SessionCode.text.toString()
        val tokenFound = tokenList.filter {
            it.session_code.equals(res)
        }
        if (tokenFound.isNotEmpty()){
            passToken(tokenFound[0])
        }
        else {
            b.SessionLayout.error = "Invalid session code"

        }
      //Toast.makeText(this,tokenFound[0].toString(),Toast.LENGTH_SHORT).show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun passToken(s: Session) {
        val timeFormat =  DateTimeFormatter.ofPattern("HH:mm")
        val currTime = LocalDateTime.now().format(timeFormat)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        currentDay = LocalDateTime.now().format(formatter)

        val stime = s.expiry_Time
        val delimiter = ":"
        val timeArray = stime!!.split(delimiter)
        val hourElement = timeArray[0].toInt()
        val minuteElement = timeArray[1].toInt()

        val currTimeArray = currTime!!.split(delimiter)
        val currhourElement = currTimeArray[0].toInt()
        val currminuteElement = currTimeArray[1].toInt()

        if (currhourElement <= hourElement && currminuteElement<= minuteElement){
            b.markPresent.visibility = View.VISIBLE
        }
        else {
            Toast.makeText(this,"Token timeout",Toast.LENGTH_SHORT).show()
        }


        //val d = currTime as LocalDateTime
        //Toast.makeText(this,d.toString(),Toast.LENGTH_SHORT).show()

    }

    private fun getAllsessionCodes() {
        db.collection(SESSION_COLLECTION).get().addOnSuccessListener {
            result ->
           // val res = result.toObjects<SessionToken>()
           // Toast.makeText(this,res.toString(),Toast.LENGTH_SHORT).show()
            tokenList = result.toObjects()
           //Toast.makeText(this,tokenList.toString(),Toast.LENGTH_SHORT).show()
        }

    }


    companion object {
        const val SESSION_COLLECTION = "Session codes"
        const val ATTENDANCE_LIST = "Unit attendance"
    }

}