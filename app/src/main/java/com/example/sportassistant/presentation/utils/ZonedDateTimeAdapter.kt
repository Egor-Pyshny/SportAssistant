package com.example.sportassistant.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.*
import java.lang.reflect.Type
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class ZonedDateTimeAdapter : JsonDeserializer<ZonedDateTime>, JsonSerializer<ZonedDateTime> {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ZonedDateTime {
        return ZonedDateTime.parse(json.asString, formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun serialize(
        src: ZonedDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.format(formatter))
    }
}
