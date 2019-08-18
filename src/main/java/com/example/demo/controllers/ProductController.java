package com.example.demo.controllers;

import com.example.demo.model.Product;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


@Controller
public class ProductController{
    private ProductService productService;
    private static String upload_dir="F:/springfileupload/";

    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(value = "/products",method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("products",productService.listAllProducts());
        System.out.println("Returning products:");
        return "product/products";
    }
    @RequestMapping("product/{id}")
    public String showProduct(@PathVariable Integer id, Model model){
        model.addAttribute("product",productService.getProductById(id));
        return "product/productshow";
    }
    @RequestMapping("product/edit/{id}")
    public String edit(@PathVariable Integer id,Model model){
        model.addAttribute("product",productService.getProductById(id));
        return "product/productform";
    }
    @RequestMapping("product/new")
    public String newProduct(Model model){
        model.addAttribute("product",new Product());
        return "product/productform";
    }
    @RequestMapping(value = "product",method = RequestMethod.POST)
    public String saveProduct(Product product){
        productService.saveProduct(product);
        return "redirect:/product/" + product.getId();
    }
    @RequestMapping(value = "product",method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id){
        productService.deleteProduct(id);
    }
    @PostMapping("/upload")
    public String fileUpload(@ModelAttribute Product product, ModelMap model, HttpSession session){
        ArrayList<String> fileNames = null;
        if (product.getImageUrl().length>0){
            fileNames = new ArrayList<String>();
            for (MultipartFile file:product.getImageUrl()){
                if (file.isEmpty()){
                    model.put("message","Please select the file to upload");
                }
                try {
                    file.transferTo(new File(upload_dir + file.getOriginalFilename()));
                    fileNames.add(file.getOriginalFilename());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        model.put("message","Please select file to upload");
        model.put("files",fileNames);
        return "Success";
    }
}
