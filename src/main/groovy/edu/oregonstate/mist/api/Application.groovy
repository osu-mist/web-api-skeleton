package edu.oregonstate.mist.api

import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle
import edu.oregonstate.mist.api.jsonapi.GenericExceptionMapper
import edu.oregonstate.mist.api.jsonapi.NotFoundExceptionMapper
import io.dropwizard.auth.AuthDynamicFeature
import io.dropwizard.auth.AuthValueFactoryProvider
import io.dropwizard.auth.basic.BasicCredentialAuthFilter
import io.dropwizard.jersey.errors.LoggingExceptionMapper
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import javax.ws.rs.WebApplicationException

/**
 * Main application base class.
 */
class Application<T extends Configuration> extends io.dropwizard.Application<T> {
    /**
     * Initializes application bootstrap.
     *
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<T> bootstrap) {
        bootstrap.addBundle(new TemplateConfigBundle())
    }

    /**
     * Performs common application setup logic.
     *
     * Currently this includes loading Error properties,
     * registering InfoResource, starting the build info lifecycle manager,
     * installing Jersey exception mappers, installing the pretty print filter,
     * and registering an authentication handler.
     *
     * Applications should call this method at the beginning of run()
     *
     * @param configuration
     * @param environment
     */
    protected void setup(T configuration, Environment environment) {
        BuildInfoManager buildInfoManager = new BuildInfoManager()
        environment.lifecycle().manage(buildInfoManager)

        environment.jersey().register(new NotFoundExceptionMapper())
        environment.jersey().register(new GenericExceptionMapper())
        environment.jersey().register(new LoggingExceptionMapper<WebApplicationException>(){})
        environment.jersey().register(new PrettyPrintResponseFilter())
        environment.jersey().register(new InfoResource(buildInfoManager.getInfo()))

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<AuthenticatedUser>()
                .setAuthenticator(new BasicAuthenticator(configuration.getCredentialsList()))
                .setRealm(this.class.simpleName)
                .buildAuthFilter()
        ))

        environment.jersey().register(new AuthValueFactoryProvider.Binder
                <AuthenticatedUser>(AuthenticatedUser.class))
    }

    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(T configuration, Environment environment) {
        this.setup(configuration, environment)
    }
}
