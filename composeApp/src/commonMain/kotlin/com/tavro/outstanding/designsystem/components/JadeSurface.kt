package com.tavro.outstanding.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput

@Composable
@NonRestartableComposable
fun JadeSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = Color.Transparent,
    contentColor: Color = Color.Black, // TODO(008): Add contentColorFor(color)
    border: BorderStroke? = null,
    content: @Composable BoxScope.() -> Unit
) {
    // TODO(008): Use CompositionLocalProvider
    Box(
        modifier = modifier
            .then(if (border != null) Modifier.border(border, shape) else Modifier)
            .background(color, shape)
            .clip(shape)
            .pointerInput(Unit) {},
        propagateMinConstraints = true
    ) {
        content()
    }
}

@Composable
@NonRestartableComposable
fun JadeSurface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = Color.Transparent,
    contentColor: Color = Color.Black, // TODO(008): Add contentColorFor(color)
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    // TODO(008): Use CompositionLocalProvider
    Box(
        modifier = modifier
            .then(if (border != null) Modifier.border(border, shape) else Modifier)
            .background(color, shape)
            .clip(shape)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = enabled,
                onClick = onClick
            ),
        propagateMinConstraints = true
    ) {
        content()
    }
}
