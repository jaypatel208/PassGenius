@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package dev.jay.passgenius.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Airlines
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jay.passgenius.R
import dev.jay.passgenius.di.models.CategoriesPasswordStoreModel
import dev.jay.passgenius.di.models.PasswordStoreModel
import dev.jay.passgenius.ui.theme.DavyGrey
import dev.jay.passgenius.ui.theme.OrangePrimary
import dev.jay.passgenius.utils.GeneralUtility
import java.util.Locale

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
fun HomeScreenTopBar(topBarContainerColor: Color) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.passwords),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }, actions = {
            IconButton(onClick = { /* TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(id = R.string.search),
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = topBarContainerColor)
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
        Icon(imageVector = Icons.Filled.Airlines, contentDescription = stringResource(id = R.string.logo))
    }
}

@Composable
fun PasswordColumnItem(passwordStoreModel: PasswordStoreModel, onPasswordClick: (PasswordStoreModel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 12.dp)
            .height(50.dp)
            .clickable { onPasswordClick(passwordStoreModel) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 12.dp)) {
            SiteLogo()
            Column(modifier = Modifier.padding(start = 24.dp)) {
                Text(
                    text = passwordStoreModel.site.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                val username = passwordStoreModel.username.ifBlank { stringResource(id = R.string.blank_field) }
                Text(text = username, maxLines = 1, color = Color.Gray)
            }
        }
        Icon(
            imageVector = Icons.Filled.MoreHoriz,
            contentDescription = stringResource(id = R.string.more_password_options),
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
    modifier: Modifier = Modifier,
    onPasswordClick: (PasswordStoreModel) -> Unit
) {
    LazyColumn(modifier) {
        categoriesPasswordStoreModel.forEach { category ->
            stickyHeader {
                Surface(Modifier.fillParentMaxWidth()) {
                    CategoryItem(text = category.alphabet, modifier = Modifier.padding(top = 8.dp))
                }
            }
            items(category.items) { item ->
                PasswordColumnItem(passwordStoreModel = item) { clickedPasswordStoreModel ->
                    onPasswordClick(clickedPasswordStoreModel)
                }
            }
        }
    }
}

@Composable
fun NoPasswordStoredComponent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.cactus),
            contentDescription = stringResource(id = R.string.no_stored_passwords),
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = stringResource(id = R.string.like_a_desert), color = DavyGrey, fontSize = 26.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = stringResource(id = R.string.no_stored_passwords), color = Color.Gray, fontSize = 16.sp)
    }
}

@Composable
fun ViewPasswordComponent(
    siteName: String,
    username: String,
    password: String,
    onUserNameLongClick: () -> Unit,
    onPasswordLongClick: () -> Unit,
    onUserNameClick: () -> Unit,
    onPasswordClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 42.dp),
        border = BorderStroke(5.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FieldIndicationText(fieldName = stringResource(id = R.string.site__name))
                    FieldValueText(
                        fieldValue = " $siteName", modifier = Modifier.padding(horizontal = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FieldIndicationText(fieldName = stringResource(id = R.string.username__))
                    FieldValueText(
                        fieldValue = " $username",
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .combinedClickable(onLongClick = {
                                onUserNameLongClick()
                            }, onClick = {
                                onUserNameClick()
                            })
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FieldIndicationText(fieldName = stringResource(id = R.string.password__))
                    FieldValueText(
                        fieldValue = " $password",
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .combinedClickable(onLongClick = {
                                onPasswordLongClick()
                            }, onClick = {
                                onPasswordClick()
                            })
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                CommonAppButton(
                    onClick = { }, buttonText = stringResource(id = R.string.edit), modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                CommonAppButton(
                    onClick = { }, buttonText = stringResource(id = R.string.delete), modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}