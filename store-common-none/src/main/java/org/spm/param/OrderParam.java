package org.spm.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.spm.pojo.CartVo;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderParam implements Serializable {

    public static final Long serialVersionUID = 1L;

    @JsonProperty("user_id")
    private Integer userId;
    private List<CartVo> products;
}