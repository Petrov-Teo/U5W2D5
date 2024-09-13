package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PrenotazioneDto(
        @NotNull(message = "Il campo Data è obbligatorio!")
        LocalDate dataPrenotazione,
        String noteEoPreferenze) {
}
