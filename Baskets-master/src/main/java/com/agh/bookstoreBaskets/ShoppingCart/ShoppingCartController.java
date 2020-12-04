package com.agh.bookstoreBaskets.ShoppingCart;

import com.agh.bookstoreBaskets.Book.BookService;
import com.agh.bookstoreBaskets.DataTransferObjects.ProductDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import feign.Response;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.ArrayUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Slf4j
public class ShoppingCartController {

    @Autowired
    BookService bookService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;


    @GetMapping("/cart")
    public ResponseEntity getCartInfo(@CookieValue("shoppingCartId") Optional<String> shoppingCartId,  HttpServletResponse response){
        ShoppingCart cart = null;

        if(shoppingCartId.isPresent()){
            log.info(shoppingCartId.get());
            cart = shoppingCartService.getShoppingCart(shoppingCartId.get());
        } else {
            cart = shoppingCartService.createNewShoppingCart();

            final Cookie cookie = new Cookie("shoppingCartId", cart.getCartId());
            cookie.setPath("/");
            //cookie.setSecure(true); //jesli by było https ogarniete - to mozna wlaczyc
            cookie.setHttpOnly(true);

            shoppingCartRepository.save(cart);
            response.addCookie(cookie);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cart);
    }

    @PostMapping("/cart/{prodid}")
    public ResponseEntity addToCart(@CookieValue("shoppingCartId") String shoppingCartId, @PathVariable("prodid") Long prodid) throws IOException {
        ShoppingCart cart = shoppingCartService.addItem(shoppingCartId, prodid, 1);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cart);
    }

    @DeleteMapping("/cart/{prodid}")
    public ResponseEntity removeFromCart(@CookieValue("shoppingCartId") String shoppingCartId, @PathVariable("prodid") Long prodid) throws IOException {
        ShoppingCart cart = shoppingCartService.removeItem(shoppingCartId, prodid);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cart);
    }

}


//            cookie.setDomain(this.cookieDomain);
//            cookie.setSecure(this.sendSecureCookie);
//            cookie.setHttpOnly(true);
//            cookie.setMaxAge(maxAge);