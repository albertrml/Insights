package br.com.arml.insights.utils.tools

import br.com.arml.insights.utils.data.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*fun <T> performDatabaseOperation(
    databaseOperation: suspend () -> T
): Flow<Response<T>> = flow {
    emit(Response.Loading)
    try {
        emit(Response.Success(databaseOperation()))
    }
    catch (e: Exception) {
        emit(Response.Failure(e))
    }
}*/

suspend fun <T> performDatabaseOperation(
    databaseOperation: suspend () -> T
): Response<T> =
    try {
        Response.Success(databaseOperation())
    }
    catch (e: Exception) {
        Response.Failure(e)
    }
