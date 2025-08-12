#! /bin/bash

if [ $# -eq 0 ]; then
    app/build/install/app/bin/app
fi

if [ "$1" = "run" ]; then
    ./gradlew build
    ./gradlew installDist
    app/build/install/app/bin/app
fi

if [ "$1" = "build" ]; then
    ./gradlew build
fi
