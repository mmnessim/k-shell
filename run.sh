#! /bin/bash

if [ $# -eq 0 ]; then
    app/build/install/app/bin/app
fi

if [ "$1" = "run" ]; then
    ./gradlew build || exit 1
    ./gradlew installDist || exit 1

    app/build/install/app/bin/app || exit 1
fi

if [ "$1" = "build" ]; then
    ./gradlew build
fi
