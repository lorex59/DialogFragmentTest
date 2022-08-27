package com.example.dialogfragmenttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var list = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var temp = findViewById<Button>(R.id.button)
        temp.setOnClickListener {
            show()
        }
        var temp2 = findViewById<Button>(R.id.button2).setOnClickListener {
            Log.d("Tag", list.toString())
        }
    }


    private fun show() {
        MultipleChoiceDialogFragment.show(supportFragmentManager, list)
    }
    private fun setupListener() {
        MultipleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.list = it
        }
    }
}