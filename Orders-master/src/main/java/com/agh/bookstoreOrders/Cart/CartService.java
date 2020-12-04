package com.agh.bookstoreOrders.Cart;

import com.agh.bookstoreOrders.DataTransferObjects.ShoppingCartDTO;
import feign.Response;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@FeignClient(
        name = "baskets-service",
        configuration = CartService.MultipartSupportConfig.class
)
public interface CartService {

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public ShoppingCartDTO getCart(@RequestHeader("Cookie") String shoppingCartId);

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public @ResponseBody
    Response getBook();

    public class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public SpringFormEncoder feignFormEncoder () {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }
}
