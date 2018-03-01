package codeit

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit;


class Validador extends Thread {
    enum TipoValidador {
        Asincronico,
        Sincronico
    }

    static Validador instancia = null
    BlockingQueue<Desafio> desafiosConCambios
    boolean mantenerVivo

    TipoValidador tipo

    static void crearInstancia(TipoValidador tipo) {
        instancia = new Validador(tipo)
    }

    static Validador obtenerInstancia() {
        return instancia;
    }

    Validador(TipoValidador tipo) {
        this.tipo = tipo
        desafiosConCambios = new LinkedBlockingDeque<>()
        mantenerVivo = true
    }

    @Override
    void run() {
        while (mantenerVivo || !desafiosConCambios.isEmpty()) {
            Desafio desafio = desafiosConCambios.poll(100, TimeUnit.MILLISECONDS)
            if (desafio != null) {
                desafio.revalidarSoluciones()
            }
        }
    }

    Validador leftShift(Desafio elemento) {
        if (tipo == TipoValidador.Asincronico) {
            desafiosConCambios << elemento
        } else {
            elemento.revalidarSoluciones()
        }
        this
    }

    void destruir() {
        mantenerVivo = false
        super.join()
    }
}
