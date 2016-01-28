package edu.oregonstate.mist.webapiskeleton

import edu.oregonstate.mist.api.Configuration
import javax.validation.Valid
import javax.validation.constraints.NotNull

public class SkeletonApplicationConfiguration extends Configuration {
    @Valid
    @NotNull
    public KatharsisConfiguration katharsis = new KatharsisConfiguration()

    public class KatharsisConfiguration {
        @Valid
        @NotNull
        public String host

        @Valid
        @NotNull
        public String searchPackage
    }
}
