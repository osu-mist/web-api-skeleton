package edu.oregonstate.mist.webapiskeleton

import com.google.common.base.Charsets
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundleConfiguration
import edu.oregonstate.mist.api.BuildInfoManager
import edu.oregonstate.mist.api.Configuration
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.InfoResource
import edu.oregonstate.mist.api.AuthenticatedUser
import edu.oregonstate.mist.api.BasicAuthenticator
import edu.oregonstate.mist.api.PrettyPrintResponseFilter
import edu.oregonstate.mist.api.jsonapi.GenericExceptionMapper
import edu.oregonstate.mist.api.jsonapi.NotFoundExceptionMapper
import io.dropwizard.Application
import io.dropwizard.auth.AuthDynamicFeature
import io.dropwizard.auth.AuthValueFactoryProvider
import io.dropwizard.auth.basic.BasicCredentialAuthFilter
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

/**
 * Main application class.
 */
class SkeletonApplication extends Application<Configuration> {
    /**
     * Initializes application bootstrap.
     *
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new TemplateConfigBundle(
                new TemplateConfigBundleConfiguration().charset(Charsets.US_ASCII)
        ))
    }

    /**
     * Registers lifecycle managers and Jersey exception mappers
     * and container response filters
     *
     * @param environment
     * @param buildInfoManager
     */
    protected void registerAppManagerLogic(Environment environment,
                                           BuildInfoManager buildInfoManager) {

        environment.lifecycle().manage(buildInfoManager)

        environment.jersey().register(new NotFoundExceptionMapper())
        environment.jersey().register(new GenericExceptionMapper())
        environment.jersey().register(new PrettyPrintResponseFilter())
    }

    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(Configuration configuration, Environment environment) {
        Resource.loadProperties()
        BuildInfoManager buildInfoManager = new BuildInfoManager()
        registerAppManagerLogic(environment, buildInfoManager)

        environment.jersey().register(new InfoResource(buildInfoManager.getInfo()))
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<AuthenticatedUser>()
                .setAuthenticator(new BasicAuthenticator(configuration.getCredentialsList()))
                .setRealm('SkeletonApplication')
                .buildAuthFilter()
        ))
        environment.jersey().register(new AuthValueFactoryProvider.Binder
                <AuthenticatedUser>(AuthenticatedUser.class))
    }

    /**
     * Instantiates the application class with command-line arguments.
     *
     * @param arguments
     * @throws Exception
     */
    public static void main(String[] arguments) throws Exception {
        new SkeletonApplication().run(arguments)
    }
}
