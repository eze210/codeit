package codeit

class ProgramadorController {

    static allowedMethods = [crearProgramador:['GET'],
                             guardarNuevoProgramador:['POST']]

    def index() {
        redirect action:"crearProgramador"
    }

    def crearProgramador() {
        render(view: "index")
    }

    def guardarNuevoProgramador() {
        render "Bye"

//        Programador nuevoProgramador = Programador(nombre:params["nombre"])
//        nuevoProgramador.save()
//        render nuevoProgramador.toString()
    }

}
