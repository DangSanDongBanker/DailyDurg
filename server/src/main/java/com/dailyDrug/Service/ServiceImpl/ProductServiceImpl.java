package com.dailyDrug.Service.ServiceImpl;

import com.dailyDrug.Service.ProductService;
import com.dailyDrug.dto.ProductDto;
import com.dailyDrug.entity.ProductEntity;
import com.dailyDrug.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.dailyDrug.specification.ProductSpecification;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    final ProductRepository productRepository;

    @Override
    public ProductDto getProductInfo(Integer productId) {

        ProductEntity product = productRepository.findByProductId(productId).orElseThrow(()
                -> new IllegalArgumentException("상품이 없습니다."));

        return new ProductDto(product);
    }

    @Override
    public List<ProductDto> getProductList(String productCategory, String productOrder, int pageNo, int pageSize) {

        //정렬 조건
        Sort sort = Sort.by(Sort.Direction.DESC, productOrder);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //검색 조건
        Specification<ProductEntity> spec = ProductSpecification.equalProductCategory(productCategory);

        Page<ProductEntity> posts= productRepository.findAll(spec, pageable);
        List<ProductEntity> listOfPosts= posts.getContent();

        return listOfPosts.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    };

    @Override
    public ProductEntity incrementProductInterest(Integer productId){

        //update를 위해 entity 조회
        ProductEntity product = productRepository.findByProductId(productId).orElseThrow(()
                -> new IllegalArgumentException("해당상품이 존재하지 않습니다."));

        // 좋아요 수를 증가시킴
        product.setInterestDrugCount(product.getInterestDrugCount() + 1);

        // 데이터베이스에 업데이트
        return productRepository.save(product);
    }



}
