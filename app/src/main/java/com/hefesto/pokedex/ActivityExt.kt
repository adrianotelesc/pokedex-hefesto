package com.hefesto.pokedex

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

fun Activity.shortToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}