package com.antepastocompany.antepastoapi.builder;

import com.antepastocompany.antepastoapi.dto.request.AntepastoDTO;
import com.antepastocompany.antepastoapi.entity.Antepasto;
import com.antepastocompany.antepastoapi.enums.Spicy;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AntepastoDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String flavor = "Caponata";

    @Builder.Default
    private String spicy = "NO";

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private double price = 25.00;

    public AntepastoDTO toAntepastoDTO() {
        return new AntepastoDTO(id,
                flavor,
                spicy,
                quantity,
                price);
    }

}
