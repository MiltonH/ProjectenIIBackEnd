/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import domain.Evaluatie;
import domain.EvaluatieFormulier;
import domain.Leerling;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Milton
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class LeerlingReader implements MessageBodyReader<Leerling>
{

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Leerling.class.isAssignableFrom(type);
    }

    @Override
    public Leerling readFrom(Class<Leerling> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        try (JsonReader in = Json.createReader(entityStream)) {
            JsonObject jsonUser = in.readObject();
            Leerling leerling = new Leerling();

            leerling.setVoornaam(jsonUser.getString("voornaam", null));
            leerling.setFamilienaam(jsonUser.getString("familienaam", null));
            leerling.setInschrijvingsNummer(jsonUser.getString("inschrijvingsnr", null));  
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                leerling.setLastEdit(df.parse(jsonUser.getString("lastEdit")));
            } catch (ParseException ex) {
                Logger.getLogger(LeerlingReader.class.getName()).log(Level.SEVERE, null, ex);
            }

            List<EvaluatieFormulier> evaluatieFormulieren = new ArrayList<>();
            JsonArray jsonForms = jsonUser.getJsonArray("evaluatieFormulieren");
            for (int i = 0; i < jsonForms.size(); i++) {
                JsonObject jsonForm = jsonForms.getJsonObject(i);
                EvaluatieFormulier formulier = new EvaluatieFormulier();

                //zithouding
                formulier.setZithoudingZithouding(Evaluatie.values()[jsonForm.getInt("zithoudingZithouding")]);
                formulier.setZithoudingGordel(Evaluatie.values()[jsonForm.getInt("zithoudingGordel")]);
                formulier.setZithoudingSpiegels(Evaluatie.values()[jsonForm.getInt("zithoudingSpiegels")]);
                formulier.setZithoudingHandrem(Evaluatie.values()[jsonForm.getInt("zithoudingHandrem")]);
                formulier.setZithoudingRec(Evaluatie.values()[jsonForm.getInt("zithoudingRec")]);

                JsonArray jsonzithoudingAnderee = jsonForm.getJsonArray("zithoudingAndere");
                List<String> zithoudingAndere = new ArrayList<>();
                if (jsonzithoudingAnderee != null) {
                    for (JsonString opm : jsonzithoudingAnderee.getValuesAs(JsonString.class)) {
                        zithoudingAndere.add(opm.getString());
                    }
                }
                formulier.setZithoudingAndere(zithoudingAndere);
                //
                //koppeling
                formulier.setKoppelingRec(Evaluatie.values()[jsonForm.getInt("koppelingRec")]);
                formulier.setKoppelingDosering(Evaluatie.values()[jsonForm.getInt("koppelingDosering")]);
                formulier.setKoppelingVolledig(Evaluatie.values()[jsonForm.getInt("koppelingVolledig")]);
                formulier.setKoppelingBediening(Evaluatie.values()[jsonForm.getInt("koppelingBediening")]);
                formulier.setKoppelingVoetaf(Evaluatie.values()[jsonForm.getInt("koppelingVoetaf")]);

                JsonArray jsonkoppelingBedieningAndere = jsonForm.getJsonArray("koppelingBedieningAndere");
                List<String> koppelingBedieningAndere = new ArrayList<>();
                if (jsonkoppelingBedieningAndere != null) {
                    for (JsonString opm : jsonkoppelingBedieningAndere.getValuesAs(JsonString.class)) {
                        koppelingBedieningAndere.add(opm.getString());
                    }
                }
                formulier.setKoppelingBedieningAndere(koppelingBedieningAndere);

                formulier.setKoppelingOnnodig(Evaluatie.values()[jsonForm.getInt("koppelingOnnodig")]);
                formulier.setKoppelingBocht(Evaluatie.values()[jsonForm.getInt("koppelingBocht")]);
                formulier.setKoppelingGebruik(Evaluatie.values()[jsonForm.getInt("koppelingGebruik")]);

                JsonArray jsonkoppelingGebruikAndere = jsonForm.getJsonArray("koppelingGebruikAndere");
                List<String> koppelingGebruikAndere = new ArrayList<>();
                if (jsonkoppelingGebruikAndere != null) {
                    for (JsonString opm : jsonkoppelingGebruikAndere.getValuesAs(JsonString.class)) {
                        koppelingGebruikAndere.add(opm.getString());
                    }
                }
                formulier.setKoppelingGebruikAndere(koppelingGebruikAndere);
                //
                //rem
                formulier.setRemDosering(Evaluatie.values()[jsonForm.getInt("remDosering")]);
                formulier.setRemVolgorde(Evaluatie.values()[jsonForm.getInt("remVolgorde")]);
                formulier.setRemRec(Evaluatie.values()[jsonForm.getInt("remRec")]);
                formulier.setRemTeLaat(Evaluatie.values()[jsonForm.getInt("remTeLaat")]);
                formulier.setRemBediening(Evaluatie.values()[jsonForm.getInt("remBediening")]);
                formulier.setRemGebruik(Evaluatie.values()[jsonForm.getInt("remGebruik")]);

                JsonArray jsonremGebruikAndere = jsonForm.getJsonArray("remGebruikAndere");
                List<String> remGebruikAndere = new ArrayList<>();
                if (jsonremGebruikAndere != null) {
                    for (JsonString opm : jsonremGebruikAndere.getValuesAs(JsonString.class)) {
                        remGebruikAndere.add(opm.getString());
                    }
                }
                formulier.setRemGebruikAndere(remGebruikAndere);
                //
                //stuur
                formulier.setStuurRec(Evaluatie.values()[jsonForm.getInt("stuurRec")]);
                formulier.setStuurDosering(Evaluatie.values()[jsonForm.getInt("stuurDosering")]);
                formulier.setStuurHouding(Evaluatie.values()[jsonForm.getInt("stuurHouding")]);

                JsonArray jsonstuurAndere = jsonForm.getJsonArray("stuurAndere");
                List<String> stuurAndere = new ArrayList<>();
                if (jsonstuurAndere != null) {
                    for (JsonString opm : jsonstuurAndere.getValuesAs(JsonString.class)) {
                        stuurAndere.add(opm.getString());
                    }
                }
                formulier.setStuurAndere(stuurAndere);
                //
                //schakel
                formulier.setSchakelRec(Evaluatie.values()[jsonForm.getInt("schakelRec")]);
                formulier.setSchakelBediening(Evaluatie.values()[jsonForm.getInt("schakelBediening")]);
                formulier.setSchakelDosering(Evaluatie.values()[jsonForm.getInt("schakelDosering")]);

                JsonArray jsonschakelBedieningAndere = jsonForm.getJsonArray("schakelBedieningAndere");
                List<String> schakelBedieningAndere = new ArrayList<>();
                if (jsonschakelBedieningAndere != null) {
                    for (JsonString opm : jsonschakelBedieningAndere.getValuesAs(JsonString.class)) {
                        schakelBedieningAndere.add(opm.getString());
                    }
                }
                formulier.setSchakelBedieningAndere(schakelBedieningAndere);

                formulier.setSchakelGebruik(Evaluatie.values()[jsonForm.getInt("schakelGebruik")]);
                formulier.setSchakelAangepast(Evaluatie.values()[jsonForm.getInt("schakelAangepast")]);
                formulier.setSchakelMotorRem(Evaluatie.values()[jsonForm.getInt("schakelMotorRem")]);

                JsonArray jsonschakelGebruikAndere = jsonForm.getJsonArray("schakelGebruikAndere");
                List<String> schakelGebruikAndere = new ArrayList<>();
                if (jsonschakelGebruikAndere != null) {
                    for (JsonString opm : jsonschakelGebruikAndere.getValuesAs(JsonString.class)) {
                        schakelGebruikAndere.add(opm.getString());
                    }
                }
                formulier.setSchakelGebruikAndere(schakelGebruikAndere);
                //
                //kijk
                formulier.setKijkVergewis(Evaluatie.values()[jsonForm.getInt("kijkVergewis")]);
                formulier.setKijkSpiegels(Evaluatie.values()[jsonForm.getInt("kijkSpiegels")]);
                formulier.setKijkDodeHoek(Evaluatie.values()[jsonForm.getInt("kijkDodeHoek")]);
                formulier.setKijkVer(Evaluatie.values()[jsonForm.getInt("kijkVer")]);
                formulier.setKijkSelecteren(Evaluatie.values()[jsonForm.getInt("kijkSelecteren")]);
                formulier.setKijkRec(Evaluatie.values()[jsonForm.getInt("kijkRec")]);

                JsonArray jsonkijkAndere = jsonForm.getJsonArray("kijkAndere");
                List<String> kijkAndere = new ArrayList<>();
                if (jsonkijkAndere != null) {
                    for (JsonString opm : jsonkijkAndere.getValuesAs(JsonString.class)) {
                        kijkAndere.add(opm.getString());
                    }
                }
                formulier.setKijkAndere(kijkAndere);
                //
                //helling
                formulier.setHellingB(Evaluatie.values()[jsonForm.getInt("hellingB")]);
                formulier.setHellingH(Evaluatie.values()[jsonForm.getInt("hellingH")]);
                formulier.setHellingV(Evaluatie.values()[jsonForm.getInt("hellingV")]);
                //
                //parkeren
                formulier.setParkerenTussen(Evaluatie.values()[jsonForm.getInt("parkerenTussen")]);
                formulier.setParkerenAchter(Evaluatie.values()[jsonForm.getInt("parkerenAchter")]);
                formulier.setParkerenLinks(Evaluatie.values()[jsonForm.getInt("parkerenLinks")]);
                formulier.setKeren(Evaluatie.values()[jsonForm.getInt("keren")]);
                //
                //garage
                formulier.setGarageEen(Evaluatie.values()[jsonForm.getInt("garageEen")]);
                formulier.setGarageDrie(Evaluatie.values()[jsonForm.getInt("garageDrie")]);
                formulier.setGarageAchterwaarts(Evaluatie.values()[jsonForm.getInt("garageAchterwaarts")]);
                formulier.setAchteruit(Evaluatie.values()[jsonForm.getInt("achteruit")]);
                formulier.setStuurOefeningen(Evaluatie.values()[jsonForm.getInt("stuurOefeningen")]);
                //
                //verkeerstechniek
                formulier.setRichtingAanwijzers(Evaluatie.values()[jsonForm.getInt("richtingAanwijzers")]);
                formulier.setOpenbareWeg(Evaluatie.values()[jsonForm.getInt("openbareWeg")]);
                formulier.setVoorrang(Evaluatie.values()[jsonForm.getInt("voorrang")]);
                formulier.setVerkeerstekens(Evaluatie.values()[jsonForm.getInt("verkeerstekens")]);
                formulier.setSnelheid(Evaluatie.values()[jsonForm.getInt("snelheid")]);
                formulier.setVolgafstand(Evaluatie.values()[jsonForm.getInt("volgafstand")]);
                formulier.setInhalen(Evaluatie.values()[jsonForm.getInt("inhalen")]);
                formulier.setKruisen(Evaluatie.values()[jsonForm.getInt("kruisen")]);
                formulier.setLinksaf(Evaluatie.values()[jsonForm.getInt("linksaf")]);
                formulier.setRechtsaf(Evaluatie.values()[jsonForm.getInt("rechtsaf")]);

                JsonArray jsonrichtingAanwijzersAndere = jsonForm.getJsonArray("richtingAanwijzersAndere");
                List<String> richtingAanwijzersAndere = new ArrayList<>();
                if (jsonkijkAndere != null) {
                    for (JsonString opm : jsonkijkAndere.getValuesAs(JsonString.class)) {
                        richtingAanwijzersAndere.add(opm.getString());
                    }
                }
                formulier.setRichtingAanwijzersAndere(richtingAanwijzersAndere);

                JsonArray jsonopenbareWegAndere = jsonForm.getJsonArray("openbareWegAndere");
                List<String> openbareWegAndere = new ArrayList<>();
                if (jsonopenbareWegAndere != null) {
                    for (JsonString opm : jsonopenbareWegAndere.getValuesAs(JsonString.class)) {
                        openbareWegAndere.add(opm.getString());
                    }
                }
                formulier.setOpenbareWegAndere(openbareWegAndere);

                JsonArray jsonvoorrangAndere = jsonForm.getJsonArray("voorrangAndere");
                List<String> voorrangAndere = new ArrayList<>();
                if (jsonvoorrangAndere != null) {
                    for (JsonString opm : jsonvoorrangAndere.getValuesAs(JsonString.class)) {
                        voorrangAndere.add(opm.getString());
                    }
                }
                formulier.setVoorrangAndere(voorrangAndere);

                JsonArray jsonverkeerstekensAndere = jsonForm.getJsonArray("verkeerstekensAndere");
                List<String> verkeerstekensAndere = new ArrayList<>();
                if (jsonverkeerstekensAndere != null) {
                    for (JsonString opm : jsonverkeerstekensAndere.getValuesAs(JsonString.class)) {
                        verkeerstekensAndere.add(opm.getString());
                    }
                }
                formulier.setVerkeerstekensAndere(verkeerstekensAndere);

                JsonArray jsonsnelheidAndere = jsonForm.getJsonArray("snelheidAndere");
                List<String> snelheidAndere = new ArrayList<>();
                if (jsonsnelheidAndere != null) {
                    for (JsonString opm : jsonsnelheidAndere.getValuesAs(JsonString.class)) {
                        snelheidAndere.add(opm.getString());
                    }
                }
                formulier.setSnelheidAndere(snelheidAndere);

                JsonArray jsonvolgafstandAndere = jsonForm.getJsonArray("volgafstandAndere");
                List<String> volgafstandAndere = new ArrayList<>();
                if (jsonvolgafstandAndere != null) {
                    for (JsonString opm : jsonvolgafstandAndere.getValuesAs(JsonString.class)) {
                        volgafstandAndere.add(opm.getString());
                    }
                }
                formulier.setVolgafstandAndere(volgafstandAndere);

                JsonArray jsoninhalenAndere = jsonForm.getJsonArray("inhalenAndere");
                List<String> inhalenAndere = new ArrayList<>();
                if (jsoninhalenAndere != null) {
                    for (JsonString opm : jsoninhalenAndere.getValuesAs(JsonString.class)) {
                        inhalenAndere.add(opm.getString());
                    }
                }
                formulier.setInhalenAndere(inhalenAndere);

                JsonArray jsonkruisenAndere = jsonForm.getJsonArray("kruisenAndere");
                List<String> kruisenAndere = new ArrayList<>();
                if (jsonkruisenAndere != null) {
                    for (JsonString opm : jsonkruisenAndere.getValuesAs(JsonString.class)) {
                        kruisenAndere.add(opm.getString());
                    }
                }
                formulier.setKruisenAndere(kruisenAndere);

                JsonArray jsonlinksafAndere = jsonForm.getJsonArray("linksafAndere");
                List<String> linksafAndere = new ArrayList<>();
                if (jsonlinksafAndere != null) {
                    for (JsonString opm : jsonlinksafAndere.getValuesAs(JsonString.class)) {
                        linksafAndere.add(opm.getString());
                    }
                }
                formulier.setLinksafAndere(linksafAndere);

                JsonArray jsonrechtsafAndere = jsonForm.getJsonArray("rechtsafAndere");
                List<String> rechtsafAndere = new ArrayList<>();
                if (jsonrechtsafAndere != null) {
                    for (JsonString opm : jsonrechtsafAndere.getValuesAs(JsonString.class)) {
                        rechtsafAndere.add(opm.getString());
                    }
                }
                formulier.setRechtsafAndere(rechtsafAndere);
                //
                //hoofdscherm
                formulier.setRotonde(Evaluatie.values()[jsonForm.getInt("rotonde")]);
                formulier.setRijstroken(Evaluatie.values()[jsonForm.getInt("rijstroken")]);
                formulier.setStad(Evaluatie.values()[jsonForm.getInt("stad")]);
                formulier.setAutosnelweg(Evaluatie.values()[jsonForm.getInt("autosnelweg")]);
                formulier.setSchakelaars(Evaluatie.values()[jsonForm.getInt("schakelaars")]);
                formulier.setVloeistoffen(Evaluatie.values()[jsonForm.getInt("vloeistoffen")]);
                formulier.setBanden(Evaluatie.values()[jsonForm.getInt("banden")]);
                formulier.setTanken(Evaluatie.values()[jsonForm.getInt("tanken")]);
                formulier.setGps(Evaluatie.values()[jsonForm.getInt("gps")]);
                formulier.setStop(Evaluatie.values()[jsonForm.getInt("stop")]);
                
                formulier.setNiveau(jsonForm.getInt("niveau"));
                
                JsonArray jsonattitude = jsonForm.getJsonArray("attitude");
                List<String> attitude = new ArrayList<>();
                if (jsonattitude != null) {
                    for (JsonString opm : jsonattitude.getValuesAs(JsonString.class)) {
                        attitude.add(opm.getString());
                    }
                }
                formulier.setAttitude(attitude);
                
                JsonArray jsonopmerkingen = jsonForm.getJsonArray("opmerkingen");
                List<String> opmerkingen = new ArrayList<>();
                if (jsonopmerkingen != null) {
                    for (JsonString opm : jsonopmerkingen.getValuesAs(JsonString.class)) {
                        opmerkingen.add(opm.getString());
                    }
                }
                formulier.setOpmerkingen(opmerkingen);
                
                //

                leerling.getEvaluatieFormulieren().add(formulier);

            }

            return leerling;
        } catch (JsonException | ClassCastException ex) {
            throw new BadRequestException("Invalid JSON");
        }
    }
}
