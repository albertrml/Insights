package br.com.arml.insights.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.arml.insights.R

val ubuntuFontFamily = FontFamily(
    Font(R.font.ubuntu_light, FontWeight.Light),
    Font(R.font.ubuntu, FontWeight.Normal),
    Font(R.font.ubuntu_medium, FontWeight.Medium),
    Font(R.font.ubuntu_bold, FontWeight.Bold),
    Font(R.font.ubuntu_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.ubuntu_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.ubuntu_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.ubuntu_bold_italic, FontWeight.Bold, FontStyle.Italic),
)

private const val activatePreview = true

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),

    headlineMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),

    headlineSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),

    titleMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),

    titleSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),

    labelMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),

    labelSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
    )
)