package com.example.lab3.view.main

import android.content.Intent

interface MainActivityInterface {

    fun startActivity(
        intent: Intent?
    )

    fun finish()

}