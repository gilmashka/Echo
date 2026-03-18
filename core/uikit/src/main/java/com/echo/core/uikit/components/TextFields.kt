package com.echo.core.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.echo.core.uikit.ui.theme.EchoTheme

@Composable
fun EchoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    leadingIcon: ImageVector? = null,
    isPassword: Boolean = false
) {
    var isVisible: Boolean by remember { mutableStateOf(true) }

    TextField(
        //основные
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = {
            Text(text = placeholder, style = MaterialTheme.typography.labelSmall)
        },

        //иконка
        leadingIcon = if (leadingIcon != null) {
            { Icon(imageVector = leadingIcon, contentDescription = null) }
        } else null,

        //по умолчанию
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        isError = isError,
        textStyle = MaterialTheme.typography.labelMedium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),

        //для пароля
        trailingIcon = {if(isPassword){
            EchoSecondaryIconButton(
                onClick = {isVisible = !isVisible},
                icon = if(isVisible){Icons.Rounded.Visibility} else Icons.Rounded.VisibilityOff,
                contentDescription = "change password visibility"
            ) }
        },
        visualTransformation = if(isPassword && !isVisible){
            PasswordVisualTransformation()}else{
            VisualTransformation.None}
    )
}

@Composable
fun EchoSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    searchIcon : ImageVector = Icons.Rounded.Search
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = {
            Text(text = placeholder, style = MaterialTheme.typography.labelSmall)
        },
        leadingIcon = { Icon(imageVector = searchIcon, contentDescription = null) },
        singleLine = true,
        textStyle = MaterialTheme.typography.labelMedium,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(Icons.Rounded.Close, contentDescription = "Clear All")
                }
            }
        },
        shape = RoundedCornerShape(50.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchClick() }),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}

@Preview(showBackground = true, widthDp = 400, heightDp = 500)
@Composable
fun EchoTextFieldPreview() {
    EchoTheme {
        var text by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EchoTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = "Введите ваше имя",
                Modifier.fillMaxWidth(),
                leadingIcon = Icons.Rounded.AccountCircle
            )

            Spacer(Modifier.height(16.dp))

            EchoTextField(
                value = text,
                onValueChange = {text = it},
                placeholder = "Поле с ошибкой",
                Modifier.fillMaxWidth(),
                isError = true
            )

            Spacer(Modifier.height(10.dp))

            EchoDivider(
                Modifier.padding(16.dp) //other
            )

            Spacer(Modifier.height(10.dp))

            EchoSearchField(
                value = text,
                onValueChange = {text = it},
                onSearchClick = {},
                Modifier.fillMaxWidth().height(76.dp),
                placeholder = "Найдите досуг на вечер..."
            )
        }
    }
}