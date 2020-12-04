package com.agh.bookstoreOrders.Account;

import com.agh.bookstoreOrders.DataTransferObjects.LoggedUserDTO;
import com.agh.bookstoreOrders.DataTransferObjects.StockUpdateDTO;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;


@FeignClient(
        name = "accounts-service",
        configuration = AccountService.MultipartSupportConfig.class
)
public interface AccountService {

    @RequestMapping(value = "/logged/sessionRoles", method = RequestMethod.GET)
    public @ResponseBody
    LoggedUserDTO getSession(@RequestParam String header);

    public class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public SpringFormEncoder feignFormEncoder () {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }
}
