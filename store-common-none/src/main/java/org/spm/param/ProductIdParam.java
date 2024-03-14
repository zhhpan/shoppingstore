package org.spm.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductIdParam {
    @NotNull
    private Integer productID;
}
