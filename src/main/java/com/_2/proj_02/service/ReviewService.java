package com._2.proj_02.service;

import com._2.proj_02.dto.response.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final InquiryRepository inquiryRepository;

    public List<ReviewResponseDTO> getReviewsList() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<InquiryResponseDTO> getInquiriesList() {
        return inquiryRepository.findAll()
                .stream()
                .map(InquiryResponseDTO::new)
                .collect(Collectors.toList());
    }
}