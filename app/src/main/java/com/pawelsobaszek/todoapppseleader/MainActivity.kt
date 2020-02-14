package com.pawelsobaszek.todoapppseleader

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var arrTodo: ArrayList<TodoItem> = ArrayList()
    var arrTodoExp: ArrayList<TodoItem> = ArrayList()
    var arrTodoConnected: ArrayList<TodoItem> = ArrayList()
    val type = object : TypeToken<ArrayList<TodoItem>>() {}.type


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildListView()
        buttonListener()
    }


    //Funkcja wywoływana po wciśnięciu przycisku back
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


    //Nasłuchiwanie przycisku
    fun buttonListener() {
        addTodoButton.setOnClickListener {
            val intent = Intent(this, FormTodoActivity::class.java)
            startActivity(intent)

        }
    }

    //Funkcja wyświetla komunikat o braku zadań do wykonania
    private fun noTodoInListView() {
        val alert = AlertDialog.Builder(this)

        alert.setTitle("Twoja lista zadań jest pusta")
        alert.setMessage("Prosimy kliknąć przycisk DODAJ TODO, aby dodać zadanie do wykonania")

        alert.setPositiveButton("Rozumiem") { dialog, positiveButton -> }

        alert.show()
    }

    //Funkcja buduję ListView
    fun buildListView() {
        //SharedPreferences
        val sharedPreferences = getSharedPreferences("Todo data", 0)
        val editor = sharedPreferences.edit()

        val gson = Gson()

        //Wczytywanie
        val json = sharedPreferences.getString("todo list", null)
        val json2 = sharedPreferences.getString("todo add to list", null)
        if (json2 == null) {
            if (json == null) {
                noTodoInListView()
                //Toast.makeText(this, "json2 = null i json = null", Toast.LENGTH_LONG).show()
            } else {
                arrTodo = gson.fromJson(json, type)
                val listView = findViewById(R.id.todo_item_list) as ListView
                listView.adapter = TodoAdapter(applicationContext, arrTodo)
                val toJson = gson.toJson(arrTodo)
                editor.putString("todo list", toJson)
                editor.apply()
                //Toast.makeText(this, "json2 = null i json != null", Toast.LENGTH_LONG).show()
            }
        } else {
            if (json == null) {
                val newJson = sharedPreferences.getString("todo add to list", null)
                arrTodoExp = gson.fromJson(newJson, type)
                val listView = findViewById(R.id.todo_item_list) as ListView
                listView.adapter = TodoAdapter(applicationContext, arrTodoExp)
                val toJson = gson.toJson(arrTodoExp)
                editor.putString("todo list", toJson)
                editor.remove("todo add to list")
                editor.apply()
                //Toast.makeText(this, "json2 != null i json = null", Toast.LENGTH_LONG).show()
            } else {
                arrTodo = gson.fromJson(json, type)
                arrTodoExp = gson.fromJson(json2, type)
                arrTodoConnected.addAll(arrTodo)
                arrTodoConnected.addAll(arrTodoExp)
                val listView = findViewById(R.id.todo_item_list) as ListView
                listView.adapter = TodoAdapter(applicationContext, arrTodoConnected)
                val json3 = gson.toJson(arrTodoConnected)
                editor.putString("todo list", json3)
                editor.remove("todo add to list")
                editor.apply()
                //Toast.makeText(this, "json2 != null i json != null", Toast.LENGTH_LONG).show()
            }
        }
    }
}
