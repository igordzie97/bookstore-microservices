package com.agh.bookstoreOrders.Order;

import com.agh.bookstoreOrders.Account.AccountService;
import com.agh.bookstoreOrders.Book.BookService;
import com.agh.bookstoreOrders.Cart.CartService;
import com.agh.bookstoreOrders.DataTransferObjects.CartProductDTO;
import com.agh.bookstoreOrders.DataTransferObjects.LoggedUserDTO;
import com.agh.bookstoreOrders.DataTransferObjects.ShoppingCartDTO;
import com.agh.bookstoreOrders.DataTransferObjects.StockUpdateDTO;
import com.agh.bookstoreOrders.OrderProduct.OrderProduct;
import com.google.common.base.Charsets;
import com.netflix.discovery.converters.Auto;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    BookService bookService;

    @Autowired
    AccountService accountService;



    @GetMapping("/order")
    public ResponseEntity getCartInfo(@CookieValue("shoppingCartId") Optional<String> shoppingCartId, HttpServletResponse response)  {
        ShoppingCartDTO resp = null;

        if(shoppingCartId.isPresent()) {
            resp = cartService.getCart("shoppingCartId="+ shoppingCartId.get());
        } else {
           //throw new Exception("No cookie: cannot place order");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(resp);
    }

    @PostMapping("/order")
    public ResponseEntity placeOrder(@CookieValue("shoppingCartId") Optional<String> shoppingCartId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        ShoppingCartDTO resp = null;

        if(shoppingCartId.isPresent()) {
            resp = cartService.getCart("shoppingCartId="+ shoppingCartId.get());
        } else {
            throw new Exception("No cookie: cannot place order");
        }

        //validate stocks
        List<CartProductDTO> cannotBuyItems = resp.getItemList().stream()
                .filter(cartItem -> cartItem.getData().getStock() < cartItem.getProductQuantity())
                .collect(Collectors.toList());
        //



        if(cannotBuyItems.size() > 0){
            throw new Exception("Brak wystarczającej ilosci produktów w magazynie");
        }

        Order order = new Order();
        orderService.save(order);

        List<StockUpdateDTO> stockUpdateDTOS = new ArrayList<>();


        resp.getItemList().forEach(cartProductDTO -> {
            OrderProduct orderProduct = new OrderProduct();

            BeanUtils.copyProperties(cartProductDTO.getData(), orderProduct);
            orderProduct.setProductQuantity(cartProductDTO.getProductQuantity());
            order.addItem(orderProduct);

            StockUpdateDTO stockUpdateDTO = new StockUpdateDTO();
            stockUpdateDTO.setProductId(orderProduct.getId());
            stockUpdateDTO.setStockToReduce(orderProduct.getProductQuantity());

            stockUpdateDTOS.add(stockUpdateDTO);

        });

        order.setCartTotal(resp.getCartTotal());
        order.setPaid(BigDecimal.ZERO);

        String header = request.getHeader("Authorization");

        if(header != null){
            LoggedUserDTO session = accountService.getSession(header);
            if(session != null){
                order.setOrderMode(OrderMode.LOGGEDUSER);
                order.setUserId(session.getUser_id());
            }
        }

        orderService.save(order);
        bookService.updateStocks(stockUpdateDTOS);


        return ResponseEntity.status(HttpStatus.OK)
                .body(resp);


    }
}
