package com.radityalabs.ui.widgets

import androidx.annotation.CheckResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

@CheckResult
@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> {
    var job: Job = Job().apply { complete() }

    return onCompletion {
        job.cancel()
    }.run {
        flow {
            coroutineScope {
                collect { value ->
                    if (!job.isActive) {
                        emit(value)
                        job = launch { delay(windowDuration) }
                    }
                }
            }
        }
    }
}
