package xyz.malefic.guptare.server.util

import co.touchlab.kermit.Logger
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import xyz.malefic.guptare.model.json
import java.io.File
import kotlin.reflect.KProperty

class FileDelegate<T>(
    private val fileName: String,
    private val defaultValue: T,
    private val serializer: KSerializer<T>,
) {
    private val file = File(assetsPath, fileName)
    private var value: T = load()

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T = value

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        newValue: T,
    ) {
        value = newValue
        save()
    }

    private fun load(): T =
        if (file.exists()) {
            try {
                json.decodeFromString(serializer, file.readText())
            } catch (e: Exception) {
                Logger.e(e, "FileDelegate") { "Error loading $fileName" }
                defaultValue
            }
        } else {
            defaultValue
        }

    private fun save() {
        file.parentFile?.mkdirs()
        file.writeText(json.encodeToString(serializer, value))
    }
}

inline fun <reified T> file(
    fileName: String,
    defaultValue: T,
) = FileDelegate(fileName, defaultValue, json.serializersModule.serializer<T>())
