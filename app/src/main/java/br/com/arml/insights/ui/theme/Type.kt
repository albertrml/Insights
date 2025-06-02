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

val smallTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 10.sp,
    )
)

val mediumTypography = Typography(
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
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 12.sp,
    )
)

val expandedTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 18.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
    )
)

val largeTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp, // Larger
    ),
    headlineMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp, // Larger
    ),
    headlineSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp, // Larger
    ),
    titleLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp, // Larger
    ),
    titleMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp, // Larger
    ),
    titleSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp, // Larger
    ),
    bodyLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp, // Larger
    ),
    bodyMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp, // Larger
    ),
    bodySmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp, // Larger
    ),
    labelLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 18.sp, // Larger
    ),
    labelMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp, // Larger
    ),
    labelSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp, // Larger
    )
)