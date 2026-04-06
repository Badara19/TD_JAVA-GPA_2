package sn.badaralodev.L2gl.app.model;

@FunctionalInterface
public interface VehiculeComparaison<T> {
    int comparer(T objet1, T objet2);   
}
