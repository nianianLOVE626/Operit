package com.ai.assistance.operit.ui.features.chat.components.style.bubble

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.json.JSONObject
import java.io.InputStreamReader

data class BubbleInsets(
    val top: Float = 0f,
    val leading: Float = 0f,
    val bottom: Float = 0f,
    val trailing: Float = 0f
)

data class BubbleThemeConfig(
    val id: String,
    val name: String,
    val file: String,
    val pixelSize: List<Int> = listOf(0, 0),
    val scale: Int = 3,
    val capInsets: BubbleInsets = BubbleInsets(),
    val contentInsets: BubbleInsets = BubbleInsets()
)

data class LoadedBubbleTheme(
    val config: BubbleThemeConfig,
    val bitmap: Bitmap,
    val imageBitmap: ImageBitmap
)

object CuteBubbleThemeManager {
    private var themes: List<LoadedBubbleTheme> = emptyList()
    private var currentThemeId: String? = null
    val currentTheme = mutableStateOf<LoadedBubbleTheme?>(null)

    fun initialize(context: Context) {
        if (themes.isNotEmpty()) return

        try {
            val configText = context.assets.open("bubbles/bubbles.json").use { stream ->
                InputStreamReader(stream).readText()
            }

            val root = JSONObject(configText)
            val bubblesArray = root.getJSONArray("bubbles")

            val loadedThemes = mutableListOf<LoadedBubbleTheme>()
            for (i in 0 until bubblesArray.length()) {
                val obj = bubblesArray.getJSONObject(i)
                val id = obj.getString("id")
                val name = obj.getString("name")
                val file = obj.getString("file")
                val scale = obj.optInt("scale", 3)

                val pixelSize = if (obj.has("pixelSize")) {
                    val arr = obj.getJSONArray("pixelSize")
                    listOf(arr.getInt(0), arr.getInt(1))
                } else listOf(0, 0)

                val capInsets = parseInsets(obj.optJSONObject("capInsets"))
                val contentInsets = parseInsets(obj.optJSONObject("contentInsets"))

                val config = BubbleThemeConfig(id, name, file, pixelSize, scale, capInsets, contentInsets)

                try {
                    val bitmap = context.assets.open("bubbles/$file").use { stream ->
                        BitmapFactory.decodeStream(stream)
                    }
                    if (bitmap != null) {
                        loadedThemes.add(
                            LoadedBubbleTheme(
                                config = config,
                                bitmap = bitmap,
                                imageBitmap = bitmap.asImageBitmap()
                            )
                        )
                    }
                } catch (_: Exception) {}
            }

            themes = loadedThemes

            val prefs = context.getSharedPreferences("lunaria_bubble", Context.MODE_PRIVATE)
            val savedId = prefs.getString("current_bubble_id", null)
            if (savedId != null) {
                setTheme(context, savedId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parseInsets(obj: JSONObject?): BubbleInsets {
        if (obj == null) return BubbleInsets()
        return BubbleInsets(
            top = obj.optDouble("top", 0.0).toFloat(),
            leading = obj.optDouble("leading", 0.0).toFloat(),
            bottom = obj.optDouble("bottom", 0.0).toFloat(),
            trailing = obj.optDouble("trailing", 0.0).toFloat()
        )
    }

    fun getAllThemes(): List<LoadedBubbleTheme> = themes

    fun getThemeById(id: String): LoadedBubbleTheme? = themes.find { it.config.id == id }

    fun setTheme(context: Context, themeId: String?) {
        currentThemeId = themeId
        currentTheme.value = if (themeId != null) getThemeById(themeId) else null

        val prefs = context.getSharedPreferences("lunaria_bubble", Context.MODE_PRIVATE)
        prefs.edit().putString("current_bubble_id", themeId).apply()
    }

    fun clearTheme(context: Context) {
        setTheme(context, null)
    }

    fun getCurrentThemeId(): String? = currentThemeId
}
