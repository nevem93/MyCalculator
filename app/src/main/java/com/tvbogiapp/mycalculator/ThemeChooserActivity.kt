package com.tvbogiapp.mycalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_theme_chooser.*

class ThemeChooserActivity : AppCompatActivity() {
    private lateinit var saveData: SaveData


    override fun onCreate(savedInstanceState: Bundle?) {

        saveData = SaveData(this)
        when {
            saveData.loadDarkModeState() == true -> {
                setTheme(R.style.DarkTheme)
            }
            saveData.loadRoseModeState() == true -> {
                setTheme(R.style.RoseTheme)}
            else -> setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_chooser)

        btnLightMode.setOnClickListener {
            saveData.setDarkModeState(false)
            saveData.setPinkModeState(false)
            restartApplication()
        }

        btnDarkMode.setOnClickListener {
            saveData.setDarkModeState(true)
            saveData.setPinkModeState(false)
            restartApplication()
        }

        btnPinkMode.setOnClickListener {
            saveData.setDarkModeState(false)
            saveData.setPinkModeState(true)
            restartApplication()
        }



    }

    private fun restartApplication(){
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}