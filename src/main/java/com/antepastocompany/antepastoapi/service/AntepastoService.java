package com.antepastocompany.antepastoapi.service;

import com.antepastocompany.antepastoapi.dto.request.AntepastoDTO;
import com.antepastocompany.antepastoapi.dto.response.MessageResponseDTO;
import com.antepastocompany.antepastoapi.entity.Antepasto;
import com.antepastocompany.antepastoapi.exceptions.AntepastoAlreadyRegisteredException;
import com.antepastocompany.antepastoapi.exceptions.AntepastoNotFoundException;
import com.antepastocompany.antepastoapi.mapper.AntepastoMapper;
import com.antepastocompany.antepastoapi.repository.AntepastoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AntepastoService {

    private final AntepastoRepository antepastoRepository;
    private final AntepastoMapper antepastoMapper = AntepastoMapper.INSTANCE;

    @Autowired
    public AntepastoService(AntepastoRepository antepastoRepository) {
        this.antepastoRepository = antepastoRepository;
    }

    public AntepastoDTO createAntepasto(AntepastoDTO antepastoDTO) throws AntepastoAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(antepastoDTO.getFlavor());
        Antepasto antepastoToSave = antepastoMapper.toModel(antepastoDTO);
        Antepasto savedAntepasto = antepastoRepository.save(antepastoToSave);

        return antepastoMapper.toDTO(savedAntepasto);
    }

    private void verifyIfIsAlreadyRegistered(String flavor) throws AntepastoAlreadyRegisteredException{
        Optional<Antepasto> optSavedBeer = antepastoRepository.findByFlavor(flavor);
        if (optSavedBeer.isPresent()) {
            throw new AntepastoAlreadyRegisteredException(flavor);
        }
    }

    public MessageResponseDTO updateAntepastoById(Long id, AntepastoDTO antepastoDTO) throws AntepastoNotFoundException{
        verifyIfExists(id);

        Antepasto antepastoToSave = antepastoMapper.toModel(antepastoDTO);
        Antepasto savedAntepasto = antepastoRepository.save(antepastoToSave);

        return createMessageResponse("Updated antepasto with flavor ", savedAntepasto);
    }

    public MessageResponseDTO updateAntepastoByFlavor(String flavor, AntepastoDTO antepastoDTO) throws AntepastoNotFoundException{
        verifyIfExists(flavor);

        Antepasto antepastoToSave = antepastoMapper.toModel(antepastoDTO);
        Antepasto savedAntepasto = antepastoRepository.save(antepastoToSave);

        return createMessageResponse("Updated antepasto with flavor ", savedAntepasto);
    }

    private MessageResponseDTO createMessageResponse(String string, Antepasto savedAntepasto) {
        return MessageResponseDTO
                .builder()
                .message(string + savedAntepasto.getFlavor())
                .build();
    }

    public List<AntepastoDTO> listAll(){
        List<Antepasto> antepastoList = antepastoRepository.findAll();
        return antepastoList.stream()
                .map(antepastoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AntepastoDTO getById(Long id) throws AntepastoNotFoundException{
        Antepasto antepasto = verifyIfExists(id);
        return antepastoMapper.toDTO(antepasto);
    }

    public AntepastoDTO getByFlavor(String flavor) throws AntepastoNotFoundException{
        Antepasto antepasto = verifyIfExists(flavor);
        return antepastoMapper.toDTO(antepasto);
    }

    public void delete(Long id) throws AntepastoNotFoundException{
        verifyIfExists(id);
        antepastoRepository.deleteById(id);
    }

    private Antepasto verifyIfExists(Long id) throws AntepastoNotFoundException {
        return antepastoRepository.findById(id)
                .orElseThrow(() -> new AntepastoNotFoundException(id));
    }

    private Antepasto verifyIfExists(String flavor) throws AntepastoNotFoundException {
        return antepastoRepository.findByFlavor(flavor)
                .orElseThrow(() -> new AntepastoNotFoundException(flavor));
    }
}
