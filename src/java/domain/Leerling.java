/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Milton
 */
@Entity
@NamedQuery(name = "Leerling.findAll", query = "SELECT l FROM Leerling l")
public class Leerling
{

    private String familienaam;

    private String voornaam;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date LastEdit;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<EvaluatieFormulier> evaluatieFormulieren;

    @Id
    private String inschrijvingsNummer;

    public Leerling() {
        this.evaluatieFormulieren = new ArrayList<>();
    }

    
    
    public String getInschrijvingsNummer() {
        return inschrijvingsNummer;
    }

    public void setInschrijvingsNummer(String inschrijvingsNummer) {
        this.inschrijvingsNummer = inschrijvingsNummer;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public void setFamilienaam(String familienaam) {
        this.familienaam = familienaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public List<EvaluatieFormulier> getEvaluatieFormulieren() {
        return evaluatieFormulieren;
    }

    public void setEvaluatieFormulieren(List<EvaluatieFormulier> evaluatieFormulieren) {
        this.evaluatieFormulieren = evaluatieFormulieren;
    }

    public Date getLastEdit() {
        return LastEdit;
    }

    public void setLastEdit(Date LastEdit) {
        this.LastEdit = LastEdit;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.inschrijvingsNummer);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Leerling other = (Leerling) obj;
        if (!Objects.equals(this.inschrijvingsNummer, other.inschrijvingsNummer)) {
            return false;
        }
        return true;
    }

}
