package codeit

class AuthenticationTagLib {

    def loggedInUsername = {
        out << currentLoggedInUsername()
    }

    def loggedInProgramador = { attrs, body ->
        def var = "programador"
        out << body((var):Programador.findByNombre(currentLoggedInUsername()))
    }

    String currentLoggedInUsername() {
        def token = session.SPRING_SECURITY_CONTEXT?.getAuthentication()
        return token?.getPrincipal()?.getUsername()
    }

}
