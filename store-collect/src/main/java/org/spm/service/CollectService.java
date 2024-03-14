package org.spm.service;

import org.spm.param.AddressListParam;
import org.spm.pojo.Collect;
import org.spm.utils.R;

public interface CollectService {
    R save(Collect collect);

    R list(AddressListParam param);

    R remove(Collect collect);
}
