package com.sujan.SpringEcom.controller;


import com.sujan.SpringEcom.model.Product;
import com.sujan.SpringEcom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("products")
    public ResponseEntity<List<Product>> getProducts(){
        return  new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id){
        Product product = productService.findById(id);

        if(product.getId() > 0){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer productId){
        Product product = productService.findById(productId);
//        System.out.println(Arrays.toString(product.getImageData()));
//            return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);

        if(product.getId() > 0){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(product.getImageType()));
            headers.setContentLength(product.getImageData().length);
            return new ResponseEntity<>(
                    product.getImageData(),
                    headers,
                    HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/product",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @RequestPart Product product,
            @RequestPart MultipartFile imageFile
            )
    {
        Product saveProduct;
        try {
            saveProduct = productService.addOrUpdateProduct(product,imageFile);
            if(saveProduct.getId() != -1){
                return new ResponseEntity<>(saveProduct,HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.fillInStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/product/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProduct(@PathVariable String id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile)
    {
        Product saveProduct;
        try {
            saveProduct = productService.addOrUpdateProduct(product,imageFile);
            if(saveProduct.getId() != -1){
                return new ResponseEntity<>("Update",HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.fillInStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();

        /*Product product = productService.findById(id);
        if(product.getId() != -1){
            try {
                productService.deleteProduct(id);
            }catch (Exception e){
                e.fillInStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
        }*/
    }

    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products = productService.searchProducts(keyword);
        System.out.println(products.toString());
        return  new ResponseEntity<>(products,HttpStatus.OK);
    }


}
