package si.fri.prpo.projektPolnilnePostaje.entitete;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "polnilnePostaje")
public class PolnilnaPostaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPostaja;
    private String lokacija;
    @Temporal(TemporalType.TIME)
    private Date uraOdprtja;
    @Temporal(TemporalType.TIME)
    private Date uraZaprtja;
    private Float cenaPolnjenja;
    private Integer tipPrikljucka;
    private Float hitrostPolnjenja;

    @OneToMany
    private Set<Ocena> ocene;

    @ManyToOne
    @JoinColumn(name = "idUporabnik")
    private LastnikPostaje lastnik;
}
