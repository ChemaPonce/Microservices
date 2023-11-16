package com.m21290940.Tarea.controller;



        import com.m21290940.Tarea.entities.Tarea;
        import com.m21290940.Tarea.entities.Usuario;
        import com.m21290940.Tarea.repository.UsuarioRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.Optional;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Consulta general
    @GetMapping
    public Iterable<Usuario> getAlUsuarios() {
        return usuarioRepository.findAll();
    }

    //Consulta por id
    @GetMapping(path = "/{idUsuario}")
    public Usuario getUsuarioById(@PathVariable long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        return usuario.orElse(null);
    }

    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        boolean isOk = true;
        if (usuario == null)
            return ResponseEntity.badRequest().build();

        if (usuario.getNombre() == null || usuario.getCorreo() == null || usuario.getContraseña() == null ||
                usuario.getNombre().isEmpty() || usuario.getCorreo().isEmpty() || usuario.getContraseña().isEmpty()) {
            isOk = false;
        }

        if (isOk) {
            Usuario saved = usuarioRepository.save(usuario);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(path = "/{idusuario}")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario, @PathVariable long idusuario) {

        Optional<Usuario> usuarioIndb = usuarioRepository.findById(idusuario);

        if (usuarioIndb.isEmpty())
            return ResponseEntity.badRequest().build();

        usuario.setIdUsuario(usuarioIndb.get().getIdUsuario());

        boolean isOk = true;
        if (usuario == null)
            return ResponseEntity.badRequest().build();

        if (usuario.getNombre() == null || usuario.getCorreo() == null || usuario.getContraseña() == null ||
                usuario.getNombre().isEmpty() || usuario.getCorreo().isEmpty() || usuario.getContraseña().isEmpty()) {
            isOk = false;
        }

        if (isOk) {
            Usuario saved = usuarioRepository.save(usuario);
            return ResponseEntity.ok(saved);
        }

        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/{idusuario}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable long idusuario) {
        Optional<Usuario> usuarioIndb = usuarioRepository.findById(idusuario);
        if (usuarioIndb.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        usuarioRepository.deleteById(idusuario);

        return ResponseEntity.ok(usuarioIndb.get());
    }

}
