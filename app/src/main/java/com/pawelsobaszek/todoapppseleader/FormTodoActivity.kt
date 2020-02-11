package com.pawelsobaszek.todoapppseleader

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_form_todo.*

class FormTodoActivity : AppCompatActivity() {

    var arrTodo: ArrayList<TodoItem> = ArrayList()
    var descriptionValue: String? = null
    var dateValue: String? = null
    var categoryValue: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_todo)

        setListener()
    }

    private fun setListener() {
        cancelButton.setOnClickListener {
            onBackPressed()
        }

        forwardButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("Todo data", 0)
            val editor = sharedPreferences.edit()

            val gson = Gson()

            val day = (dateTodo.dayOfMonth).toString()
            val month = (dateTodo.month + 1).toString()
            val year = (dateTodo.year).toString()

            descriptionValue = nameTodo.text.toString()
            dateValue = (day + "." + month + "." + year)
            categoryValue = spinner_category.selectedItem.toString()

            arrTodo.add(TodoItem(descriptionValue!!,dateValue!!, categoryValue!!))

            val jsonNewTodo = gson.toJson(arrTodo)
            editor.putString("todo add to list", jsonNewTodo)
            editor.apply()

            Toast.makeText(this, "Zadanie zostało poprawnie dodane", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}