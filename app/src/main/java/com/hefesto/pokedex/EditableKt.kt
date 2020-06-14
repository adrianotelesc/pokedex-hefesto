package com.hefesto.pokedex

import android.text.Editable

val Editable?.isFilled: Boolean get() = !(isNullOrEmpty() || isNullOrBlank())