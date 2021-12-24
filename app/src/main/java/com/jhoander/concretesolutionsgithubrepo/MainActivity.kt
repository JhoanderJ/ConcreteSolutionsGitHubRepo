package com.jhoander.concretesolutionsgithubrepo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {


    private var languageSelected: String? = null
    var sendUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSearch = findViewById<TextView>(R.id.btnSearch)
        val tvSearch = findViewById<TextView>(R.id.tvSearch)
        val toolbarPrincipal = findViewById<Toolbar>(R.id.toolbarPrincipal)
        toolbarPrincipal.title = "Git Hub"
        toolbarPrincipal.setTitleTextColor(resources.getColor(R.color.white))

        btnSearch.setOnClickListener {
            languageSelected = tvSearch.text.toString()
            if (languageSelected.isNullOrEmpty()){
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(btnSearch.windowToken, 0)
                Toast.makeText(this@MainActivity, "Escribe algun nombre", Toast.LENGTH_LONG).show()

            } else{
                sendUser  = ""
                val intent = Intent(applicationContext, PopularActivity::class.java)
                intent.putExtra("language", languageSelected)
                startActivity(intent)
            }
        }

    }

    override fun onRestart() {
        super.onRestart()
        val tvSearch = findViewById<TextView>(R.id.tvSearch)
        tvSearch.text = ""
    }
}