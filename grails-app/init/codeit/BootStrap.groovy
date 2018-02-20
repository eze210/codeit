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
            Programador prog5 = new Programador("Oompa Loompa")
            prog5.save flush: true

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
            Desafio des1 = prog1.proponerDesafio("EL desafío", "Sólo tenés que hacer la mejor función del mundo", DateTime.now().plusDays(5))
            des1.save flush: true
            Ejercicio ej1_1 = prog1.proponerEjercicioPara(des1, "Para hacer la mejor función del mundo, tiene que aprobarnos con 10")
            ej1_1.save flush: true
            des1.save flush: true

            Desafio des2 = prog4.proponerDesafio("Mi primer desafío", "Sólo tenés que hacer la UI para que le puedan proponer soluciones a esto")
            des2.save flush: true
            Ejercicio ej2_1 = prog4.proponerEjercicioPara(des2, "Primero tenés que hacer que se puedan listar los desafíos")
            ej2_1.save flush: true
            Prueba pr2_1_1 = ej2_1.agregarPrueba("", "")
            pr2_1_1.save flush: true
            Prueba pr2_1_2 = ej2_1.agregarPrueba("2", "2")
            pr2_1_2.save flush: true
            ej2_1.save flush: true
            Ejercicio ej2_2 = prog4.proponerEjercicioPara(des2, "Después entrar a ver el detalle de un desafío")
            ej2_2.save flush: true
            Prueba pr2_2_1 = ej2_2.agregarPrueba("", "")
            pr2_2_1.save flush: true
            ej2_2.save flush: true
            Ejercicio ej2_3 = prog4.proponerEjercicioPara(des2, "Finalmente proponer una solución a él")
            ej2_3.save flush: true
            des2.save flush: true
        }
    }
    def destroy = {
    }
}
