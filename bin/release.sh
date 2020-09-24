#!/usr/bin/env bash

echo "Releasing Katsu ..."

./gradlew clean shadowJar -Dkatsu.buildProd=true
open build/libs
