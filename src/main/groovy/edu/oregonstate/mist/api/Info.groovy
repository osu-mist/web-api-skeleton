package edu.oregonstate.mist.api

import org.yaml.snakeyaml.Yaml

/**
 * Object containing build and runtime information about the application.
 */
class Info {
    String name
    Long time
    String commit
    Integer admin
    String documentation

    private Properties properties
    private Map<String,Object> configuration

    public Info() {
        loadProperties()
        name = properties.get('name')
        time = Long.parseLong(properties.getProperty('time'))
        commit = properties.get('commit')
        documentation = properties.get('documentation')
        admin = getAdminPort()
    }

    private Properties loadProperties() {
        properties = new Properties()
        properties.load(new FileReader('build.properties'))
    }

    private Map<String,Object> loadConfiguration() {
        Yaml yaml = new Yaml()
        configuration = (Map<String,Object>)yaml.load(new FileInputStream('configuration.yaml'))
    }

    private Integer getAdminPort() {
        loadConfiguration()
        Map<String,Object> server = (Map<String,Object>)configuration.get('server')
        ArrayList adminConnectors = (ArrayList)server.get('adminConnectors')
        LinkedHashMap<String,Object> list = (LinkedHashMap<String,Object>)adminConnectors.get(0)
        list.get('port')
    }
}
