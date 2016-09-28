if [ "$2" = "yes" ]
    then
        rm -rf src/main/groovy/edu/oregonstate/mist/contrib/
        git clone -q "$1" src/main/groovy/edu/oregonstate/mist/contrib
        cd src/main/groovy/edu/oregonstate/mist/contrib
        git reset --hard "$3"
        rm -rf .git/
        rm .gitignore
fi