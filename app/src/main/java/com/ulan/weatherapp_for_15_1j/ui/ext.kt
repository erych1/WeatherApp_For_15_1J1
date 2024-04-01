package com.ulan.weatherapp_for_15_1j.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).centerCrop().into(this)
}

fun Context.mainIntent(class1 : Activity){
    startActivity(Intent(this , class1::class.java))

}