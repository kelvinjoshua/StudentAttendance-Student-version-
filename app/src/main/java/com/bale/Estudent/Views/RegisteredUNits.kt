package com.bale.Estudent.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.bale.Estudent.Adpaters.AllUnitsAdapt
import com.bale.Estudent.Models.JoinedEntries
import com.bale.Estudent.Models.lecturerUnitEntry
import com.bale.Estudent.databinding.ActivityRegisteredUnitsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

/*This class displays all units registered by this student*/
@Suppress("DEPRECATION")
class RegisteredUNits : AppCompatActivity() {
    val db = Firebase.firestore
   lateinit var a:AllUnitsAdapt
        //  val referenceVm:SharedViewModel by
    var  allUnits:MutableList<lecturerUnitEntry>?=null
    private lateinit var b:ActivityRegisteredUnitsBinding
    lateinit var selectedUnit:lecturerUnitEntry
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisteredUnitsBinding.inflate(layoutInflater)
        setContentView(b.root)

        displayAllunits()
        getUnits()
        //inflateRadio()
        b.Register.setOnClickListener {
            b.unitsCard.visibility = View.VISIBLE
           // getUnits()
        }
        b.cancel.setOnClickListener {
            b.unitsCard.visibility = View.GONE
           // b.unitList.clearCheck()
        }
        b.submitEntry.setOnClickListener {
            registerStudentUnit()
        }
        b.accountPage.setOnClickListener {
            val intent = Intent(this,AccountInfo::class.java)
            startActivity(intent)
        }

    }

    private fun displayAllunits() {
        val accountPreference = getSharedPreferences(AccountInfo.PREFERENCE_FILE_NAME, MODE_PRIVATE)
        val studentAdm = accountPreference.getString(AccountInfo.STUDENT_ADMISSION,"")
         val query = db.collection(REGISTRATION_COLLECTION).whereEqualTo("studentAdmission",studentAdm).get().addOnSuccessListener {
            ls ->
            val res = ls.toObjects<JoinedEntries>()
             if(res.size >0 ){
                 b.placeholder.visibility = View.GONE
                 b.fetchRegisteredUnits.visibility = View.GONE
                    a = AllUnitsAdapt(res)
                 b.UnitsrecyclerView.apply {
                     adapter = a
                 }
             }
            // Log.d("all",res.toString())

        }


               /*for (v in value){
                   Log.d("all",v.data.toString())

               }*/

         //   Log.d("all",value.toString())

        /*adapter = object  : UnitAdapter(query,this@RegisteredUNits){
            override fun getItemCount(): Int {
                val r = super.getItemCount()
                if(r>0){
                        b.placeholder.visibility = View.GONE
                }
                else {
                    b.placeholder.visibility = View.VISIBLE
                    //binding.fetchUnitsProgress.visibility = View.GONE
                }
                //Toast.makeText(this@SubmittedUnits,r.toString(),Toast.LENGTH_SHORT).show()
                return r
            }
        }
      b.UnitsrecyclerView.adapter = adapter
        b.UnitsrecyclerView.layoutManager = LinearLayoutManager(this)*/
    }

    private fun registerStudentUnit() {
        val accountPreference = getSharedPreferences(AccountInfo.PREFERENCE_FILE_NAME, MODE_PRIVATE)
        val studentName = accountPreference.getString(AccountInfo.STUDENT_NAME,"")
        val campus = accountPreference.getString(AccountInfo.CAMPUS,"")
        val modeOfStudy =accountPreference.getString(AccountInfo.STUDY_MODE,"")
        val studentAdm = accountPreference.getString(AccountInfo.STUDENT_ADMISSION,"")
        val cohort = accountPreference.getString(AccountInfo.COHORT,"")

        val studentUnitRegistration = JoinedEntries(studentName = studentName!!, unitName = selectedUnit.unit_name!!, unitCode = selectedUnit.unit_code!!, lectrurerName = selectedUnit.lecturer!!, modeOfStudy = modeOfStudy!!, campus = campus!!, cohort = cohort!!, studentAdmission = studentAdm!!)
        db.collection(REGISTRATION_COLLECTION).add(studentUnitRegistration).addOnSuccessListener {
            _ ->
            Toast.makeText(this,cohort+" "+"Unit registered succesfully",Toast.LENGTH_SHORT).show()
            b.unitsCard.visibility = View.GONE
            displayAllunits()
        }
    }

    private fun inflateRadio(ls: List<lecturerUnitEntry>) {
        for (x in ls){
            val newRadio = RadioButton(this)
            newRadio.id = ls.indexOf(x)
            newRadio.text = x.unit_name
            newRadio.setTextAppearance(this, com.google.android.material.R.style.TextAppearance_AppCompat_Medium)
            b.unitList.addView(newRadio)
        }
    }

    private fun getUnits() {
        val accountPreference = getSharedPreferences(AccountInfo.PREFERENCE_FILE_NAME, MODE_PRIVATE)
        val cohort = accountPreference.getString(AccountInfo.COHORT,"")
        val campus = accountPreference.getString(AccountInfo.CAMPUS,"")
        val modeOfStudy =accountPreference.getString(AccountInfo.STUDY_MODE,"")
        db.collection("Units by lecturer").whereEqualTo("cohort",cohort).whereEqualTo("campus",campus).whereEqualTo("studyMode",modeOfStudy).get().addOnSuccessListener {
            documents ->
            val ls= documents.toObjects<lecturerUnitEntry>()
            b.loadingProgress.visibility = View.GONE
            inflateRadio(ls)
            setRadioListener(ls)
            Log.d("Result", ls.toString())

            /*for (doc in documents){


             val newObj = doc.toObject<lecturerUnitEntry>()
                //Log.d("Result", doc.data.toString())
                Log.d("Result", newObj.toString())
              // allUnits!!.add(newObj)
             //  Toast.makeText(this,newObj.lecturer,Toast.LENGTH_SHORT).show()
             //  Toast.makeText(this,allUnits!!.size.toString(),Toast.LENGTH_SHORT).show()


            }*/
        }
        /*db.collection("Units by lecturer").whereEqualTo(COH,cohort).addSnapshotListener { value, error ->
            for (v in value!!){
                val unit = v.data
                allUnits?.add(unit.toString())
                Toast.makeText(this,allUnits.toString(),Toast.LENGTH_SHORT).show()
            }
            //setListener()
        }*/

    }

    private fun setRadioListener(ls: List<lecturerUnitEntry>) {
        b.unitList.setOnCheckedChangeListener { radioGroup, i ->
            val elementIndex = radioGroup.checkedRadioButtonId
            selectedUnit = ls[elementIndex]
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

        companion object {
            const val COH = "cohort"
            const val STDM = "studyMode"
            const val CAM =  "campus"
            const val UNIT_NAME ="unit_name"
            const val STUDENT_NAME = "Student name"
            const val STUDENT_ADMISSION = "Admission-no"
            const val REGISTRATION_COLLECTION = "Registered students by unit"
        }

    override fun onDestroy() {
        super.onDestroy()
       // selectedUnit = null

    }
}