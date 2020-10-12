package com.good.framework.utils

import android.util.Log
import com.google.gson.*
import com.google.gson.JsonParseException
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Type
import java.sql.Timestamp
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.set


class JsonUtil private constructor(){

    companion object{
        private val gson : Gson by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED ){
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss")
            gsonBuilder.registerTypeAdapter(Any::class.java,AnyTypeAdapter())
            gsonBuilder.create()
        }

        val instance : JsonUtil by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
            JsonUtil();
        }
    }

    fun getGson() : Gson{
        return gson;
    }


    private class TimestampTypeAdapter : JsonSerializer<Timestamp?>,
        JsonDeserializer<Timestamp?> {
        private val format: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        override fun serialize(
            src: Timestamp?,
            t: Type?,
            jsc: JsonSerializationContext?
        ): JsonElement? {
            val dfString: String = format.format(src?.time?.let { Date(it) })
            return JsonPrimitive(dfString)
        }

        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            t: Type?,
            jsc: JsonDeserializationContext?
        ): Timestamp {
            if (json !is JsonPrimitive) {
                throw JsonParseException("The date should be a string value")
            }
            return try {
                val date: Date = format.parse(json.getAsString())
                Timestamp(date.getTime())
            } catch (e: ParseException) {
                throw JsonParseException(e)
            }
        }
    }


    private class IntegerDefaultAdapter : TypeAdapter<Int?>() {
        @Throws(IOException::class)
        override fun write(jsonWriter: JsonWriter, integer: Int?) {
            if (integer == null) {
                jsonWriter.value("-1")
            } else {
                jsonWriter.value(integer.toString())
            }
        }

        @Throws(IOException::class)
        override fun read(jsonReader: JsonReader): Int {
            return try {
                var s = jsonReader.nextString();
                Log.d("Integer===>",s)
                Integer.valueOf(s);
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                -1
            }
        }
    }


    private class StringDefaultAdapter : TypeAdapter<String?>() {
        @Throws(IOException::class)
        override fun write(jsonWriter: JsonWriter, str: String?) {
            if (str.isNullOrEmpty()) {
                jsonWriter.value("")
            } else {
                jsonWriter.value(str)
            }
        }

        @Throws(IOException::class)
        override fun read(jsonReader: JsonReader): String {
            return jsonReader.nextString().toString();
        }
    }

    private class DoubleDefaultAdapter : TypeAdapter<Double?>() {
        @Throws(IOException::class)
        override fun write(jsonWriter: JsonWriter, dou: Double?) {
            if (dou == null) {
                jsonWriter.value("0.00")
            } else {
                jsonWriter.value(dou.toString())
            }
        }

        @Throws(IOException::class)
        override fun read(jsonReader: JsonReader): Double {
            return try {
                java.lang.Double.valueOf(jsonReader.nextString())
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                0.0
            }
        }
    }


    class AnyTypeAdapter : TypeAdapter<Any?>() {
        private val delegate =
            Gson().getAdapter(Any::class.java)

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Any? {
            val token: JsonToken = `in`.peek()
            return when (token) {
                JsonToken.BEGIN_ARRAY -> {
                    val list: MutableList<Any?> = ArrayList()
                    `in`.beginArray()
                    while (`in`.hasNext()) {
                        list.add(read(`in`))
                    }
                    `in`.endArray()
                    list
                }
                JsonToken.BEGIN_OBJECT -> {
                    val map: MutableMap<String, Any?> =
                        LinkedTreeMap()
                    `in`.beginObject()
                    while (`in`.hasNext()) {
                        map[`in`.nextName()] = read(`in`)
                    }
                    `in`.endObject()
                    map
                }
                JsonToken.STRING -> `in`.nextString()
                JsonToken.NUMBER -> {
                    /**
                     * 改写数字的处理逻辑，将数字值分为整型与浮点型。
                     */
                    val dbNum = `in`.nextDouble()

                    // 数字超过long的最大值，返回浮点类型
                    if (dbNum > Long.MAX_VALUE) {
                        return dbNum.toString()
                    }

                    // 判断数字是否为整数值
                    val lngNum = dbNum.toLong()
                    if (dbNum == lngNum.toDouble()) {
                        lngNum.toString()
                    } else {
                        dbNum.toString()
                    }
                }
                JsonToken.BOOLEAN -> `in`.nextBoolean()
                JsonToken.NULL -> {
                    `in`.nextNull()
                    null
                }
                else -> throw IllegalStateException()
            }
        }

        @Throws(IOException::class)
        override fun write(
            out: JsonWriter,
            value: Any?
        ) {
            delegate.write(out, value)
        }
    }


}