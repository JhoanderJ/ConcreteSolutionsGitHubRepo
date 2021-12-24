package com.jhoander.concretesolutionsgithubrepo

import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.jhoander.concretesolutionsgithubrepo.adapter.JsonDataAdapter
import com.jhoander.concretesolutionsgithubrepo.domain.models.JsonDataModel
import com.jhoander.concretesolutionsgithubrepo.remote.RemoteFetch
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.ArrayList

class PopularActivity : AppCompatActivity() {

    private var language: String? = null
    private var pagCurrent = 0
    private lateinit var lvPopular: ListView
    private lateinit var  pbPopular : RelativeLayout
    private var numResult = 0
    private lateinit var listPopular: ArrayList<JsonDataModel> 
    private lateinit var toolbarList: Toolbar
    private lateinit var adapter: JsonDataAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular)

       
        val i = intent
        language = i.getStringExtra("language")

        pbPopular = findViewById(R.id.pbCharacter)
        lvPopular = findViewById(R.id.lvPopular)
        toolbarList = findViewById(R.id.toolbarPopular)


        toolbarList.title = language
        toolbarList.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbarList)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbarList.navigationIcon!!.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)


        listPopular = ArrayList<JsonDataModel>()
        adapter = JsonDataAdapter(this, listPopular)
        lvPopular.adapter = adapter

        pagCurrent = 1

        listPopular.clear()
        val popularesURL = "search/repositories?q=language:$language&sort=stars&page=$pagCurrent"
        searchPopularGit(popularesURL)
    }

    private fun searchPopularGit(popular: String) {
        val handler = Handler()
        pbPopular.visibility = View.VISIBLE
        object : Thread() {
            override fun run() {
                val json = RemoteFetch.getJSON(popular)
                if (json == null) {
                    handler.post {
                        Toast.makeText(this@PopularActivity, "Lenguaje no encontrado , por favor intenta de nuevo!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    handler.post { renderPopular(json) }
                }
            }
        }.start()
    }

    private fun renderPopular(json: JSONObject?) {
        try {
            pbPopular.visibility = View.GONE
            var datos: String? = ""
            datos = json?.getString("items")
            val dadosArray = JSONArray(datos)
            var pos = 0
            val sizeList = dadosArray.length()
            while (pos < sizeList) {
                val datosObject = dadosArray.getJSONObject(pos)
                val datosUser = JSONObject(datosObject.getString("owner"))
                val nameRepo = datosObject.getString("name")
                val descriptionRepo = datosObject.getString("description")
                val nameUser = datosUser.getString("login")
                val photoUser = datosUser.getString("avatar_url")
                val forksRepo = datosObject.getInt("forks_count")
                val starsRepo = datosObject.getInt("stargazers_count")
                listPopular.add(JsonDataModel(nameRepo, descriptionRepo, nameUser, photoUser, forksRepo,photoUser, starsRepo))
                pos++
                if (pos == sizeList) {
                    pagCurrent++
                    if (pagCurrent == 35) {
                        break
                    }
                    val handler = Handler()
                    handler.postDelayed({
                        val popularesURL = "search/repositories?q=language:$language&sort=stars&page=$pagCurrent"
                        searchPopularGit(popularesURL)
                    }, 6000)
                }
            }
            adapter.notifyDataSetChanged()
            numResult = listPopular.size
            val pluralResultado: String = if (numResult > 1) {
                "results"
            } else {
                "result"
            }
            toolbarList.title = language!!.replace("+", " ") + " (" + numResult + " " + pluralResultado + ")"
        } catch (e: Exception) {
            Log.e("Concrete Project", "Ha ocurrido un error")
        }
    }
}