package sn.badaralodev.L2gl.app.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import sn.badaralodev.L2gl.app.model.Entretien;
import sn.badaralodev.L2gl.app.model.Vehicule;
import sn.badaralodev.L2gl.app.model.VehiculeAction;
import sn.badaralodev.L2gl.app.model.VehiculeComparaison;
import sn.badaralodev.L2gl.app.model.VehiculeTest;
import sn.badaralodev.L2gl.app.model.VehiculeTransformation;

public interface IParcAutoService {
    List<Vehicule> filtrerVehicules(List<Vehicule> src, VehiculeTest<Vehicule> regle);
    List<String> mapperVehicules(List<Vehicule> src, VehiculeTransformation<Vehicule, String> f);
    void appliquerSurVehicules(List<Vehicule> src, VehiculeAction<Vehicule> action);
    void trierVehicules(List<Vehicule> src, VehiculeComparaison<Vehicule> cmp);
    void ajouterVehicule(Vehicule v);
    void supprimerVehicule(String immat);
    boolean ajouterEntretien(Entretien e);
    Set<Vehicule> vehiculesUniques();
    List<Entretien> getEntretiens(Long vehiculeId);
    Optional<Vehicule> rechercher(String immat);
}
