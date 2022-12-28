package com.example.interviewtask


object Utils {

    fun convertString(id: Int): String {
        return ImageApplication.INSTANCE.getString(id)
    }
}