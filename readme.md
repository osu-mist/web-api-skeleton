# Web API Skeleton

Skeleton for Dropwizard Web APIs.


## Tasks

List all tasks runnable from root project:

    $ gradle tasks

### IntelliJ IDEA

Generate IntelliJ IDEA project:

    $ gradle idea

Open with `File` -> `Open Project`.

### Generate Keys

HTTPS is required for Web APIs in development and production. Use `keytool(1)` to generate public and private keys.

Generate key pair and keystore:

    $ keytool \
        -genkey \
        -dname "CN=Jane Doe, OU=Enterprise Computing Services, O=Oregon State University, L=Corvallis, S=Oregon, C=US" \
        -alias "doej" \
        -keyalg "RSA" \
        -keysize 2048 \
        -validity 365 \
        -keystore doej.keystore

Create self-signed certificate:

    $ keytool \
        -selfcert \
        -alias "doej" \
        -sigalg "SHA256withRSA" \
        -keystore doej.keystore

Export certificate to file:

    $ keytool \
        -export \
        -alias "doej" \
        -keystore doej.keystore \
        -file doej.cer

Import certificate into truststore:

    $ keytool \
        -import \
        -alias "doej" \
        -file doej.cer \
        -keystore doej.truststore

### Configure

Copy [configuration-example.yaml](configuration-example.yaml) to `configuration.yaml`. Modify as necessary, being careful to avoid committing sensitive data.

### Build

Build the project:

    $ gradle build

JARs [will be saved](https://github.com/johnrengelman/shadow#using-the-default-plugin-task) into the directory `build/libs/`.

### Run

Run the project:

    $ gradle run


## Base an Existing Project off the Skeleton

Add the skeleton as a remote:

    $ git remote add skeleton https://github.com/osu-mist/web-api-skeleton.git
    $ git fetch skeleton

Create a branch to track the skeleton:

    $ git checkout -b skeleton-master skeleton/master

Merge the skeleton into your codebase:

    $ git checkout feature/abc-123-branch
    $ git merge skeleton-master
    ...
    $ git commit -v


## Incorporate Updates from the Skeleton

Ensure that branch `skeleton-master` is tracking remote `skeleton`:

    $ git branch -u skeleton/master skeleton-master

Update local branch:

    $ git fetch skeleton
    $ git pull

Merge the updates into your codebase as before. Note that changes to CodeNarc configuration may introduce build failures.


## Resources

The Web API definition is contained in the [Swagger specification](swagger.yaml).

### GET /

This resource returns build and runtime information:

    $ nc localhost 8888 << HERE
    > GET /api/v0/ HTTP/1.0
    > 
    > HERE
    HTTP/1.1 200 OK
    Date: Thu, 15 Oct 2015 17:42:33 GMT
    Content-Type: application/json
    Content-Length: 113
    
    {"name":"web-api-skeleton","time":1444930929595,"commit":"942c7a2","admin":8081,"documentation":"swagger.yaml"}

### GET /sample

This sample resource returns a short message:

    $ nc localhost 8080 << HERE
    > GET /api/v0/sample HTTP/1.0
    > 
    > HERE
    HTTP/1.1 200 OK
    Date: Wed, 09 Sep 2015 20:47:45 GMT
    Content-Type: text/plain
    Content-Length: 11
    
    hello world

### POST /sample

This sample resource returns the request message:

    $ nc localhost 8080 << HERE
    > POST /api/v0/sample HTTP/1.0
    > Content-Type: text/plain
    > Content-Length: 14
    > 
    > goodbye world
    > 
    > HERE
    HTTP/1.1 200 OK
    Date: Wed, 09 Sep 2015 20:48:46 GMT
    Content-Type: text/plain
    Content-Length: 14
    
    goodbye world
