package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.services;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Dipendente;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions.NotFoundException;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload.DipendenteDto;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories.DipendenteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DipendenteService {

    @Autowired
    DipendenteRepository dipendenteRepository;

    public Dipendente saveDipendente(DipendenteDto body) throws BadRequestException {
        if (this.dipendenteRepository.findByEmail(body.email()).isPresent()) {
            throw new BadRequestException("L'email: " + body.email() + " è già in uso!");
        }

        Dipendente dipendente = new Dipendente(
                body.username(),
                body.nome(),
                body.cognome(),
                body.email(),
                "https://picsum.photos/200/300"
        );

        return this.dipendenteRepository.save(dipendente);
    }

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendenteRepository.findAll(pageable);
    }

    public Dipendente findById(UUID idDipendente) {
        return this.dipendenteRepository.findById(idDipendente).orElseThrow(() -> new NotFoundException(idDipendente));
    }

    public void findAndDelete(UUID idDipendente) {
        Dipendente found = this.findById(idDipendente);
        this.dipendenteRepository.delete(found);
    }

    public Dipendente findAndUpdite(UUID idDipendente, DipendenteDto body) throws BadRequestException {
        if (this.dipendenteRepository.findByEmail(body.email()).isPresent()) {
            throw new BadRequestException("L'email: " + body.email() + " è già in uso!");
        }
        Dipendente found = this.findById(idDipendente);
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setAvatarUrl("https://picsum.photos/200/300");
        return this.dipendenteRepository.save(found);
    }
}
