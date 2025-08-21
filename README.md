Network lib

# Installation

### Method 1

Add `maven { url 'https://jitpack.io' }` to `settings.gradle`:
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

and add to `app\build.gradle`:
```groovy
dependencies {
    implementation "com.github.luna-park:antares:1.0.3"
}
```

---

### Method 2

Copy .aar from "Releases"-section to `app\libs` and add to `app\build.gradle`:
```groovy
dependencies {
    implementation fileTree(include: ["*.jar", "*.aar"], dir: 'libs')
    implementation("com.google.code.gson:gson:2.11.0")
}
```
