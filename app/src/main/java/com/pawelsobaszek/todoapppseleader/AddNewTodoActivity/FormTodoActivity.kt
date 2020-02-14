package com.pawelsobaszek.todoapppseleader.AddNewTodoActivity


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.pawelsobaszek.todoapppseleader.MainActivity
import com.pawelsobaszek.todoapppseleader.R
import com.pawelsobaszek.todoapppseleader.TodoItem
import kotlinx.android.synthetic.main.activity_form_todo.*


class FormTodoActivity : AppCompatActivity() {

    //Lista w której umieszczać będziemy dodane zadanie
    var arrTodo: ArrayList<TodoItem> = ArrayList()
    //String z zadaniem
    var descriptionValue: String? = null
    //Data zadania
    var dateValue: String? = null
    //Kategoria zadania
    var categoryValue: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_todo)

        setListener()

    }


    //Nasłuchiwanie przycisków
    private fun setListener() {

        cancelButton.setOnClickListener {
            onBackPressed()
        }

        forwardButton.setOnClickListener {

            //Sprawdzamy czy pole do wpisywania zadania jest puste
            if (TextUtils.isEmpty(nameTodo.getText().toString())) {
                Toast.makeText(this, "Błąd! Pole treści zadania nie może być puste.", Toast.LENGTH_LONG).show()
            } else {
                //Shared preferences
                val sharedPreferences = getSharedPreferences("Todo data", 0)
                val editor = sharedPreferences.edit()

                val gson = Gson()

                //Pobieramy wybrany dzień
                val day = (dateTodo.dayOfMonth).toString()
                //Pobieramy wybrany miesiąc
                val month = (dateTodo.month + 1).toString()
                //Pobieramy wybrany rok
                val year = (dateTodo.year).toString()

                //Pobieramy String z zadaniem, które wpisał użytkownik
                descriptionValue = nameTodo.text.toString()
                //Ustawiamy String z datą poprzez połączenie Stringów
                dateValue = (day + "." + month + "." + year)
                //Pobieramy kategorię, którą wybrał użytkownik
                categoryValue = spinner_category.selectedItem.toString()

                //Umieszczamy w Liście wartości potrzebne do wyświetlenia zadania
                arrTodo.add(
                    TodoItem(
                        descriptionValue!!,
                        dateValue!!,
                        categoryValue!!
                    )
                )

                val jsonNewTodo = gson.toJson(arrTodo)
                //Zapisujemy wartość zadania w SharedPreferences
                editor.putString("todo add to list", jsonNewTodo)
                editor.apply()

                //Wyświetlamy informację o poprawnym umieszczeniu zadania w SharedPreferences
                Toast.makeText(this, "Zadanie zostało poprawnie dodane", Toast.LENGTH_LONG).show()

                //Przechodzimy do MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }


    }

}