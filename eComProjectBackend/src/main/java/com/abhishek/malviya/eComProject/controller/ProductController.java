package com.abhishek.malviya.eComProject.controller;

import com.abhishek.malviya.eComProject.model.Product;
import com.abhishek.malviya.eComProject.service.ProductService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

//    @RequestMapping("/")
//    public String greet(){
//        return "Hey this is Greet! HelloWorld";
//    }

    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK) ;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product!=null) return new ResponseEntity<>(product, HttpStatus.OK) ;
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product product1 = service.addProduct(product,imageFile);
            return new ResponseEntity<>(product,HttpStatus.CREATED);
        }catch(Exception exception){
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product=service.getProductById(productId);
        byte[] imageFile=product.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,
                                                @RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product product1;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        }catch (IOException e){
            return new ResponseEntity<>("Failed to update.", HttpStatus.BAD_REQUEST);
        }
        if(product1!=null) return new ResponseEntity<>("Updated",HttpStatus.OK);
        else return new ResponseEntity<>("Failed to update.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        Product product=service.getProductById(id);
        if(product!=null){
            service.deleteProduct(id);
            return  new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        System.out.println("Searching with: "+keyword);
        List<Product> products =service.searchProducts(keyword);
        return new ResponseEntity<> (products,HttpStatus.OK);
    }

}
