#!/usr/bin/env bash

VERSION_PROPERTIES_FILE=version.properties

version="N/A"
source ${VERSION_PROPERTIES_FILE}
CURRENT_MAJOR_VERSION=$(echo ${version}| cut -d "." -f 1)
CURRENT_MINOR_VERSION=$(echo ${version}| cut -d "." -f 2)
NEXT_MINOR_VERSION=$((CURRENT_MINOR_VERSION + 1))
nextVersion="${CURRENT_MAJOR_VERSION}.${NEXT_MINOR_VERSION}"

JAR_NAME="Katsu-${nextVersion}.jar"

checkLastCommand() {
    if [ $? -ne 0 ] ; then
        myEcho "Last command did not end successful!"
        exit 1
    fi
}


echo "Releasing Katsu ..."
echo "Next version: ${nextVersion}"
echo
while true; do
    read -p "Do you confirm this release? [y/n] >> " yn
    case ${yn} in
        [Yy]* ) break;;
        [Nn]* ) echo "Aborted"; exit;;
        * ) echo "Please answer y(es) or n(o)";;
    esac
done
echo

#./gradlew clean check test
#checkLastCommand

echo "Increasing version number ..."
echo "version=${nextVersion}" > ${VERSION_PROPERTIES_FILE}
checkLastCommand

./gradlew shadowJar -Dkatsu.buildProd=true
checkLastCommand

cp build/libs/"${JAR_NAME}" /Applications
rm /Applications/Katsu.jar
ln /Applications/"${JAR_NAME}" /Applications/Katsu.jar
