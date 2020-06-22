package com.tvbogiapp.mycalculator

import android.content.Context
import android.content.SharedPreferences

class SaveData(context: Context) {

    private val sharedPreferences:SharedPreferences = context.getSharedPreferences("file", Context.MODE_PRIVATE)

    fun setDarkModeState(state:Boolean?){
        val editor=sharedPreferences.edit()
        editor.putBoolean("Dark", state!!)
        editor.apply()
    }

    fun setPinkModeState(state: Boolean?){
        val editor=sharedPreferences.edit()
        editor.putBoolean("Rose", state!!)
        editor.apply()
    }

    fun loadDarkModeState():Boolean?{
        val state = sharedPreferences.getBoolean("Dark", false)
        return (state)
    }

    fun loadRoseModeState():Boolean?{
        val state = sharedPreferences.getBoolean("Rose", false)
        return (state)
    }
}