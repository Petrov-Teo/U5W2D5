package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions;

public class BadRequestExeption extends RuntimeException {
    public BadRequestExeption(String st) {
        super("Verifica i campi inseriti err-400" + st);
    }
}
