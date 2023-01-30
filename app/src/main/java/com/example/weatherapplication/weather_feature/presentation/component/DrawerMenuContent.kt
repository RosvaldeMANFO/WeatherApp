package com.example.weatherapplication.weather_feature.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.ui.theme.Season
import com.example.weatherapplication.ui.theme.lightMode
import com.example.weatherapplication.weather_feature.domain.weather.DrawerOptionType


@Composable
fun DrawerMenuContent(
    season: Season,
    contentColor: Color,
    onClick: (option: DrawerOptionType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        DrawerHeader(imageId = season.imageRes)
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            DrawerItem(
                title = if(lightMode) "Dark mode" else "Light mode",
                contentColor = contentColor
            ) {
                ToggleButton(
                    isChecked = lightMode,
                    contentColor = contentColor,
                    icon = if(lightMode) Icons.Filled.Light else Icons.Filled.ShieldMoon,
                    onClick = {onClick(DrawerOptionType.ToggleMode)}
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            DrawerItem(
                title = "About",
                contentColor = contentColor
            ) {
                IconButton(
                    onClick = {onClick(DrawerOptionType.About)}
                ){
                    Icon(
                        imageVector = Icons.Filled.QuestionMark,
                        contentDescription = null,
                        tint = contentColor
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerHeader(
    imageId: Int,
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(id = imageId) ,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun DrawerItem(
    title: String,
    contentColor: Color,
    content: @Composable () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            color = contentColor,
        )

        content()
    }
}