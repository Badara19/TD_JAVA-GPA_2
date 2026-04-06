package sn.badaralodev.L2gl.app.model;

public class Vehicule {
    private Long id;
    private String immatriculation;
    private String marque;
    private String modele;
    private int annee;
    private int kilometrage;
    private String statut; // "disponible", "en_panne", "en_revision", "loue"

    public Vehicule(Long id, String immatriculation, String marque, String modele, int annee, int kilometrage, String statut) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.kilometrage = kilometrage;
        this.statut = statut;
    }

    public Long getId() { return id; }
    public String getImmatriculation() { return immatriculation; }
    public String getMarque()          { return marque; }
    public String getModele()          { return modele; }
    public int    getAnnee()           { return annee; }
    public int    getKilometrage()     { return kilometrage; }
    public String getStatut()          { return statut; }

    public void setStatut(String statut)           { this.statut = statut; }
    public void setKilometrage(int kilometrage)    { this.kilometrage = kilometrage; }

    @Override
    public String toString() {
        return immatriculation + " | " + marque + " " + modele + " | " + annee + " | " + kilometrage + " km | " + statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicule vehicule = (Vehicule) o;
        return immatriculation.equals(vehicule.immatriculation);
    }
    @Override
    public int hashCode() {
        return immatriculation.hashCode();
    }

}
