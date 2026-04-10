package sn.badaralodev.L2gl.app.repo;

import java.util.List;
import java.util.Optional;

public interface CRUD <T extends Identifiable> {
     T create(T entity);
    Optional<T>  read(Long id);
    T update(T entity);
    void delete(Long id);
    List<T> findAll();
}
