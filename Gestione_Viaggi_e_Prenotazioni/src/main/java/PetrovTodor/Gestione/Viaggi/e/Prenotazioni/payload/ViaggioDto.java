package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Dipendente;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViaggioDto(
        @NotEmpty(message = "Il campo destinazione è obbligatorio!")
        String destinazione,
        @NotNull(message = "il campo data  obbligatorio!")
        LocalDate data,
        @NotNull(message = "il campo è obbligatorio")
        Dipendente dipendente) {
}
