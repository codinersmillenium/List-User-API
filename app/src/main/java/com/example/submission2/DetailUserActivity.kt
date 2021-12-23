package com.example.submission2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submission2.Adapter.PageAdapter
import com.example.submission2.Model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_user.*
import kotlinx.android.synthetic.main.layout_informasi_user.*
import org.json.JSONObject

class DetailUserActivity: AppCompatActivity() {


    companion object {
        private val TAG = DetailUserActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_user)
        supportActionBar?.title = "Detail User"
        val data = intent.extras?.getParcelable<User>("DATAUSER") as User
        Glide.with(this).load(data.photo.toString())
            .into(image_user)
        val login = data.name
        text_name.text = ""
        text_username.text = ""
        text_followers.text = ""
        text_following.text = ""
        text_company.text = ""
        text_location.text = ""
        text_rps.text = ""
        getDataUser("$login")
        val PageAdapter = PageAdapter(this, supportFragmentManager)
        view_pager.adapter = PageAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }


    private fun getDataUser(login: String) {
        progressBarDetail.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$login"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray
            ) {
                progressBarDetail.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(DetailUserActivity.TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String? = jsonObject.getString("login").toString()
                    val name: String? = jsonObject.getString("name").toString()
                    val company: String? = jsonObject.getString("company").toString()
                    val location: String? = jsonObject.getString("location").toString()
                    val repository: String? = jsonObject.getString("public_repos")
                    val followers: Int = jsonObject.getInt("followers")
                    val following: Int = jsonObject.getInt("following")
                    text_name.text = name
                    text_username.text = username
                    text_followers.text = followers.toString()
                    text_following.text = following.toString()
                    text_company.text = company
                    text_location.text = location
                    text_rps.text = repository

                } catch (e: Exception) {
                    Toast.makeText(this@DetailUserActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable
            ) {
                progressBarDetail.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@DetailUserActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}