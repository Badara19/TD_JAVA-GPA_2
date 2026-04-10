package sn.badaralodev.L2gl.app.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class InMemoryCRUD <T extends Identifiable> implements CRUD<T>{
    protected final Map<Long, T> storage = new HashMap<>();
    @Override
    public T create(T entity) {
        if (entity == null)
            throw new IllegalArgumentException("L'entité ne peut pas être null.");

        if (entity.getId() == null)
            throw new IllegalArgumentException("L'id ne peut pas être null.");

        if (storage.containsKey(entity.getId()))
            throw new IllegalStateException(
                "Un élément avec l'id=" + entity.getId() + " existe déjà.");

        storage.put(entity.getId(), entity);
        return entity;
    }
    @Override
    public Optional<T> read(Long id) {
        if (id == null)
            throw new IllegalArgumentException("L'id de recherche ne peut pas être null.");

        return Optional.ofNullable(storage.get(id));
    }
    
     @Override
    public T update(T entity) {
        if (entity == null)
            throw new IllegalArgumentException("L'entité ne peut pas être null.");

        if (!storage.containsKey(entity.getId()))
            throw new NoSuchElementException(
                "Impossible de mettre à jour : aucun élément avec l'id="
                + entity.getId() + " n'existe.");

        storage.put(entity.getId(), entity);
        return entity;
    }
     @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("L'id ne peut pas être null.");

        if (!storage.containsKey(id))
            throw new NoSuchElementException(
                "Impossible de supprimer : aucun élément avec l'id=" + id + " n'existe.");

        storage.remove(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

}
