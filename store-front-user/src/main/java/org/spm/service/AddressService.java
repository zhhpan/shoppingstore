package org.spm.service;

import org.spm.param.AddressListParam;
import org.spm.param.AddressRemoveParam;
import org.spm.pojo.Address;
import org.spm.utils.R;

public interface AddressService {
    R list(AddressListParam addressListParam);

    R save(Address address);

    R remove(AddressRemoveParam addressRemoveParam);
}
