package codeit

class AuthenticationTagLib {

    def loggedInUsername = {
        out << currentLoggedInUsername()
    }

    def loggedInProgramador = { attrs, body ->
        out << body(programador: Programador.findByNombre(currentLoggedInUsername()))
    }

    String currentLoggedInUsername() {
        def token = session.SPRING_SECURITY_CONTEXT?.getAuthentication()
        return token?.getPrincipal()?.getUsername()
    }

}
