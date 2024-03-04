package dev.jay.passgenius.di.models

data class PasswordStoreModel(
    val id: Int,
    val site: String,
    val username: String,
    val password: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            site, username, site.first().toString(), username.first().toString()
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
