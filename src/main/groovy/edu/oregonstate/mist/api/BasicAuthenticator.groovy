package edu.oregonstate.mist.api

import io.dropwizard.auth.Authenticator
import io.dropwizard.auth.AuthenticationException
import io.dropwizard.auth.basic.BasicCredentials
import com.google.common.base.Optional

/**
 * Class which authenticates user-provided credentials.
 */
class BasicAuthenticator implements Authenticator<BasicCredentials, AuthenticatedUser> {
    public Optional<AuthenticatedUser> authenticate(BasicCredentials basicCredentials)
            throws AuthenticationException {
        if (valid(basicCredentials)) {
            return Optional.of(new AuthenticatedUser(basicCredentials.username))
        } else {
            return Optional.absent()
        }
    }

    // TODO: read credentials from configuration file
    private Boolean valid(BasicCredentials basicCredentials) {
        ((basicCredentials.username == 'username')
                && (basicCredentials.password == 'password'))
    }
}
