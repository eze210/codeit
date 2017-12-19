package codeit

import com.intellij.util.containers.OrderedSet
import com.sun.org.apache.xpath.internal.operations.Bool

class Solucion {

    static hasMany = [resoluciones: Resolucion]

    Participante participante
    String descripcion
    OrderedSet<Resolucion> resoluciones

    static constraints = {
        participante nullable:false
    }

    Boolean agregarResolucion(Resolucion resolucion) {
        return resoluciones.add(resolucion)
    }

}
