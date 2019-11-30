package es.iessaladillo.pedrojoya.pr04.data.entity

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

// TODO: Crea una clase llamada Task con las siguientes propiedades:
//  id (Long), concept(String), createdAt (String),
//  completed (Boolean), completedAt (String)

data class Task(val id: Long, val concep: String,var  completed: Boolean) {
    val createdAt: String = "created at ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())}"
    lateinit var completedAt: String

}