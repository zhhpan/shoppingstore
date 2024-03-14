package org.spm.param;

import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRemoveParam {

    @NotNull
    @TableField("user_id")
    private Integer id;
}
