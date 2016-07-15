# Web API Skeleton

Skeleton for Dropwizard Web APIs.

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

## Gradle

This project uses the build automation tool Gradle. Use the [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) to download and install it automatically:

    $ ./gradlew

The Gradle wrapper installs Gradle in the directory `~/.gradle`. To add it to your `$PATH`, add the following line to `~/.bashrc`:

    $ export PATH=$PATH:/home/user/.gradle/wrapper/dists/gradle-2.4-all/WRAPPER_GENERATED_HASH/gradle-2.4/bin

The changes will take effect once you restart the terminal or `source ~/.bashrc`.

## Tasks

List all tasks runnable from root project:

    $ gradle tasks

### IntelliJ IDEA

Generate IntelliJ IDEA project:

    $ gradle idea

Open with `File` -> `Open Project`.

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

    $ git remote add skeleton git@github.com:osu-mist/web-api-skeleton.git
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

The following examples demonstrate the use of `curl` to make authenticated HTTPS requests.

### GET /

This resource returns build and runtime information:

    $ curl \
    > --insecure \
    > --user "username:password" \
    > https://localhost:8080/api/v0/
    {"name":"web-api-skeleton","time":1445964601991,"commit":"f08ce22","documentation":"swagger.yaml"}

NOTE: the --insecure option should only be used for local testing.
Production servers should use a real certificate
issued by a valid certificate authority.
