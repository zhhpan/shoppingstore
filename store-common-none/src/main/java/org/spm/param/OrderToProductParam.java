package org.spm.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderToProductParam implements Serializable {

    public static final Long serialVersionUID = 1L;


    private Integer productId;
    private Integer num;

}
