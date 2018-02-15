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
        Boolean sameLower;
        if (xVigencia.rangoDeFechas.hasLowerBound() && yVigencia.rangoDeFechas.hasLowerBound()) {
            sameLower = (xVigencia.rangoDeFechas.lowerEndpoint() == yVigencia.rangoDeFechas.lowerEndpoint())
        } else if (!xVigencia.rangoDeFechas.hasLowerBound() && !yVigencia.rangoDeFechas.hasLowerBound()) {
            sameLower = true
        } else {
            sameLower = false
        }
        Boolean sameUpper;
        if (xVigencia.rangoDeFechas.hasUpperBound() && yVigencia.rangoDeFechas.hasUpperBound()) {
            sameUpper = (xVigencia.rangoDeFechas.upperEndpoint() == yVigencia.rangoDeFechas.upperEndpoint())
        } else if (!xVigencia.rangoDeFechas.hasUpperBound() && !yVigencia.rangoDeFechas.hasUpperBound()) {
            sameUpper = true
        } else {
            sameUpper = false
        }
        sameLower && sameUpper
    }

    @Override
    int hashCode(Object x) throws HibernateException {
        return x.hashCode()
    }

    @Override
    Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        assert names.length == 2
        Date fechaDesde = DateType.INSTANCE.nullSafeGet(rs, names[0], session)
        Date fechaHasta = DateType.INSTANCE.nullSafeGet(rs, names[1], session)
        fechaDesde = fechaDesde == new Date(0,0,0) ? null : fechaDesde
        fechaHasta = fechaHasta == new Date(0,0,0) ? null : fechaHasta
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
            if (vig.rangoDeFechas.hasLowerBound()) {
                DateType.INSTANCE.nullSafeSet(st, vig.rangoDeFechas.lowerEndpoint().toDate(), index, session)
            } else {
                DateType.INSTANCE.nullSafeSet(st, new Date(0,0,0), index, session)
            }
            if (vig.rangoDeFechas.hasUpperBound()) {
                DateType.INSTANCE.nullSafeSet(st, vig.rangoDeFechas.upperEndpoint().toDate(), index + 1, session)
            } else {
                DateType.INSTANCE.nullSafeSet(st, new Date(0,0,0), index + 1, session)
            }
        }
    }

    @Override
    Object deepCopy(Object value) throws HibernateException {
        Vigencia vig = value.asType(Vigencia)
        if (!vig) return vig
        if (vig.rangoDeFechas.hasLowerBound() && vig.rangoDeFechas.hasUpperBound()) {
            return new Vigencia(vig.rangoDeFechas.lowerEndpoint(), vig.rangoDeFechas.upperEndpoint())
        }
        if (vig.rangoDeFechas.hasUpperBound()) {
            return new Vigencia(vig.rangoDeFechas.upperEndpoint())
        }
        return new Vigencia()
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