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
        when:"Se agrega un ejercicio a un desafío nuevo"
        Programador programador = new Programador("El nombre")
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                DateTime.now(),
                DateTime.now())
        Ejercicio ejercicio = new Ejercicio("El enunciado")
        desafioNuevo.agregarEjercicio(ejercicio)

        then:"El desafío tiene ese ejercicio"
        desafioNuevo.ejercicios.size() == 1
        desafioNuevo.ejercicios.contains(ejercicio)
    }

    void "Agregar ejercicios"() {
        when:"Se agregan N ejercicio a un desafío nuevo"
        Programador programador = new Programador("El nombre")
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                DateTime.now(),
                DateTime.now())
        Integer numeroDeEjercicios = 10
        for (Integer i = 0; i < numeroDeEjercicios; ++i) {
            Ejercicio ejercicio = new Ejercicio("El enunciado " + i)
            desafioNuevo.agregarEjercicio(ejercicio)
        }

        then:"El desafío tiene esa cantidad de ejercicios"
        desafioNuevo.ejercicios.size() == numeroDeEjercicios
    }

    void "Desafío vigente"() {
        when:"Se crea un desafío valido desde ahora y por un día"
        Programador programador = new Programador("El nombre")

        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio desafioNuevo = new Desafio(
                "El título",
                "La descripción",
                programador,
                ahora,
                maniana)

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

}
