package br.com.arml.insights.ui.screen.splash

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R
import br.com.arml.insights.ui.theme.BlueLight
import br.com.arml.insights.ui.theme.Yellow100
import br.com.arml.insights.ui.theme.Yellow800
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateTo: () -> Unit = {}
){

    LaunchedEffect(Unit) {
        delay(5_000)
        onNavigateTo()
    }

    val infiniteTransition = rememberInfiniteTransition()
    val rotationAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val colorStart = infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.primary,
        targetValue = MaterialTheme.colorScheme.tertiary,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = stringResource(R.string.gradient_start_color_label)
    )

    val colorEnd = infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.tertiary,
        targetValue = MaterialTheme.colorScheme.primary,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = stringResource(R.string.gradient_end_color_label)
    )

    val animatedColor by animateColorAsState(
        targetValue = if (colorStart.value == BlueLight) Yellow800 else Yellow100,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = stringResource(R.string.gradient_lamp_label),
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorStart.value,
                        colorEnd.value
                    )
                )
            ).rotate(rotationAnimation.value)
    ){ }

    Box(
        modifier = modifier.fillMaxSize(),
    ){
        Image(
            modifier = modifier.align(Alignment.Center).size(300.dp),
            painter = painterResource(id = R.drawable.ic_insights_foreground),
            contentDescription = stringResource(R.string.insight_logo_description),
            colorFilter = ColorFilter.tint(animatedColor)
        )
    }

}

@Preview
@Composable
fun SplashScreenPreview(){
    SplashScreen()
}