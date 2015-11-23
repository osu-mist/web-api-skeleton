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

The following examples demonstrate the use of `openssl s_client` and `curl` to make authenticated HTTPS requests.

### GET /

This resource returns build and runtime information:

    $ echo -n "username:password" | base64
    dXNlcm5hbWU6cGFzc3dvcmQ=
    $ openssl s_client -connect localhost:8080 -CAfile doej.cer 
    CONNECTED(00000004)
    depth=0 C = US, ST = Oregon, L = Corvallis, O = Oregon State University, OU = Enterprise Computing Services, CN = Jane Doe
    verify error:num=18:self signed certificate
    verify return:1
    depth=0 C = US, ST = Oregon, L = Corvallis, O = Oregon State University, OU = Enterprise Computing Services, CN = Jane Doe
    verify return:1
    ---
    Certificate chain
     0 s:/C=US/ST=Oregon/L=Corvallis/O=Oregon State University/OU=Enterprise Computing Services/CN=Jane Doe
       i:/C=US/ST=Oregon/L=Corvallis/O=Oregon State University/OU=Enterprise Computing Services/CN=Jane Doe
    ---
    Server certificate
    -----BEGIN CERTIFICATE-----
    MIIDvzCCAqegAwIBAgIEHOuDIzANBgkqhkiG9w0BAQsFADCBjzELMAkGA1UEBhMC
    VVMxDzANBgNVBAgTBk9yZWdvbjESMBAGA1UEBxMJQ29ydmFsbGlzMSAwHgYDVQQK
    ExdPcmVnb24gU3RhdGUgVW5pdmVyc2l0eTEmMCQGA1UECxMdRW50ZXJwcmlzZSBD
    b21wdXRpbmcgU2VydmljZXMxETAPBgNVBAMTCEphbmUgRG9lMB4XDTE1MTAyNzE5
    MDMxOFoXDTE2MDEyNTE5MDMxOFowgY8xCzAJBgNVBAYTAlVTMQ8wDQYDVQQIEwZP
    cmVnb24xEjAQBgNVBAcTCUNvcnZhbGxpczEgMB4GA1UEChMXT3JlZ29uIFN0YXRl
    IFVuaXZlcnNpdHkxJjAkBgNVBAsTHUVudGVycHJpc2UgQ29tcHV0aW5nIFNlcnZp
    Y2VzMREwDwYDVQQDEwhKYW5lIERvZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCC
    AQoCggEBAKJUuSjl/TjFYtkh0/5cb+TBMvhndKl+lIe75KB81PoXQpkePz2456Rv
    4KXyM94Dyg+i+eccBC2RNXVTrq0bXqQ/utWRBpEn6IRLFobA29lH9ZoDhhHe52kK
    tP1mTFCCi/3GCGTZXuj65DCyLI5gyT2Cyjjf8rpXdSDXhHATRdpdw474JcngMbIo
    8JtgsHp6b5X87FfZGrKAOwQLp+ifzBU5sVK+mhi1pwlyzBkPx9Ma/ctrVR4NEJGX
    z/NJegEE4o3FVVHJnOiFZWfYdYUqQi8WtNaG6oRdwsBS3nfdD/EIum0j5EMOFxji
    jaCNYIzkZFGhlSqPrcHQ8pPcnVoi53MCAwEAAaMhMB8wHQYDVR0OBBYEFKVL9W4P
    /fUH1JfWkMu1Ty+PZF5gMA0GCSqGSIb3DQEBCwUAA4IBAQBetD1CpwAThmSxTkX+
    sowZ/vvhKGiYI+3PIKCasXYw37Kdg15xfN1LJQVpgKhlvT5U6i03dSg0ZXUhpwLb
    LWsI6Heq5y549+4HJyhqGTyec5HCxFgLAvGh4Tc5bD/zrEDi366YPrxj7nzapfge
    S3xhvF2V0VuS0LVZ0cwENKzVuz1FSNyZG6VEQ1slGuUYJ+laRZ5CPBo5d5KfdIKG
    8gVTetwacPP8fNNt2IOg4DledSzFn2ahLxXtyzXvu2gjFukfVC0bR82KuYZnJQIu
    ezfIeCrkHo3HUX7KfDbFGnjtOXN1B175cAY3zZb3IKUQMQoy+MPJBoC8YU25LL6n
    K4vv
    -----END CERTIFICATE-----
    subject=/C=US/ST=Oregon/L=Corvallis/O=Oregon State University/OU=Enterprise Computing Services/CN=Jane Doe
    issuer=/C=US/ST=Oregon/L=Corvallis/O=Oregon State University/OU=Enterprise Computing Services/CN=Jane Doe
    ---
    No client certificate CA names sent
    ---
    SSL handshake has read 1567 bytes and written 544 bytes
    ---
    New, TLSv1/SSLv3, Cipher is ECDHE-RSA-AES128-SHA256
    Server public key is 2048 bit
    Secure Renegotiation IS supported
    Compression: NONE
    Expansion: NONE
    SSL-Session:
        Protocol  : TLSv1.2
        Cipher    : ECDHE-RSA-AES128-SHA256
        Session-ID: 562FCC0D8492CDA3515903B2B0D25D20D7EA9BBF7F8C21F84FBFA7EC294B98A3
        Session-ID-ctx: 
        Master-Key: 264ABBA6CCE9F6516E0709201011219E8BBD08F90B08CF732E26951A91C0BA148B844FCAFA112304269C4781D2851462
        Key-Arg   : None
        PSK identity: None
        PSK identity hint: None
        SRP username: None
        Start Time: 1445973005
        Timeout   : 300 (sec)
        Verify return code: 18 (self signed certificate)
    ---
    GET /api/v0 HTTP/1.0
    Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=
    
    HTTP/1.1 200 OK
    Date: Tue, 27 Oct 2015 19:10:23 GMT
    Content-Type: application/json
    Content-Length: 111
    
    {"name":"web-api-skeleton","time":1445964601991,"commit":"f08ce22","documentation":"swagger.yaml"}closed

### GET /sample

This sample resource returns a short message:

    $ curl \
    > --insecure \
    > --key doej.cer \
    > --user "username:password" \
    > https://localhost:8080/api/v0/sample
    hello world

### POST /sample

This sample resource returns the request message:

    $ curl \
    > --insecure \
    > --key doej.cer \
    > --user "username:password" \
    > --header "Content-Type: text/plain" \
    > --data "goodbye world" \
    > https://localhost:8080/api/v0/sample
    goodbye world
