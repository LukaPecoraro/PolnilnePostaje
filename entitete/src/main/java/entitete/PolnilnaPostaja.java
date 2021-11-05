package entitete;

import javax.persistence.*;
import java.sql.Time;

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
}
