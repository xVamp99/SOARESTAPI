package metiers;

import entities.Option;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OptionBusiness {
    private static List<Option> options = new ArrayList<Option>();

    // Donnees de depart : executees UNE SEULE FOIS au chargement de la classe.
    // (Avant, ce code etait dans le constructeur : chaque "new OptionBusiness()"
    //  - donc chaque requete HTTP - remettait la liste a zero !)
    static {
        options.add(new Option(1, "Informatique", "Informatique", "M. Responsable Informatique", 30, 1, 30));
        options.add(new Option(2, "Mathématiques", "Mathématiques", "Mme Responsable Mathématiques", 25, 1, 25));
        options.add(new Option(3, "Physique", "Physique", "M. Responsable Physique", 20, 2, 20));
        options.add(new Option(4, "Infographie", "Infographie", "Mme Responsable Infographie", 15, 1, 15));
        options.add(new Option(5, "Chimie", "Chimie", "M. Responsable Chimie", 20, 2, 20));
    }

    public OptionBusiness() {
    }

    public Option getOptionByCode(int code) {
        for (Option option : options) {
            if (option.getCodeOption() == code)
                return option;
        }
        return null;
    }

    public boolean addOption(Option option) {
        return options.add(option);
    }

    public List<Option> getOptionsByDomaine(String domaine) {
        List<Option> liste = new ArrayList<Option>();
        for (Option option : options) {
            if (domaine != null && domaine.equalsIgnoreCase(option.getDomaine()))
                liste.add(option);
        }
        return liste;
    }

    public List<Option> getOptionsBySemestre(int semestre) {
        List<Option> liste = new ArrayList<Option>();
        for (Option option : options) {
            if (option.getSemestre() == semestre)
                liste.add(option);
        }
        return liste;
    }

    public boolean deleteOption(int code) {
        Iterator<Option> iterator = options.iterator();
        while (iterator.hasNext()) {
            Option option = iterator.next();
            if (option.getCodeOption() == code) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean updateOption(int code, Option updatedOption) {
        Option existing = getOptionByCode(code);
        if (existing == null) {
            return false;
        }
        // On modifie l'objet existant "en place" : les etudiants qui pointent
        // vers cette option voient ainsi automatiquement les nouvelles valeurs.
        existing.setLibelle(updatedOption.getLibelle());
        existing.setDomaine(updatedOption.getDomaine());
        existing.setResponsable(updatedOption.getResponsable());
        existing.setCredits(updatedOption.getCredits());
        existing.setSemestre(updatedOption.getSemestre());
        existing.setCapacite(updatedOption.getCapacite());
        return true;
    }

    public List<Option> getListeOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        OptionBusiness.options = options;
    }
}