package sn.badaralodev.L2gl.app.model;

public class Conducteur {
    private String nom;
    private String numeroPermis;

    public Conducteur(String nom, String numeroPermis) {
        this.nom = nom;
        this.numeroPermis = numeroPermis;
    }

    public String getNom()           { return nom; }
    public String getNumeroPermis()  { return numeroPermis; }

    @Override
    public String toString() {
        return nom + " (permis: " + numeroPermis + ")";
    }
}
