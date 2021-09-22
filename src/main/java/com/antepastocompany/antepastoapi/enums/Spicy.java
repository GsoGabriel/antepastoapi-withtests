package com.antepastocompany.antepastoapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Spicy {

    YES("Yes"), NO("No");

    private final String description;
}
