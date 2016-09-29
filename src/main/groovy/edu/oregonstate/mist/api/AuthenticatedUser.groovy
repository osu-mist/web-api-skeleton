package edu.oregonstate.mist.api

import java.security.Principal

/**
 * Authenticated user representation class.
 */
class AuthenticatedUser implements Principal {
    String username

    @Override
    String getName() {
        username
    }

    AuthenticatedUser(String username) {
        this.username = username
    }
}
