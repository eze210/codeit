grails.project.dependency.resolution = {
    plugins {
        compile 'org.grails.plugins:clover:4.2.0'
    }
    // For *Grails 2.2* or later you must also add a dependency to the Clover Core
    // or use the "legacyResolve true" option
    dependencies {
        compile 'org.openclover:clover:4.2.0'
    }
}