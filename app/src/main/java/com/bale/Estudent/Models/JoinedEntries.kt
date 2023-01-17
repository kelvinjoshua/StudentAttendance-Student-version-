package com.bale.Estudent.Models

data class JoinedEntries(
    val studentName:String?= null,
    val studentAdmission:String? = null,
    val unitName:String?= null,
    val unitCode:String?=null,
    val lectrurerName:String?= null,
    val modeOfStudy:String?= null,
    val campus:String?=null,
    val cohort:String?=null
) {
        companion object {
            const val STUDENT_NAME = "studentName"
            const val STUDENT_ADMISSION ="studentAdmission"
            const val UNIT_NAME = "unitName"
            const val UNIT_CODE = "unitCode"
            const val LEC_NAME = "lecturerName"
            const val STUDY_MODE = "modeOfStudy"
            const val CAMPUS = "campus"
            const val COHORT = "cohort"
        }
}
