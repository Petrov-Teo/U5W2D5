package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.enums.StatoViaggio;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "viaggi")
public class Viaggio {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idViaggio;
    private String destinazione;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private StatoViaggio stato;

    @ManyToOne
    @JoinColumn(name = "idDipendente", nullable = false)
    private Dipendente dipendente;

    public Viaggio(String destinazione, LocalDate data, Dipendente dipendente) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = StatoViaggio.PROGRMMTO;
        this.dipendente = dipendente;
    }
}
