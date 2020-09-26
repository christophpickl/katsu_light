# Katsu Light

A shiatsu management desktop application.

# Todo

## Iteration 2

* ~~packaging of app (JAR is good enough)~~
* ~~DEV/PROD mode (different save location)~~
* ~~data migrator~~
* pictures

## Iteration 1

* ~~list of treatments~~
* ~~text area for treatment~~
* ~~repository (JSON full data load)~~
* ~~add shutdown hook to persist data~~

## Iteration 3

* treatment counter/days ago
* new property client: birthday, flags (tantsu, donations, like-A, like-C)
* store preferences in JSON data (window size/position, recent folders, ...)

## Iteration X

* searching/filtering/sorting of client list
* auto backup on startup (keep x recent backups)
* versions gradle plugin

## Iteration Y

* more TCM like stuff (elements, colors, meridians, indications from prefilled list)
* rich text editor
* google calendar integration
* gmail integration

## Iteration Z

* doodle integration / custom solution
* human body indicating issues


# Notes

## MacAppBundle howto

```

    id("edu.sc.seis.macAppBundle") version "2.3.0"

macAppBundle {
    mainClassName = Konfig.mainClass
    appName = "Katsu"
    jvmVersion = "1.8+"
//    icon = "src/main/buildMac/logo.icns"
//    javaProperties.put("apple.laf.useScreenMenuBar", "true")
//    javaProperties.put("gadsu.isMacApp", "true")
//    javaExtras.put("-splash:\$APP_ROOT/Contents/Resources/splashscreen.jpg", null)
}

#./gradlew createApp -Dkatsu.enableMacBundle=true
```