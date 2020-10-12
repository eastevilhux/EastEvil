package com.good.framework.utils

import android.util.Log
import androidx.annotation.StringDef
import com.google.gson.*
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

class JsonUtil private constructor(){

    companion object{
        private val gson : Gson by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED ){
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss")
            gsonBuilder.registerTypeAdapter(Timestamp::class.java, TimestampTypeAdapter())
            gsonBuilder.registerTypeAdapter(Int::class.java, IntegerDefaultAdapter())
            gsonBuilder.registerTypeAdapter(Double::class.java, DoubleDefaultAdapter())
            gsonBuilder.registerTypeAdapter(String::class.java,StringDefaultAdapter());
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
}