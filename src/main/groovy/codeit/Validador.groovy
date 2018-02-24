package codeit

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit;


class Validador extends Thread {
    static Validador instancia = new Validador();
    BlockingQueue<Desafio> desafiosConCambios
    boolean mantenerVivo

    static Validador obtenerInstancia() {
        return instancia;
    }

    private Validador() {
        desafiosConCambios = new LinkedBlockingDeque<>()
        mantenerVivo = true
    }

    @Override
    void run() {
        while (mantenerVivo && !desafiosConCambios.isEmpty()) {
            Desafio desafio = desafiosConCambios.poll(100, TimeUnit.MILLISECONDS)
            if (desafio != null)
                desafio.revalidarSoluciones()
        }
    }

    Validador leftShift(Desafio elemento) {
        desafiosConCambios << elemento
        this
    }

    void destruir() {
        mantenerVivo = false
    }
}
