package codeit

import org.joda.time.DateTime

/** Clase desafío. */
class Desafio implements Puntuable {

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

    /** Reultados de las soluciones propuestas. */
    Set<Resultado> resultados

    /** Insignias que debe tener un participante para participar del desafío. */
    Set<Insignia> insigniasRequeridas

    /** Puntaje del desafío. */
    Puntaje puntaje

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [creador: Programador]
    static hasMany = [ejercicios          : Ejercicio,
                      resultados          : Resultado,
                      insigniasRequeridas : Insignia]

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
        this.resultados = new LinkedHashSet<>()
        this.puntaje = new Puntaje([new Faceta(TipoFaceta.Desafio)])
    }

    /** Función para agregar la nueva solución del progroamador.
     *
     * @param solucion Solución que se desea proponer.
     * @throws ComparteMiembrosConCreador
     * @throws YaParticipaDelDesafio
     * @throws DesafioNoVigente
     */
    void proponerSolucion(Solucion solucion) throws ComparteMiembrosConCreador, YaParticipaDelDesafio, DesafioNoVigente {
        // TODO: tirar las excepciones que dice la doc.
        int index = resultados.findIndexOf { it.solucion == solucion }
        if (~index) {
            resultados[index].invalidar()
        } else {
            Resultado resultado = new Resultado(solucion)
            resultados.add(resultado)
        }
        Validador.obtenerInstancia() << this
    }

    /** Función para procesar la solución del usuario.
     *
     * @param solucion La solución que se quiere procesar.
     * @return El resultado de procesar la solución.
     */
    Resultado validarSolucion(Solucion solucion) {
        int index = resultados.findIndexOf { it.solucion == solucion }
        if (~index && !resultados[index].estaProcesado()) {
            resultados[index].procesar()
        }
        resultados[index]
    }

    /** Devuelve el resultado actual de una solución.
     *
     * @param solucion Solución de la que se quiere obtener el resultado.
     * @return El resultado actual de una solución.
     */
    Resultado obtenerResultadoActualDeSolucion(Solucion solucion) {
        resultados.find { it.solucion == solucion }
    }

    /** Verifica las reglas de negocio para que un participante pueda participar de unn desafío.
     *
     * @param participante Participante que quiere participar del desafío.
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
        if (propusoAlgunaSolucion(participante))
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
     * @return \c true si el participante participa del desafío, o \c false en otro caso.
     */
    Boolean propusoAlgunaSolucion(Participante participante) {
        resultados.find { it.solucion.participante == participante } != null
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
     * @return Los nuevos resultados.
     */
    Set<Resultado> agregarEjercicio(Ejercicio ejercicio) throws DesafioNoVigente {
        if (!estaVigente()) {
            throw new DesafioNoVigente()
        }

        assert ejercicios != null
        ejercicios.add(ejercicio)

        invalidarSoluciones()
        Validador.obtenerInstancia() << this
        resultados
    }

    void invalidarSoluciones() {
        resultados.forEach { it.invalidar() }
    }

    void invalidarSolucion(Solucion solucion) {
        resultados.find { it.solucion.id == solucion.id }?.invalidar()
    }

    /** Vuelve a calcular los resultados para las soluciones propuestas.
     *
     * @return La nueva colección de resultados.
     */
    Set<Resultado> revalidarSoluciones() {
        resultados.forEach { it.procesar() }
        resultados
    }

    /** Devuelve las insignias requeridas para que un participante pueda proponer una solución.
     *
     * @return Las insignias requeridas para que un participante pueda proponer una solución.
     */
    Set<Insignia> obtenerInsigniasRequeridas() {
        insigniasRequeridas
    }

    /* ****************************************************************** *
     * Implementación de la Interfaz Puntuable.
     * ****************************************************************** */

    @Override
    Set<Insignia> otorgarInsignia(Insignia insignia) {
        puntaje.otorgarInsignia(insignia)
    }

    @Override
    Set<Insignia> obtenerInsignias() {
        puntaje.obtenerInsignias()
    }

    @Override
    Insignia retirarInsignia(Insignia insignia) {
        puntaje.retirarInsignia(insignia)
    }

    @Override
    Integer otorgarPuntoEnFaceta(TipoFaceta tipoFaceta) {
        creador.otorgarPuntoEnFaceta(TipoFaceta.Desafiante)
        puntaje.otorgarPuntoEnFaceta(tipoFaceta)
    }

    @Override
    Integer obtenerPuntajeEnFaceta(TipoFaceta tipoFaceta) {
        puntaje.obtenerPuntajeEnFaceta(tipoFaceta)
    }
}
