package com.example.menu

import  com.example.menu.R
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppLocale(getSavedLocale())
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.cities)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.lang_en -> changeLanguage("en")
                R.id.lang_ru -> changeLanguage("ru")
            }
            true
        }
    }

    private fun changeLanguage(language: String) {
        saveLocale(language)
        setAppLocale(language)
        restartActivity()
    }

    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        baseContext.createConfigurationContext(config)
    }


    private fun restartActivity() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun saveLocale(language: String) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        prefs.edit().putString("lang", language).apply()
    }

    private fun getSavedLocale(): String {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        return prefs.getString("lang", "en") ?: "en"
    }
}
