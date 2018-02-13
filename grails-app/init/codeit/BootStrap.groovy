package codeit

class BootStrap {

    def init = { servletContext ->
        if (Participante.count() == 0) {
            Programador prog1 = new Programador("Esio Trot")
            prog1.save flush: true
            Programador prog2 = new Programador("Halle Luyah")
            prog2.save flush: true
            Equipo eq1 = new Equipo("Los mejores !")
            eq1.agregarMiembro(prog1)
            eq1.agregarMiembro(prog2)
            eq1.save flush: true
            Programador prog3 = new Programador("Armando Esteban Quito")
            prog3.save flush: true
            Programador prog4 = new Programador("Susana Horia")
            prog4.save flush: true
            Equipo eq2 = new Equipo("Los segundos mejores !")
            eq2.agregarMiembro(prog3)
            eq2.agregarMiembro(prog4)
            eq2.save flush: true
        }
    }
    def destroy = {
    }
}
