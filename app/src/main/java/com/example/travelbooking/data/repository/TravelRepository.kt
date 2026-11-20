package com.example.travelbooking.data.repository

import com.example.travelbooking.R
import com.example.travelbooking.data.model.Category
import com.example.travelbooking.data.model.Destination
import com.example.travelbooking.data.model.Facility
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TravelRepository {

    private val initialDestinations = listOf(
        Destination(
            id = "villa_kayu_lama",
            title = "Villa Kayu Lama",
            location = "Ubud, Indonesia",
            rating = 4.5,
            reviewsCount = 2400,
            pricePerNight = 60,
            imageUrl = "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?auto=format&fit=crop&w=1200&q=80",
            isFavorite = true,
            description = "Villa Kayu Lama is located in the scenic and tranquil cultural heart of Ubud, Bali. Surrounded by emerald rice terraces and lush tropical palm gardens, it features private outdoor villas with dedicated sun loungers, luxury ensuite bathrooms, and signature Indonesian hospitality.",
            facilities = listOf(
                Facility("Outdoor Pool", "POOL", "Outdoor Pool"),
                Facility("1 Bathtub", "TUB", "1 Bathtub"),
                Facility("Free Wi-Fi", "WIFI", "Free Wi-Fi"),
                Facility("Free Breakfast", "DINNER", "Free Breakfast")
            ),
            category = "Hotels",
            isPopular = true,
            isRecommended = true,
            imageRes = R.drawable.img_villa_kayu_lama
        ),
        Destination(
            id = "bali_coast_retreat",
            title = "Uluwatu Ocean Cliff Resort",
            location = "Bali, Indonesia",
            rating = 4.9,
            reviewsCount = 1850,
            pricePerNight = 120,
            imageUrl = "https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1200&q=80",
            isFavorite = true,
            description = "Perched on the dramatic limestone cliffs of southern Bali overlooking the Indian Ocean. Enjoy breathtaking sunset views, direct beach path access, and cliffside infinity pools.",
            facilities = listOf(
                Facility("Infinity Pool", "POOL", "Infinity Pool"),
                Facility("Free Wi-Fi", "WIFI", "Free Wi-Fi"),
                Facility("Free Breakfast", "DINNER", "Buffet"),
                Facility("Spa Tub", "TUB", "Ocean Tub")
            ),
            category = "Villas",
            isPopular = true,
            isRecommended = true,
            imageRes = R.drawable.img_uluwatu_cliff
        ),
        Destination(
            id = "canggu_beach_villa",
            title = "Canggu Sunset Haven",
            location = "Canggu, Bali",
            rating = 4.8,
            reviewsCount = 920,
            pricePerNight = 85,
            imageUrl = "https://images.unsplash.com/photo-1540555700478-4be289fbecef?auto=format&fit=crop&w=1200&q=80",
            isFavorite = false,
            description = "Modern bohemian lifestyle villa situated just minutes from Echo Beach, popular surf breaks, and artisanal cafes. Features open-air living areas and private lush plunge pool.",
            facilities = listOf(
                Facility("Private Pool", "POOL", "Private Pool"),
                Facility("Free Wi-Fi", "WIFI", "High-speed"),
                Facility("1 Bathtub", "TUB", "Stone Tub"),
                Facility("Breakfast", "DINNER", "Floating Breakfast")
            ),
            category = "Hotels",
            isPopular = false,
            isRecommended = true,
            imageRes = R.drawable.img_canggu_haven
        )
    )

    private val _destinations = MutableStateFlow(initialDestinations)
    val destinations: StateFlow<List<Destination>> = _destinations.asStateFlow()

    val categories = listOf(
        Category("hotels", "Hotels"),
        Category("foods", "Foods"),
        Category("activities", "Activities")
    )

    fun toggleFavorite(destinationId: String) {
        _destinations.update { list ->
            list.map { item ->
                if (item.id == destinationId) {
                    item.copy(isFavorite = !item.isFavorite)
                } else {
                    item
                }
            }
        }
    }

    fun getDestinationById(id: String): Destination? {
        return _destinations.value.find { it.id == id }
    }
}
