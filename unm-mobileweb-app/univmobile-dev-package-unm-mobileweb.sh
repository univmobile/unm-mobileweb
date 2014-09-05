#!/bin/sh

URL=http://univmobile.vswip.com/nexus/content/repositories/releases/fr/univmobile/unm-mobileweb-app/0.0.4/unm-mobileweb-app-0.0.4.war

curl -o target/unm-mobileweb.war "${URL}"

JSON_URL=https://univmobile-dev.univ-paris1.fr/json

if [ -z "${JSON_URL}" ]; then
	echo "*** ERROR: ${JSON_URL} is empty."
	echo "Exiting."
	exit 1
fi

BASE_URL=http://univmobile-dev.univ-paris1.fr/unm-mobileweb-app-0.0.4/

ant -f ../unm-mobileweb-app/build.xml -DwarFile=$(pwd)/target/unm-mobileweb.war \
	-DjsonURL="${JSON_URL}" -DbaseURL="${BASE_URL}" || exit 1

echo "Done."