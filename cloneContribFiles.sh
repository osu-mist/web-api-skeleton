if [ "$2" = "yes" ]
    then
        rm -rf src/main/groovy/edu/oregonstate/mist/contrib/
        git clone --depth=1 -q $1 src/main/groovy/edu/oregonstate/mist/contrib
        rm -rf src/main/groovy/edu/oregonstate/mist/contrib/.git/
        rm src/main/groovy/edu/oregonstate/mist/contrib/.gitignore
fi