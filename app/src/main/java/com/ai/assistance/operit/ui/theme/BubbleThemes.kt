package com.ai.assistance.operit.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Lunaria 21种聊天气泡皮肤
 */
data class BubbleTheme(
    val id: String,
    val name: String,
    val userBubbleColor: Color,
    val aiBubbleColor: Color,
    val userTextColor: Color,
    val aiTextColor: Color,
    val shape: Shape,
    val hasGradient: Boolean = false,
    val gradientColors: List<Color> = emptyList()
)

object BubbleThemes {
    
    // 1. 默认粉紫（已有）
    val Default = BubbleTheme(
        id = "default",
        name = "默认粉紫",
        userBubbleColor = Color(0xFFE8D5F2),
        aiBubbleColor = Color(0xFFF5E6FF),
        userTextColor = Color(0xFF1E1E1E),
        aiTextColor = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(20.dp)
    )
    
    // 2. 微信绿
    val WechatGreen = BubbleTheme(
        id = "wechat",
        name = "微信绿",
        userBubbleColor = Color(0xFF95EC69),
        aiBubbleColor = Color(0xFFFFFFFF),
        userTextColor = Color(0xFF1E1E1E),
        aiTextColor = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(8.dp)
    )
    
    // 3. QQ蓝
    val QQBlue = BubbleTheme(
        id = "qq",
        name = "QQ蓝",
        userBubbleColor = Color(0xFF4A90E2),
        aiBubbleColor = Color(0xFFFFFFFF),
        userTextColor = Color(0xFFFFFFFF),
        aiTextColor = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(12.dp)
    )
    
    // 4. 少女粉
    val GirlyPink = BubbleTheme(
        id = "girly_pink",
        name = "少女粉",
        userBubbleColor = Color(0xFFFFB3D9),
        aiBubbleColor = Color(0xFFFFF0F5),
        userTextColor = Color(0xFF5C0A36),
        aiTextColor = Color(0xFF5C0A36),
        shape = RoundedCornerShape(20.dp)
    )
    
    // 5. 奶油黄
    val CreamYellow = BubbleTheme(
        id = "cream_yellow",
        name = "奶油黄",
        userBubbleColor = Color(0xFFFFF8DC),
        aiBubbleColor = Color(0xFFFFFAF0),
        userTextColor = Color(0xFF8B4513),
        aiTextColor = Color(0xFF8B4513),
        shape = RoundedCornerShape(16.dp)
    )
    
    // 6. 薄荷绿
    val MintGreen = BubbleTheme(
        id = "mint_green",
        name = "薄荷绿",
        userBubbleColor = Color(0xFFB2F2E3),
        aiBubbleColor = Color(0xFFE0FFF4),
        userTextColor = Color(0xFF0D4D3D),
        aiTextColor = Color(0xFF0D4D3D),
        shape = RoundedCornerShape(18.dp)
    )
    
    // 7. 天空蓝
    val SkyBlue = BubbleTheme(
        id = "sky_blue",
        name = "天空蓝",
        userBubbleColor = Color(0xFF87CEEB),
        aiBubbleColor = Color(0xFFE6F3FF),
        userTextColor = Color(0xFF00008B),
        aiTextColor = Color(0xFF00008B),
        shape = RoundedCornerShape(15.dp)
    )
    
    // 8. 暗夜黑
    val DarkNight = BubbleTheme(
        id = "dark_night",
        name = "暗夜黑",
        userBubbleColor = Color(0xFF2C2C2C),
        aiBubbleColor = Color(0xFF1A1A1A),
        userTextColor = Color(0xFFE0E0E0),
        aiTextColor = Color(0xFFE0E0E0),
        shape = RoundedCornerShape(12.dp)
    )
    
    // 9. 樱花粉
    val SakuraPink = BubbleTheme(
        id = "sakura",
        name = "樱花粉",
        userBubbleColor = Color(0xFFFFB7D5),
        aiBubbleColor = Color(0xFFFFF0F6),
        userTextColor = Color(0xFF8B0A50),
        aiTextColor = Color(0xFF8B0A50),
        shape = RoundedCornerShape(22.dp)
    )
    
    // 10. 薰衣草紫
    val Lavender = BubbleTheme(
        id = "lavender",
        name = "薰衣草紫",
        userBubbleColor = Color(0xFFD4C5F9),
        aiBubbleColor = Color(0xFFF3EFFF),
        userTextColor = Color(0xFF4B0082),
        aiTextColor = Color(0xFF4B0082),
        shape = RoundedCornerShape(18.dp)
    )
    
    // 11. 珊瑚橙
    val CoralOrange = BubbleTheme(
        id = "coral",
        name = "珊瑚橙",
        userBubbleColor = Color(0xFFFF7F50),
        aiBubbleColor = Color(0xFFFFF5EE),
        userTextColor = Color(0xFF8B4513),
        aiTextColor = Color(0xFF8B4513),
        shape = RoundedCornerShape(16.dp)
    )
    
