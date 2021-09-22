package com.antepastocompany.antepastoapi.controller;

import com.antepastocompany.antepastoapi.dto.request.AntepastoDTO;
import com.antepastocompany.antepastoapi.dto.response.MessageResponseDTO;
import com.antepastocompany.antepastoapi.exceptions.AntepastoAlreadyRegisteredException;
import com.antepastocompany.antepastoapi.exceptions.AntepastoNotFoundException;
import com.antepastocompany.antepastoapi.service.AntepastoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/antepasto")
public class AntepastoController {

    private AntepastoService antepastoService;

    @Autowired
    public AntepastoController(AntepastoService antepastoService) {
        this.antepastoService = antepastoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AntepastoDTO createAntepasto(@RequestBody AntepastoDTO antepastoDTO) throws AntepastoAlreadyRegisteredException {
        return antepastoService.createAntepasto(antepastoDTO);
    }

    @GetMapping
    public List<AntepastoDTO> listAll(){
        return antepastoService.listAll();
    }

    @GetMapping("/{id}")
    public AntepastoDTO findById(@PathVariable Long id) throws AntepastoNotFoundException {
        return antepastoService.getById(id);
    }
    @GetMapping("/flavor/{flavor}")
    public AntepastoDTO findByFlavor(@PathVariable String flavor) throws AntepastoNotFoundException {
        return antepastoService.getByFlavor(flavor);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateAntepastoById(@PathVariable Long id, @RequestBody AntepastoDTO antepastoDTO)
            throws AntepastoNotFoundException{
        return antepastoService.updateAntepastoById(id, antepastoDTO);
    }

    @PutMapping("/flavor/{flavor}")
    public MessageResponseDTO updateAntepastoByFlavor(@PathVariable String flavor, @RequestBody AntepastoDTO antepastoDTO)
            throws AntepastoNotFoundException{
        return antepastoService.updateAntepastoByFlavor(flavor, antepastoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws AntepastoNotFoundException{
        antepastoService.delete(id);
    }

}
