package org.spm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/*
** User实体类
 */
@TableName("user")
@Data
public class User implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("user_id")
    private Integer userId;
    @NotBlank
    private String  userName;
    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  password;
    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  userPhonenumber;
}
