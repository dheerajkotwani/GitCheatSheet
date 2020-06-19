package project.dheeraj.gitfinder

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)

        if (sharedPref.getBoolean(AppConfig.IS_DARK, false))
            setTheme(R.style.DarkTheme)
        else
            setTheme(R.style.LightTheme)

        setContentView(R.layout.activity_main)

        setupDayNightMode()

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

    }

    private fun setupDayNightMode() {
        if (sharedPref.getBoolean(AppConfig.IS_DARK, false)) {
            iconDayNight.setColorFilter(
                ContextCompat.getColor(this, R.color.grey_500),
                PorterDuff.Mode.SRC_IN
            );
        } else {
            iconDayNight.setColorFilter(
                ContextCompat.getColor(this, R.color.yellow_700),
                PorterDuff.Mode.SRC_IN
            );
        }

        iconDayNight.setOnClickListener {
            if (sharedPref.getBoolean(AppConfig.IS_DARK, false)) {
                sharedPref.edit().putBoolean(AppConfig.IS_DARK, false).commit()
                iconDayNight.setColorFilter(
                    ContextCompat.getColor(this, R.color.yellow_700),
                    PorterDuff.Mode.SRC_IN
                );
                setTheme(R.style.LightTheme)
                recreate()
            } else {
                sharedPref.edit().putBoolean(AppConfig.IS_DARK, true).commit()
                iconDayNight.setColorFilter(
                    ContextCompat.getColor(this, R.color.grey_500),
                    PorterDuff.Mode.SRC_IN
                );
                setTheme(R.style.DarkTheme)
                recreate()
            }
        }
    }
}
