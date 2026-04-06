package sn.badaralodev.L2gl.app.model;
@FunctionalInterface
public interface VehiculeTransformation<T,R> {
    R transformer(T objet);
}

