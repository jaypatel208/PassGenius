@file:OptIn(ExperimentalMaterial3Api::class)

package dev.jay.passgenius.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Airlines
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jay.passgenius.R
import dev.jay.passgenius.ui.theme.OrangeMetrics
import dev.jay.passgenius.utils.GeneralUtility

@Composable
fun MetricsComponent(totalPasswords: String, strongPasswords: String, mediocrePasswords: String) {
    val screenHeight = GeneralUtility.getScreenHeightDP()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3)
            .padding(top = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(screenHeight / 30))
                .background(OrangeMetrics)
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                TextTotalPasswords(totalPasswords)
                TextPasswordStrength(count = strongPasswords, label = stringResource(id = R.string.strong))
                TextPasswordStrength(count = mediocrePasswords, label = stringResource(id = R.string.mediocre))
            }
        }
    }
}

@Composable
fun TextTotalPasswords(text: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = text,
            fontSize = 65.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (text.toInt() <= 1)
                stringResource(id = R.string.password)
            else stringResource(id = R.string.passwords),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        NoTopRectangle(130)
    }
}

@Composable
fun TextPasswordStrength(count: String, label: String) {
    Column(
        modifier = Modifier.padding(start = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
        NoTopRectangle(width = 90)
    }
}

@Composable
private fun NoTopRectangle(width: Int) {
    Row(verticalAlignment = Alignment.Bottom) {
        Spacer(
            modifier = Modifier
                .height(25.dp)
                .width(3.dp)
                .background(Color.Black)
        )
        Spacer(
            modifier = Modifier
                .height(3.dp)
                .width(width.dp)
                .background(Color.Black)
        )
        Spacer(
            modifier = Modifier
                .height(25.dp)
                .width(3.dp)
                .background(Color.Black)
        )
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.passwords),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }, actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(id = R.string.search),
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = OrangeMetrics)
    )
}

@Composable
fun PasswordColumnItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end =12.dp)) {
            SiteLogo()
            Column(modifier = Modifier.padding(start = 24.dp)) {
                Text(
                    text = "Google.com",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "leohoward@gmail.com", maxLines = 1, color = Color.Gray)
            }
        }
        Icon(
            imageVector = Icons.Filled.MoreHoriz,
            contentDescription = "More Password Column Item",
            modifier = Modifier.padding(start = 12.dp, end = 8.dp)
        )
    }
}

@Composable
private fun SiteLogo() {
    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .size(size = 50.dp)
        ) {
            drawRect(color = Color.Gray)
        }
        Icon(imageVector = Icons.Filled.Airlines, contentDescription = "Logo")
    }
}

@Composable
@Preview
fun PasswordColumnItemPreview() {
    PasswordColumnItem()
}