package codeit

import codeit.seguridad.*

/** Clase que encapsula el manejo de las clases auto-generadas por el módulo de seguridad. */
class Seguridad {

    /** Instancia un nuevo usuario.
     *
     * @param nombre Nombre del nuevo usuario.
     * @param contraseña Contraseña del nuevo usuario.
     *
     * @return El nuevo usuario.
     */
    static Usuario crearUsuario(String nombre, String contraseña) {
        Usuario nuevoUsuario = new Usuario(username: nombre, password: contraseña)
        try {
            Rol role = Rol.findByAuthority('ROLE_ADMIN')
            nuevoUsuario.save(flush: true)
            UsuarioRol.create(nuevoUsuario, role, true)
            UsuarioRol.withSession {
                it.flush()
                it.clear()
            }
        } catch (IllegalStateException e) {
            e.printStackTrace()
        } catch (NullPointerException e) {
            e.printStackTrace()
        }
        nuevoUsuario
    }

}
