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
    implementation("org.openjfx:javafx-controls:21.0.5")
    // https://mvnrepository.com/artifact/org.openjfx/javafx-base
    implementation("org.openjfx:javafx-base:21.0.5")
    // https://mvnrepository.com/artifact/org.openjfx/javafx-fxml
    implementation ("org.openjfx:javafx-fxml:21.0.5")

    implementation("org.apache.logging.log4j:log4j-core:2.18.0")
    implementation("org.apache.logging.log4j:log4j-api:2.18.0")

    // https://mvnrepository.com/artifact/org.openjfx/javafx-graphics
    implementation ("org.openjfx:javafx-graphics:21.0.5")

    implementation ("org.mindrot:jbcrypt:0.4")

    implementation ("org.openjfx:javafx-controls:21.0.5:mac")
    // https://mvnrepository.com/artifact/org.openjfx/javafx-base
    implementation ("org.openjfx:javafx-base:21.0.5:mac")
    // https://mvnrepository.com/artifact/org.openjfx/javafx-fxml
    implementation ("org.openjfx:javafx-fxml:21.0.5:mac")

    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    implementation ("org.hibernate.validator:hibernate-validator:8.0.0.Final")
    implementation ("org.glassfish:jakarta.el:4.0.2")
    implementation ("org.hibernate.orm:hibernate-community-dialects:6.4.4.Final")
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.10.0.jre11")
}

tasks.test {
    useJUnitPlatform()
}