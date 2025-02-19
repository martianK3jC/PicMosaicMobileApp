package com.android.picmosaic

data class UserProfile(
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val address: String,
    val city: String
)

object DummyUserData {
    private val userCredentials = mapOf(
        "test@email.com" to "password123",
        "john@email.com" to "john123",
        "jane@email.com" to "jane123",
        "keshample@email.com" to "keshample"
    )

    private var userProfiles = mutableMapOf(
        "test@email.com" to UserProfile(
            email = "test@email.com",
            firstName = "Test",
            lastName = "User",
            phone = "123-456-7890",
            address = "123 Test St",
            city = "Test City"
        ),
        "john@email.com" to UserProfile(
            email = "john@email.com",
            firstName = "John",
            lastName = "Doe",
            phone = "123-456-7891",
            address = "456 John St",
            city = "John City"
        ),
        "jane@email.com" to UserProfile(
            email = "jane@email.com",
            firstName = "Jane",
            lastName = "Smith",
            phone = "123-456-7892",
            address = "789 Jane St",
            city = "Jane City"
        ),
        "keshample@email.com" to UserProfile(
            email = "keshample@email.com",
            firstName = "Kesh",
            lastName = "Ample",
            phone = "123-456-7893",
            address = "321 Kesh St",
            city = "Kesh City"
        )
    )

    fun validateCredentials(email: String, password: String): Boolean {
        return userCredentials[email] == password
    }

    fun getUserProfile(email: String): UserProfile? {
        return userProfiles[email]
    }

    fun updateUserProfile(email: String, profile: UserProfile) {
        userProfiles[email] = profile
    }
}
