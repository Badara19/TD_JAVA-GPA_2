package sn.badaralodev.L2gl.app.model;

import java.time.LocalDate;

public class Location extends Entite {
    private Vehicule vehicule;
    private Conducteur conducteur;
    private String dateDebut;
    private String dateFin; // null si location en cours

    public Location(Long id, Vehicule vehicule, Conducteur conducteur, String dateDebut, String dateFin) {
        super(id);
        this.vehicule   = vehicule;
        this.conducteur = conducteur;
        this.dateDebut  = dateDebut;
        this.dateFin    = dateFin;
    }

    public Vehicule   getVehicule()   { return vehicule; }
    public Conducteur getConducteur() { return conducteur; }
    public String     getDateDebut()  { return dateDebut; }
    public String     getDateFin()    { return dateFin; }

    public void setDateFin(String dateFin) { this.dateFin = dateFin; }

    @Override
    public String toString() {
        return vehicule.getImmatriculation() + " loué par " + conducteur.getNom()
             + " début: " + dateDebut + "  fin: " + (dateFin != null ? dateFin : "en cours");
    }
    @Override
    public void afficher() {
        System.out.println(vehicule.getImmatriculation() + " loué par " + conducteur.getNom()
             + " début: " + dateDebut + "  fin: " + (dateFin != null ? dateFin : "en cours"));
    }
    public int dureeJours() {
        if (dateFin == null) {
            return -1; // Indique que la location est en cours
        }
        try {
            java.time.LocalDate debut = java.time.LocalDate.parse(dateDebut);
            java.time.LocalDate fin   = java.time.LocalDate.parse(dateFin);
            return (int) java.time.temporal.ChronoUnit.DAYS.between(debut, fin);
        } catch (java.time.format.DateTimeParseException e) {
            System.err.println("Format de date invalide pour la location id=" + getId());
            return -1;
        }
    }
    public void terminer(LocalDate fin) {
        if(fin.isAfter(java.time.LocalDate.parse(this.dateDebut))){
             Vehicule v = this.getVehicule();
             v.setStatut("disponible");
        }
    }
}
