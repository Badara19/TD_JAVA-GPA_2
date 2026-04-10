package sn.badaralodev.L2gl.app.model;

import sn.badaralodev.L2gl.app.repo.Identifiable;

public abstract class Entite implements Identifiable {
    private final Long  id;

    public Entite(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide pour l'entité");
        }
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public abstract void afficher();
}
