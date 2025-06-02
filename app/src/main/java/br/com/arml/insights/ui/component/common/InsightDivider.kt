package br.com.arml.insights.ui.component.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import br.com.arml.insights.R
import br.com.arml.insights.ui.theme.cancelColor
import br.com.arml.insights.ui.theme.dimens

@Composable
fun AnimatedHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp,
    endColor: Color,
){
    val infiniteTransition = rememberInfiniteTransition()
    val animationFloat = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val colorStart = infiniteTransition.animateColor(
        initialValue = Color.White,
        targetValue = endColor,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = stringResource(R.string.gradient_start_color_label)
    )

    val colorEnd = infiniteTransition.animateColor(
        initialValue = endColor,
        targetValue = Color.White,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = stringResource(R.string.gradient_end_color_label)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorStart.value,
                        colorEnd.value
                    )
                )
            )
            .rotate(animationFloat.value)
    ){ }
}

@Preview
@Composable
fun AnimatedHorizontalDividerPreview(){
    AnimatedHorizontalDivider(
        thickness = MaterialTheme.dimens.mediumThickness,
        endColor = cancelColor
    )
}