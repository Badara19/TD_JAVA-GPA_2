    package sn.badaralodev.L2gl.app.model;

    public class Entretien {
        private String description;
        private int cout; // coût de base en FCFA
        private Vehicule vehicule; 

        public Entretien(String description, int cout, Vehicule vehicule) {
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
    }
