package com.methodsignature.healthyrecipes.service._api.errors

import java.lang.Exception

/**
 * A requested entity could not be found at the expected location.
 */
class EntityNotFoundException(message: String) : Exception(message)