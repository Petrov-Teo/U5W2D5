package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idPrenotazione;
    private LocalDate dataPrenotazione;
    @Column(columnDefinition = "TEXT")
    private String noteEoPreferenze;

    @ManyToOne
    @JoinColumn(name = "idViaggio", nullable = false)
    private Viaggio viaggio;

    public Prenotazione(LocalDate dataPrenotazione, String noteEoPreferenze, Viaggio viaggio) {
        this.dataPrenotazione = dataPrenotazione;
        this.noteEoPreferenze = noteEoPreferenze;
        this.viaggio = viaggio;
    }
}
