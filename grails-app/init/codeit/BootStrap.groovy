package codeit

import codeit.seguridad.Rol
import org.joda.time.DateTime

class BootStrap {

    def init = { servletContext ->
        if (Participante.count() == 0) {
            new Rol(authority: 'ROLE_USER').save(flush: true)
            assert Rol.count() == 1
            assert Rol.findAll().size() == 1

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
            Ejercicio ej1_1 = prog1.proponerEjercicioPara(des1, "Para hacer la mejor función del mundo, tiene que aprobarnos con 10")
            des1.save flush: true

            Desafio des2 = prog4.proponerDesafio("Mi primer desafío", "Sólo tenés que hacer la UI para que le puedan proponer soluciones a esto")
            Ejercicio ej2_1 = prog4.proponerEjercicioPara(des2, "Primero tenés que hacer que se puedan listar los desafíos")
            Prueba pr2_1_1 = ej2_1.agregarPrueba("", "")
            Prueba pr2_1_2 = ej2_1.agregarPrueba("2", "")
            Ejercicio ej2_2 = prog4.proponerEjercicioPara(des2, "Después entrar a ver el detalle de un desafío")
            Prueba pr2_2_1 = ej2_2.agregarPrueba("", "")
            Ejercicio ej2_3 = prog4.proponerEjercicioPara(des2, "Finalmente proponer una solución a él")

            Solucion sol2_1 = prog1.proponerSolucionPara(des2, "Esto no fue nada fácil. Pero de a poquito pude ir pasando todos los ejercicios. Obviamente el más difícil fue el último.")
            Resolucion sol2_1_1 = new Resolucion(ej2_1, "")
            sol2_1.agregarResolucion(sol2_1_1)
            Resolucion sol2_1_2 = new Resolucion(ej2_2, "return \"\"")
            sol2_1.agregarResolucion(sol2_1_2)
            Resolucion sol2_1_3 = new Resolucion(ej2_3, "x + 2")
            sol2_1.agregarResolucion(sol2_1_3)

            Solucion sol2_2 = prog2.proponerSolucionPara(des2, "Esto fue super fácil, soy súper crack de Groovy y más de Grails y su magia.")
            Resolucion sol2_2_1 = new Resolucion(ej2_1, "\"lala\"")
            sol2_2.agregarResolucion(sol2_2_1)
            Resolucion sol2_2_2 = new Resolucion(ej2_2, "x + y")
            sol2_2.agregarResolucion(sol2_2_2)
            Resolucion sol2_2_3 = new Resolucion(ej2_3, "def scaffold = true")
            sol2_2.agregarResolucion(sol2_2_3)

            des2.save flush: true
            Validador.obtenerInstancia().start()
        }
    }

    def destroy = {
        Validador.obtenerInstancia().destruir()
        Validador.obtenerInstancia().join()
    }
}
