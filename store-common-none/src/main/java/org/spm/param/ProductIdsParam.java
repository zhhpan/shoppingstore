package org.spm.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductIdsParam {

    @NotNull
    private List<Integer> categoryID = null;

    private int currentPage = 1;

    private int pageSize = 15;

}
