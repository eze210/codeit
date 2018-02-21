package codeit

import org.joda.time.DateTime

/** Clase desafío. */
class Desafio {

    /** Título del desafío. */
    String titulo

    /** Descripción del desafío. */
    String descripcion

    /** Vigencia del desafío. */
    Vigencia vigencia

    /** Programador que creó el desafío. */
    Programador creador

    /** Ejercicios que debe resolver una solución propuesta para ser válida. */
    Set<Ejercicio> ejercicios

    /** Soluciones propuestas para el desafío. */
    Set<Solucion> soluciones

    /** Reultados de las soluciones propuestas. */
    Set<Resultado> resultados

    /** Insignias que debe tener un participante para participar del desafío. */
    Set<Insignia> insigniasRequeridas

    /** Insignias que el creador del desafío puede asignar a las soluciones. */
    Set<Insignia> insigniasHabilitadas

    /** Facetas en las que se puede puntuar al desafío. */
    Set<Faceta> facetas

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [creador: Programador]
    static hasMany = [ejercicios          : Ejercicio,
                      soluciones          : Solucion,
                      resultados          : Resultado,
                      insigniasRequeridas : Insignia,
                      insigniasHabilitadas: Insignia,
                      facetas             : Faceta]

    /** Reglas para el mapeo relacional. */
    static constraints = {
        titulo nullable: false, blank: false, unique: true
        descripcion nullable: false, blank: false, unique: true
        creador nullable: false
    }

    /** Reglas especiales para el mapeo relacional de value objects. */
    static mapping = {
        vigencia type: VigenciaUserType, {
            column name: "desde"
            column name: "hasta"
        }
    }


    /** Constructor del desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @param creador Programador que crea el nuevo desafío.
     * @param insigniasRequeridas Insignias que requiere el nuevo desafío.
     * @param fechaDesde Fecha desde la cual estará vigente el nuevo desafío.
     * @param fechaHasta Fecha hasta la cual estará vigente el nuevo desafío.
     */
    Desafio(String titulo, String descripcion, Programador creador, Set<Insignia> insigniasRequeridas, DateTime fechaDesde, DateTime fechaHasta) {
        init(titulo, descripcion, creador, insigniasRequeridas)
        this.vigencia = new Vigencia(fechaDesde, fechaHasta)
    }


    /** Constructor del desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @param creador Programador que crea el nuevo desafío.
     * @param insigniasRequeridas Insignias que requiere el nuevo desafío.
     * @param fechaHasta Fecha hasta la cual estará vigente el nuevo desafío.
     */
    Desafio(String titulo, String descripcion, Programador creador, Set<Insignia> insigniasRequeridas, DateTime fechaHasta) {
        init(titulo, descripcion, creador, insigniasRequeridas)
        this.vigencia = new Vigencia(fechaHasta)
    }


    /** Constructor del desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @param creador Programador que crea el nuevo desafío.
     * @param insigniasRequeridas Insignias que requiere el nuevo desafío.
     */
    Desafio(String titulo, String descripcion, Programador creador, Set<Insignia> insigniasRequeridas) {
        init(titulo, descripcion, creador, insigniasRequeridas)
        this.vigencia = new Vigencia()
    }


    /** Función auxiliar del constructor del desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @param creador Programador que crea el nuevo desafío.
     * @param insigniasRequeridas Insignias que requiere el nuevo desafío.
     */
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


    /** Función para agregar la nueva solución del progroamador.
     *
     * @param solucion Solución que se desea proponer.
     * @throws ComparteMiembrosConCreador
     * @throws YaParticipaDelDesafio
     * @throws DesafioNoVigente
     */
    void proponerSolucion(Solucion solucion) throws ComparteMiembrosConCreador, YaParticipaDelDesafio, DesafioNoVigente {
        soluciones.remove(solucion)
        soluciones.add(solucion)
        resultados.removeIf({ it.solucion == solucion })
        Resultado resultado = new Resultado(solucion)
        assert resultado != null
        resultados.add(resultado)
    }


    /** Función para procesar la solución del usuario.
     *
     * @param solucion La solución que se quiere procesar.
     *
     * @return El resultado de procesar la solución.
     */
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


    /** Devuelve el resultado actual de una solución.
     *
     * @param solucion Solución de la que se quiere obtener el resultado.
     *
     * @return El resultado actual de una solución.
     */
    Resultado obtenerResultadoActualDeSolucion(Solucion solucion) {
        resultados.find { it.solucion == solucion }
    }


