package com.antepastocompany.antepastoapi.service;

import com.antepastocompany.antepastoapi.builder.AntepastoDTOBuilder;

import com.antepastocompany.antepastoapi.dto.request.AntepastoDTO;
import com.antepastocompany.antepastoapi.entity.Antepasto;
import com.antepastocompany.antepastoapi.exceptions.AntepastoAlreadyRegisteredException;
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
}
