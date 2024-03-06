package br.pedroso.citieslist.databaseinitialization

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkInfo.State
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class DatabaseInitializationManager
    @Inject
    constructor(
        private val workManager: WorkManager,
    ) {
        val isInitializingDatabase: Flow<Boolean> =
            workManager
                .getWorkInfosForUniqueWorkFlow(INITIALIZATION_WORK_NAME)
                .map(List<WorkInfo>::anyRunning)
                .conflate()

        internal fun startInitializationWorker(): UUID {
            val request = OneTimeWorkRequestBuilder<DatabaseInitializationWorker>().build()
            workManager.enqueueUniqueWork(INITIALIZATION_WORK_NAME, ExistingWorkPolicy.KEEP, request)
            return request.id
        }

        companion object {
            const val INITIALIZATION_WORK_NAME = "INITIALIZATION_WORK_NAME"
        }
    }

// This function was inspired on
// https://github.com/android/nowinandroid/blob/main/sync/work/src/main/kotlin/com/google/samples/apps/nowinandroid/sync/status/WorkManagerSyncManager.kt
private fun List<WorkInfo>.anyRunning() = any { it.state in listOf(State.RUNNING, State.ENQUEUED) }
