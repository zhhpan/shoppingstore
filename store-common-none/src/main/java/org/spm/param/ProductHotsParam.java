package org.spm.param;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductHotsParam {

    @NotEmpty
    private List<String> categoryName;
}
