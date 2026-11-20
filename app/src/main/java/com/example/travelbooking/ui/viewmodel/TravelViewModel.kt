package com.example.travelbooking.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbooking.data.model.Category
import com.example.travelbooking.data.model.Destination
import com.example.travelbooking.data.repository.TravelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class BookingState(
    val checkInDate: String = "18 Dec",
    val checkOutDate: String = "22 Dec",
    val nightsCount: Int = 4,
    val guestCount: Int = 2,
    val isConfirmed: Boolean = false
)

class TravelViewModel(
    private val repository: TravelRepository = TravelRepository()
) : ViewModel() {

    val categories: List<Category> = repository.categories

    private val _selectedCategory = MutableStateFlow("Location")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _bookingState = MutableStateFlow(BookingState())
    val bookingState: StateFlow<BookingState> = _bookingState

    val allDestinations: StateFlow<List<Destination>> = repository.destinations

    val popularDestinations: StateFlow<List<Destination>> = combine(
        repository.destinations,
        _searchQuery,
        _selectedCategory
    ) { destinations, query, category ->
        destinations.filter { item ->
            val matchesQuery = query.isBlank() || 
                item.title.contains(query, ignoreCase = true) ||
                item.location.contains(query, ignoreCase = true)
            val matchesCategory = category.equals("Location", ignoreCase = true) || 
                item.category.equals(category, ignoreCase = true)
            item.isPopular && matchesQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recommendedDestinations: StateFlow<List<Destination>> = combine(
        repository.destinations,
        _searchQuery
    ) { destinations, query ->
        destinations.filter { item ->
            val matchesQuery = query.isBlank() ||
                item.title.contains(query, ignoreCase = true) ||
                item.location.contains(query, ignoreCase = true)
            item.isRecommended && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteDestinations: StateFlow<List<Destination>> = repository.destinations.combine(
        MutableStateFlow(Unit)
    ) { destinations, _ ->
        destinations.filter { it.isFavorite }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavorite(destinationId: String) {
        repository.toggleFavorite(destinationId)
    }

    fun getDestination(id: String): Destination? {
        return repository.getDestinationById(id)
    }

    fun incrementGuests() {
        _bookingState.value = _bookingState.value.copy(
            guestCount = _bookingState.value.guestCount + 1
        )
    }

    fun decrementGuests() {
        if (_bookingState.value.guestCount > 1) {
            _bookingState.value = _bookingState.value.copy(
                guestCount = _bookingState.value.guestCount - 1
            )
        }
    }

    fun confirmBooking() {
        _bookingState.value = _bookingState.value.copy(isConfirmed = true)
    }

    fun resetBookingState() {
        _bookingState.value = BookingState()
    }
}
