package br.pedroso.citieslist.databaseinitialization.fakes

import br.pedroso.citieslist.databaseinitialization.InitializeDatabaseUseCase

/**
 * This is a fake implementation of [InitializeDatabaseUseCase] that run successfully.
 */
class SuccessfulInitializeDatabaseUseCase : InitializeDatabaseUseCase {
    private var executionCount = 0

    val hasBeenExecuted: Boolean
        get() = executionCount > 0

    override suspend fun invoke() {
        executionCount++
    }
}
