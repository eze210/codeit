package codeit

import org.joda.time.DateTime

class Desafio {

    String titulo
    String descripcion
    Vigencia vigencia

    static belongsTo = [creador: Programador]
    static hasMany = [ejercicios: Ejercicio,
                      soluciones: Solucion,
                      resultados: Resultado,
                      insigniasRequeridas: Insignia,
                      insigniasHabilitadas: Insignia,
                      facetas: Faceta]

    static constraints = {
        titulo nullable: false, blank: false, unique: true
        descripcion nullable: false, blank: false, unique: true
        creador nullable: false
    }

    static mapping = {
        vigencia type: VigenciaUserType, {
            column name: "desde"
            column name: "hasta"
        }
    }

    Desafio(String titulo, String descripcion, Programador creador, Set<Insignia> insigniasRequeridas, DateTime fechaDesde, DateTime fechaHasta) {
        init(titulo, descripcion, creador, insigniasRequeridas)
        this.vigencia = new Vigencia(fechaDesde, fechaHasta)
    }

    Desafio(String titulo, String descripcion, Programador creador, Set<Insignia> insigniasRequeridas, DateTime fechaHasta) {
        init(titulo, descripcion, creador, insigniasRequeridas)
        this.vigencia = new Vigencia(fechaHasta)
    }

    Desafio(String titulo, String descripcion, Programador creador, Set<Insignia> insigniasRequeridas) {
        init(titulo, descripcion, creador, insigniasRequeridas)
        this.vigencia = new Vigencia()
    }

    private void init(String titulo, String descripcion, Programador creador, Set<Insignia> insigniasRequeridas) {
        this.titulo = titulo
        this.descripcion = descripcion
        this.creador = creador
        this.insigniasRequeridas = insigniasRequeridas
        this.ejercicios = new LinkedHashSet<>()
        this.soluciones = new LinkedHashSet<>()
        this.resultados = new LinkedHashSet<>()
        this.insigniasHabilitadas = new LinkedHashSet<>()
        this.facetas = new LinkedHashSet<>([new Faceta(TipoFaceta.Desafio)])
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
        participante.comparteMiembrosCon(creador)
    }

    private Boolean puedeParticipar(Participante participante) {
        if (!estaVigente())
            return false;

        if (esCreador(participante))
            return false;

        if (resultados.find { it.solucion.participante.comparteMiembrosCon(participante) })
            return false;

        return true;
    }

    private Boolean esParticipante(Participante participante) {
        soluciones.find { it.participante == participante }
    }

    Boolean estaVigente() {
        assert vigencia != null
        vigencia.estaVigente()
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

    Set<Insignia> obtenerInsigniasHabilitadas() {
        insigniasHabilitadas
    }

    Set<Insignia> obtenerInsigniasRequeridas() {
        insigniasRequeridas;
    }

    Integer asignarPunto() {
        insigniasHabilitadas.addAll(facetas.find { it.tipo == TipoFaceta.Desafio }.asignarPuntos(1))
        creador.asignarPuntoEnFaceta(TipoFaceta.Desafiante)
    }

    Integer obtenerPuntajeTotal() {
        facetas.find { it.tipo == TipoFaceta.Desafio }.puntosAcumulados
    }

}
