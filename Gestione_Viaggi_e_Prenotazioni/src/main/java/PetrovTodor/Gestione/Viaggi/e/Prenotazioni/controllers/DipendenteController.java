package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.controllers;


import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Dipendente;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions.BadRequestException;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload.DipendenteDto;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload.responsPayload.DipendenteResponseDto;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/*
1. GET http://localhost:3001/dipendenti
2. POST http://localhost:3001/dipendenti (+ body)
3. GET  http://localhost:3001/dipendenti/{autoreId}
4. PUT http://localhost:3001/dipendenti/{autoreId}
5. DELETE http://localhost:3001/dipendenti/{autoreId}


 */
@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    DipendenteService dipendenteService;

    //1. GET http://localhost:3001/dipendenti
    @GetMapping
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "idDipendente") String sorteBy) {

        return dipendenteService.findAll(page, size, sorteBy);
    }

    //2. POST http://localhost:3001/dipendenti (+ body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DipendenteResponseDto saveDipendente(@RequestBody @Validated DipendenteDto body, BindingResult validationResult) throws org.apache.coyote.BadRequestException {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Controlla i seguenti errori: " + messages);
        } else {
            return new DipendenteResponseDto(this.dipendenteService.saveDipendente(body).getIdDiprndente());
        }
    }
    //3. GET  http://localhost:3001/dipendenti/{autoreId}


}
