    package sn.badaralodev.L2gl.app.model;

    public class Entretien extends Entite {
        private String description;
        private int cout; // coût de base en FCFA
        private Vehicule vehicule; 

        public Entretien( Long id, String description, int cout, Vehicule vehicule) {
            super(id);
            this.description = description;
            this.cout = cout;
            this.vehicule = vehicule; // Le véhicule associé sera défini ultérieurement
        }

        public String getDescription() { return description; }
        public int    getCout()        { return cout; }

        public Vehicule getVehicule(){
            return vehicule;
        }
        @Override
        public String toString() {
            return description + " | coût: " + cout + " FCFA";
        }
        @Override
        public void afficher() {
            System.out.println(description + " | coût: " + cout + " FCFA"); 

    }
}