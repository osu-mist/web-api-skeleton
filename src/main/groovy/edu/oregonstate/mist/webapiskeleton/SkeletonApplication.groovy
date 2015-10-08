package edu.oregonstate.mist.webapiskeleton

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.NotFoundMapper
import edu.oregonstate.mist.api.WebApplicationExceptionMapper
import edu.oregonstate.mist.api.ExceptionMapper
import edu.oregonstate.mist.webapiskeleton.resources.SampleResource
import io.dropwizard.Application
import io.dropwizard.Configuration
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
    public void initialize(Bootstrap<Configuration> bootstrap) {}

    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(Configuration configuration, Environment environment) {
        Resource.loadProperties('resource.properties')
        environment.jersey().register(new SampleResource())
        environment.jersey().register(new NotFoundMapper())
        environment.jersey().register(new WebApplicationExceptionMapper())
        environment.jersey().register(new ExceptionMapper())
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
