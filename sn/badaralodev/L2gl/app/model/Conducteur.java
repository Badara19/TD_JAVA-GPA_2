package sn.badaralodev.L2gl.app.model;

public class Conducteur extends Entite {
    private String nom;
    private String numeroPermis;

    public Conducteur(Long id, String nom, String numeroPermis) {
        super(id);
        this.nom = nom;
        this.numeroPermis = numeroPermis;
    }

    public String getNom()           { return nom; }
    public String getNumeroPermis()  { return numeroPermis; }

    @Override
    public String toString() {
        return nom + " (permis: " + numeroPermis + ")";
    }
    @Override
    public void afficher() {
        System.out.println(nom + " (permis: " + numeroPermis + ")");
}
}
