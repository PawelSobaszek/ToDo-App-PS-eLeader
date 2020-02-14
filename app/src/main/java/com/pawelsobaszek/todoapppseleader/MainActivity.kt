package com.pawelsobaszek.todoapppseleader

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pawelsobaszek.todoapppseleader.AddNewTodoActivity.FormTodoActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    //lista aktualnych zadań
    var arrTodo: ArrayList<TodoItem> = ArrayList()
    //lista dodanego zadania
    var arrTodoExp: ArrayList<TodoItem> = ArrayList()
    //lista połączonych arrTodo i arrTodoExp
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

        //Wczytywanie aktualnej listy
        val json = sharedPreferences.getString("todo list", null)
        //Wczytywanie dodanego zadania
        val json2 = sharedPreferences.getString("todo add to list", null)
        //Sprawdzamy czy zostało dodane zadanie
        if (json2 == null) {
            //Sprawdzamy czy w aplikacji zapisane są już zadania
            if (json == null) {
                //Jeżeli nie dodaliśmy zadania oraz w aplikacji nie są zapisane zadania do wykonania wyświetlamy komunikat o braku zadań
                noTodoInListView()
                //Toast.makeText(this, "json2 = null i json = null", Toast.LENGTH_LONG).show()
            } else {
                arrTodo = gson.fromJson(json, type)
                val listView = findViewById(R.id.todo_item_list) as ListView
                listView.adapter = TodoAdapter(applicationContext, arrTodo)
                val toJson = gson.toJson(arrTodo)
                //Zapisujemy aktualną listę zadań
                editor.putString("todo list", toJson)
                editor.apply()
                //Toast.makeText(this, "json2 = null i json != null", Toast.LENGTH_LONG).show()
            }
        } else {
            if (json == null) {
                //Pobieramy nowo dodane zadanie
                val newJson = sharedPreferences.getString("todo add to list", null)
                arrTodoExp = gson.fromJson(newJson, type)
                val listView = findViewById(R.id.todo_item_list) as ListView
                //Dodajemy zadanie do ListView
                listView.adapter = TodoAdapter(applicationContext, arrTodoExp)
                val toJson = gson.toJson(arrTodoExp)
                //Zapisujemy zadanie w liście wszystkich zadań
                editor.putString("todo list", toJson)
                //Usuwamy zadanie z listy dodawanych zadań
                editor.remove("todo add to list")
                editor.apply()
                //Toast.makeText(this, "json2 != null i json = null", Toast.LENGTH_LONG).show()
            } else {
                arrTodo = gson.fromJson(json, type)
                arrTodoExp = gson.fromJson(json2, type)
                //Łączymy obie listy w jedną
                arrTodoConnected.addAll(arrTodo)
                arrTodoConnected.addAll(arrTodoExp)
                val listView = findViewById(R.id.todo_item_list) as ListView
                //Dodajemy połączoną listę zadań do ListView
                listView.adapter = TodoAdapter(applicationContext, arrTodoConnected)
                val json3 = gson.toJson(arrTodoConnected)
                //Zapisujemy zadania w pamięci
                editor.putString("todo list", json3)
                //Usuwamy nowo dodane zadanie z pamięci SharedPreferences
                editor.remove("todo add to list")
                editor.apply()
                //Toast.makeText(this, "json2 != null i json != null", Toast.LENGTH_LONG).show()
            }
        }
    }
}
