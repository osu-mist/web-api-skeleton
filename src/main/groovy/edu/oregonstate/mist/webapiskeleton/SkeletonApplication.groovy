package edu.oregonstate.mist.webapiskeleton

import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

class SkeletonApplication extends Application<Configuration>{

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {}

    @Override
    public void run(Configuration configuration, Environment environment) {}

    public static void main(String[] arguments) throws Exception {
        new SkeletonApplication().run(arguments)
    }
}
