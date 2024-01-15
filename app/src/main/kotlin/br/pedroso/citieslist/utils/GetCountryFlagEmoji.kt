package br.pedroso.citieslist.utils

private const val ASCII_OFFSET = 0x41
private const val FLAG_OFFSET = 0x1F1E6

// This code is based on this StackOverflow question:
// https://stackoverflow.com/questions/42234666/get-emoji-flag-by-country-code
fun getCountryFlagEmoji(countryCode: String): String {
    val firstChar = countryCode[0].code - ASCII_OFFSET + FLAG_OFFSET

    val secondChar = countryCode[1].code - ASCII_OFFSET + FLAG_OFFSET

    return (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
}