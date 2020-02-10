package com.pawelsobaszek.todoapppseleader

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_form_todo.*

class FormTodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_todo)

        setListener()
    }

    private fun setListener() {
        cancelButton.setOnClickListener {
            onBackPressed()
        }
    }

}