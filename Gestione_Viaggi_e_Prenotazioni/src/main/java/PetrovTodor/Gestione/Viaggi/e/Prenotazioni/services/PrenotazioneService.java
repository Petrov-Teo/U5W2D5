package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.services;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Prenotazione;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Viaggio;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions.NotFoundException;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload.PrenotazioneDto;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories.PrenotazioneRepository;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    PrenotazioneRepository prenotazioneRepository;
    @Autowired
    ViaggioRepository viaggioRepository;


    public Prenotazione savePrenotazione(PrenotazioneDto body) {
        Viaggio viaggio = this.viaggioRepository.findById(body.idViaggio())
                .orElseThrow(() -> new NotFoundException(body.idViaggio()));
        Prenotazione prenotazione = new Prenotazione(body.dataPrenotazione(), body.noteEoPreferenze(), viaggio);
        return this.prenotazioneRepository.save(prenotazione);
    }

    public Page<Prenotazione> findAll(int pag, int size, String sortBy) {
        Pageable pageable = PageRequest.of(pag, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(UUID idPrenotazione) {
        return this.
                prenotazioneRepository.
                findById(idPrenotazione).
                orElseThrow(()
                        -> new NotFoundException(idPrenotazione));
    }

    public Prenotazione findAndUpdite(UUID idPrenotazione, PrenotazioneDto body) {
        Viaggio viaggio = this.viaggioRepository.findById(body.idViaggio())
                .orElseThrow(() -> new NotFoundException(body.idViaggio()));
        Prenotazione prenotazione = findById(idPrenotazione);
        prenotazione.setDataPrenotazione(body.dataPrenotazione());
        prenotazione.setViaggio(viaggio);
        prenotazione.setNoteEoPreferenze(body.noteEoPreferenze());
        return this.prenotazioneRepository.save(prenotazione);
    }

    public void findAndDelete(UUID idPrenotazione) {
        Prenotazione prenotazione = findById(idPrenotazione);
        this.prenotazioneRepository.delete(prenotazione);
    }
}
