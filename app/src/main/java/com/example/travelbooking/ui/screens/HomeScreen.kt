package com.example.travelbooking.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.travelbooking.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.travelbooking.data.model.Destination
import com.example.travelbooking.ui.theme.BackgroundCanvas
import com.example.travelbooking.ui.theme.DarkSlateNavy
import com.example.travelbooking.ui.theme.DeepNavy
import com.example.travelbooking.ui.theme.PromoYellow
import com.example.travelbooking.ui.theme.StarGold
import com.example.travelbooking.ui.theme.SurfaceWhite
import com.example.travelbooking.ui.theme.TealAccent
import com.example.travelbooking.ui.theme.TealAccentLight
import com.example.travelbooking.ui.theme.TextMuted
import com.example.travelbooking.ui.theme.TextPrimary
import com.example.travelbooking.ui.theme.TextSecondary
import com.example.travelbooking.ui.viewmodel.TravelViewModel

@Composable
fun HomeScreen(
    viewModel: TravelViewModel,
    onDestinationClicked: (String) -> Unit,
    onFavoritesClicked: () -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val popularList by viewModel.popularDestinations.collectAsState()
    val bookingState by viewModel.bookingState.collectAsState()
    val allDestinations by viewModel.allDestinations.collectAsState()

    var selectedNavIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = BackgroundCanvas,
        bottomBar = {
            FigmaFloatingBottomNav(
                selectedIndex = selectedNavIndex,
                onItemSelected = { index ->
                    if (index == 1) {
                        onFavoritesClicked()
                    } else {
                        selectedNavIndex = index
                    }
                }
            )
        }
    ) { innerPadding ->
        when (selectedNavIndex) {
            2 -> {
                MapTabContent(
                    destinations = allDestinations,
                    onDestinationClicked = onDestinationClicked,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            3 -> {
                ProfileTabContent(
                    destinations = allDestinations,
                    onDestinationClicked = onDestinationClicked,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    // 1. Header: Greeting & Avatar
                    item {
                        FigmaUserHeader(
                            userName = "Dennis",
                            modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
                        )
                    }

                    // 2. Search Card Container
                    item {
                        FigmaSearchCard(
                            locationQuery = searchQuery,
                            onLocationChanged = viewModel::onSearchQueryChanged,
                            dateRange = "July 08 - July 15",
                            guestCount = bookingState.guestCount,
                            onIncrementGuests = viewModel::incrementGuests,
                            onDecrementGuests = viewModel::decrementGuests,
                            onSearchClick = {
                                val destination = popularList.firstOrNull()
                                if (destination != null) {
                                    onDestinationClicked(destination.id)
                                }
                            },
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                        )
                    }

                    // 3. Featured Promotion Card ("Save 50%" - Bali Promo)
                    item {
                        FigmaPromoCard(
                            onExploreClick = {
                                onDestinationClicked("villa_kayu_lama")
                            },
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                        )
                    }

                    // 4. Popular Accommodations Section
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 22.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Popular Stays",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "See all",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TealAccent,
                                modifier = Modifier.clickable { onFavoritesClicked() }
                            )
                        }
                    }

                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            items(popularList, key = { it.id }) { destination ->
                                FigmaHotelCard(
                                    destination = destination,
                                    onCardClick = { onDestinationClicked(destination.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FigmaUserHeader(
    userName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // User Avatar
            Image(
                painter = painterResource(R.drawable.img_avatar),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "Hello, $userName!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Ready for your vacation?",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        // Notification Icon Pill
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(SurfaceWhite)
                .shadow(elevation = 2.dp, shape = CircleShape)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = DarkSlateNavy,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun FigmaSearchCard(
    locationQuery: String,
    onLocationChanged: (String) -> Unit,
    dateRange: String,
    guestCount: Int,
    onIncrementGuests: () -> Unit,
    onDecrementGuests: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Location Input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(BackgroundCanvas)
                    .padding(horizontal = 14.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = TealAccent,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    value = locationQuery,
                    onValueChange = onLocationChanged,
                    placeholder = {
                        Text("Bali, Indonesia", color = TextMuted, fontSize = 14.sp)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Filter",
                    tint = DarkSlateNavy,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { }
                )
            }

            // 2. Date Selection Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(BackgroundCanvas)
                    .clickable { }
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = TealAccent,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = dateRange,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            }

            // 3. Guest Stepper Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(BackgroundCanvas)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = null,
                        tint = TealAccent,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "$guestCount Guests",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(SurfaceWhite)
                            .clickable { onDecrementGuests() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = TextPrimary, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(TealAccent)
                            .clickable { onIncrementGuests() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }

            // 4. Search CTA Button
            Button(
                onClick = onSearchClick,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkSlateNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Search",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun FigmaPromoCard(
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onExploreClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.img_bali_promo),
                    contentDescription = "Bali Beach",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Save 50% Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(TealAccent)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Save 50%",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "DETAILS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TealAccent,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "There is no other place like Bali in this world. A magical blend of culture, people, nature, activities, weather, culinary delights, nightlife, and many other interesting experiences...",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Read more",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkSlateNavy
                )
            }
        }
    }
}

@Composable
fun FigmaHotelCard(
    destination: Destination,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            val hotelPainter = if (destination.imageRes != null) {
                painterResource(destination.imageRes)
            } else {
                rememberAsyncImagePainter(destination.imageUrl)
            }
            Image(
                painter = hotelPainter,
                contentDescription = destination.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            )

            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = destination.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TealAccent,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = destination.location,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = StarGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${destination.rating}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Text(
                        text = "$${destination.pricePerNight} / Night",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkSlateNavy
                    )
                }
            }
        }
    }
}

@Composable
fun FigmaFloatingBottomNav(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(DeepNavy)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navItems = listOf(
                Icons.Default.Home to "Home",
                Icons.Default.Search to "Explore",
                Icons.Default.LocationOn to "Map",
                Icons.Default.Person to "Profile"
            )

            navItems.forEachIndexed { index, (icon, desc) ->
                val isSelected = selectedIndex == index
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) SurfaceWhite else Color.Transparent)
                        .clickable { onItemSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = desc,
                        tint = if (isSelected) TealAccent else Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}
