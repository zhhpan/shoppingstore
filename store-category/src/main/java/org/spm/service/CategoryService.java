package org.spm.service;

import org.spm.param.ProductHotsParam;
import org.spm.utils.R;

public interface CategoryService {

    R byName(String categoryName);

    R hotsCategory(ProductHotsParam productHotsParam);

    R list();
}
