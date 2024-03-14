package org.spm.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginParam {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
