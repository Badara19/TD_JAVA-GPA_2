package sn.badaralodev.L2gl.app.model;

@FunctionalInterface
public interface VehiculeAction<T> {
    void agir(T objet);
}

