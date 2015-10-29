package edu.oregonstate.mist.api

/**
 * Object containing build and runtime information about the application.
 */
class Info {
    String name
    Long time
    String commit
    String documentation

    private Properties properties

    public Info() {
        loadProperties()
        name = properties.get('name')
        time = Long.parseLong(properties.getProperty('time'))
        commit = properties.get('commit')
        documentation = properties.get('documentation')
    }

    private Properties loadProperties() {
        properties = new Properties()
        properties.load(new FileReader('build.properties'))
    }
}
