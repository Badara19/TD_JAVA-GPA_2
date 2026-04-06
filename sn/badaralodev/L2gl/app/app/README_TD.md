# TP Java — Parc Auto
## Étape 2 : Regroupement des comportements en signatures

---

### Rappel des 4 paquets (contrats)

| Paquet | Description | Interface Java |
|--------|-------------|----------------|
| Paquet 1 | Prend 1 objet → retourne `boolean` | `Predicate<T>` |
| Paquet 2 | Prend 1 objet → retourne une valeur `R` | `Function<T, R>` |
| Paquet 3 | Prend 1 objet → ne retourne rien (`void`) | `Consumer<T>` |
| Paquet 4 | Prend 2 objets → retourne un `int` (ordre) | `Comparator<T>` |

---

### Tableau : comportement → groupe → signature pressentie

| # comportement | Libellé | Groupe | Signature pressentie |
|----------------|---------|--------|----------------------|
| 1 | Véhicule disponible ? | Paquet 1 — Predicate | `boolean test(Vehicule v)` |
| 2 | Véhicule en panne ? | Paquet 1 — Predicate | `boolean test(Vehicule v)` |
| 3 | Kilométrage > seuil ? | Paquet 1 — Predicate | `boolean test(Vehicule v)` |
| 4 | Véhicule à réviser : (km > seuil) OU (année < seuilAnnée) | Paquet 1 — Predicate | `boolean test(Vehicule v)` |
| 5 | Conducteur autorisé : permis correspond à un format donné | Paquet 1 — Predicate | `boolean test(Conducteur c)` |
| 6 | Résumé véhicule (String) | Paquet 2 — Function | `String apply(Vehicule v)` |
| 7 | Extraire immatriculation (String) | Paquet 2 — Function | `String apply(Vehicule v)` |
| 8 | Calculer âge du véhicule (int) | Paquet 2 — Function | `int apply(Vehicule v)` ou `Integer apply(Vehicule v)` |
| 9 | Coût total d'un entretien (int) | Paquet 2 — Function | `int apply(Entretien e)` ou `Integer apply(Entretien e)` |
| 10 | Marquer véhicule en révision | Paquet 3 — Consumer | `void accept(Vehicule v)` |
| 11 | Augmenter kilométrage d'un véhicule de X | Paquet 3 — Consumer | `void accept(Vehicule v)` |
| 12 | Terminer une location (mettre dateFin) | Paquet 3 — Consumer | `void accept(Location l)` |
| 13 | Comparer deux véhicules par kilométrage (ordre croissant) | Paquet 4 — Comparator | `int compare(Vehicule v1, Vehicule v2)` |
| 14 | Comparer deux véhicules par immatriculation (ordre alphabétique) | Paquet 4 — Comparator | `int compare(Vehicule v1, Vehicule v2)` |

---

### Observation importante : le rôle du paramètre

Remarque que la signature change selon **l'objet concerné**, pas selon le groupe :

- Comportements 1–4 → agissent sur un `Vehicule`
- Comportement 5 → agit sur un `Conducteur`
- Comportements 6–8 → lisent un `Vehicule`
- Comportement 9 → lit un `Entretien`
- Comportements 10–11 → modifient un `Vehicule`
- Comportement 12 → modifie une `Location`
- Comportements 13–14 → comparent deux `Vehicule`

Le **groupe** (Paquet 1/2/3/4) est déterminé par **ce que retourne** le comportement.
Le **type paramètre T** est déterminé par **l'objet sur lequel** il s'applique.

---

### Mini-conclusion

Pourquoi 14 comportements peuvent être couverts par seulement ~4 interfaces fonctionnelles ?

Parce que ce qui distingue une interface fonctionnelle, c'est uniquement la **forme du contrat** :
ce qu'elle reçoit en entrée et ce qu'elle retourne en sortie.
Or, quelles que soient les règles métier (disponibilité, panne, kilométrage...),
elles tombent toutes dans l'une de ces quatre formes possibles :
tester, transformer, agir, ou comparer.
Java exploite cette observation en proposant des interfaces génériques paramétrées par `<T>` et `<R>`,
ce qui permet de réutiliser le même "moule" pour des dizaines de comportements différents,
en changeant uniquement la logique interne (le lambda), pas la structure du contrat.
