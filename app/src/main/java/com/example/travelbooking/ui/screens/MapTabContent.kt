package com.example.travelbooking.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.travelbooking.data.model.Destination
import com.example.travelbooking.ui.theme.DarkSlateNavy
import com.example.travelbooking.ui.theme.DeepNavy
import com.example.travelbooking.ui.theme.StarGold
import com.example.travelbooking.ui.theme.SurfaceWhite
import com.example.travelbooking.ui.theme.TealAccent
import com.example.travelbooking.ui.theme.TextPrimary
import com.example.travelbooking.ui.theme.TextSecondary

@Composable
fun MapTabContent(
    destinations: List<Destination>,
    onDestinationClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDestinationId by remember {
        mutableStateOf(destinations.firstOrNull()?.id ?: "villa_kayu_lama")
    }

    val selectedDestination = destinations.find { it.id == selectedDestinationId }
        ?: destinations.firstOrNull()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE9F1F4))
    ) {
        // 1. Stylized Bali Map Canvas with roads and geography
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Coastline contour (island mass)
            val islandPath = Path().apply {
                moveTo(w * 0.15f, h * 0.35f)
                cubicTo(w * 0.25f, h * 0.20f, w * 0.65f, h * 0.18f, w * 0.85f, h * 0.30f)
                cubicTo(w * 0.95f, h * 0.45f, w * 0.80f, h * 0.70f, w * 0.65f, h * 0.85f)
                cubicTo(w * 0.45f, h * 0.90f, w * 0.30f, h * 0.78f, w * 0.22f, h * 0.65f)
                cubicTo(w * 0.12f, h * 0.55f, w * 0.10f, h * 0.40f, w * 0.15f, h * 0.35f)
                close()
            }
            drawPath(islandPath, color = Color(0xFFF6FAF8))

            // Main geographic connecting roads
            drawLine(
                color = Color(0xFFD4E2E7),
                start = Offset(w * 0.35f, h * 0.50f),
                end = Offset(w * 0.50f, h * 0.35f),
                strokeWidth = 6f
            )
            drawLine(
                color = Color(0xFFD4E2E7),
                start = Offset(w * 0.50f, h * 0.35f),
                end = Offset(w * 0.55f, h * 0.75f),
                strokeWidth = 6f
            )
            drawLine(
                color = Color(0xFFD4E2E7),
                start = Offset(w * 0.35f, h * 0.50f),
                end = Offset(w * 0.55f, h * 0.75f),
                strokeWidth = 6f
            )
        }

        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .align(Alignment.TopCenter)
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite.copy(alpha = 0.95f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Bali Island Map",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Showing ${destinations.size} luxury destinations",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(TealAccent.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Indonesia",
                            color = TealAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Interactive Map Pins
        // Pin 1: Ubud - Villa Kayu Lama (Center-North)
        MapLocationPin(
            title = "Villa Kayu Lama",
            price = "$60",
            isSelected = selectedDestinationId == "villa_kayu_lama",
            onClick = { selectedDestinationId = "villa_kayu_lama" },
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (0).dp, y = (-70).dp)
        )

        // Pin 2: Canggu - Sunset Haven (South-West)
        MapLocationPin(
            title = "Canggu Haven",
            price = "$85",
            isSelected = selectedDestinationId == "canggu_beach_villa",
            onClick = { selectedDestinationId = "canggu_beach_villa" },
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-100).dp, y = (40).dp)
        )

        // Pin 3: Uluwatu - Ocean Cliff (South)
        MapLocationPin(
            title = "Uluwatu Cliff",
            price = "$120",
            isSelected = selectedDestinationId == "bali_coast_retreat",
            onClick = { selectedDestinationId = "bali_coast_retreat" },
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (40).dp, y = (140).dp)
        )

        // Map Control Floating Buttons (Right side)
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FloatingMapButton(icon = Icons.Default.Add, onClick = {})
            FloatingMapButton(icon = Icons.Default.Remove, onClick = {})
            FloatingMapButton(icon = Icons.Default.MyLocation, onClick = {})
        }

        // Bottom Selected Destination Preview Card
        if (selectedDestination != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .align(Alignment.BottomCenter)
                    .clickable { onDestinationClicked(selectedDestination.id) },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val imgPainter = if (selectedDestination.imageRes != null) {
                        painterResource(selectedDestination.imageRes)
                    } else {
                        rememberAsyncImagePainter(selectedDestination.imageUrl)
                    }

                    Image(
                        painter = imgPainter,
                        contentDescription = selectedDestination.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(86.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = selectedDestination.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                maxLines = 1
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = TealAccent,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = selectedDestination.location,
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = StarGold,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${selectedDestination.rating}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }

                            Text(
                                text = "$${selectedDestination.pricePerNight} / night",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepNavy
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MapLocationPin(
    title: String,
    price: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        // Price badge
        Box(
            modifier = Modifier
                .shadow(if (isSelected) 8.dp else 4.dp, RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .background(if (isSelected) DeepNavy else SurfaceWhite)
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) TealAccent else Color(0xFFE2E8F0),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = price,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else DeepNavy
            )
        }

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = title,
            tint = if (isSelected) TealAccent else DeepNavy,
            modifier = Modifier
                .size(if (isSelected) 36.dp else 28.dp)
                .offset(y = (-4).dp)
        )
    }
}

@Composable
fun FloatingMapButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .shadow(4.dp, CircleShape)
            .clip(CircleShape)
            .background(SurfaceWhite)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = DeepNavy,
            modifier = Modifier.size(20.dp)
        )
    }
}
