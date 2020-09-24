# Katsu Light

A shiatsu management desktop application.

# Todo

## Iteration 1

* ~~list of treatments~~
* ~~text area for treatment~~
* repository (JSON full data load)
* add shutdown hook to persist data

## Iteration 2

* packaging of app
* DEV/PROD mode (different save location); konfig
* versioning of data (data migrator)

## Iteration 3

* pictures
* sorting of client list
* versions gradle plugin
* store preferences in JSON data (window size/position, recent folders, ...)

## Iteration Z

* auto backup


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