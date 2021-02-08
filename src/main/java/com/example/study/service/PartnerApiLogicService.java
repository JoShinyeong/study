package com.example.study.service;

import com.example.study.model.entity.Partner;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.repository.CategoryRepository;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PartnerApiLogicService extends BaseService<PartnerApiRequest, PartnerApiResponse, Partner>{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {
        return Optional.ofNullable(request.getData())
                .map(body -> {
                    Partner partner = Partner.builder()
                            .name(body.getName())
                            .status(body.getStatus())
                            .address(body.getAddress())
                            .callCenter(body.getCallCenter())
                            .partnerNumber(body.getPartnerNumber())
                            .businessNumber(body.getBusinessNumber())
                            .ceoName(body.getCeoName())
                            .registeredAt(body.getRegisteredAt())
                            .category(categoryRepository.getOne(body.getCategoryId()))
                            .build();
                    return partner;
                })
                .map(newPartner -> baseRepository.save(newPartner))
                .map(newPartner -> response(newPartner))
                .orElseGet(()->Header.ERROR(("데이터없음")));
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(partner -> response(partner))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {
        return Optional.ofNullable(request.getData())
                .map(body -> {
                                return baseRepository.findById(body.getId())
                                        .map(partner -> {
                                            partner
                                                    .setName(body.getName())
                                                    .setStatus(body.getStatus())
                                                    .setAddress(body.getAddress())
                                                    .setCallCenter(body.getCallCenter())
                                                    .setPartnerNumber(body.getPartnerNumber())
                                                    .setBusinessNumber(body.getBusinessNumber())
                                                    .setCeoName(body.getCeoName())
                                                    .setRegisteredAt(body.getRegisteredAt())
                                                    .setUnregisteredAt(body.getUnregisteredAt())
                                                    .setCategory(categoryRepository.getOne(body.getCategoryId()))
                                            ;
                                            return partner;


                                        })
                                        .map(changePartner -> baseRepository.save(changePartner))
                                        .map(this::response)
                                        .orElseGet(() -> Header.ERROR("데이터없음"));
                            })
                            .orElseGet(() -> Header.ERROR("데이터없음"));


    }


    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(partner -> {
                    baseRepository.delete(partner);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }


    private Header<PartnerApiResponse> response(Partner partner){

        PartnerApiResponse body = PartnerApiResponse.builder()
                .id(partner.getId())
                .name(partner.getName())
                .status(partner.getStatus())
                .address(partner.getAddress())
                .callCenter(partner.getCallCenter())
                .partnerNumber(partner.getPartnerNumber())
                .businessNumber(partner.getBusinessNumber())
                .ceoName(partner.getCeoName())
                .registeredAt(partner.getRegisteredAt())
                .unregisteredAt(partner.getUnregisteredAt())
                .categoryId(partner.getCategory().getId())
                .build();

        return Header.OK(body);
    }
}
