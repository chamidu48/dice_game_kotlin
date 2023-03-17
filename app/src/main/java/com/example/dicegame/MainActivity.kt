package com.example.dicegame

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnnewgame=findViewById<Button>(R.id.btnnewgame)
        val btnabout=findViewById<Button>(R.id.btnabout)

        var dialog= Dialog(this)
        var dialogBinding=layoutInflater.inflate(R.layout.popup_about,null)


        btnnewgame.setOnClickListener{
            val newgameintent=Intent(this,Game::class.java)
            startActivity(newgameintent)
        }

        btnabout.setOnClickListener{
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(true)
            dialog.show()
        }

    }
}