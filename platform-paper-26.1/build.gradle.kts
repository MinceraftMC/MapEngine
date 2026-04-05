import io.papermc.paperweight.userdev.ReobfArtifactConfiguration

plugins {
    id("io.papermc.paperweight.userdev")
}

dependencies {
    api(project(":platform-common"))
    paperweight.paperDevBundle("26.1.1.build.+")
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

// hack to allow depending on this platform in java 17 projects
configurations.runtimeElements.configure {
    attributes.attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 17)
}

paperweight {
    reobfArtifactConfiguration = ReobfArtifactConfiguration.MOJANG_PRODUCTION
}

tasks.reobfJar {
    // hacky workaround to make the shadow plugin shadow our mojang-mapped jar
    outputJar.set(tasks.jar.map { it.outputs.files.singleFile }.get())
    enabled = false
}
