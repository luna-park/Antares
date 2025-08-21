Network lib

### Installation

settings.gradle:
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

app\build.gradle:
```groovy
dependencies {
    implementation "com.github.luna-park:antares:1.0.3"
}
```
