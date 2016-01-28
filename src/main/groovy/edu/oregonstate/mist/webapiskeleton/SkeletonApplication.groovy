package edu.oregonstate.mist.webapiskeleton

import edu.oregonstate.mist.api.Configuration
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.InfoResource
import edu.oregonstate.mist.api.AuthenticatedUser
import edu.oregonstate.mist.api.BasicAuthenticator
import edu.oregonstate.mist.webapiskeleton.resources.SampleResource
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.dropwizard.auth.AuthFactory
import io.dropwizard.auth.basic.BasicAuthFactory
import io.katharsis.locator.SampleJsonServiceLocator

import io.katharsis.locator.SampleJsonServiceLocator
import io.katharsis.rs.KatharsisFeature
import io.katharsis.rs.KatharsisProperties

/**
 * Main application class.
 */
class SkeletonApplication extends Application<SkeletonApplicationConfiguration> {
    /**
     * Initializes application bootstrap.
     *
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<SkeletonApplicationConfiguration> bootstrap) {}

    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(SkeletonApplicationConfiguration configuration, Environment environment) {
        environment.jersey().property(KatharsisProperties.RESOURCE_DEFAULT_DOMAIN, configuration.katharsis.host)
        environment.jersey().property(KatharsisProperties.RESOURCE_SEARCH_PACKAGE, configuration.katharsis.searchPackage)
        KatharsisFeature katharsisFeature = new KatharsisFeature(environment.getObjectMapper(), new
                SampleJsonServiceLocator())
        environment.jersey().register(katharsisFeature)

        Resource.loadProperties('resource.properties')
//        environment.jersey().register(new SampleResource())
        environment.jersey().register(new InfoResource())
        /*environment.jersey().register(
                AuthFactory.binder(
                        new BasicAuthFactory<AuthenticatedUser>(
                                new BasicAuthenticator(configuration.getCredentialsList()),
                                'SkeletonApplication',
                                AuthenticatedUser.class)))*/
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
