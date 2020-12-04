package com.agh.bookstoreBaskets.Book;

import com.agh.bookstoreBaskets.DataTransferObjects.ProductDTO;
import feign.Headers;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@FeignClient(
        name = "products-service",
        configuration = BookService.MultipartSupportConfig.class
)
public interface BookService {

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public @ResponseBody
    Response listBooks();

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ProductDTO getBook(@PathVariable Long id);

    @RequestMapping(value = "/books/list", method = RequestMethod.GET)
    public @ResponseBody
    List<ProductDTO> getBookList(@RequestParam Long[] prodIds);

    public class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public SpringFormEncoder feignFormEncoder () {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }
}
