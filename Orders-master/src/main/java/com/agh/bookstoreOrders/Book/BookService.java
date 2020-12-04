package com.agh.bookstoreOrders.Book;

import com.agh.bookstoreOrders.DataTransferObjects.StockUpdateDTO;
import feign.Response;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(
        name = "products-service",
        configuration = BookService.MultipartSupportConfig.class
)
public interface BookService {

    @RequestMapping(value = "/books/updateStocks", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity updateStocks(@RequestBody List<StockUpdateDTO> prodIds);

    public class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public SpringFormEncoder feignFormEncoder () {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }
}
