package com.antepastocompany.antepastoapi.controller;

import com.antepastocompany.antepastoapi.builder.AntepastoDTOBuilder;
import com.antepastocompany.antepastoapi.dto.request.AntepastoDTO;
import com.antepastocompany.antepastoapi.exceptions.AntepastoAlreadyRegisteredException;
import com.antepastocompany.antepastoapi.service.AntepastoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.antepastocompany.antepastoapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AntepastoControllerTest {

    private static final String ANTEPASTO_API_URL_PATH = "/api/v1/antepasto";
    private static final long VALID_ANTEPASTO_ID = 1L;
    private static final long INVALID_ANTEPASTO_ID = 2l;

    private MockMvc mockMvc;

    @Mock
    private AntepastoService antepastoService;

    @InjectMocks
    private AntepastoController antepastoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(antepastoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAnAntepastoIsCreated() throws AntepastoAlreadyRegisteredException, Exception {
        //given
        AntepastoDTO antepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();

        //when
        when(antepastoService.createAntepasto(any())).thenReturn(antepastoDTO);

        mockMvc.perform(post(ANTEPASTO_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(antepastoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flavor", is(antepastoDTO.getFlavor())))
                .andExpect(jsonPath("$.spicy", is(antepastoDTO.getSpicy())))
                .andExpect(jsonPath("$.quantity", is(antepastoDTO.getQuantity())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenReturnAnError() throws Exception {
        //given
        AntepastoDTO antepastoDTO = AntepastoDTOBuilder.builder().build().toAntepastoDTO();
        antepastoDTO.setFlavor(null);

        mockMvc.perform(post(ANTEPASTO_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(antepastoDTO)))
                .andExpect(status().isBadRequest());

    }
}
