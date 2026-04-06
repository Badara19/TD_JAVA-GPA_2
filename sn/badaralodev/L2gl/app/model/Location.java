package sn.badaralodev.L2gl.app.model;

public class Location {
    private Vehicule vehicule;
    private Conducteur conducteur;
    private String dateDebut;
    private String dateFin; // null si location en cours

    public Location(Vehicule vehicule, Conducteur conducteur, String dateDebut) {
        this.vehicule   = vehicule;
        this.conducteur = conducteur;
        this.dateDebut  = dateDebut;
        this.dateFin    = null;
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
}
