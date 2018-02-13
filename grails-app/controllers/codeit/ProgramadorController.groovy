package codeit

class ProgramadorController {

    static allowedMethods = [verProgramadores:['GET'], crearProgramador:['POST']]

    def index() {
        redirect action:"crearProgramador"
    }

    def verProgramadores() {
        Set<Programador> programadores = Programador.findAll()
        render programadores
    }

    def crearProgramador() {
        render(view: "index")
    }

    def guardarNuevoProgramador() {
        Programador nuevoProgramador = new Programador(params)
        if (nuevoProgramador.save()) {
            render nuevoProgramador.toString()
        } else {
            // ya existe otro programador registrado con ese nombre
            render "No es un nombre válido"
        }
    }

}
