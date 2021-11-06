package si.fri.prpo.projektPolnilnePostaje.entitete;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

@Entity
public class PolnilnaPostaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPostaja;
    private String lokacija;
    @Temporal(TemporalType.TIME)
    private Time uraOdprtja;
    @Temporal(TemporalType.TIME)
    private Time uraZaprtja;
    private Float cenaPolnjenja;
    private Integer tipPrikljucka;
    private Float hitrostPolnjenja;

    @OneToMany
    private Set<Ocena> ocene;

    @ManyToOne
    @JoinColumn(name = "idUporabnik")
    private LastnikPostaje lastnik;
}
