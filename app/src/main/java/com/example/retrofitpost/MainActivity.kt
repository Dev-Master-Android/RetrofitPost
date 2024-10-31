package com.example.retrofitpost

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var buttonLoad: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView (R.layout.activity_main)
        setSupportActionBar (findViewById(R.id.toolbar))
        imageView = findViewById (R.id.imageView)
        buttonLoad = findViewById (R.id.buttonLoad)
        buttonLoad.setOnClickListener {
            loadRandomImage()
        }
    }

    private fun loadRandomImage() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                RetrofitInstance.api.getRandomDog()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return@launch
            }
            if (response != null) {
                withContext(Dispatchers.Main) {
                    Glide.with(this@MainActivity).load(response.url).into(imageView)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}