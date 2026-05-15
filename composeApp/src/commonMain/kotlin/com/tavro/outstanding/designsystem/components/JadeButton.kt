package com.tavro.outstanding.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.min

@Composable
private fun ButtonBase(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    minFontSize: TextUnit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
) {
    @Suppress
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    JadeSurface(
        onClick = onClick,
        interactionSource = interactionSource,
        enabled = enabled,
        modifier = modifier.semantics{ role = Role.Button }, // TODO(XXX): Add min height
        // TODO(008): Pass colors
        // TODO(XXX): Pass shape
        // TODO(008): Add border color
    ) {
        // TODO(008): Implement ProvideContentColorTextStyle
        Box(contentAlignment = Alignment.Center) {
            JadeText(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                // TODO(XXX): autoSize
                minFontSize = minFontSize
            )
        }
    }
}

// TODO(008): Update with new args
@Composable
fun JadeFilledButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    minFontSize: TextUnit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
) {
    ButtonBase(
        text = text,
        onClick = onClick,
        enabled = enabled,
        minFontSize = minFontSize,
        modifier = modifier,
        interactionSource = interactionSource
    )
}
