package org.spm.param;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCheckParam {

    //字符串不能为空和空串
    @NotBlank
    //@JsonProperty("user_id")
    private String userName;

}
