package com.methodsignature.healthyrecipes.service._api.errors

/**
 * While attempting to prepare data for a response, the data was found to be malformed or missing.
 */
class MalformedDataException(message: String) : Exception(message)