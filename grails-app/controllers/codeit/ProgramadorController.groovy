package codeit

class ProgramadorController {

    static allowedMethods = [crearProgramador:['GET']]//,
                             //guardarNuevoProgramador:['POST']]

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
            render "No es un nombre válido"
        }
    }

}
