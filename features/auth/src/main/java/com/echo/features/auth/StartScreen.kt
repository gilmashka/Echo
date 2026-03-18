package com.echo.features.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.echo.core.uikit.components.EchoDivider
import com.echo.core.uikit.components.EchoPrimaryButton
import com.echo.core.uikit.components.EchoSecondaryButton
import com.echo.core.uikit.ui.theme.EchoTheme


@Composable
fun StartScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Spacer(modifier = Modifier.height(300.dp))

            Text(
                text = "Echo",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "hear the music of the city",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(10.dp))

            EchoDivider()

            Spacer(Modifier.height(25.dp))

            EchoPrimaryButton(
                text = "Вход",
                onClick = {},
                modifier = Modifier.height(55.dp).width(205.dp),
                icon = Icons.Rounded.Login
            )

            Spacer(Modifier.height(25.dp))

            EchoSecondaryButton(
                text = "Регистрация",
                onClick = {},
                modifier = Modifier.height(55.dp).width(205.dp),
                icon = Icons.Rounded.PersonAdd
            )
        }
    }

}

@Preview
@Composable
fun StartScreenPreview(){
    EchoTheme {
        StartScreen()
    }
}