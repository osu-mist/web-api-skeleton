package edu.oregonstate.mist.api

import io.dropwizard.lifecycle.Managed

class BuildInfoManager implements Managed {
    private Info info = new Info()

    Info getInfo() {
        info
    }

    /**
     * Loads builds.properties.
     * DW App will halt during startup if FileNotFoundException is thrown.
     *
     * @throws FileNotFoundException
     */
    @Override
    public void start() throws FileNotFoundException {
        def stream = InfoResource.class.getResourceAsStream('/build.properties')
        if (stream == null) {
            throw new FileNotFoundException("couldn't load build.properties")
        }

        def properties = new Properties()
        properties.load(stream)

        info.name = properties.get('name')
        info.time = properties.get('time')
        info.unixTime = Long.parseLong(properties.getProperty('unixTime'))
        info.commit = properties.get('commit')
        info.documentation = properties.get('documentation')
    }

    @Override
    public void stop() throws Exception {
    }
}
