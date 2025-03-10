package br.com.arml.insights.utils.tools

import br.com.arml.insights.utils.data.Response

suspend fun <T> performDatabaseOperation(
    databaseOperation: suspend () -> T
): Response<T> =
    try {
        Response.Success(databaseOperation())
    }
    catch (e: Exception) {
        Response.Failure(e)
    }
