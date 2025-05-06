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

// Compact Small Typography (Smaller Font Sizes)
val CompactSmallTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp, // Slightly smaller
    ),
    headlineMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp, // Slightly smaller
    ),
    headlineSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp, // Slightly smaller
    ),
    titleLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp, // Smaller
    ),
    titleMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp, // Smaller
    ),
    titleSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp, // Smaller
    ),
    bodyLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp, // Smaller
    ),
    bodyMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp, // Smaller
    ),
    bodySmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 8.sp, // Smaller
    ),
    labelLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp, // Smaller
    ),
    labelMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp, // Smaller
    ),
    labelSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp, // Smaller
    )
)

val CompactMediumTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 23.sp, // In between
    ),
    headlineMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 19.sp, // In between
    ),
    headlineSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp, // In between
    ),
    titleLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp, // In between
    ),
    titleMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp, // In between
    ),
    titleSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 9.sp, // In between
    ),
    bodyLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp, // In between
    ),
    bodyMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp, // In between
    ),
    bodySmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 9.sp, // In between
    ),
    labelLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp, // In between
    ),
    labelMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp, // In between
    ),
    labelSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 9.sp, // In between
    )
)

val CompactTypography = Typography(
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

val MediumTypography = Typography(
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

val LargeTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp, // Larger
    ),
    headlineMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp, // Larger
    ),
    headlineSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp, // Larger
    ),
    titleLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp, // Larger
    ),
    titleMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp, // Larger
    ),
    titleSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp, // Larger
    ),
    bodyLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp, // Larger
    ),
    bodyMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp, // Larger
    ),
    bodySmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp, // Larger
    ),
    labelLarge = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp, // Larger
    ),
    labelMedium = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp, // Larger
    ),
    labelSmall = TextStyle(
        fontFamily = if (activatePreview) FontFamily.Default else ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp, // Larger
    )
)