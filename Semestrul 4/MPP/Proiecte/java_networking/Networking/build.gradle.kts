plugins {
    id("java")
}

group = "map"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":Model"))
    implementation(project(":Services"))
    implementation("org.openjfx:javafx-controls:21.0.5")
    // https://mvnrepository.com/artifact/org.openjfx/javafx-base
    implementation("org.openjfx:javafx-base:21.0.5")
    // https://mvnrepository.com/artifact/org.openjfx/javafx-fxml
    implementation ("org.openjfx:javafx-fxml:21.0.5")

    // https://mvnrepository.com/artifact/org.openjfx/javafx-graphics
    implementation ("org.openjfx:javafx-graphics:21.0.5")

    implementation ("org.mindrot:jbcrypt:0.4")

    implementation ("org.openjfx:javafx-controls:21.0.5:mac")
    // https://mvnrepository.com/artifact/org.openjfx/javafx-base
    implementation ("org.openjfx:javafx-base:21.0.5:mac")
    // https://mvnrepository.com/artifact/org.openjfx/javafx-fxml
    implementation ("org.openjfx:javafx-fxml:21.0.5:mac")
}

tasks.test {
    useJUnitPlatform()
}