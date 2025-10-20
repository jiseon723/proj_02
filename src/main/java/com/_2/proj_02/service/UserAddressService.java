package com._2.proj_02.service;

import com._2.proj_02.dto.request.UserAddressCreateDTO;
import com._2.proj_02.dto.request.UserAddressUpdateDTO;
import com._2.proj_02.dto.response.UserAddressResponseDTO;
import com._2.proj_02.entity.UserAddress;
import com._2.proj_02.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserAddressService {

    private final UserAddressRepository addressRepository;

    public List<UserAddressResponseDTO> getAddressList() {
        return addressRepository.findAll()
                .stream()
                .map(UserAddressResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void addAddress(UserAddressCreateDTO dto) {
        UserAddress address = new UserAddress(dto);
        addressRepository.save(address);
    }

    public void updateAddress(Long addressId, UserAddressUpdateDTO dto) {
        UserAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("주소 없음"));
        address.updateFromDTO(dto);
        addressRepository.save(address);
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}