package edu.oregonstate.mist.api

import io.dropwizard.auth.Authenticator
import io.dropwizard.auth.AuthenticationException
import io.dropwizard.auth.basic.BasicCredentials

/**
 * Class which authenticates user-provided credentials.
 */
class BasicAuthenticator implements Authenticator<BasicCredentials, AuthenticatedUser> {
    private List<Credentials> credentialsList

    public BasicAuthenticator(List<Credentials> credentialsList) {
        this.credentialsList = credentialsList
    }

    @Override
    public Optional<AuthenticatedUser> authenticate(BasicCredentials basicCredentials)
            throws AuthenticationException {
        if (valid(basicCredentials)) {
            return Optional.of(new AuthenticatedUser(basicCredentials.username))
        } else {
            return Optional.empty()
        }
    }

    private Boolean valid(BasicCredentials basicCredentials) {
        Iterator<Credentials> iterator = credentialsList.iterator()
        Credentials credentials
        while (iterator.hasNext()) {
            credentials = iterator.next()
            if ((basicCredentials.username == credentials.username)
                    && (basicCredentials.password == credentials.password)) {
                return true
            }
        }
        false
    }
}
