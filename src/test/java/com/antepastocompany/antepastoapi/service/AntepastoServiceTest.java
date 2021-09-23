package com.antepastocompany.antepastoapi.service;

import com.antepastocompany.antepastoapi.builder.AntepastoDTOBuilder;

import com.antepastocompany.antepastoapi.dto.request.AntepastoDTO;
import com.antepastocompany.antepastoapi.dto.response.MessageResponseDTO;
import com.antepastocompany.antepastoapi.entity.Antepasto;
import com.antepastocompany.antepastoapi.exceptions.AntepastoAlreadyRegisteredException;
import com.antepastocompany.antepastoapi.exceptions.AntepastoNotFoundException;
import com.antepastocompany.antepastoapi.mapper.AntepastoMapper;
import com.antepastocompany.antepastoapi.repository.AntepastoRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AntepastoServiceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private AntepastoRepository antepastoRepository;

    private AntepastoMapper antepastoMapper = AntepastoMapper.INSTANCE;

    @InjectMocks
    private AntepastoService antepastoService;

    @Test
    void whenAntepastoInformedThenItShouldBeCreated() throws AntepastoAlreadyRegisteredException {
        // given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto expectedSavedAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        // when
        when(antepastoRepository.findByFlavor(expectedAntepastoDTO.getFlavor())).thenReturn(Optional.empty());
        when(antepastoRepository.save(any())).thenReturn(expectedSavedAntepasto);

        //then
        AntepastoDTO createdAntepastoDTO = antepastoService.createAntepasto(expectedAntepastoDTO);

        assertThat(createdAntepastoDTO.getId(), is(equalTo(expectedAntepastoDTO.getId())));
        assertThat(createdAntepastoDTO.getFlavor(), is(equalTo(expectedAntepastoDTO.getFlavor())));
        assertThat(createdAntepastoDTO.getQuantity(), is(equalTo(expectedAntepastoDTO.getQuantity())));

    }

    @Test
    void whenAntepastoInformedAlreadyExistsThenItMustThrowsAnException() {
        // given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto duplicatedAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        // when
        when(antepastoRepository.findByFlavor(expectedAntepastoDTO.getFlavor())).thenReturn(Optional.of(duplicatedAntepasto));

        // then
        assertThrows(AntepastoAlreadyRegisteredException.class, () -> antepastoService.createAntepasto(expectedAntepastoDTO));
    }

    @Test
    void whenListAntepastoIsCalledThenReturnAList() throws AntepastoAlreadyRegisteredException{
        // given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto expectedFoundAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        // when
        when(antepastoRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundAntepasto));

        // then
        List<AntepastoDTO> expectedList = antepastoService.listAll();

        assertThat(expectedList, is(not(empty())));
        assertThat(expectedList.get(0).getFlavor(), is(equalTo(expectedFoundAntepasto.getFlavor())));
    }

    @Test
    void whenAntepastoIdIsGivenThenReturnAAntepasto() throws AntepastoNotFoundException {
        // given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto expectedFoundAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        // when
        when(antepastoRepository.findById(expectedAntepastoDTO.getId())).thenReturn(Optional.of(expectedFoundAntepasto));

        // then
        AntepastoDTO antepastoDTO = antepastoService.getById(expectedAntepastoDTO.getId());

        assertThat(antepastoDTO.getFlavor(), is(equalTo(expectedFoundAntepasto.getFlavor())));
    }

    @Test
    void whenAntepastoFlavorIsGivenThenReturnAAntepasto() throws AntepastoNotFoundException {
        // given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto expectedFoundAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        // when
        when(antepastoRepository.findByFlavor(expectedAntepastoDTO.getFlavor())).thenReturn(Optional.of(expectedFoundAntepasto));

        // then
        AntepastoDTO antepastoDTO = antepastoService.getByFlavor(expectedAntepastoDTO.getFlavor());

        assertThat(antepastoDTO.getFlavor(), is(equalTo(expectedFoundAntepasto.getFlavor())));
    }

    @Test
    void whenNotRegisteredAntepastoFlavorIsGivenThenThrowsAnException() throws AntepastoNotFoundException {
        // given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto expectedFoundAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        // when
        when(antepastoRepository.findByFlavor(expectedAntepastoDTO.getFlavor())).thenReturn(Optional.empty());

        // then
        assertThrows(AntepastoNotFoundException.class, () -> antepastoService.getByFlavor(expectedAntepastoDTO.getFlavor()));
        assertThrows(AntepastoNotFoundException.class, () -> antepastoService.updateAntepastoByFlavor(expectedAntepastoDTO.getFlavor(), expectedAntepastoDTO));
    }

    @Test
    void whenNotRegisteredAntepastoIdIsGivenThenThrowsAnException() throws AntepastoNotFoundException {
        // given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto expectedFoundAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        // when
        when(antepastoRepository.findById(expectedAntepastoDTO.getId())).thenReturn(Optional.empty());

        // then
        assertThrows(AntepastoNotFoundException.class, () -> antepastoService.getById(expectedAntepastoDTO.getId()));
        assertThrows(AntepastoNotFoundException.class, () -> antepastoService.updateAntepastoById(expectedAntepastoDTO.getId(), expectedAntepastoDTO));
    }

    @Test
    void whenAntepastoIdAndAntepastoDTOIsGivenThenReturnAMessageResponse() throws AntepastoNotFoundException {
        //given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto expectedFoundAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        //when
        when(antepastoRepository.findById(expectedAntepastoDTO.getId())).thenReturn(Optional.of(expectedFoundAntepasto));
        when(antepastoRepository.save(any())).thenReturn(expectedFoundAntepasto);

        //then
        MessageResponseDTO messageResponse = antepastoService.updateAntepastoById(expectedAntepastoDTO.getId(), expectedAntepastoDTO);

        assertThat(messageResponse.getMessage(), is(equalTo("Updated antepasto with flavor " + expectedFoundAntepasto.getFlavor())));
    }

    @Test
    void whenAntepastoFlavorAndAntepastoDTOIsGivenThenReturnAMessageResponse() throws AntepastoNotFoundException {
        //given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto expectedFoundAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        //when
        when(antepastoRepository.findByFlavor(expectedAntepastoDTO.getFlavor())).thenReturn(Optional.of(expectedFoundAntepasto));
        when(antepastoRepository.save(any())).thenReturn(expectedFoundAntepasto);

        //then
        MessageResponseDTO messageResponse = antepastoService.updateAntepastoByFlavor(expectedAntepastoDTO.getFlavor(), expectedAntepastoDTO);

        assertThat(messageResponse.getMessage(), is(equalTo("Updated antepasto with flavor " + expectedFoundAntepasto.getFlavor())));
    }

    @Test
    void whenExclusionIsCalledWithValidAntepastoIdThenItShoudBeDeleted() throws AntepastoNotFoundException {
        //given
        AntepastoDTO expectedAntepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        Antepasto expectedDeletedAntepasto = antepastoMapper.toModel(expectedAntepastoDTO);

        // when
        when(antepastoRepository.findById(expectedAntepastoDTO.getId())).thenReturn(Optional.of(expectedDeletedAntepasto));

        //then
        antepastoService.delete(expectedAntepastoDTO.getId());

        verify(antepastoRepository, times(1)).findById(expectedAntepastoDTO.getId());
        verify(antepastoRepository, times(1)).deleteById(expectedAntepastoDTO.getId());
    }

}
