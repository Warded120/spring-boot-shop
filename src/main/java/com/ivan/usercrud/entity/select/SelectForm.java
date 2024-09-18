package com.ivan.usercrud.entity.select;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SelectForm {
    private String keyword;
    private String sortType;
}
