package codeit

import grails.testing.gorm.DomainUnitTest
import org.joda.time.DateTime
import spock.lang.Specification

class DesafioSpec extends Specification implements DomainUnitTest<Desafio> {

    def setup() {
        Validador.crearInstancia(Validador.TipoValidador.Sincronico)
    }

    def cleanup() {
    }

    void creacionDelDesafio() {
        when:"Se crea un desafío con ciertos parámetros"
        Programador programador = new Programador("El nombre")
        Desafio desafio = programador.proponerDesafio(
                "El título",
                "La descripción",
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

    void agregarUnEjercicio() {
        given:"Un desafío"
        Programador programador = new Programador("El nombre")
        Desafio desafioNuevo = programador.proponerDesafio(
                "El título",
                "La descripción",
                DateTime.now(),
                DateTime.now().plusDays(1))

        when:"se agrega un ejercicio a ese desafío"
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafioNuevo, "El enunciado")
        desafioNuevo.agregarEjercicio(ejercicio)

        then:"El desafío tiene ese ejercicio"
        desafioNuevo.ejercicios.size() == 1
        desafioNuevo.ejercicios.contains(ejercicio)
    }

    void agregarEjercicios() {
        given:"Un desafío nuevo"
        Programador programador = new Programador("El nombre")
        Desafio desafioNuevo = programador.proponerDesafio(
                "El título",
                "La descripción",
                DateTime.now(),
                DateTime.now().plusDays(1))

        when:"Se agregan N ejercicios ese desafío"
        Integer numeroDeEjercicios = 10
        for (Integer i = 0; i < numeroDeEjercicios; ++i) {
            Ejercicio ejercicio = programador.proponerEjercicioPara(desafioNuevo, "El enunciado " + i)
            desafioNuevo.agregarEjercicio(ejercicio)
        }

        then:"El desafío tiene esa cantidad de ejercicios"
        desafioNuevo.ejercicios.size() == numeroDeEjercicios
    }

    void desafioVigente() {
        when:"Se crea un desafío valido desde ahora y por un día"
        Programador programador = new Programador("El nombre")

        DateTime ahora = DateTime.now()
        DateTime unDiaDespues = ahora.plusDays(1)
        Desafio desafioNuevo = programador.proponerDesafio(
                "El título",
                "La descripción",
                ahora,
                unDiaDespues)

        then:"El desafío sigue vigente después de pasar el tiempo de ejecución de la prueba"
        desafioNuevo.estaVigente()
    }

    void desafioNoVigente() {
        when:"Se crea un desafío valido desde ayer y por un día"
        Programador programador = new Programador("El nombre")

        DateTime ahora = DateTime.now()
        DateTime ayer = ahora.minusDays(1).minusMillis(1)
        DateTime haceUnMilisegundo = ahora.minusMillis(1)
        Desafio desafioNuevo = programador.proponerDesafio(
                "El título",
                "La descripción",
                ayer,
                haceUnMilisegundo)

        then:"El desafío no está vigente después de pasar el tiempo de ejecución de la prueba"
        !desafioNuevo.estaVigente()
    }

    void solucionNuevaParaDsafioNuevo() {
        when:"Un desafío es nuevo y una solución es nueva"
        Programador programador = new Programador("El nombre")

        DateTime ahora = DateTime.now()
        DateTime unDiaDespues = ahora.plusDays(1)
        Desafio desafioNuevo = programador.proponerDesafio(
                "El título",
                "La descripción",
                ahora,
                unDiaDespues)

        Programador resolvedor = new Programador("Otro nombre")
        Solucion solucion = resolvedor.proponerSolucionPara(desafioNuevo, "Descripción")

        then:"la solución resuelve el desafío"
        desafioNuevo.obtenerResultadoActualDeSolucion(solucion) != null
    }

    void solucionSeInvalidaCuandoSeAgregaUnNuevoEjercicio() {
        given:"Un desafío con un ejercicio"
        Programador programador = new Programador("El nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio desafioNuevo = programador.proponerDesafio(
                "El título",
                "La descripción",
                ahora,
                maniana)
        Ejercicio ejercicio1 = programador.proponerEjercicioPara(desafioNuevo, "Ejercicio viejo")
        desafioNuevo.agregarEjercicio(ejercicio1)

        and:"que tenía una solución válida"
        Programador resolvedor = new Programador("Otro nombre")
        Solucion solucion = resolvedor.proponerSolucionPara(desafioNuevo, "Descripción")
        Resolucion resolucion = new Resolucion(ejercicio1, "")
        solucion.agregarResolucion(resolucion)
        assert desafioNuevo.resultados.size() == 1
        assert desafioNuevo.validarSolucion(solucion).valido

        when:"se agrega un nuevo ejercicio al desafío"
        Ejercicio ejercicio2 = programador.proponerEjercicioPara(desafioNuevo, "Ejercicio nuevo")
        desafioNuevo.agregarEjercicio(ejercicio2)

        then:"la solución debe reprocesarse"
        desafioNuevo.resultados.size() == 1
        desafioNuevo.obtenerResultadoActualDeSolucion(solucion) != null
        !desafioNuevo.obtenerResultadoActualDeSolucion(solucion).estaProcesado()

        and:"la solución ya no resuelve el desafío"
        !desafioNuevo.validarSolucion(solucion).valido
    }

    void solucionVuelveASerValidaCuandoSeAgregaUnaResolucionParaElEjercicioNuevo() {
        given:"Un desafío con un ejercicio"
        Programador programador = new Programador("El nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio desafio = programador.proponerDesafio(
                "El título",
                "La descripción",
                ahora,
                maniana)
        Ejercicio ejercicio1 = programador.proponerEjercicioPara(desafio, "Ejercicio viejo")
        desafio.agregarEjercicio(ejercicio1)

        and:"que era resuelto por una solución"
        Programador resolvedor = new Programador("Otro nombre")
        Solucion solucion = resolvedor.proponerSolucionPara(desafio, "Descripción")
        Resolucion resolucion = new Resolucion(ejercicio1, "")
        solucion.agregarResolucion(resolucion)
        assert desafio.resultados.size() == 1
        assert desafio.validarSolucion(solucion).valido

        when:"se agrega un ejercicio"
        Ejercicio ejercicio2 = programador.proponerEjercicioPara(desafio, "Ejercicio nuevo")
        desafio.agregarEjercicio(ejercicio2)

        desafio.save flush: true

        then:"la solución ya no resuelve el desafío"
        !desafio.obtenerResultadoActualDeSolucion(solucion).estaProcesado()
        !desafio.validarSolucion(solucion).valido

        when:"se agrega una resolución para el nuevo ejercicio"
        Resolucion resolucion2 = new Resolucion(ejercicio2, "def f = {x -> x}")
        solucion.agregarResolucion(resolucion2)

        then:"la solución debe reprocesarse"
        !desafio.obtenerResultadoActualDeSolucion(solucion).estaProcesado()

        when:"la solución se revalida"
        desafio.revalidarSoluciones()

        then:"la solución vuelve a ser válida"
        desafio.validarSolucion(solucion).valido
    }

}
