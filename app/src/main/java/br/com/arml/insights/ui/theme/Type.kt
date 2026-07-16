package br.com.arml.insights.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import br.com.arml.insights.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Caudex"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Petit Formal Script"),
        fontProvider = provider,
    )
)

val smallTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),

    headlineLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 10.sp,
    )
)

val mediumTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 12.sp,
    )
)

val expandedTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 18.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
    )
)

val largeTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp, // Larger
    ),
    displayMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp, // Larger
    ),
    displaySmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp, // Larger
    ),
    headlineLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp, // Larger
    ),
    headlineMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp, // Larger
    ),
    headlineSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp, // Larger
    ),
    titleLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp, // Larger
    ),
    titleMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp, // Larger
    ),
    titleSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp, // Larger
    ),
    bodyLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp, // Larger
    ),
    bodyMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp, // Larger
    ),
    bodySmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp, // Larger
    ),
    labelLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 18.sp, // Larger
    ),
    labelMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp, // Larger
    ),
    labelSmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp, // Larger
    )
)