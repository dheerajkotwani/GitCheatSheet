package project.dheeraj.gitfinder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import project.dheeraj.gitfinder.Adapter.RecyclerViewAdapter
import project.dheeraj.gitfinder.Model.PrimaryModel
import project.dheeraj.gitfinder.Model.SecondaryModel


class MainActivity : AppCompatActivity(), ClickInterface {

    private lateinit var sharedPref : SharedPreferences
    private lateinit var jsonObject : JSONObject
    private var list : ArrayList<PrimaryModel> = ArrayList()
    private var listRecycerView : ArrayList<SecondaryModel> = ArrayList()
    private val FILENAME: String = "git_command_explorer.json"
    private lateinit var primaryValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)
        setTheme()
        overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out );
        setContentView(R.layout.activity_main)

        setupDayNightMode()

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        jsonObject = JSONObject(loadJSONFromAsset())

        getPrimaryOptions()
        inputField.setOnTouchListener { _, _ ->
            inputField.showDropDown()
            false
        }

        inputField.setOnItemClickListener { adapterView, view, pos, l ->
            primaryValue = list.find {
                it.label == inputField.text.toString()
            }?.value!!
            dismissKeyboard(inputField)
            getItems()
        }




    }

    private fun getItems() {
        listRecycerView.clear()
        val jsonSecondaryOptionsObject = jsonObject.getJSONObject("secondary_options")
        val jsonArray = jsonSecondaryOptionsObject.getJSONArray(primaryValue)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val secondary = SecondaryModel()
            secondary.value = jsonObject.getString("value")
            secondary.label = jsonObject.getString("label")
            if (jsonObject.has("usage"))
                secondary.usage = jsonObject.getString("usage")
            if (jsonObject.has("nb"))
                secondary.nb = jsonObject.getString("nb")
            listRecycerView.add(secondary)
        }

        recyclerView.adapter =
            RecyclerViewAdapter(
                this,
                listRecycerView,
                this)
    }

    private fun setTheme() {
        if (sharedPref.getBoolean(AppConfig.IS_DARK, false))
            setTheme(R.style.DarkTheme)
        else
            setTheme(R.style.LightTheme)
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

    private fun getPrimaryOptions() {
        val jsonPrimaryOptionsArray = jsonObject.getJSONArray("primary_options")
        for (i in 0 until jsonPrimaryOptionsArray.length()) {
            val jsonObject = jsonPrimaryOptionsArray.getJSONObject(i)
            val primary = PrimaryModel()
            primary.value = jsonObject.getString("value")
            primary.label = jsonObject.getString("label")
            list.add(primary)
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, list.map {
                it.label
            }
        )
        inputField.setAdapter(adapter)
    }


    private fun Context.dismissKeyboard(view: View?) {
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun loadJSONFromAsset(): String {
        return assets.open(FILENAME).bufferedReader().use {
            it.readText()
        }
    }

    override fun onBackPressed() {
        dismissKeyboard(inputField)
        super.onBackPressed()
    }

    override fun clickListener(position: Int) {

        val intent = Intent(this, InformationActivity::class.java)
        intent.putExtra("label", listRecycerView[position].label)
        intent.putExtra("value", listRecycerView[position].value)
        intent.putExtra("usage", listRecycerView[position].usage)
        intent.putExtra("nb", listRecycerView[position].nb)
        startActivity(intent)
        overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out );

    }
}

