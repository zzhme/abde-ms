package com.zzh.service.impl;

import com.zzh.mappers.LogMapper;
import com.zzh.pojo.OperateLog;
import com.zzh.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    LogMapper logMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insert(OperateLog operateLog) {
        logMapper.insert(operateLog);
    }
}
