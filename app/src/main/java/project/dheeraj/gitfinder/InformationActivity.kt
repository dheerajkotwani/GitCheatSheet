package project.dheeraj.gitfinder

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)
        setTheme()
        setContentView(R.layout.activity_information)

        textlabel.text = intent.getStringExtra("label").capitalize()
        textDisplayCommand.text = intent.getStringExtra("usage")
        textDisplayNote.text = intent.getStringExtra("nb")

        if (textDisplayNote.text.isNullOrEmpty()) {
            cardNote.visibility = View.GONE
            textNote.visibility = View.GONE
        }


    }

    private fun setTheme() {
        if (sharedPref.getBoolean(AppConfig.IS_DARK, false))
            setTheme(R.style.DarkTheme)
        else
            setTheme(R.style.LightTheme)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out );
    }
}
