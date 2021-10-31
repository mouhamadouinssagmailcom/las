package com.myvision.Super.Web;

import com.myvision.Super.Entity.Product;
import com.myvision.Super.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLConnection;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepository;


    @GetMapping("/products")
    public String getAllProducts(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "8") int size,
                                 @RequestParam(name = "keyword", defaultValue = "") String mc
    ) {
        Page<Product> productPage = productRepository.findBycategoryContains(mc, PageRequest.of(page, size));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("pages", new int[productPage.getTotalPages()]);
        model.addAttribute("size", size);
        model.addAttribute("keyword", mc);
        model.addAttribute("curentpage", page);

        return "product";
    }


    @GetMapping("/adminisproducts")
    public String getAllProductsBydesign(Model model,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size,
                                         @RequestParam(name = "keyword", defaultValue = "") String mc
    ) {
        Page<Product> productPage = productRepository.findByDesignContains(mc, PageRequest.of(page, size));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("pages", new int[productPage.getTotalPages()]);
        model.addAttribute("size", size);
        model.addAttribute("keyword", mc);
        model.addAttribute("curentpage", page);

        return "adminisproduct";
    }

    @GetMapping("/produitAdd")
    public String showproduitAdd(Product product) {
        return "ajoutproduct";
    }

    @PostMapping("/saveproduct")
    public String saveOneproduct(@RequestParam("myfile") MultipartFile myfile,
                                 @Valid Product product, BindingResult result, Model model) throws IOException {

        product.setName(myfile.getOriginalFilename());
        product.setImage(myfile.getBytes());
        if (result.hasErrors()) {
            return "ajoutproduct";
        }
        productRepository.save(product);
        return "redirect:/products";
    }


    @PostMapping("/updateproduct/{id}")
    public String UpdateOneProduct(@RequestParam("myfile") MultipartFile myfile,
                                   @PathVariable("id") long id, @Valid Product product,
                                   BindingResult result, Model model) throws IOException {
        product.setName(myfile.getOriginalFilename());
        product.setImage(myfile.getBytes());
        if (result.hasErrors()) {
            product.getName();
            product.setId(id);
            return "productUpdate";
        }

        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping(value = "produitUpdat/{id}")
    public String produitUpdate(@PathVariable("id") long id, String name, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Produit Id:" + id));
        model.addAttribute("produit", product);
        return "produitUpdate";
    }

    @GetMapping("/suppression")
    public String delete(Model model, Long id, String keyword, int page, int size) {
        productRepository.deleteById(id);
        return "redirect:/adminisproducts?page =" + page + "&size=" + size + "&keyword=" + keyword;
    }

    @GetMapping("/Edition")
    public String edit(Model model, Long id) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "Edition";
    }

    @GetMapping("/delete")
    public String deleteProduit(@PathVariable("id") long id, String keyword, int page, int size, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid produit Id:" + id));
        productRepository.delete(product);
        return "redirect:/adminisproducts?page =" + page + "&size=" + size + "&keyword=" + keyword;
    }

    @GetMapping("/image/produitdetails")
    public String productDetail(@RequestParam("id") Long id, Optional<Product> product, Model model) {
        try {

            if (id != 0) {
                product = productRepository.findById(id);
                if (product.isPresent()) {
                    model.addAttribute("design", product.get().getDesign());
                    model.addAttribute("desc", product.get().getDesc());
                    model.addAttribute("name", product.get().getName());
                    model.addAttribute("prix", product.get().getPrix());
                    return "produitdetails";
                }
                return "redirect:/products";
            }
            return "redirect:/products";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/products";
        }

    }

    @GetMapping("/images/{nameImage}")
    public void getImage(@PathVariable("nameImage") String nameImage, HttpServletResponse response) throws Exception {

        Optional<Product> img = productRepository.findByName(nameImage);
        if (img.isPresent()) {

            //System.out.println("returning file:"+nameImage);
            byte[] bytes = img.get().getImage();
            InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            response.setContentType(mimeType);

            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        }
    }

    /////////
}
