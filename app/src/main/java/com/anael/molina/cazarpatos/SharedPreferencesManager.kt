package com.anael.molina.cazarpatos

import android.app.Activity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import android.widget.Toast


class ExternalStorageManager(private val actividad: Activity) : FileHandler {

    private val nombreArchivo = "usuario_externo.txt"

    override fun SaveInformation(datosAGrabar: Pair<String, String>) {
        try {
            val archivo = File(actividad.getExternalFilesDir(null), nombreArchivo)
            val fos = FileOutputStream(archivo)
            val datos = "${datosAGrabar.first};${datosAGrabar.second}"
            fos.write(datos.toByteArray())
            fos.close()
            // Muestra Toast para confirmar que sí se ejecutó
            Toast.makeText(actividad, "Archivo externo guardado", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(actividad, "Error al guardar archivo", Toast.LENGTH_SHORT).show()
        }
    }

    override fun ReadInformation(): Pair<String, String> {
        return try {
            val archivo = File(actividad.getExternalFilesDir(null), nombreArchivo)
            val fis = FileInputStream(archivo)
            val contenido = fis.bufferedReader().use { it.readText() }
            val partes = contenido.split(";")
            val email = partes.getOrNull(0) ?: ""
            val clave = partes.getOrNull(1) ?: ""
            email to clave
        } catch (e: Exception) {
            "" to ""
        }
    }
}
