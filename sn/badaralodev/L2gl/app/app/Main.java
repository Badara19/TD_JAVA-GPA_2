package sn.badaralodev.L2gl.app.app;

import sn.badaralodev.L2gl.app.model.*;
import sn.badaralodev.L2gl.app.service.ParcAutoService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        // ════════════════════════════════════════════════════════════
        // PARTIE 1 : FLOTTE DE TEST INITIALE (avec lambdas et filtres)
        // ════════════════════════════════════════════════════════════

        List<Vehicule> flotte = new ArrayList<>(Arrays.asList(
            new Vehicule(1L, "DK-1234-AB", "Toyota",  "Corolla", 2015, 120000, "disponible"),
            new Vehicule(2L, "DK-5678-CD", "Renault", "Clio",    2020,  32000, "en_panne"),
            new Vehicule(3L, "DK-9012-EF", "Peugeot", "508",     2012, 210000, "disponible"),
            new Vehicule(4L, "DK-3456-GH", "Honda",   "Civic",   2019,  78000, "loue")
        ));

        Conducteur c1 = new Conducteur(1L, "Moussa Diallo",  "B-20451");
        Conducteur c2 = new Conducteur(2L, "Fatou Seck",     "A-98321");

        Entretien e1 = new Entretien(1L, "Vidange + filtres",    45000, flotte.get(0));
        Entretien e2 = new Entretien(2L, "Remplacement plaquettes", 80000, flotte.get(1));

        Location loc1 = new Location(1L, flotte.get(3), c1, "2025-06-01", "2025-06-07");

        final int SEUIL_KM        = 100000;
        final int SEUIL_ANNEE     = 2016;
        final int TAXE_ENTRETIEN  = 5000;
        final int ANNEE_COURANTE  = 2025;

        // ── Comportement 1 : Véhicule disponible 
        VehiculeTest<Vehicule> estDisponible = v -> v.getStatut().toLowerCase().equals("disponible");
        List<Vehicule> disponibles = new ParcAutoService().filtrerVehicules(flotte, estDisponible);
        System.out.println("1. Véhicules disponibles :");
        disponibles.forEach(v -> System.out.println("   → " + v));

        // ── Comportement 2 : Véhicule en panne ?
        VehiculeTest<Vehicule> estEnPanne = v -> v.getStatut().equals("en_panne");
        List<Vehicule> enPanne = new ParcAutoService().filtrerVehicules(flotte, estEnPanne);
        System.out.println("\n2. Véhicules en panne :");
        enPanne.forEach(v -> System.out.println("   → " + v));

        // ── Comportement 3 : Kilométrage > seuil ?
        VehiculeTest<Vehicule> kmDepasse = v -> v.getKilometrage() > SEUIL_KM;
        List<Vehicule> grandKm = new ParcAutoService().filtrerVehicules(flotte, kmDepasse);
        System.out.println("\n3. Véhicules avec km > " + SEUIL_KM + " :");
        grandKm.forEach(v -> System.out.println("   → " + v));

        // ── Comportement 4 : À réviser ? (km > seuil OU année < seuil)
        VehiculeTest<Vehicule> aReviserTest = v ->
                v.getKilometrage() > SEUIL_KM || v.getAnnee() < SEUIL_ANNEE;
        List<Vehicule> aRevoir = new ParcAutoService().filtrerVehicules(flotte, aReviserTest);
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
        List<String> resumes = new ParcAutoService().mapperVehicules(flotte, resume);
        System.out.println("\n6. Résumés :");
        resumes.forEach(r -> System.out.println("   → " + r));

        // ── Comportement 7 : Extraire immatriculation (String) 
        VehiculeTransformation<Vehicule, String> extraireImmat = v -> v.getImmatriculation();
        List<String> immatriculations = new ParcAutoService().mapperVehicules(flotte, extraireImmat);
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
        System.out.println("\n10. Avant marquage : " + flotte.get(0).getStatut());
        new ParcAutoService().appliquerSurVehicules(
            new ParcAutoService().filtrerVehicules(flotte, estDisponible),
            marquerRevision
        );
        System.out.println("    Après marquage disponibles → en_revision :");
        flotte.forEach(v -> System.out.println("   → " + v.getImmatriculation() + " : " + v.getStatut()));

        // ── Comportement 11 : Augmenter kilométrage de X 
        final int AJOUT_KM = 500;
        VehiculeAction<Vehicule> augmenterKm = v -> v.setKilometrage(v.getKilometrage() + AJOUT_KM);
        System.out.println("\n11. Kilométrages avant ajout (" + AJOUT_KM + " km) :");
        flotte.forEach(v -> System.out.println("   → " + v.getImmatriculation() + " : " + v.getKilometrage()));
        new ParcAutoService().appliquerSurVehicules(flotte, augmenterKm);
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
        System.out.println("\n13. Tri par kilométrage croissant :");
        new ParcAutoService().trierVehicules(flotte, parKilometrage);
        flotte.forEach(v -> System.out.println("   → " + v.getImmatriculation() + " : " + v.getKilometrage() + " km"));

        // ── Comportement 14 : Comparer par immatriculation (alphabétique) 
        VehiculeComparaison<Vehicule> parImmatriculation =
                (v1, v2) -> v1.getImmatriculation().compareTo(v2.getImmatriculation());
        System.out.println("\n14. Tri par immatriculation alphabétique :");
        new ParcAutoService().trierVehicules(flotte, parImmatriculation);
        flotte.forEach(v -> System.out.println("   → " + v.getImmatriculation()));

        // ════════════════════════════════════════════════════════════
        // PARTIE 2 : SCÉNARIO ÉTAPE 14-15 (rapport + véhicules à réviser)
        // ════════════════════════════════════════════════════════════

        ParcAutoService service = new ParcAutoService();

        Vehicule A = new Vehicule(10L, "AB-001", "Toyota", "Corolla", 2015, 120000, "disponible");
        Vehicule v2 = new Vehicule(11L, "CD-002", "Renault", "Clio", 2020, 45000, "disponible");
        Vehicule v3 = new Vehicule(12L, "EF-003", "Toyota", "Corolla", 2012, 200000, "disponible");
        Vehicule v4 = new Vehicule(13L, "GH-004", "Ford", "Focus", 2018, 87000, "disponible");

        service.ajouterVehicule(A);
        service.ajouterVehicule(v2);
        service.ajouterVehicule(v3);
        service.ajouterVehicule(v4);

        // Entretiens
        service.ajouterEntretien(new Entretien(20L, "Vidange + filtres", 45000, A));
        service.ajouterEntretien(new Entretien(21L, "Freins", 85000, A));
        service.ajouterEntretien(new Entretien(22L, "Courroie distribution", 120000, v3));

        // 📊 Afficher le rapport
        System.out.println("\n\n=== ÉTAPE 14-15 : RAPPORT DES VÉHICULES ===");
        service.afficherRapport();

        // 🛠️ Afficher les véhicules à réviser
        System.out.println("\n=== VÉHICULES À RÉVISER ===");
        List<Vehicule> aReviserList = service.vehiculesAReviser();
        aReviserList.forEach(v -> System.out.println("   → " + v.getImmatriculation() + " - " + v.getMarque() + " - " + v.getKilometrage() + " km"));

        // 📈 Statistiques
        System.out.println("\n=== STATISTIQUES ===");
        System.out.printf("   Kilométrage moyen : %.0f km%n", service.kilometrageMoyen());
        System.out.println("   Top 3 kilométrage : " + service.top3Kilometrage());

        // 📋 Immatriculations triées
        System.out.println("\n=== IMMATRICULATIONS TRIÉES ===");
        service.immatriculationsTriees().forEach(i -> System.out.println("   → " + i));

        // 💰 Coût entretien par véhicule
        System.out.println("\n=== COÛT ENTRETIEN PAR VÉHICULE ===");
        service.coutEntretienParVehicule()
               .forEach((immat, total) -> System.out.printf("   %-10s → %d FCFA%n", immat, total));

        System.out.println("\n✅ Scénario intégral exécuté avec succès !");
    }
}