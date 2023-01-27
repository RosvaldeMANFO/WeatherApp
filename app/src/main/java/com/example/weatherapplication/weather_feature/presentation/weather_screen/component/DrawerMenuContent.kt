package com.example.weatherapplication.weather_feature.presentation.weather_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.ui.theme.Season


@Composable
fun DrawerMenuContent(
    season: Season,
    onClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.White
) {
    val icons = listOf(
        Icons.Filled.LocationSearching
    )
    Column(
        modifier = modifier
    ) {
        DrawerHeader(imageId = season.imageRes)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            icons.forEachIndexed{index, icon ->
                DrawerItem(
                    title = "Change location",
                    icon = icon ,
                    contentColor = contentColor
                ) {
                    onClick(index)
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
    icon: ImageVector,
    contentColor: Color,
    onClick: () -> Unit,
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            color = Color.White,
        )
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = contentColor
        )
    }
}