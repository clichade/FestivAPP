package com.example.trjano.festivapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.View
import android.widget.Button
import com.example.trjano.festivapp.database.AdminSQLiteOpenHelper

class MainActivity : AppCompatActivity() {

    private val btn_search : Button by bind(R.id.main_btn_search)
    private val btn_favorites : Button by bind(R.id.main_btn_favorites)
    private val btn_pending : Button by bind(R.id.main_btn_pending)
    private val btn_assisted : Button by bind(R.id.main_btn_assisted)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        components_setup()
    }


    fun components_setup(){
        btn_search.setOnClickListener{
            val intent = Intent(this, FinderActivity::class.java)
            startActivity(intent)
        }

        btn_favorites.setOnClickListener(){
            //Todo Implementar el funcionamiento del botón favoritos
            val intent = Intent(this, EventListActivity::class.java)
            intent.putExtra("Type","FAVORITES_EVENTS")
            startActivity(intent)
        }

        btn_pending.setOnClickListener(){
            //Todo Implementar el funcionamiento del botón pendientes
            val intent = Intent(this, EventListActivity::class.java)
            intent.putExtra("Type","UPCOMING_EVENTS")
            startActivity(intent)
        }

        btn_assisted.setOnClickListener(){
            //Todo Implementar el funcionamiento del botón asistidos
            val intent = Intent(this, EventListActivity::class.java)
            intent.putExtra("Type","PAST_EVENTS")
            startActivity(intent)
        }
    }

    fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy { findViewById(res) as T }
    }
}
