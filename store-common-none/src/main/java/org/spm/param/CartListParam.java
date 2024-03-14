package org.spm.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class CartListParam {

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
}