package br.com.arml.insights.utils.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

sealed class Response <out T>{
    data class Success <out T>(val result: T): Response<T>()
    data class Failure (val exception: Exception): Response<Nothing>()
    data object Loading: Response<Nothing>()
}

inline fun <T, S> Response<T>.mapTo(transform: (T) -> S): Response<S> {
    return when (this) {
        is Response.Success -> Response.Success(transform(this.result))
        is Response.Failure -> Response.Failure(this.exception)
        is Response.Loading -> Response.Loading
    }
}


inline fun <T, S> Response<T>.update(
    uiState: MutableStateFlow<S>,
    updateState: (S, Response<T>) -> S
) {
    uiState.update { state ->
        updateState(state, this)
    }
}

@Composable
fun <T> Response<T>.ShowResults(
    successContent: @Composable (T) -> Unit = {},
    loadingContent: @Composable () -> Unit = {},
    failureContent: @Composable (Exception) -> Unit = {},
    actionOnSuccess: (T) -> Unit = {},
    actionOnFailure: (Exception) -> Unit = {},
    delay: Long = 500
) {
    LaunchedEffect(Unit) {
        delay(delay)
    }
    when (this) {
        is Response.Success -> {
            successContent(this.result)
            actionOnSuccess(this.result)
        }

        is Response.Loading -> {
            loadingContent()
        }

        is Response.Failure -> {
            actionOnFailure(this.exception)
            failureContent(this.exception)
        }
    }
}

/*
fun <T> Response<T>.showResults(
    successViewGroup: ViewGroup,
    loadingViewGroup: ViewGroup,
    failureViewGroup: ViewGroup,
    actionOnSuccess: (T) -> Unit = {},
    actionOnFailure: (Exception) -> Unit = {},
    delay: Long = 500
) {
    when (this) {
        is Response.Success -> {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    successViewGroup.visibility = ViewGroup.VISIBLE
                    loadingViewGroup.visibility = ViewGroup.GONE
                    failureViewGroup.visibility = ViewGroup.GONE
                    actionOnSuccess(this.result)
                },
                delay
            )
        }

        is Response.Loading -> {
            successViewGroup.visibility = ViewGroup.GONE
            loadingViewGroup.visibility = ViewGroup.VISIBLE
            failureViewGroup.visibility = ViewGroup.GONE
            Handler(Looper.getMainLooper()).postDelayed({}, delay)
        }

        is Response.Failure -> {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    successViewGroup.visibility = ViewGroup.GONE
                    loadingViewGroup.visibility = ViewGroup.GONE
                    failureViewGroup.visibility = ViewGroup.VISIBLE
                    actionOnFailure(this.exception)
                }, delay
            )
        }
    }
}*/
