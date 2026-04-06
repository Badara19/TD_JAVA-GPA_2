package sn.badaralodev.L2gl.app.service;

import sn.badaralodev.L2gl.app.interfaces.IParcAutoService;
import sn.badaralodev.L2gl.app.model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;



public class ParcAutoService implements IParcAutoService {
    
    private List<Vehicule> vehicules;
    private Map<String, Vehicule> indexParImmat;
    private Map<Long, List<Entretien>> entretiensParVehiculeId;
    

    public ParcAutoService() {
        this.vehicules      = new ArrayList<>();
        this.indexParImmat  = new HashMap<>();
        this.entretiensParVehiculeId = new HashMap<>();
    }

    public List<Vehicule> filtrerVehicules(List<Vehicule> src, VehiculeTest<Vehicule> regle) {
        List<Vehicule> resultat = new ArrayList<>();
        for (Vehicule v : src) {
            if (regle.tester(v)) {
                resultat.add(v);
            }
        }
        return resultat;
    }

    
    public List<String> mapperVehicules(List<Vehicule> src, VehiculeTransformation<Vehicule, String> f) {
        List<String> resultat = new ArrayList<>();
        for (Vehicule v : src) {
            resultat.add(f.transformer(v));
        }
        return resultat;
    }


    public void appliquerSurVehicules(List<Vehicule> src, VehiculeAction<Vehicule> action) {
        for (Vehicule v : src) {
            action.agir(v);
        }
    }


    public void trierVehicules(List<Vehicule> src, VehiculeComparaison<Vehicule> cmp) {
        // Tri à bulles classique — pas de Stream, pas de Collections.sort
        int n = src.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (cmp.comparer(src.get(j), src.get(j + 1)) > 0) {
                    Vehicule temp = src.get(j);
                    src.set(j, src.get(j + 1));
                    src.set(j + 1, temp);
                }
            }
        }
    }

    public void ajouterVehicule(Vehicule v) {
        if(v != null && !indexParImmat.containsKey(v.getImmatriculation())) {
            vehicules.add(v);
            indexParImmat.put(v.getImmatriculation(), v);
            System.out.println("Véhicule ajouté : " + v.getImmatriculation());
        }else{ 
            System.out.println("Véhicule déjà existant ou null : " + (v != null ? v.getImmatriculation() : "null"));
        } 
    }
    public void supprimerVehicule(String immat){
        if(immat!=null && indexParImmat.containsKey(immat)){
            Vehicule v = indexParImmat.get(immat);
            vehicules.remove(v);
            indexParImmat.remove(immat);
            System.out.println("Véhicule supprimé : " + immat);
        }else{
            System.out.println("Véhicule non trouvé ou null : " + immat);
        }
    }

    public Vehicule rechercher(String immat){
        if(immat !=null && indexParImmat.containsKey(immat)){
            return indexParImmat.get(immat);
        }else{
            System.out.println("Véhicule non trouvé ou null : " + immat);
            return null;
        }
    }

    public Set<Vehicule> vehiculesUniques(){
        return new HashSet<>(vehicules);
    }

    public List<Entretien> getEntretiens(Long vehiculeId) {
    if (vehiculeId == null) return Collections.emptyList();
    return entretiensParVehiculeId.getOrDefault(vehiculeId, Collections.emptyList());
}

public boolean ajouterEntretien(Entretien e) {
    if (e == null || e.getVehicule() == null) {
        System.out.println("Entretien invalide.");
        return false;
    }

    String immat = e.getVehicule().getImmatriculation();

    if (!indexParImmat.containsKey(immat)) {
        System.out.println("Véhicule pour entretien non trouvé : " + immat);
        return false;
    }

    Vehicule v = indexParImmat.get(immat);

    // ✓ Stable et unique
    entretiensParVehiculeId.computeIfAbsent(v.getId(), k -> new ArrayList<>()).add(e);
    System.out.println("Entretien ajouté pour véhicule : " + immat);
    return true;
}
    public List<Vehicule> vehiculesDisponibles() {
        return vehicules.stream()
                .filter(v -> v.getStatut().equals("disponible"))    
                .collect(Collectors.toList());
    }
    public List<String> immatriculationsTriees() {
    return vehicules.stream()
            .map(Vehicule::getImmatriculation)    // Vehicule → String
            .sorted()                             // ordre alphabétique naturel
            .collect(Collectors.toList());
}
    public List<Vehicule> top3Kilometrage() {
    return vehicules.stream()
            .sorted((a, b) -> b.getKilometrage() - a.getKilometrage()) // décroissant
            .limit(3)                             // garde les 3 premiers
            .collect(Collectors.toList());
}
    public double kilometrageMoyen() {
        return vehicules.stream()
                .mapToInt(Vehicule::getKilometrage)   // IntStream pour les opérations numériques
                .average()                             // retourne OptionalDouble
                .orElse(0.0);                          // liste vide → 0 plutôt que crash
    }
    public Map<Boolean, Long> nombreParEtat() {
    return vehicules.stream()
            .collect(Collectors.groupingBy(
                    v -> v.getStatut().equals("disponible"),    // critère de regroupement
                    Collectors.counting()          // combien dans chaque groupe
            ));
}
public Map<String, Integer> coutEntretienParVehicule() {
    return entretiensParVehiculeId.entrySet().stream()
            .collect(Collectors.toMap(
                    entry -> indexParImmat.entrySet().stream()    // retrouver l'immat depuis l'id
                                 .filter(e -> e.getValue().getId().equals(entry.getKey()))
                                 .map(Map.Entry::getKey)
                                 .findFirst()
                                 .orElse("id=" + entry.getKey()),
                    entry -> entry.getValue().stream()
                                 .collect(Collectors.summingInt(Entretien::getCout))
            ));
}

}