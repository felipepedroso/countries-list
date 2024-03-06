package br.pedroso.citieslist.databaseinitialization

import androidx.work.ListenableWorker.Result
import br.pedroso.citieslist.databaseinitialization.DatabaseInitializationWorker.Companion.ERROR_MESSAGE_KEY
import br.pedroso.citieslist.databaseinitialization.fakes.FailingInitializeDatabaseUseCase
import br.pedroso.citieslist.databaseinitialization.fakes.SuccessfulInitializeDatabaseUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DatabaseInitializationWorkerTest {
    @Test
    fun `given use case finishes successfully when worker runs then work result must be success`() =
        runTest {
            val useCase = SuccessfulInitializeDatabaseUseCase()

            val worker = createDatabaseInitializationWorker(useCase)

            val result = worker.doWork()

            assertThat(result).isEqualTo(Result.success())

            assertThat(useCase.hasBeenExecuted).isTrue()
        }

    @Test
    fun `given use case fails when worker runs then work result must be failure`() =
        runTest {
            val worker = createDatabaseInitializationWorker(FailingInitializeDatabaseUseCase())

            val result = worker.doWork() as Result.Failure

            val errorMessage = result.outputData.getString(ERROR_MESSAGE_KEY)

            assertThat(errorMessage).contains(FailingInitializeDatabaseUseCase.testError.message)
        }
}
