package org.spm.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductPromoParam {

    @NotBlank
    private String categoryName;
}
