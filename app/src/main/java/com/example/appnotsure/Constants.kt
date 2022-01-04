package com.example.appnotsure

object Constants {
    const val PARTICIPANTS_KEY = "numberParticipants"
    const val PARTICIPANTS_NUMBER_SET_KEY = "participants_number_set"
    const val ACTIVITY_TYPE_SET_KEY = "activity_type_set"
    const val ACTIVITY_NAME_KEY = "activity_name"
    const val ACTIVITY_PRICE_KEY = "activity_price"
    const val ACTIVITY_TYPE_KEY = "activity_type"
    const val ACTIVITY_ERROR_KEY = "activity_error"
    const val PARTICIPANTS_NUMBER_KEY = "participants_number"
    const val FRAGMENT_NAME = "suggestion_fragment"
    const val WRONG_NUMBER_OF_PARTICIPANTS_MESSAGE = "Enter the correct number of participants"
    const val BASE_URL = "http://www.boredapi.com/api/"
    const val DEFAULT_NUMBER_PARTICIPANTS = 1
    val LIST_OF_TYPES_OF_ACTIVITIES = arrayListOf(
        "Education",
        "Recreational",
        "Social",
        "Diy",
        "Charity",
        "Cooking",
        "Relaxation",
        "Music",
        "Busywork"
    )
    const val ERROR_DEFAULT = "Some error, try again"

    const val PRICE_FREE = "Free"
    const val PRICE_LOW = "Low"
    const val PRICE_MEDIUM = "Medium"
    const val PRICE_HIGH = "High"
    const val UNKNOWN = "Unknown"
}