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
                DateTime.now().plusDays(1))

        then:"el desafío tiene esos parámetros y es válido"
        desafio.creador == programador
        desafio.titulo == "El título"
        desafio.descripcion == "La descripción"

        desafio.ejercicios.size() == 0
        desafio.resultados.size() == 0
        desafio.vigencia.rangoDeFechas.upperEndpoint() >= desafio.vigencia.rangoDeFechas.lowerEndpoint()
    }

    void "Agregar un ejercicio"() {
        given:"Un desafío"
        Programador programador = new Programador("El nombre")
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                DateTime.now(),
                DateTime.now().plusDays(1))

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
                DateTime.now().plusDays(1))

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
        given:"Un desafío con un ejercicio"
        Programador programador = new Programador("El nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                ahora,
                maniana)
        Ejercicio ejercicio1 = new Ejercicio(desafioNuevo, "Ejercicio viejo")
        desafioNuevo.agregarEjercicio(ejercicio1)

        and:"que tenía una solución válida"
        Programador resolvedor = new Programador("Otro nombre")
        Solucion solucion = new Solucion(resolvedor, "Descripción", desafioNuevo)
        Resolucion resolucion = new Resolucion(ejercicio1, "")
        solucion.agregarResolucion(resolucion)
        desafioNuevo.proponerSolucion(solucion)
        assert desafioNuevo.validarSolucion(solucion).valido

        when:"se agrega un nuevo ejercicio al desafío"
        Ejercicio ejercicio2 = new Ejercicio(desafioNuevo, "Ejercicio nuevo")
        desafioNuevo.agregarEjercicio(ejercicio2)

        then:"la solución debe reprocesarse"
        and:"la solución ya no resuelve el desafío"
        assert desafioNuevo.resultados.count() == 1
        assert desafioNuevo.obtenerResultadoActualDeSolucion(solucion) != null
        !desafioNuevo.obtenerResultadoActualDeSolucion(solucion).estaProcesado() &&
                !desafioNuevo.validarSolucion(solucion).valido
    }

    void "Solución vuelve a ser válida cuando se agrega una resolución para el ejercicio nuevo"() {
        given:"Un desafío con un ejercicio"
        Programador programador = new Programador("El nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio desafio = new Desafio(
                "El título",
                "La descripción",
                programador,
                ahora,
                maniana)
        Ejercicio ejercicio1 = new Ejercicio(desafio, "Ejercicio viejo")
        desafio.agregarEjercicio(ejercicio1)

        and:"que era resuelto por una solución"
        Programador resolvedor = new Programador("Otro nombre")
        Solucion solucion = new Solucion(resolvedor, "Descripción", desafio)
        desafio.proponerSolucion(solucion)
        assert desafio.validarSolucion(solucion).valido

        when:"se agrega un ejercicio"
        Ejercicio ejercicio2 = new Ejercicio(desafio, "Ejercicio nuevo")
        desafio.agregarEjercicio(ejercicio2)

        then:"la solución ya no resuelve el desafío"
        assert !desafio.obtenerResultadoActualDeSolucion(solucion).estaProcesado() &&
                !desafio.validarSolucion(solucion).valido

        when:"se agrega una resolución para el nuevo ejercicio"
        solucion.agregarResolucion(new Resolucion(ejercicio2, "{x -> x}"))

        then:"la solución debe reprocesarse"
        and:"la solución vuelve a ser válida"
        !desafio.obtenerResultadoActualDeSolucion(solucion).estaProcesado() &&
                !desafio.validarSolucion(solucion).valido
    }

}
