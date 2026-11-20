package com.example.travelbooking.data.model

data class Destination(
    val id: String,
    val title: String,
    val location: String,
    val rating: Double,
    val reviewsCount: Int,
    val pricePerNight: Int,
    val imageUrl: String,
    val isFavorite: Boolean = false,
    val description: String,
    val facilities: List<Facility>,
    val category: String,
    val isPopular: Boolean = false,
    val isRecommended: Boolean = false,
    val imageRes: Int? = null
)

data class Facility(
    val name: String,
    val iconType: String,
    val quantity: String? = null
)

data class Category(
    val id: String,
    val title: String
)
