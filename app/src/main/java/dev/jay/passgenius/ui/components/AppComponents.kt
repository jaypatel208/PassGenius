@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package dev.jay.passgenius.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Airlines
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jay.passgenius.R
import dev.jay.passgenius.models.CategoriesPasswordStoreModel
import dev.jay.passgenius.ui.theme.OrangePrimary
import dev.jay.passgenius.utils.GeneralUtility
import kotlin.math.roundToInt

@Composable
fun MetricsComponent(totalPasswords: String, strongPasswords: String, mediocrePasswords: String) {
    val screenHeight = GeneralUtility.getScreenHeightDP()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 4)
    ) {
        val roundCorners = screenHeight / 40
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = roundCorners, bottomEnd = roundCorners))
                .background(OrangePrimary)
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
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = if (text.toInt() <= 1)
                stringResource(id = R.string.password)
            else stringResource(id = R.string.passwords),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
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
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
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
fun HomeScreenTopBar() {
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
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = OrangePrimary)
    )
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
fun PasswordColumnItem(site: String, username: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 12.dp)
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 12.dp)) {
            SiteLogo()
            Column(modifier = Modifier.padding(start = 24.dp)) {
                Text(
                    text = site,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(text = username, maxLines = 1, color = Color.Gray)
            }
        }
        Icon(
            imageVector = Icons.Filled.MoreHoriz,
            contentDescription = "More Password Options",
            modifier = Modifier.padding(start = 12.dp, end = 8.dp),
            tint = Color.Black
        )
    }
}

@Composable
fun CategoryItem(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun PasswordsLazyColumn(
    categoriesPasswordStoreModel: List<CategoriesPasswordStoreModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        categoriesPasswordStoreModel.forEach { category ->
            stickyHeader {
                Surface(Modifier.fillParentMaxWidth()) {
                    CategoryItem(text = category.alphabet, modifier = Modifier.padding(top = 8.dp))
                }
            }
            items(category.items) { item ->
                PasswordColumnItem(item.site, item.username)
            }
        }
    }
}

@Composable
fun PasswordGenerateScreenTopBar(title: String, icon: ImageVector) {
    TopAppBar(title = { }, actions = {
        Text(text = title, color = Color.Black)
        Icon(
            imageVector = icon,
            contentDescription = "Password Generate Screen",
            modifier = Modifier.padding(end = 16.dp, start = 8.dp),
            tint = Color.Black
        )
    }, navigationIcon = {
        Icon(
            imageVector = Icons.Outlined.ArrowBackIosNew,
            contentDescription = "Back",
            modifier = Modifier.padding(start = 16.dp),
            tint = Color.Black
        )
    })
}

@Composable
fun PasswordGenerateText(modifier: Modifier = Modifier, fontSize: Int) {
    Column(modifier = modifier) {
        Text(text = "Create", color = Color.Black, fontSize = fontSize.sp)
        Text(text = "a solid password by", color = Color.Gray, fontSize = fontSize.sp)
        Text(text = "choosing properties", color = Color.Gray, fontSize = fontSize.sp)
    }
}

@Composable
fun CharactersComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "Characters", fontSize = 16.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            var sliderPosition by remember { mutableFloatStateOf(0f) }
            Text(
                text = sliderPosition.toInt().toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it.roundToInt().toFloat() },
                modifier = Modifier.padding(start = 100.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color.Black,
                    activeTrackColor = Color.Black,
                    inactiveTrackColor = Color.Gray
                ),
                valueRange = 12f..22f,
                steps = 10
            )
        }
    }
}

@Composable
fun AddCharacteristicsComponent(characteristicName: String, characteristicValue: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = characteristicName, fontSize = 16.sp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = characteristicValue.toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            PlusMinusComponent()
        }
    }
}

@Composable
private fun PlusMinusComponent() {
    Row {
        CircleShapeComponent(Icons.Outlined.Add, OrangePrimary, Color.Black)
        Spacer(modifier = Modifier.width(12.dp))
        CircleShapeComponent(Icons.Outlined.Remove, Color.Black, Color.White)
    }
}

@Composable
fun CircleShapeComponent(imageVector: ImageVector, boxColor: Color, iconColor: Color) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
    ) {
        Column(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(boxColor),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = imageVector, contentDescription = "Add", tint = iconColor)
        }
    }
}

@Composable
fun PasswordShowComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = "Te69Y:0!Wn8nmk"
            val annotatedString = buildAnnotatedString {
                for (char in text) {
                    if (char.isDigit()) {
                        withStyle(style = SpanStyle(color = OrangePrimary)) {
                            append(char.toString())
                        }
                    } else {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(char.toString())
                        }
                    }
                }
            }
            Text(
                text = annotatedString,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.Autorenew,
                contentDescription = "Generate New Password",
                tint = Color.Gray
            )
        }
        Icon(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = "Password Done",
            tint = OrangePrimary,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
fun PasswordChoiceText(modifier: Modifier = Modifier, fontSize: Int) {
    Column(modifier = modifier) {
        Text(text = "Secure Your World:", color = Color.Black, fontSize = fontSize.sp)
        Text(text = "Pick Your", color = Color.Gray, fontSize = fontSize.sp)
        Text(text = "Password Style", color = Color.Gray, fontSize = fontSize.sp)
    }
}

@Preview
@Composable
fun PasswordChoiceCard() {

}