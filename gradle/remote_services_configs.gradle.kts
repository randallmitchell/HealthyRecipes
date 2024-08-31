import java.util.Properties

val localPropertiesFile = file("$rootDir/gradle/remote_services_configs.properties")

if (localPropertiesFile.exists()) {
    createFromLocalPropertiesFiles(localPropertiesFile)
} else {
    createFromEnvironmentalVariables()
}

fun createFromEnvironmentalVariables() {
    val NEW_RELIC_API_KEY by rootProject.extra {
        System.getenv("NEW_RELIC_API_KEY")
            ?: throw GradleException("environment variable not found: NEW_RELIC_API_KEY")
    }
}

fun createFromLocalPropertiesFiles(file: File) {
    val properties = Properties()
    file.inputStream().use { properties.load(it) }
    val NEW_RELIC_API_KEY by rootProject.extra { properties.getProperty("NEW_RELIC_API_KEY") }
}
