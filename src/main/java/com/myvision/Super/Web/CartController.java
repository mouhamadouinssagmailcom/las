package com.myvision.Super.Web;

import com.myvision.Super.Entity.Product;
import com.myvision.Super.Repository.ProductRepository;
import com.myvision.Super.Services.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private ShoppingCartService shoppingCartService;
    private ProductRepository productRepository;

    @Autowired
    public CartController(ShoppingCartService shoppingCartService, ProductRepository productRepository) {
        this.shoppingCartService = shoppingCartService;
        this.productRepository = productRepository;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("products", shoppingCartService.productsInCart());
        model.addAttribute("totalPrice", shoppingCartService.totalPrice());

        return "cart";
    }

    @GetMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") long id) {
        Product product = productRepository.findById(id).get();
        if (product != null) {
            shoppingCartService.addProduct(product);
            logger.debug(String.format("Product with id: %s added to shopping cart.", id));
        }
        return "redirect:/products";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable("id") long id) {
        Product product = productRepository.findById(id).get();
        if (product != null) {
            shoppingCartService.removeProduct(product);
            logger.debug(String.format("Product with id: %s removed from shopping cart.", id));
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearProductsInCart() {
        shoppingCartService.clearProducts();

        return "redirect:/cart";
    }

    @GetMapping("/cart/checkout")
    public String cartCheckout() {
        shoppingCartService.cartCheckout();

        return "redirect:/cart";
    }
}


