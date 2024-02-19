package br.pedroso.citieslist.designsystem.utils

private val emojiCache = mutableMapOf<String, String>()
private const val ASCII_OFFSET = 0x41
private const val FLAG_OFFSET = 0x1F1E6

// This code is based on this StackOverflow question:
// https://stackoverflow.com/questions/42234666/get-emoji-flag-by-country-code
fun getCountryFlagEmoji(countryCode: String): String {
    return emojiCache.getOrPut(countryCode) {
        val firstChar = countryCode[0].code - ASCII_OFFSET + FLAG_OFFSET

        val secondChar = countryCode[1].code - ASCII_OFFSET + FLAG_OFFSET

        (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
    }
}
