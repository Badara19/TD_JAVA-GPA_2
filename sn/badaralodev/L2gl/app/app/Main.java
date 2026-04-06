package sn.badaralodev.L2gl.app.app;

import sn.badaralodev.L2gl.app.model.*;
import sn.badaralodev.L2gl.app.service.ParcAutoService;
import sn.badaralodev.L2gl.app.interfaces.IParcAutoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
        private static IParcAutoService service = new ParcAutoService();
    public static void main(String[] args) {

        //  FLOTTE DE TEST
         List<Vehicule> flotte = new ArrayList<>(Arrays.asList(
            new Vehicule(1L, "DK-1234-AB", "Toyota",  "Corolla", 2015, 120000, "disponible"),
            new Vehicule(2L, "DK-5678-CD", "Renault", "Clio",    2020,  32000, "en_panne"),
            new Vehicule(3L, "DK-9012-EF", "Peugeot", "508",     2012, 210000, "disponible"),
            new Vehicule(4L, "DK-3456-GH", "Honda",   "Civic",   2019,  78000, "loue")
        ));

        Conducteur c1 = new Conducteur("Moussa Diallo",  "B-20451");
        Conducteur c2 = new Conducteur("Fatou Seck",     "A-98321");

        Entretien e1 = new Entretien("Vidange + filtres",    45000, flotte.get(0));
        Entretien e2 = new Entretien("Remplacement plaquettes", 80000, flotte.get(1));

        Location loc1 = new Location(flotte.get(3), c1, "2025-06-01");

        

      //  CONSTANTES UTILITAIRES
        final int SEUIL_KM        = 100000;
        final int SEUIL_ANNEE     = 2016;
        final int TAXE_ENTRETIEN  = 5000;
        final int ANNEE_COURANTE  = 2025;

        // ── Comportement 1 : Véhicule disponible 
        VehiculeTest<Vehicule> estDisponible = v -> v.getStatut().toLowerCase().equals("disponible");

        List<Vehicule> disponibles = service.filtrerVehicules(flotte, estDisponible);
        System.out.println("1. Véhicules disponibles :");
        disponibles.forEach(v -> System.out.println("   → " + v));

        // ── Comportement 2 : Véhicule en panne ?
        VehiculeTest<Vehicule> estEnPanne = v -> v.getStatut().equals("en_panne");

        List<Vehicule> enPanne = service.filtrerVehicules(flotte, estEnPanne);
        System.out.println("\n2. Véhicules en panne :");
        enPanne.forEach(v -> System.out.println("   → " + v));

        // ── Comportement 3 : Kilométrage > seuil ?
        VehiculeTest<Vehicule> kmDepasse = v -> v.getKilometrage() > SEUIL_KM;

        List<Vehicule> grandKm = service.filtrerVehicules(flotte, kmDepasse);
        System.out.println("\n3. Véhicules avec km > " + SEUIL_KM + " :");
        grandKm.forEach(v -> System.out.println("   → " + v));

        // ── Comportement 4 : À réviser ? (km > seuil OU année < seuil) ─
        VehiculeTest<Vehicule> aReviser = v ->
                v.getKilometrage() > SEUIL_KM || v.getAnnee() < SEUIL_ANNEE;

        List<Vehicule> aRevoir = service.filtrerVehicules(flotte, aReviser);
        System.out.println("\n4. Véhicules à réviser :");
        aRevoir.forEach(v -> System.out.println("   → " + v));

        // ── Comportement 5 : Conducteur autorisé ? (permis commence par 'B')
        VehiculeTest<Conducteur> conducteurAutorise = c -> c.getNumeroPermis().startsWith("B");

        System.out.println("\n5. Conducteurs autorisés (permis commence par 'B') :");
        for (Conducteur c : new Conducteur[]{c1, c2}) {
            System.out.println("   → " + c.getNom() + " : " + conducteurAutorise.tester(c));
        }

        // ── Comportement 6 : Résumé véhicule (String)
        VehiculeTransformation<Vehicule, String> resume = v ->
                "[" + v.getImmatriculation() + "] " + v.getMarque() + " "
                + v.getModele() + " — " + v.getStatut();

        List<String> resumes = service.mapperVehicules(flotte, resume);
        System.out.println("6. Résumés :");
        resumes.forEach(r -> System.out.println("   → " + r));

        // ── Comportement 7 : Extraire immatriculation (String) 
        VehiculeTransformation<Vehicule, String> extraireImmat = v -> v.getImmatriculation();

        List<String> immatriculations = service.mapperVehicules(flotte, extraireImmat);
        System.out.println("\n7. Immatriculations :");
        immatriculations.forEach(i -> System.out.println("   → " + i));

        // ── Comportement 8 : Calculer âge du véhicule (int)
        VehiculeTransformation<Vehicule, Integer> calculerAge = v -> ANNEE_COURANTE - v.getAnnee();

        System.out.println("\n8. Âges des véhicules :");
        for (Vehicule v : flotte) {
            System.out.println("   → " + v.getImmatriculation() + " : "
                    + calculerAge.transformer(v) + " ans");
        }

        // ── Comportement 9 : Coût total d'un entretien (int)
        VehiculeTransformation<Entretien, Integer> coutTotal = e -> e.getCout() + TAXE_ENTRETIEN;

        System.out.println("\n9. Coût total des entretiens (avec taxe " + TAXE_ENTRETIEN + " FCFA) :");
        for (Entretien e : new Entretien[]{e1, e2}) {
            System.out.println("   → " + e.getDescription() + " : "
                    + coutTotal.transformer(e) + " FCFA");
        }
        // ── Comportement 10 : Marquer véhicule en révision
        VehiculeAction<Vehicule> marquerRevision = v -> v.setStatut("en_revision");

        System.out.println("10. Avant marquage : " + flotte.get(0).getStatut());
        service.appliquerSurVehicules(
            service.filtrerVehicules(flotte, estDisponible),
            marquerRevision
        );
        System.out.println("    Après marquage disponibles → en_revision :");
        flotte.forEach(v -> System.out.println("   → " + v.getImmatriculation() + " : " + v.getStatut()));

        // ── Comportement 11 : Augmenter kilométrage de X 
        final int AJOUT_KM = 500;
        VehiculeAction<Vehicule> augmenterKm = v -> v.setKilometrage(v.getKilometrage() + AJOUT_KM);

        System.out.println("\n11. Kilométrages avant ajout (" + AJOUT_KM + " km) :");
        flotte.forEach(v -> System.out.println("   → " + v.getImmatriculation() + " : " + v.getKilometrage()));
        service.appliquerSurVehicules(flotte, augmenterKm);
        System.out.println("    Kilométrages après :");
        flotte.forEach(v -> System.out.println("   → " + v.getImmatriculation() + " : " + v.getKilometrage()));

        // ── Comportement 12 : Terminer une location (mettre dateFin) 
        VehiculeAction<Location> terminerLocation = l -> l.setDateFin("2025-06-15");

        System.out.println("\n12. Avant : " + loc1);
        terminerLocation.agir(loc1);
        System.out.println("    Après : " + loc1);

        // ── Comportement 13 : Comparer par kilométrage (croissant)
        VehiculeComparaison<Vehicule> parKilometrage =
                (v1, v2) -> v1.getKilometrage() - v2.getKilometrage();

        System.out.println("13. Tri par kilométrage croissant :");
        service.trierVehicules(flotte, parKilometrage);
        flotte.forEach(v -> System.out.println("   → " + v.getImmatriculation() + " : " + v.getKilometrage() + " km"));

        // ── Comportement 14 : Comparer par immatriculation (alphabétique) 
        VehiculeComparaison<Vehicule> parImmatriculation =
                (v1, v2) -> v1.getImmatriculation().compareTo(v2.getImmatriculation());

        System.out.println("\n14. Tri par immatriculation alphabétique :");
        service.trierVehicules(flotte, parImmatriculation);
        flotte.forEach(v -> System.out.println("   → " + v.getImmatriculation()));

        service.ajouterVehicule(new Vehicule(5L, "DK-7890-IJ", "Ford", "Focus", 2018, 90000, "disponible"));
        System.out.println("\n15. Après ajout d'un nouveau véhicule :");
        flotte.forEach(v -> System.out.println("   → " + v));

        ParcAutoService service = new ParcAutoService();
Vehicule A = new Vehicule(1L, "AB-001", "Toyota", "Corolla", 2015, 120000, "disponible");

Vehicule v2 = new Vehicule(2L, "CD-002", "Renault", "Clio", 2020, 45000, "non_disponible");

Vehicule v3 = new Vehicule(3L, "EF-003", "Toyota", "Corolla", 2012, 200000, "disponible");

Vehicule v4 = new Vehicule(4L, "GH-004", "Ford", "Focus", 2018, 87000, "non_disponible");

service.ajouterVehicule(A);
service.ajouterVehicule(v2);
service.ajouterVehicule(v3);
service.ajouterVehicule(v4);

// Entretiens pour A et v3
service.ajouterEntretien(new Entretien("Vidange",45000, A));
service.ajouterEntretien(new Entretien("Freins", 85000, A));
service.ajouterEntretien(new Entretien("Courroie distribution", 120000, v3));

// --- 1. Kilométrage moyen ---
System.out.println("=== Kilométrage moyen ===");
System.out.printf("  %.0f km%n", service.kilometrageMoyen());

// --- 2. Nombre par état ---
System.out.println("\n=== Véhicules par état ===");
Map<Boolean, Long> parEtat = service.nombreParEtat();
System.out.println("  Disponibles     : " + parEtat.getOrDefault(true,  0L));
System.out.println("  Non disponibles : " + parEtat.getOrDefault(false, 0L));

// --- 3. Coût total par véhicule ---
System.out.println("\n=== Coût total d'entretien par véhicule ===");
service.coutEntretienParVehicule()
       .forEach((immat, total) ->
               System.out.printf("  %-10s → %d FCFA%n", immat, total));



    }
}

