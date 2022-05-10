package com.miraka.signupproject.repos

import com.miraka.signupproject.model.SignedUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Store user info in memory.
 * Here is where remote and local user repository could be injected into the constructor for a real service.
 */
class UserRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {

    // simply keep it in memory, no persistence
    var user: SignedUser? = null


    /**
     * There is no need to mark this function as suspended.
     * But it is, so one can insert a delay here to simulate a network call and DB update.
     */
    suspend fun updateUserInfo(signedUser: SignedUser) = withContext(dispatcher) {
        user = signedUser
    }
}