@file:OptIn(ExperimentalMaterial3Api::class)

package com.capstone.chillgoapp.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.ui.theme.PrimaryBody
import com.capstone.chillgoapp.ui.theme.PrimaryBorder
import com.capstone.chillgoapp.ui.theme.PrimaryMain
import com.capstone.chillgoapp.ui.theme.TextColor
import com.capstone.chillgoapp.ui.theme.componentShapes

@Composable
fun LogoTextComponent(value: String = "ChillGo App") {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .height(31.dp)
                .width(41.dp),
            painter = painterResource(id = R.drawable.logo_image),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            modifier = Modifier
                .heightIn(min = 40.dp),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily(Font(R.font.calistoga_regular))
            ), color = PrimaryMain, textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 26.sp,
            fontWeight = FontWeight.W600,
            fontStyle = FontStyle.Normal
        ), color = PrimaryMain, textAlign = TextAlign.Center
    )
}

@Composable
fun NormalTextComponent(
    modifier: Modifier = Modifier,
    value: String,
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = 14.sp,
    color: Color = PrimaryMain
) {
    Text(
        text = value,
        modifier = modifier,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = color, textAlign = textAlign
    )
}

@Composable
fun MyTextFieldComponent(
    labelValue: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    onTextChanged: (String) -> Unit = {},
    errorStatus: Boolean = false
) {

    val textValue = remember {
        mutableStateOf("")
    }

    CompositionLocalProvider(LocalContentColor provides TextColor) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(componentShapes.small),
            label = { Text(text = labelValue, color = TextColor) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBorder,
                focusedLabelColor = PrimaryBody,
                cursorColor = PrimaryBorder,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                unfocusedBorderColor = Color.LightGray
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
            maxLines = 1,
            value = textValue.value,
            onValueChange = {
                textValue.value = it
                onTextChanged(it)
            },
            leadingIcon = leadingIcon,
            shape = RoundedCornerShape(8.dp),
            isError = !errorStatus
        )
    }
}

@Composable
fun PasswordTextFieldComponent(
    labelValue: String,
    leadingIcon: @Composable (() -> Unit)?,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {

    val localFocusManager = LocalFocusManager.current
    val pwd = remember {
        mutableStateOf("")
    }

    val pwdVisible = remember {
        mutableStateOf(false)
    }

    CompositionLocalProvider(LocalContentColor provides TextColor) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(componentShapes.small),
            label = { Text(text = labelValue, color = TextColor) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBorder,
                focusedLabelColor = PrimaryBody,
                cursorColor = PrimaryBorder,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                unfocusedBorderColor = Color.LightGray
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            maxLines = 1,
            keyboardActions = KeyboardActions {
                localFocusManager.clearFocus()
            },
            value = pwd.value,
            onValueChange = {
                pwd.value = it
                onTextSelected(it)
            },
            leadingIcon = leadingIcon,
            trailingIcon = {
                val iconImage = if (pwdVisible.value) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }

                var description = if (pwdVisible.value) {
                    stringResource(id = R.string.hide_password)
                } else {
                    stringResource(id = R.string.show_password)
                }

                IconButton(onClick = { pwdVisible.value = !pwdVisible.value }) {
                    Icon(imageVector = iconImage, contentDescription = description)
                }
            },
            visualTransformation = if (pwdVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            isError = !errorStatus,
            shape = RoundedCornerShape(8.dp),
        )
    }
}

@Composable
fun CheckboxComponent(
    value: String, onTextSelected: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val checkState = remember {
            mutableStateOf(false)
        }

        Checkbox(checked = checkState.value,
            onCheckedChange = {
                checkState.value = !checkState.value
                onCheckedChange.invoke(it)
            })

        ClickableTextComponent(value = value, onTextSelected = onTextSelected)
    }
}

@Composable
fun ClickableTextComponent(
    value: String,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    onTextSelected: (String) -> Unit
) {
    val initTxt = "By continuing you accept our "
    val privacyPolicyTxt = "Privacy Policy"
    val andTxt = " and "
    val termConditionTxt = "Term of Use"

    val annotatedString = buildAnnotatedString {
        append(initTxt)
        withStyle(
            style = SpanStyle(
                color = PrimaryMain,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        ) {
            pushStringAnnotation(tag = privacyPolicyTxt, annotation = privacyPolicyTxt)
            append(privacyPolicyTxt)
        }
        append(andTxt)
        withStyle(
            style = SpanStyle(
                color = PrimaryMain,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        ) {
            pushStringAnnotation(tag = termConditionTxt, annotation = termConditionTxt)
            append(termConditionTxt)
        }
    }

    ClickableText(
        style = TextStyle(color = TextColor),
        text = annotatedString, onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{$span}")

                    if ((span.item == termConditionTxt) || (span.item == privacyPolicyTxt)) {
                        onTextSelected(span.item)
                    }
                }
        })
}

@Composable
fun ButtonComponent(
    value: String = "Click",
    onButtonClicked: () -> Unit = {},
    isEnabled: Boolean = false
) {
    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryMain
        )
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DividerTextComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 70.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color(0XFF8885AC),
            thickness = 1.dp
        )

        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = stringResource(R.string.or_better_yet),
            fontSize = 14.sp,
            color = TextColor
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color(0XFF8885AC),
            thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initTxt = if (tryingToLogin) "Already have an account? " else "Don’t have an account? "
    val loginTxt = if (tryingToLogin) "Login" else "Sign Up"

    val annotatedString = buildAnnotatedString {
        append(initTxt)
        withStyle(
            style = SpanStyle(color = PrimaryMain, textDecoration = TextDecoration.Underline)
        ) {
            pushStringAnnotation(tag = loginTxt, annotation = loginTxt)
            append(loginTxt)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            color = TextColor
        ),
        text = annotatedString, onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{$span}")

                    if (span.item == loginTxt) {
                        onTextSelected(span.item)
                    }
                }
        })
}

@Composable
fun UnderlineNormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = PrimaryMain,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}
