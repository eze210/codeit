package codeit

import org.hibernate.HibernateException
import org.hibernate.engine.spi.SessionImplementor
import org.hibernate.type.DateType
import org.hibernate.usertype.UserType
import org.joda.time.DateTime

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class VigenciaUserType implements UserType {

    @Override
    int[] sqlTypes() {
        [DateType.INSTANCE.sqlType(), DateType.INSTANCE.sqlType()] as int[]
    }

    @Override
    Class<Vigencia> returnedClass() {
        Vigencia.class
    }

    @Override
    boolean equals(Object x, Object y) throws HibernateException {
        Vigencia xVigencia = x.asType(Vigencia)
        Vigencia yVigencia = y.asType(Vigencia)
        assert xVigencia != null && yVigencia != null
        (xVigencia.rangoDeFechas.lowerEndpoint() == yVigencia.rangoDeFechas.lowerEndpoint()) &&
                (xVigencia.rangoDeFechas.upperEndpoint() == yVigencia.rangoDeFechas.upperEndpoint())
    }

    @Override
    int hashCode(Object x) throws HibernateException {
        return x.hashCode()
    }

    @Override
    Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        assert names.length == 2
        log.debug(">>mullSafeGet(name=${names}")

        Date fechaDesde = (Date) DateType.INSTANCE.nullSafeGet(rs, names[0], session)
        Date fechaHasta = DateType.INSTANCE.nullSafeGet(rs, names[1], session)
        DateTime desde = fechaDesde ? new DateTime(fechaDesde) : null
        DateTime hasta = fechaHasta ? new DateTime(fechaHasta) : null
        if (desde && hasta) {
            new Vigencia(hasta, desde)
        } else if (hasta) {
            new Vigencia(hasta)
        } else {
            new Vigencia()
        }
    }

    @Override
    void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            DateType.INSTANCE.nullSafeSet(st, null, index, session)
            DateType.INSTANCE.nullSafeSet(st, null, index+1, session)
        } else {
            final Vigencia vig = (Vigencia) value
            DateType.INSTANCE.nullSafeSet(st, vig.rangoDeFechas.lowerEndpoint().toDate(), index, session)
            DateType.INSTANCE.nullSafeSet(st, vig.rangoDeFechas.upperEndpoint().toDate(), index+1, session)
        }
    }

    @Override
    Object deepCopy(Object value) throws HibernateException {
        Vigencia vig = value.asType(Vigencia)
        if (!vig) return vig
        new Vigencia(vig.rangoDeFechas.lowerEndpoint(), vig.rangoDeFechas.upperEndpoint())
    }

    @Override
    boolean isMutable() {
        false
    }

    @Override
    Serializable disassemble(Object value) throws HibernateException {
        (Serializable) value
    }

    @Override
    Object assemble(Serializable cached, Object owner) throws HibernateException {
        cached
    }

    @Override
    Object replace(Object original, Object target, Object owner) throws HibernateException {
        original
    }
}

class Desafio {
    // TODO: agregar requisitos para participar

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
        vigencia nullable: false, blank: false
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
