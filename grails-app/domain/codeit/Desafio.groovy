package codeit

import org.joda.time.DateTime

class Desafio {
    // TODO: agregar requisitos para participar

    static belongsTo = Programador
    static hasMany = [ejercicios: Ejercicio, soluciones: Solucion, resultados: Resultado]

    String titulo
    String descripcion
    Programador creador
    Vigencia vigencia

    Set<Ejercicio> ejercicios
    Set<Solucion> soluciones
    Set<Resultado> resultados
    Integer puntajeTotal

    static constraints = {
        titulo nullable: false, blank: false, unique: true
        descripcion nullable: false, blank: false, unique: true
        creador nullable: false
        puntajeTotal nullable: false
    }

    static mapping = {
        vigencia type: VigenciaUserType, {
            column name: "desde"
            column name: "hasta"
        }
    }

    Desafio(String titulo, String descripcion, Programador creador, DateTime fechaDesde, DateTime fechaHasta) {
        init(titulo, descripcion, creador)
        this.vigencia = new Vigencia(fechaDesde, fechaHasta)
    }

    Desafio(String titulo, String descripcion, Programador creador, DateTime fechaHasta) {
        init(titulo, descripcion, creador)
        this.vigencia = new Vigencia(fechaHasta)
    }

    Desafio(String titulo, String descripcion, Programador creador) {
        init(titulo, descripcion, creador)
        this.vigencia = new Vigencia()
    }

    private void init(String titulo, String descripcion, Programador creador) {
        this.titulo = titulo
        this.descripcion = descripcion
        this.creador = creador
        this.puntajeTotal = 0
        this.ejercicios = new LinkedHashSet<>()
        this.soluciones = new LinkedHashSet<>()
        this.resultados = new LinkedHashSet<>()
    }

    // Función para agregar la nueva solución del usuario
    void proponerSolucion(Solucion solucion) throws InvolucraAlCreador, YaParticipaDelDesafio, DesafioNoVigente {
        if (!estaVigente()) {
            throw new DesafioNoVigente()
        }
        if (esCreador(solucion.participante)) {
            throw new InvolucraAlCreador()
        }
        if (!puedeParticipar(solucion.participante)) {
            throw new YaParticipaDelDesafio()
        }
        soluciones.remove(solucion)
        soluciones.add(solucion)
        resultados.removeIf({it.solucion == solucion})
        Resultado resultado = new Resultado(solucion)
        assert resultado != null
        resultados.add(resultado)
    }

    // Función para procesar la solución del usuario
    Resultado validarSolucion(Solucion solucion) {
        int index = resultados.findIndexOf { it.solucion == solucion }
        if (~index && !resultados[index].estaProcesado()) {
            soluciones.remove(solucion)
            soluciones.add(solucion)
            resultados.removeIf({ it.solucion == solucion })
            Resultado resultado = solucion.validar(ejercicios)
            assert resultado != null
            resultados.add(resultado)
            resultado
        } else {
            assert resultados[index] != null
            resultados[index]
        }
    }

    Resultado obtenerResultadoActualDeSolucion(Solucion solucion) {
        resultados.find { it.solucion == solucion }
    }

    private Boolean esCreador(Participante participante) {
        creador.involucraA(participante)
    }

    private Boolean puedeParticipar(Participante participante) {
        estaVigente() &&
                ((!esCreador(participante)) ||
                esParticipante(participante) ||
                !resultados.find { it.solucion.participante.involucraA(participante) })
    }

    private Boolean esParticipante(Participante participante) {
        soluciones.find { it.participante == participante }
    }

    Boolean estaVigente() {
        assert vigencia != null
        vigencia.contiene(DateTime.now())
    }

    void agregarEjercicio(Ejercicio ejercicio) throws DesafioNoVigente {
        if (!estaVigente()) {
            throw DesafioNoVigente()
        }

        assert ejercicios != null
        ejercicios.add(ejercicio)

        resultados.clear()
        resultados.addAll(soluciones.collect { new Resultado(it) })
    }

    void revalidarSoluciones() {
        resultados.clear()
        resultados.addAll(soluciones.collect { it.validar(ejercicios) })
    }

    Integer asignarPunto() {
        puntajeTotal += 1
    }
}
