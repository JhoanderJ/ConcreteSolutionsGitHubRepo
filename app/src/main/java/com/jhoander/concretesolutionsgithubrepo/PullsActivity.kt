package com.jhoander.concretesolutionsgithubrepo

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhoander.concretesolutionsgithubrepo.adapter.CustomAdapter
import com.jhoander.concretesolutionsgithubrepo.data.GetDataServiceApi
import com.jhoander.concretesolutionsgithubrepo.domain.models.RepositoryData
import com.jhoander.concretesolutionsgithubrepo.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullsActivity : AppCompatActivity() {

    var namePulls: String? = null
    private var nameRepo: String? = null

    private lateinit var adapter: CustomAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var progressDialog: ProgressDialog
    private lateinit var toolbarUser: Toolbar


    companion object{
        lateinit var sendPulls: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulls)


        val i = intent
        namePulls = i.getStringExtra("pulls")
        nameRepo = i.getStringExtra("repo")


        toolbarUser = findViewById(R.id.toolbarUsers)


        toolbarUser.setTitle(nameRepo)
        toolbarUser.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbarUser)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        sendPulls()
        progressDialog = ProgressDialog(this@PullsActivity)
        progressDialog.setMessage("Loading....")
        progressDialog.show()


        val service: GetDataServiceApi = RetrofitClient.getRetrofitInstance()!!.create(GetDataServiceApi::class.java)
        val call: Call<List<RepositoryData>> = service.getDataAllUsers()
        call.enqueue(object : Callback<List<RepositoryData>> {
            override fun onResponse(call: Call<List<RepositoryData>>, response: Response<List<RepositoryData>>) {
                progressDialog.dismiss()
                response.body()?.let { generateDataList(it) }
            }

            override fun onFailure(call: Call<List<RepositoryData>>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@PullsActivity, "Algo ha salido mal", Toast.LENGTH_SHORT).show()

            }
        })
    }


    private fun generateDataList(userList: List<RepositoryData>) {
        recyclerView = findViewById(R.id.customRecyclerView)
        adapter = CustomAdapter(this,userList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@PullsActivity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun sendPulls() {
        sendPulls = namePulls.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}