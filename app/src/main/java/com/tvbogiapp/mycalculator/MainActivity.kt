package com.tvbogiapp.mycalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
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
        setContentView(R.layout.activity_main)

        btnThemes.setOnClickListener {
            startActivity(Intent(this, ThemeChooserActivity::class.java))
        }

    }

    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumeric=true

    }

    fun onBackspace(view: View){
        var value = tvInput.text.toString()
        if (value.isNotEmpty()){
            value = value.substring(0,value.length - 1)
            tvInput.text = value
            if (value.isNotEmpty()){
                lastNumeric = true
            }else if(value.isEmpty()) {lastNumeric = false}
        }
    }

    fun onClear(view: View){
        tvInput.text= ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onOperatorSubtract(view: View){
        tvInput.append((view as Button).text)
        lastNumeric = false
        lastDot = true

    }

    private fun removeZeroAfterDot(result:String) : String {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length - 2)
        return value
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if (value.startsWith("-")) {
            false
        }else{
            (value.contains("/") || value.contains("*")
                    || value.contains("+")|| value.contains("-") )
        }
    }

    fun onEqual(view: View){
        if (lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (prefix.isNotEmpty()){
                            one = prefix +one
                        }

                        tvInput.text = removeZeroAfterDot ((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (prefix.isNotEmpty()){
                            one = prefix +one
                        }

                        tvInput.text = removeZeroAfterDot ((one.toDouble() * two.toDouble()).toString())
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (prefix.isNotEmpty()){
                            one = prefix +one
                        }

                        tvInput.text = removeZeroAfterDot ((one.toDouble() / two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (!prefix.isEmpty()){
                            one = prefix +one
                        }

                        tvInput.text = removeZeroAfterDot ((one.toDouble() + two.toDouble()).toString())
                    }
                }

            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}
