package codeit

class Equipo extends Participante {

    /** Miembros del equipo. */
    Set<Programador> programadores

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = Programador
    static hasMany = [programadores: Programador]

    /** Reglas para el mapeo relacional. */
    static constraints = {
        programadores nullable: false
    }


    /** Constructor de un nuevo equipo.
     *
     * @param nombre Nombre del equipo.
     */
    Equipo(String nombre) {
        super(nombre)
        this.programadores = new HashSet<>()
    }


    /** Devuelve una colección con los miembros del equipo.
     *
     * @return Una colección con los miembros del equipo.
     */
    Set<Programador> programadoresInvolucrados() {
        programadores.findAll() //para crear una copia
    }


    /** Agrega un miembro a un equipo.
     *
     * @param nuevoMiembro Nuevo programador que debe agregarse al equipo.
     * @throws ProgramadorYaMiembro
     * @throws EquipoYaExistente
     *
     * @return El equipo con el nuevo miembro agregado.
     */
    Equipo agregarMiembro(Programador nuevoMiembro) throws ProgramadorYaMiembro, EquipoYaExistente {
        if (programadores.contains(nuevoMiembro))
            throw new ProgramadorYaMiembro()

        if (!formanEquipoValido(this, nuevoMiembro))
            throw new EquipoYaExistente()

        programadores.add(nuevoMiembro)
        nuevoMiembro.equipos.add(this)
        this
    }


    /** Quita un programador de un equipo.
     *
     * @param programador Programador a quitar.
     *
     * @return El equipo sin el programador quitado.
     */
    Equipo removerMiembro(Programador programador) {
        if (!formanEquipoValido(programadores - programador))
            throw new EquipoInvalido()

        programadores.remove(programador)
        this
    }


    /** Crea una invitación y se la envía a un programador.
     *
     * @param programador
     * @return
     */
    Invitacion invitar(Programador programador) {
        new Invitacion(this, programador)
    }


    /** Verifica las reglas de negocio acerca de la conformación de equipos.
     *
     * @param miembros Miembros del hipotético equipo.
     *
     * @return \c true si los hipotéticos miembros forman un equipo válido, o \c false en otro caso.
     */
    static Boolean formanEquipoValido(Set<Programador> miembros) {
        // todo: improve with database direct access
        // findByProgramadores no funciona
        List<Equipo> otrosEquipos = Equipo.findAll()
        for (Equipo equipo: otrosEquipos) {
            if ((equipo.programadores.size() == miembros.size()) &&
                    (equipo.programadores.collect { it.nombre }.intersect(miembros.collect { it.nombre }).size() == miembros.size())) {
                return false
            }
        }
        return true
    }


    /** Verifica las reglas de negocio acerca de la conformación de equipos.
     *
     * @param parte1 Participante que involucra algunos miembros.
     * @param parte2 Participante que involucra algunos miembros.
     *
     * @return \c true si los hipotéticos miembros forman un equipo válido, o \c false en otro caso.
     */
    static Boolean formanEquipoValido(Participante parte1, Participante parte2) {
        Set<Programador> miembros = new HashSet<>(parte1.programadoresInvolucrados())
        miembros.addAll(parte2.programadoresInvolucrados())
        formanEquipoValido(miembros)
    }

}
