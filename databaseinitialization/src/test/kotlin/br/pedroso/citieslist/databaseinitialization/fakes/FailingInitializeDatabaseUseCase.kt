package br.pedroso.citieslist.databaseinitialization.fakes

import br.pedroso.citieslist.databaseinitialization.InitializeDatabaseUseCase

/**
 * This is a fake implementation of [InitializeDatabaseUseCase] that always fails.
 */
class FailingInitializeDatabaseUseCase : InitializeDatabaseUseCase {
    override suspend fun invoke() = throw Exception(testError)

    companion object {
        val testError = Throwable("This test use case always fails.")
    }
}
