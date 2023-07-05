package com.bale.Estudent.Views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bale.Estudent.R
import com.bale.Estudent.databinding.ActivityAccountInfoBinding

/*This activity displays and obtains the student's details*/
class AccountInfo : AppCompatActivity() {
    private lateinit var binding:ActivityAccountInfoBinding
    private var campus:String? = null
    private var cohort:String? = null
    private var studyMode:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountInfoBinding.inflate(layoutInflater)
        getSharedPreferences(PREFERENCE_FILE_NAME, MODE_PRIVATE)
        setContentView(binding.root)
        checkUser()

        binding.save.setOnClickListener {
            saveToPreferences()
        }

        binding.Back.setOnClickListener {
            val intent = Intent(this,RegisteredUNits::class.java)
            startActivity(intent)
        }

        binding.campusRadio.setOnCheckedChangeListener { radioGroup, i ->
            when(radioGroup.checkedRadioButtonId){
               R.id.kitengela -> {
                   campus = "Kitengela"
               }
                R.id.Kisumu -> {
                    campus = "Kisumu"
                }
                R.id.Main -> {
                    campus = "Main"
                }
            }
        }
        binding.cohortRadio.setOnCheckedChangeListener { radioGroup, i ->
            when(radioGroup.checkedRadioButtonId){
                R.id.bsd -> {
                    cohort = "BSD"
                }
                R.id.Kisumu -> {
                    cohort = "BBIT"

                }
                R.id.Main -> {
                    cohort = "BIT"
                }
            }
        }
        binding.studyModeRadio.setOnCheckedChangeListener { radioGroup, i ->
            when(radioGroup.checkedRadioButtonId){
                R.id.fullTime -> {
                    studyMode = "Full-time"
                }
                R.id.partTime -> {
                    studyMode = "Part-time"
                }

            }
        }
    }

    /*This function checks if a student's detials have been previously created and saved*/
    private fun checkUser() {
        val accountPreference = getSharedPreferences(PREFERENCE_FILE_NAME, MODE_PRIVATE)
        if(accountPreference.getString(STUDENT_NAME,"").isNullOrEmpty()) binding.AccountCard.visibility = View.VISIBLE
        else displaySavedDetails()
    }

    /*This function saves the Student's details based on the fields provided*/
    private fun saveToPreferences() {

        val stdn = binding.stdn.text.toString()
        val admn = binding.adm.text.toString()

        if (stdn.isEmpty()|| admn.isEmpty() || cohort.isNullOrEmpty() || campus.isNullOrEmpty() || studyMode.isNullOrEmpty()){
            Toast.makeText(this,"All fields required",Toast.LENGTH_LONG).show()
        }
        else {
            val lPref = getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
            val prefEditor: SharedPreferences.Editor = lPref.edit()
            with(prefEditor){
                putString(STUDENT_NAME,stdn)
                putString(STUDENT_ADMISSION,admn)
                putString(CAMPUS,campus)
                putString(COHORT,cohort)
                putString(STUDY_MODE,studyMode)
                apply()
            }
            Toast.makeText(this,"Account details saved",Toast.LENGTH_SHORT).show()
            binding.AccountCard.visibility = View.GONE
            displaySavedDetails()
        }


    }

    /*This function displays saved details on thus student*/
    private fun displaySavedDetails() {
        val accountPreference = getSharedPreferences(PREFERENCE_FILE_NAME, MODE_PRIVATE)

        binding.savedName.text = accountPreference.getString(STUDENT_NAME,"")
        binding.savedAdmno.text = accountPreference.getString(STUDENT_ADMISSION,"")
        binding.savedCampus.text = accountPreference.getString(CAMPUS,"")
        binding.cohortSaved.text = accountPreference.getString(COHORT,"")
        binding.studyModeSaved.text = accountPreference.getString(STUDY_MODE,"")
    }

    companion object {
        const val PREFERENCE_FILE_NAME = "Account information"
        const val STUDENT_NAME = "Student name"
        const val STUDENT_ADMISSION = "Admission-no"
        const val CAMPUS = "Campus"
        const val COHORT = "Cohort"
        const val STUDY_MODE = "Study mode"


    }
}