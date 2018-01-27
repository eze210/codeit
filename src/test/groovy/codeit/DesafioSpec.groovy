package codeit

import grails.testing.gorm.DomainUnitTest
import org.joda.time.DateTime
import spock.lang.Specification

class DesafioSpec extends Specification implements DomainUnitTest<Desafio> {

    def setup() {
    }

    def cleanup() {
    }

    void "Creación del desafío"() {
        when:"Se crea un desafío con ciertos parámetros"
        Programador programador = new Programador("El nombre")
        Desafio desafio = new Desafio(
                "El título",
                "La descripción",
                programador,
                DateTime.now(),
                DateTime.now())

        then:"el desafío tiene esos parámetros y es válido"
        desafio.creador == programador
        desafio.titulo == "El título"
        desafio.descripcion == "La descripción"

        desafio.ejercicios.size() == 0
        desafio.resultados.size() == 0
        desafio.vigencia.rangoDeFechas.to >= desafio.vigencia.rangoDeFechas.from
    }

    void "Agregar un ejercicio"() {
        given:"Un desafío"
        Programador programador = new Programador("El nombre")
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                DateTime.now(),
                DateTime.now())

        when:"se agrega un ejercicio a ese desafío"
        Ejercicio ejercicio = new Ejercicio(desafioNuevo, "El enunciado")
        desafioNuevo.agregarEjercicio(ejercicio)

        then:"El desafío tiene ese ejercicio"
        desafioNuevo.ejercicios.size() == 1
        desafioNuevo.ejercicios.contains(ejercicio)
    }

    void "Agregar ejercicios"() {
        given:"Un desafío nuevo"
        Programador programador = new Programador("El nombre")
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                DateTime.now(),
                DateTime.now())

        when:"Se agregan N ejercicios ese desafío"
        Integer numeroDeEjercicios = 10
        for (Integer i = 0; i < numeroDeEjercicios; ++i) {
            Ejercicio ejercicio = new Ejercicio(desafioNuevo, "El enunciado " + i)
            desafioNuevo.agregarEjercicio(ejercicio)
        }

        then:"El desafío tiene esa cantidad de ejercicios"
        desafioNuevo.ejercicios.size() == numeroDeEjercicios
    }

    void "Desafío vigente"() {
        when:"Se crea un desafío valido desde ahora y por un día"
        Programador programador = new Programador("El nombre")

        DateTime ahora = DateTime.now()
        DateTime unDiaDespues = ahora.plusDays(1)
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                ahora,
                unDiaDespues)

        then:"El desafío sigue vigente después de pasar el tiempo de ejecución de la prueba"
        desafioNuevo.estaVigente()
    }

    void "Desafío no vigente"() {
        when:"Se crea un desafío valido desde ayer y por un día"
        Programador programador = new Programador("El nombre")

        DateTime ahora = DateTime.now()
        DateTime ayer = ahora.minusDays(1).minusMillis(1)
        DateTime haceUnMilisegundo = ahora.minusMillis(1)
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                ayer,
                haceUnMilisegundo)

        then:"El desafío no está vigente después de pasar el tiempo de ejecución de la prueba"
        !desafioNuevo.estaVigente()
    }

    void "Solución nueva para desafío nuevo"() {
        when:"Un desafío es nuevo y una solución es nueva"
        Programador programador = new Programador("El nombre")

        DateTime ahora = DateTime.now()
        DateTime unDiaDespues = ahora.plusDays(1)
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                ahora,
                unDiaDespues)

        Programador resolvedor = new Programador("Otro nombre")
        Solucion solucion = new Solucion(resolvedor, "Descripción", desafioNuevo)

        then:"la solución resuelve el desafío"
        desafioNuevo.proponerSolucion(solucion)
    }

    void "Solución se invalida cuando se agrega un nuevo ejercicio"() {
        given:"Un desafío nuevo"
        Programador programador = new Programador("El nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                ahora,
                maniana)

        and:"que era resuelto por una solución"
        Programador resolvedor = new Programador("Otro nombre")
        Solucion solucion = new Solucion(resolvedor, "Descripción", desafioNuevo)
        assert desafioNuevo.proponerSolucion(solucion)

        when:"se agrega un nuevo ejercicio al desafío"
        Ejercicio ejercicio = new Ejercicio(desafioNuevo, "Ejercicio nuevo")
        assert desafioNuevo.agregarEjercicio(ejercicio)

        then:"la solución ya no resuelve el desafío"
        !desafioNuevo.validarSolucion(solucion)
    }

    void "Solución vuelve a ser válida cuando se agrega una resolución para el ejercicio nuevo"() {
        given:"Un desafío"
        Programador programador = new Programador("El nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio desafio = new Desafio(
                "El título",
                "La descripción",
                programador,
                ahora,
                maniana)

        and:"que era resuelto por una solución"
        Programador resolvedor = new Programador("Otro nombre")
        Solucion solucion = new Solucion(resolvedor, "Descripción", desafio)
        assert desafio.proponerSolucion(solucion)

        when:"se agrega un ejercicio"
        Ejercicio ejercicio = new Ejercicio(desafio, "Ejercicio nuevo")
        assert desafio.agregarEjercicio(ejercicio)

        then:"la solución ya no resuelve el desafío"
        !desafio.validarSolucion(solucion)

        when:"se agrega una resolución para el nuevo ejercicio"
        solucion.agregarResolucion(new Resolucion(ejercicio, "{x -> x}"))

        then:"la solución es válida de nuevo"
        desafio.validarSolucion(solucion)
    }

}
