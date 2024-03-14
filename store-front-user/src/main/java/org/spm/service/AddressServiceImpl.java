package org.spm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.spm.mapper.AddressMapper;
import org.spm.param.AddressListParam;
import org.spm.param.AddressRemoveParam;
import org.spm.pojo.Address;
import org.spm.pojo.User;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressMapper addressMapper;
    /**
     * @param addressListParam
     * @return
     */
    @Override
    public R list(AddressListParam addressListParam) {
        //封装参数userQueryWrapper
        QueryWrapper<Address> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("user_id",addressListParam.getUserId());
        //数据库查询
        List<Address> addresses = addressMapper.selectList(QueryWrapper);

        return R.ok("Search Successfully",addresses);
    }

    /**
     * @param address
     * @return
     */
    @Override
    public R save(Address address) {
        int rows = 0;

        try {
            rows = addressMapper.insert(address);
        }catch (Exception e){
            return R.fail("Insert address failure");
        }
        if(rows == 0){
            return R.fail("Insert address failure");
        }
        return list(new AddressListParam(address.getUserId()));
    }

    /**
     * @param addressRemoveParam
     * @return
     */
    @Override
    public R remove(AddressRemoveParam addressRemoveParam) {
        int rows =0;
        try{
            rows = addressMapper.deleteById(addressRemoveParam.getId());
        }catch (Exception e){
            return R.fail("Remove address failure");
        }

        if(rows == 0){
            return R.fail("Remove address failure");
        }
        return R.ok("Remove address successfully");
    }
}
