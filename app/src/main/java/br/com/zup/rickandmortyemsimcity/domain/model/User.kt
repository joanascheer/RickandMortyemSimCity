package br.com.zup.rickandmortyemsimcity.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String = "",
    var email: String = "",
    var password: String = ""
): Parcelable