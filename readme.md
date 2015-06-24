# Web API Skeleton

Skeleton for Dropwizard Web APIs.


## Tasks

List all tasks runnable from root project:

    $ gradle tasks

### IntelliJ IDEA

Generate IntelliJ IDEA project:

    $ gradle idea

Open with `File` -> `Open Project`.

### Build

Build the project:

    $ gradle build

JARs [will be saved](https://github.com/johnrengelman/shadow#using-the-default-plugin-task) into the directory `build/libs/`.

### Run

Run the project:

    $ gradle run


## Resources

The Web API definition is contained in the [Swagger specification](swagger.yaml).

Example usage is documented with `curl`. To print a newline after each response, add a line to `.curlrc`:

    $ echo '--write-out "\n"' >> ~/.curlrc

### GET /

This sample resource returns a short message:

    $ curl --include --request GET localhost:8008/
    HTTP/1.1 200 OK
    Date: Thu, 18 Jun 2015 22:11:47 GMT
    Content-Type: text/plain
    Content-Length: 11
    
    hello world
