package com.sujan.SpringEcom.service;


import com.sujan.SpringEcom.model.Product;
import com.sujan.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> findAll(){
        return productRepo.findAll();
    }

    public Product findById(Integer id) {
        return productRepo.findById(id).orElse(new Product(id = -1));
    }

    public Product addOrUpdateProduct(Product product, MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        productRepo.save(product);
        return productRepo.findById(product.getId()).orElse(new Product(-1));
    }

    public void deleteProduct(Integer id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );
        productRepo.delete(product);

        /*try {
            productRepo.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Product not found");
        }*/
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