    // 12. 森林绿
    val ForestGreen = BubbleTheme(
        id = "forest",
        name = "森林绿",
        userBubbleColor = Color(0xFF90EE90),
        aiBubbleColor = Color(0xFFF0FFF0),
        userTextColor = Color(0xFF006400),
        aiTextColor = Color(0xFF006400),
        shape = RoundedCornerShape(14.dp)
    )
    
    // 13. 玫瑰金
    val RoseGold = BubbleTheme(
        id = "rose_gold",
        name = "玫瑰金",
        userBubbleColor = Color(0xFFECC5C0),
        aiBubbleColor = Color(0xFFFFF5F3),
        userTextColor = Color(0xFF8B4C5C),
        aiTextColor = Color(0xFF8B4C5C),
        shape = RoundedCornerShape(20.dp)
    )
    
    // 14. 深海蓝
    val DeepSeaBlue = BubbleTheme(
        id = "deep_sea",
        name = "深海蓝",
        userBubbleColor = Color(0xFF1E90FF),
        aiBubbleColor = Color(0xFFE6F2FF),
        userTextColor = Color(0xFFFFFFFF),
        aiTextColor = Color(0xFF00008B),
        shape = RoundedCornerShape(12.dp)
    )
    
    // 15. 柠檬黄
    val LemonYellow = BubbleTheme(
        id = "lemon",
        name = "柠檬黄",
        userBubbleColor = Color(0xFFFFFACD),
        aiBubbleColor = Color(0xFFFFFFF0),
        userTextColor = Color(0xFF8B8B00),
        aiTextColor = Color(0xFF8B8B00),
        shape = RoundedCornerShape(16.dp)
    )
    
    // 16. 灰度简约
    val GrayMinimal = BubbleTheme(
        id = "gray_minimal",
        name = "灰度简约",
        userBubbleColor = Color(0xFFD3D3D3),
        aiBubbleColor = Color(0xFFF5F5F5),
        userTextColor = Color(0xFF000000),
        aiTextColor = Color(0xFF000000),
        shape = RoundedCornerShape(10.dp)
    )
    
    // 17. 渐变彩虹
    val RainbowGradient = BubbleTheme(
        id = "rainbow",
        name = "渐变彩虹",
        userBubbleColor = Color(0xFFFF6B9D),
        aiBubbleColor = Color(0xFFC4E0FF),
        userTextColor = Color(0xFFFFFFFF),
        aiTextColor = Color(0xFF000000),
        shape = RoundedCornerShape(20.dp),
        hasGradient = true,
        gradientColors = listOf(
            Color(0xFFFF6B9D),
            Color(0xFFFFA07A),
            Color(0xFFFFD700)
        )
    )
    
    // 18. 极简白
    val MinimalWhite = BubbleTheme(
        id = "minimal_white",
        name = "极简白",
        userBubbleColor = Color(0xFFFFFFFF),
        aiBubbleColor = Color(0xFFF8F8F8),
        userTextColor = Color(0xFF000000),
        aiTextColor = Color(0xFF000000),
        shape = RoundedCornerShape(8.dp)
    )
    
    // 19. 紫罗兰
    val Violet = BubbleTheme(
        id = "violet",
        name = "紫罗兰",
        userBubbleColor = Color(0xFF9370DB),
        aiBubbleColor = Color(0xFFE6E6FA),
        userTextColor = Color(0xFFFFFFFF),
        aiTextColor = Color(0xFF4B0082),
        shape = RoundedCornerShape(18.dp)
    )
    
    // 20. 桃花粉
    val PeachBlossom = BubbleTheme(
        id = "peach",
        name = "桃花粉",
        userBubbleColor = Color(0xFFFFDAB9),
        aiBubbleColor = Color(0xFFFFF5EE),
        userTextColor = Color(0xFF8B4513),
        aiTextColor = Color(0xFF8B4513),
        shape = RoundedCornerShape(20.dp)
    )
    
    // 21. 钛金银
    val TitaniumSilver = BubbleTheme(
        id = "titanium",
        name = "钛金银",
        userBubbleColor = Color(0xFFC0C0C0),
        aiBubbleColor = Color(0xFFE8E8E8),
        userTextColor = Color(0xFF2F4F4F),
        aiTextColor = Color(0xFF2F4F4F),
        shape = RoundedCornerShape(10.dp)
    )
    
    // 所有主题列表
    val allThemes = listOf(
        Default,
        WechatGreen,
        QQBlue,
        GirlyPink,
        CreamYellow,
        MintGreen,
        SkyBlue,
        DarkNight,
        SakuraPink,
        Lavender,
        CoralOrange,
        ForestGreen,
        RoseGold,
        DeepSeaBlue,
        LemonYellow,
        GrayMinimal,
        RainbowGradient,
        MinimalWhite,
        Violet,
        PeachBlossom,
        TitaniumSilver
    )
}