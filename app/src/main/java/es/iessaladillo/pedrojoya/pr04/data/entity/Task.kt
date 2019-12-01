package es.iessaladillo.pedrojoya.pr04.data.entity

import android.os.Build
import android.provider.Settings.Global.getString
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*



data class Task(var id: Long, val concep: String, var completed: Boolean) {
    val createdAt: String =
        "created at ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())}"
    var completedAt: String = ""


    fun cambiarEstado() {
        if (!completed) {
            completed = !completed
            completedAt = "completed at ${SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss",
                Locale.getDefault()
            ).format(Date())}"
        } else {
            completed = !completed
        }

    }
}