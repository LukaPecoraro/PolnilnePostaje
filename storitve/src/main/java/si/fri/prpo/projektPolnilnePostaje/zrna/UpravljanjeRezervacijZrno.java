package si.fri.prpo.projektPolnilnePostaje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PraznikDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazRezervacijeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeRezervacijeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class UpravljanjeRezervacijZrno {
    @Inject
    private PolnilnicaZrno pz;

    @Inject
    private UporabnikZrno uz;

    @Inject
    private RezervacijeZrno rz;

    private Logger log = Logger.getLogger(UpravljanjeRezervacijZrno.class.getName());
    private UUID uid = UUID.randomUUID();

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna " + UpravljanjeRezervacijZrno.class.getSimpleName());

        //init virov
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinicializacija zrna " + UpravljanjeRezervacijZrno.class.getSimpleName());

        //zapiranje virov
    }

    @Transactional
    public PrikazRezervacijeDTO dodajRezervacijo(UrejanjeRezervacijeDTO dto) {
        Uporabnik uporabnik = uz.getUporabnikById(dto.getIdUporabnik());
        if (uporabnik == null) {
            log.info("Uporabnik ne obstaja.");
            return null;
        }

        PolnilnaPostaja postaja = pz.getPostajaById(dto.getIdPostaja());
        if (postaja == null){
            log.info("Postaja ne obstaja.");
            return null;
        }
        if (dto.getDatumRezervacije() == null || dto.getUraZacetka() == null || dto.getUraKonca() == null) {
            log.info("Ni vseh podatkov.");
            return null;
        }

        Date datum = dto.getDatumRezervacije();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datum);


        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String country = "SI";
        String apiKey = "ba38f7ad929c49138365bca59d5265ee";

        Client client = ClientBuilder.newClient();
        String url = "https://holidays.abstractapi.com/v1/?api_key=" + apiKey +
                "&country=" + country + "&year=" + year + "&month=" + month + "&day=" + day;

        Response response = client.target(url).request(MediaType.APPLICATION_JSON).get(Response.class);
        if (response.getStatus() == 200) {
            try {
                List<PraznikDTO> prazniki = response.readEntity(new GenericType<List<PraznikDTO>>() {});
                for (PraznikDTO praznik : prazniki) {
                    if (praznik.getType().equals("National")) {
                        log.info("Ni mogoce rezervirati zaradi: " + praznik.toString());
                        return null;
                    }
                }
            } catch (Exception e) {
                log.severe(e.getMessage());
            }
        }

        Rezervacija novaRezervacija = new Rezervacija();
        novaRezervacija.setUporabnik(uporabnik);
        novaRezervacija.setPolnilnaPostaja(postaja);
        novaRezervacija.setDatumRezervacije(dto.getDatumRezervacije());
        novaRezervacija.setUraZacetka(dto.getUraZacetka());
        novaRezervacija.setUraKonca(dto.getUraKonca());

        Rezervacija r = rz.createRezervacija(novaRezervacija);
        if (r != null) {
            uporabnik.getRezervacije().add(r);
            postaja.getRezervacije().add(r);
            return PrikazRezervacijeDTO.toDto(r);
        }
        return null;
    }

    public List<PrikazRezervacijeDTO> vrniRezervacije(QueryParameters query) {
        return rz.getRezervacije(query).stream()
                .map(PrikazRezervacijeDTO::toDto)
                .collect(Collectors.toList());
    }

    public PrikazRezervacijeDTO vrniRezervacijo(int idRezervacija){
        Rezervacija rezervacija = rz.getRezervacija(idRezervacija);
        if (rezervacija != null) {
            log.info("Rezervacija " + rezervacija.getIdRezervacija() + " najdena.");
            return PrikazRezervacijeDTO.toDto(rezervacija);
        } else {
            log.info("Rezervacija " + idRezervacija + " ni bila najdena.");
        }
        return null;
    }

    @Transactional
    public boolean izbrisiRezervacijo(int idRezervacija) {
        boolean uspeh = rz.deleteRezervacija(idRezervacija);
        if (uspeh) {
            log.info("Rezervacija " + idRezervacija + " izbrisana.");
        } else {
            log.info("Rezervacija ni bila najdena.");
        }
        return uspeh;
    }

    public UUID izpisiUUID() {
        log.info("Zrno je obsega @RequestScoped in ima UUID: " + uid);
        return uid;
    }

    public Long vrniSteviloRezervacij(QueryParameters query) {
        return rz.getRezervacijeCount(query);
    }
}


