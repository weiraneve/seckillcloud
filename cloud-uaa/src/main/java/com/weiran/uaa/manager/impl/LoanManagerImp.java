package com.weiran.uaa.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.uaa.entity.Loan;
import com.weiran.uaa.manager.LoanManager;
import com.weiran.uaa.mapper.LoanMapper;
import org.springframework.stereotype.Service;

/**
 * 客户贷款表 服务实现类
 */
@Service
public class LoanManagerImp extends ServiceImpl<LoanMapper, Loan> implements LoanManager {

}
