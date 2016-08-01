package com.sq.drogon.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.sq.drogon.repository.MesuringPointRepository;


@Service
public class MesuringPointService {
	
	@Resource
	private MesuringPointRepository mesuringPointRepository;
	

}
