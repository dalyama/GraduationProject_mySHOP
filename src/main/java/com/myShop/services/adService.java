package com.myShop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.entities.ad;
import com.myShop.repository.adRepo;

@Service
public class adService {

	@Autowired
	adRepo adRepo;

	public List<ad> getTop5() {
		return adRepo.findTop5ByOrderByIdDesc();
	}

	public ad saveAd(ad ad) {
		return adRepo.save(ad);
	}
}
