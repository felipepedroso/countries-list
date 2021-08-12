package br.pedroso.citieslist.repository

import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.repository.CitiesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.math.floor

class BinarySearchCitiesRepository(
    private val citiesDataSource: CitiesDataSource,
    scope: CoroutineScope = GlobalScope,
) : CitiesRepository {

    private val sortedCitiesListDeferred: Deferred<List<City>> =
        scope.async(start = CoroutineStart.LAZY) {
            prepareCitiesList()
        }

    override suspend fun getCities(searchQuery: String): List<City> {
        return if (searchQuery.isBlank()) {
            sortedCitiesListDeferred.await()
        } else {
            getCitiesWithPrefix(searchQuery)
        }
    }

    private suspend fun getCitiesWithPrefix(prefix: String): List<City> {
        val availableCities = sortedCitiesListDeferred.await()

        return if (availableCities.isEmpty()) {
            emptyList()
        } else {
            coroutineScope {
                val leftmostDeferred = async { findLeftmostIndexWithPrefix(prefix) }

                val rightmostDeferred = async { findRightmostIndexWithPrefix(prefix) }

                val leftmostIndex = leftmostDeferred.await()

                val rightmostIndex = rightmostDeferred.await()

                when {
                    leftmostIndex > rightmostIndex ->
                        throw InvalidBoundariesIndexesException(leftmostIndex, rightmostIndex)
                    leftmostIndex == INVALID_INDEX || rightmostIndex == INVALID_INDEX -> emptyList()
                    leftmostIndex == rightmostIndex -> listOf(availableCities[leftmostIndex])
                    else -> availableCities.slice(leftmostIndex..rightmostIndex)
                }
            }
        }
    }

    private suspend fun findRightmostIndexWithPrefix(prefix: String): Int {
        val availableCities = sortedCitiesListDeferred.await()

        var leftIndex = 0
        var rightIndex = availableCities.size

        while (leftIndex < rightIndex) {
            val middleIndex: Int = calculateMiddleIndex(leftIndex, rightIndex)
            val city = availableCities[middleIndex]
            val compareResult = city.name.compareToPrefix(prefix)

            if (compareResult > 0) {
                rightIndex = middleIndex
            } else {
                leftIndex = middleIndex + 1
            }
        }

        return if (availableCities[rightIndex - 1].name.startsWith(prefix, ignoreCase = true)) {
            rightIndex - 1
        } else {
            INVALID_INDEX
        }
    }

    private suspend fun findLeftmostIndexWithPrefix(prefix: String): Int {
        val availableCities = sortedCitiesListDeferred.await()

        var leftIndex = 0
        var rightIndex = availableCities.size

        while (leftIndex < rightIndex) {
            val middleIndex: Int = calculateMiddleIndex(leftIndex, rightIndex)
            val city = availableCities[middleIndex]
            val compareResult = city.name.compareToPrefix(prefix)

            if (compareResult < 0) {
                leftIndex = middleIndex + 1
            } else {
                rightIndex = middleIndex
            }
        }

        return if (availableCities[leftIndex].name.startsWith(prefix, ignoreCase = true)) {
            leftIndex
        } else {
            INVALID_INDEX
        }
    }

    private fun calculateMiddleIndex(leftIndex: Int, rightIndex: Int) =
        floor((leftIndex + rightIndex) / 2.0f).toInt()

    private fun String.compareToPrefix(prefix: String): Int =
        take(prefix.length).compareTo(prefix, ignoreCase = true)

    private suspend fun prepareCitiesList(): List<City> {
        return citiesDataSource.getCities()
            .asSequence()
            .map { it.mapToEntity() }
            .sortedWith(compareBy<City> { it.name }.thenBy { it.countryCode })
            .toList()
    }

    class InvalidBoundariesIndexesException(leftIndex: Int, rightIndex: Int) : Throwable(
        "It seems something is wrong with the algorithm and indexes received an invalid value. " +
                "Left index: $leftIndex - Right index: $rightIndex"
    )

    companion object {
        private const val INVALID_INDEX = -1
    }
}
