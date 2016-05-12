/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import domain.EvaluatieFormulier;
import domain.Leerling;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Milton
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class LeerlingListWriter implements MessageBodyWriter<List<Leerling>>
{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (!List.class.isAssignableFrom(type)) {
            return false;
        }
        if (genericType instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
            return arguments.length == 1 && arguments[0].equals(Leerling.class);
        } else {
            return false;
        }
    }

    @Override
    public long getSize(List<Leerling> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(List<Leerling> leerlingen, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonArrayBuilder jsonLeerlingen = Json.createArrayBuilder();

        for (Leerling l : leerlingen) {
            JsonObjectBuilder JsonLeerling = Json.createObjectBuilder();
            JsonLeerling.add("familienaam", l.getFamilienaam());
            JsonLeerling.add("voornaam", l.getVoornaam());
            JsonLeerling.add("inschrijvingsnr", l.getInschrijvingsNummer());
            
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            JsonLeerling.add("lastEdit", df.format(l.getLastEdit()));

            JsonArrayBuilder jsonEvals = Json.createArrayBuilder();

            for (EvaluatieFormulier EvalForm : l.getEvaluatieFormulieren()) {
                JsonObjectBuilder JsonFormulier = Json.createObjectBuilder();

                //zithouding
                JsonFormulier.add("zithoudingZithouding", EvalForm.getZithoudingZithouding().ordinal());
                JsonFormulier.add("zithoudingGordel", EvalForm.getZithoudingGordel().ordinal());
                JsonFormulier.add("zithoudingSpiegels", EvalForm.getZithoudingSpiegels().ordinal());
                JsonFormulier.add("zithoudingHandrem", EvalForm.getZithoudingHandrem().ordinal());
                JsonFormulier.add("zithoudingRec", EvalForm.getZithoudingRec().ordinal());
                JsonArrayBuilder jsonzithoudingAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getZithoudingAndere()) {
                    jsonzithoudingAndere.add(opm);
                }
                JsonFormulier.add("zithoudingAndere", jsonzithoudingAndere);
                //
                //koppeling
                JsonFormulier.add("koppelingRec", EvalForm.getKoppelingRec().ordinal());
                JsonFormulier.add("koppelingDosering", EvalForm.getKoppelingDosering().ordinal());
                JsonFormulier.add("koppelingVolledig", EvalForm.getKoppelingVolledig().ordinal());
                JsonFormulier.add("koppelingBediening", EvalForm.getKoppelingBediening().ordinal());
                JsonFormulier.add("koppelingVoetaf", EvalForm.getKoppelingVoetaf().ordinal());
                JsonArrayBuilder jsonkoppelingBedieningAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getKoppelingBedieningAndere()) {
                    jsonkoppelingBedieningAndere.add(opm);
                }
                JsonFormulier.add("koppelingBedieningAndere", jsonkoppelingBedieningAndere);

                JsonFormulier.add("koppelingOnnodig", EvalForm.getKoppelingVolledig().ordinal());
                JsonFormulier.add("koppelingBocht", EvalForm.getKoppelingBediening().ordinal());
                JsonFormulier.add("koppelingGebruik", EvalForm.getKoppelingVoetaf().ordinal());
                JsonArrayBuilder jsonkoppelingGebruikAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getKoppelingGebruikAndere()) {
                    jsonkoppelingGebruikAndere.add(opm);
                }
                JsonFormulier.add("koppelingBedieningAndere", jsonkoppelingGebruikAndere);
                //
                //rem
                JsonFormulier.add("remDosering", EvalForm.getRemDosering().ordinal());
                JsonFormulier.add("remVolgorde", EvalForm.getRemVolgorde().ordinal());
                JsonFormulier.add("remRec", EvalForm.getRemRec().ordinal());
                JsonFormulier.add("remTeLaat", EvalForm.getRemTeLaat().ordinal());
                JsonFormulier.add("remBediening", EvalForm.getRemBediening().ordinal());
                JsonFormulier.add("remGebruik", EvalForm.getRemGebruik().ordinal());
                JsonArrayBuilder jsonremGebruikAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getRemGebruikAndere()) {
                    jsonremGebruikAndere.add(opm);
                }
                JsonFormulier.add("remGebruikAndere", jsonremGebruikAndere);
                //
                //stuur
                JsonFormulier.add("stuurRec", EvalForm.getStuurRec().ordinal());
                JsonFormulier.add("stuurDosering", EvalForm.getStuurDosering().ordinal());
                JsonFormulier.add("stuurHouding", EvalForm.getStuurHouding().ordinal());
                JsonArrayBuilder jsonstuurAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getStuurAndere()) {
                    jsonstuurAndere.add(opm);
                }
                JsonFormulier.add("stuurAndere", jsonstuurAndere);
                //
                //schakel
                JsonFormulier.add("schakelRec", EvalForm.getSchakelRec().ordinal());
                JsonFormulier.add("schakelBediening", EvalForm.getSchakelBediening().ordinal());
                JsonFormulier.add("schakelDosering", EvalForm.getSchakelDosering().ordinal());
                JsonArrayBuilder jsonschakelBedieningAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getSchakelBedieningAndere()) {
                    jsonschakelBedieningAndere.add(opm);
                }
                JsonFormulier.add("schakelBedieningAndere", jsonschakelBedieningAndere);

                JsonFormulier.add("schakelGebruik", EvalForm.getSchakelGebruik().ordinal());
                JsonFormulier.add("schakelAangepast", EvalForm.getSchakelAangepast().ordinal());
                JsonFormulier.add("schakelMotorRem", EvalForm.getSchakelMotorRem().ordinal());
                JsonArrayBuilder jsonschakelGebruikAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getSchakelGebruikAndere()) {
                    jsonschakelGebruikAndere.add(opm);
                }
                JsonFormulier.add("schakelGebruikAndere", jsonschakelGebruikAndere);
                //
                //kijk
                JsonFormulier.add("kijkVergewis", EvalForm.getKijkVergewis().ordinal());
                JsonFormulier.add("kijkSpiegels", EvalForm.getKijkSpiegels().ordinal());
                JsonFormulier.add("kijkDodeHoek", EvalForm.getKijkDodeHoek().ordinal());
                JsonFormulier.add("kijkVer", EvalForm.getKijkVer().ordinal());
                JsonFormulier.add("kijkSelecteren", EvalForm.getKijkSelecteren().ordinal());
                JsonFormulier.add("kijkRec", EvalForm.getKijkRec().ordinal());
                JsonArrayBuilder jsonkijkAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getKijkAndere()) {
                    jsonkijkAndere.add(opm);
                }
                JsonFormulier.add("kijkAndere", jsonkijkAndere);
                //
                //helling
                JsonFormulier.add("hellingB", EvalForm.getHellingB().ordinal());
                JsonFormulier.add("hellingH", EvalForm.getHellingH().ordinal());
                JsonFormulier.add("hellingV", EvalForm.getHellingV().ordinal());
                //
                //parkeren
                JsonFormulier.add("parkerenTussen", EvalForm.getParkerenTussen().ordinal());
                JsonFormulier.add("parkerenAchter", EvalForm.getParkerenAchter().ordinal());
                JsonFormulier.add("parkerenLinks", EvalForm.getParkerenLinks().ordinal());
                JsonFormulier.add("keren", EvalForm.getKeren().ordinal());
                //
                //garage
                JsonFormulier.add("garageEen", EvalForm.getGarageEen().ordinal());
                JsonFormulier.add("garageDrie", EvalForm.getGarageDrie().ordinal());
                JsonFormulier.add("garageAchterwaarts", EvalForm.getGarageAchterwaarts().ordinal());
                JsonFormulier.add("achteruit", EvalForm.getAchteruit().ordinal());
                JsonFormulier.add("stuurOefeningen", EvalForm.getStuurOefeningen().ordinal());
                //
                //verkeerstechniek
                JsonFormulier.add("richtingAanwijzers", EvalForm.getRichtingAanwijzers().ordinal());
                JsonFormulier.add("openbareWeg", EvalForm.getOpenbareWeg().ordinal());
                JsonFormulier.add("voorrang", EvalForm.getVoorrang().ordinal());
                JsonFormulier.add("verkeerstekens", EvalForm.getVerkeerstekens().ordinal());
                JsonFormulier.add("snelheid", EvalForm.getSnelheid().ordinal());
                JsonFormulier.add("volgafstand", EvalForm.getVolgafstand().ordinal());
                JsonFormulier.add("inhalen", EvalForm.getInhalen().ordinal());
                JsonFormulier.add("kruisen", EvalForm.getKruisen().ordinal());
                JsonFormulier.add("linksaf", EvalForm.getLinksaf().ordinal());
                JsonFormulier.add("rechtsaf", EvalForm.getRechtsaf().ordinal());

                JsonArrayBuilder jsonrichtingAanwijzersAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getRichtingAanwijzersAndere()) {
                    jsonrichtingAanwijzersAndere.add(opm);
                }
                JsonFormulier.add("richtingAanwijzersAndere", jsonrichtingAanwijzersAndere);

                JsonArrayBuilder jsonopenbareWegAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getOpenbareWegAndere()) {
                    jsonopenbareWegAndere.add(opm);
                }
                JsonFormulier.add("openbareWegAndere", jsonopenbareWegAndere);

                JsonArrayBuilder jsonvoorrangAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getVoorrangAndere()) {
                    jsonvoorrangAndere.add(opm);
                }
                JsonFormulier.add("voorrangAndere", jsonvoorrangAndere);

                JsonArrayBuilder jsonverkeerstekensAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getVerkeerstekensAndere()) {
                    jsonverkeerstekensAndere.add(opm);
                }
                JsonFormulier.add("verkeerstekensAndere", jsonverkeerstekensAndere);

                JsonArrayBuilder jsonsnelheidAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getSnelheidAndere()) {
                    jsonsnelheidAndere.add(opm);
                }
                JsonFormulier.add("snelheidAndere", jsonsnelheidAndere);

                JsonArrayBuilder jsonvolgafstandAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getVolgafstandAndere()) {
                    jsonvolgafstandAndere.add(opm);
                }
                JsonFormulier.add("volgafstandAndere", jsonvolgafstandAndere);

                JsonArrayBuilder jsoninhalenAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getInhalenAndere()) {
                    jsoninhalenAndere.add(opm);
                }
                JsonFormulier.add("inhalenAndere", jsoninhalenAndere);

                JsonArrayBuilder jsonkruisenAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getKruisenAndere()) {
                    jsonkruisenAndere.add(opm);
                }
                JsonFormulier.add("kruisenAndere", jsonkruisenAndere);

                JsonArrayBuilder jsonlinksafAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getLinksafAndere()) {
                    jsonlinksafAndere.add(opm);
                }
                JsonFormulier.add("linksafAndere", jsonlinksafAndere);

                JsonArrayBuilder jsonrechtsafAndere = Json.createArrayBuilder();
                for (String opm : EvalForm.getRechtsafAndere()) {
                    jsonrechtsafAndere.add(opm);
                }
                JsonFormulier.add("rechtsafAndere", jsonrechtsafAndere);
                //
                //hoofdscherm
                JsonFormulier.add("rotonde", EvalForm.getRotonde().ordinal());
                JsonFormulier.add("rijstroken", EvalForm.getRijstroken().ordinal());
                JsonFormulier.add("stad", EvalForm.getStad().ordinal());
                JsonFormulier.add("autosnelweg", EvalForm.getAutosnelweg().ordinal());
                JsonFormulier.add("schakelaars", EvalForm.getSchakelaars().ordinal());
                JsonFormulier.add("vloeistoffen", EvalForm.getVloeistoffen().ordinal());
                JsonFormulier.add("banden", EvalForm.getBanden().ordinal());
                JsonFormulier.add("tanken", EvalForm.getTanken().ordinal());
                JsonFormulier.add("gps", EvalForm.getGps().ordinal());
                JsonFormulier.add("stop", EvalForm.getStop().ordinal());
                JsonFormulier.add("niveau", EvalForm.getNiveau());

                JsonArrayBuilder jsonattitude = Json.createArrayBuilder();
                for (String opm : EvalForm.getAttitude()) {
                    jsonattitude.add(opm);
                }
                JsonFormulier.add("attitude", jsonattitude);

                JsonArrayBuilder jsonopmerkingen = Json.createArrayBuilder();
                for (String opm : EvalForm.getOpmerkingen()) {
                    jsonopmerkingen.add(opm);
                }
                JsonFormulier.add("opmerkingen", jsonopmerkingen);
                //

                jsonEvals.add(JsonFormulier);
            }

            JsonLeerling.add("evaluatieformulieren", jsonEvals);

            jsonLeerlingen.add(JsonLeerling);
        }

        try (JsonWriter out = Json.createWriter(entityStream)) {
            out.writeArray(jsonLeerlingen.build());
        }
    }
}
