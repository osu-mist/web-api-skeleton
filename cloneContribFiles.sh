if [ "$2" = "yes" ]
    then
        rm -rf src/main/groovy/edu/oregonstate/mist/contrib/
        git clone -q "$1" src/main/groovy/edu/oregonstate/mist/contrib
        WORKDIR=`pwd`
        cd src/main/groovy/edu/oregonstate/mist/contrib
        git reset --hard "$3"
        cd "$WORKDIR"
        rm -rf src/main/groovy/edu/oregonstate/mist/contrib/.git/
        rm src/main/groovy/edu/oregonstate/mist/contrib/.gitignore
fi