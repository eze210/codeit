package codeit

import org.joda.time.DateTime

class BootStrap {

    def init = { servletContext ->
        if (Participante.count() == 0) {
            // Programadores
            Programador prog1 = new Programador("Esio Trot")
            prog1.save flush: true
            Programador prog2 = new Programador("Halle Luyah")
            prog2.save flush: true
            Programador prog3 = new Programador("Armando Esteban Quito")
            prog3.save flush: true
            Programador prog4 = new Programador("Susana Horia")
            prog4.save flush: true

            // Equipos
            Equipo eq1 = new Equipo("Los mejores !")
            eq1.agregarMiembro(prog1)
            eq1.agregarMiembro(prog2)
            eq1.save flush: true
            Equipo eq2 = new Equipo("Los segundos mejores !")
            eq2.agregarMiembro(prog3)
            eq2.agregarMiembro(prog4)
            eq2.save flush: true

            // Desafíos
            Desafio des1 = new Desafio("EL desafío", "Sólo tenés que hacer la mejor función del mundo", prog1, DateTime.now().plusDays(5))
            des1.save flush: true
            Ejercicio ej1_1 = new Ejercicio(des1, "Para hacer la mejor función del mundo, tiene que aprobarnos con 10")
            des1.agregarEjercicio(ej1_1)
            ej1_1.save flush: true
            des1.save flush: true
            Desafio des2 = new Desafio("Mi primer desafío", "Sólo tenés que hacer la UI para que le puedan proponer soluciones a esto", prog4)
            des2.save flush: true
            Ejercicio ej2_1 = new Ejercicio(des1, "Primero tenés que hacer que se puedan listar lso desafíos")
            des2.agregarEjercicio(ej2_1)
            ej2_1.save flush: true
            Ejercicio ej2_2 = new Ejercicio(des1, "Después entrar a ver el detalle de un desafío")
            des2.agregarEjercicio(ej2_2)
            ej2_2.save flush: true
            Ejercicio ej2_3 = new Ejercicio(des1, "Finalmentem proponer una solución a él")
            des2.agregarEjercicio(ej2_3)
            ej2_3.save flush: true
            des2.save flush: true
        }
    }
    def destroy = {
    }
}
