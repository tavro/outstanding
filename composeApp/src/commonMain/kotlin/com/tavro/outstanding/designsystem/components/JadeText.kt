package com.tavro.outstanding.designsystem.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun JadeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    // TODO(XXX): Add JadeTextStyle
    // TODO(XXX): autoSizeText: Boolean = false,
    minFontSize: TextUnit = TextUnit.Unspecified,
) {
    BasicText(
        text = text,
        style = TextStyle.Default, // TODO(XXX): Use Jade version
        onTextLayout = onTextLayout,
        // TODO(XXX): resolveTextOverflow()
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        // TODO(XXX): autoSize
    )
}

// TODO(XXX): Add AnnotatedString/inlineContent version of JadeText