    /** Verifica las reglas de negocio para que un participante pueda participar de unn desafío.
     *
     * @param participante Participante que quiere participar del desafío.
     *
     * @return \c true si el participante puede participar, o \c false en otro caso.
     */
    Boolean puedeParticipar(Participante participante) {
        try {
            validarParticipacion(participante)
        } catch (Exception) {
            return false
        }
        return true;
    }


    /** Verifica las reglas de negocio para que un participante pueda participar de unn desafío.
     *
     * @param participante Participante que quiere participar del desafío.
     * @throws DesafioNoVigente,
     * @throws ComparteMiembrosConCreador,
     * @throws ComparteMiembrosConParticipante,
     * @throws NoPoseeInsignias
     *
     * @return \c true si ninguna validación falla.
     */
    Boolean validarParticipacion(Participante participante) throws
            DesafioNoVigente,
            ComparteMiembrosConCreador,
            ComparteMiembrosConParticipante,
            NoPoseeInsignias {
        if (!estaVigente())
            throw new DesafioNoVigente()

        if (participante.comparteMiembrosCon(creador))
            throw new ComparteMiembrosConCreador()

        if (participante.comparteAlgunEquipoCon(creador))
            throw new ComparteEquipoConCreador()

        /* si ya es participante puede seguir participando */
        if (esParticipante(participante))
            return true;

        /* todavía no participa pero comparte miembros con algún participante */
        if (resultados.findAll { it.solucion.participante.comparteMiembrosCon(participante) })
            throw new ComparteMiembrosConParticipante()

        if (!participante.obtenerInsignias().containsAll(insigniasRequeridas))
            throw new NoPoseeInsignias();

        return true;
    }


    /** Verifica si un participante ya propuso alguna solución al desafío.
     *
     * @param participante El participante sobre el que se efectúa la consulta.
     *
     * @return \c true si el participante participa del desafío, o \c false en otro caso.
     */
    Boolean esParticipante(Participante participante) {
        soluciones.find { it.participante == participante }
    }


    /** Corrobora que un desafío esté en vigencia.
     *
     * @return \c true si el desafío está vigente, o \c false sino.
     */
    Boolean estaVigente() {
        assert vigencia != null
        vigencia.estaVigente()
    }


    /** Agrega un ejercicio al desafío.
     *
     * @param ejercicio Ejercicio que se quiere agregar.
     * @throws DesafioNoVigente
     *
     * @return Los nuevos resultados.
     */
    Set<Resultado> agregarEjercicio(Ejercicio ejercicio) throws DesafioNoVigente {
        if (!estaVigente()) {
            throw new DesafioNoVigente()
        }

        assert ejercicios != null
        ejercicios.add(ejercicio)

        //TODO: Conviene usar revalidarSoluciones()?
        resultados.clear()
        resultados.addAll(soluciones.collect { new Resultado(it) })
        resultados
    }


    /** Vuelve a calcular los resultados para las soluciones propuestas.
     *
     * @return La nueva colección de resultados.
     */
    Set<Resultado> revalidarSoluciones() {
        resultados.clear()
        resultados.addAll(soluciones.collect { it.validar(ejercicios) })
        resultados
    }


    /** Devuelve las insignias habilitadas para que el creador las otorgue a las soluciones.
     *
     * @return Las insignias habilitadas para que el creador las otorgue a las soluciones.
     */
    Set<Insignia> obtenerInsigniasHabilitadas() {
        //TODO: Devolver solamente las que no se hayan asignado a alguna solución.
        insigniasHabilitadas
    }


    /** Devuelve las insignias requeridas para que un participante pueda proponer una solución.
     *
     * @return Las insignias requeridas para que un participante pueda proponer una solución.
     */
    Set<Insignia> obtenerInsigniasRequeridas() {
        insigniasRequeridas
    }


    /** Asigna un punto al desafío.
     *
     * @return La nueva cantidad de puntos que tiene un desafío.
     */
    Integer asignarPunto() {
        Faceta facetaDesafiante = facetas.find { it.tipo == TipoFaceta.Desafio }
        insigniasHabilitadas.addAll(facetaDesafiante.asignarPuntos(1))
        creador.asignarPuntoEnFaceta(TipoFaceta.Desafiante)
        facetaDesafiante.getPuntosAcumulados()
    }


    /** Devuelve el puntaje del desafío.
     *
     * @return El puntaje del desafío.
     */
    Integer obtenerPuntajeTotal() {
        facetas.find { it.tipo == TipoFaceta.Desafio }.puntosAcumulados
    }

}
