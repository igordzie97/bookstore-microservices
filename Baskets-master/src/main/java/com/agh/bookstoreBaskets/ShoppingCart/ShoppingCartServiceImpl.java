package com.agh.bookstoreBaskets.ShoppingCart;

import com.agh.bookstoreBaskets.Book.BookService;
import com.agh.bookstoreBaskets.DataTransferObjects.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService{

    @Autowired
    BookService bookService;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl() {

    }

    @Override
    public ShoppingCart getShoppingCart(String cartId) {
        ShoppingCart cart = shoppingCartRepository.findByCartId(cartId);
        cart.setNewShoppingCartItemList(refreshCartProducts(cart));
        cart.recalculateCart();

        return cart;
    }

    @Override
    public ShoppingCart createNewShoppingCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.setCartId(this.generateRandomCartId("MEGASECRETKEY"));
        return cart;
    }

    @Override
    public ShoppingCart removeItem(String cartId, Long prodId) {

        ShoppingCart cart = this.getShoppingCart(cartId);

        ShoppingCartItem shoppingCartItem = this.getShoppingCartItem(prodId, cart);

        cart.removeShoppingCartItem(shoppingCartItem);
        cart.setNewShoppingCartItemList(refreshCartProducts(cart));
        cart.recalculateCart();

        shoppingCartRepository.save(cart);

        return cart;
    }


    @Override
    public ShoppingCart addItem(String cartId, Long prodId, int quantity) {

        ShoppingCart cart = this.getShoppingCart(cartId);
        ShoppingCartItem shoppingCartItem = getShoppingCartItem(prodId, cart);

        if(shoppingCartItem == null){
            if(bookService.getBook(prodId) == null)
                throw new BookNotFoundException(prodId);

            shoppingCartItem = new ShoppingCartItem(cart.getId());
            cart.addShoppingCartItem(shoppingCartItem);

        }

        shoppingCartItem.setProductId(prodId);
        shoppingCartItem.setProductQuantity(shoppingCartItem.getProductQuantity() + quantity);

        shoppingCartRepository.save(cart);

        cart.setNewShoppingCartItemList(refreshCartProducts(cart));
        cart.recalculateCart();

        shoppingCartRepository.save(cart);
        return cart;
    }

    private String generateRandomCartId(String shopsalt) {
        SecureRandom random = new SecureRandom();

        String candidates = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" + shopsalt;
        char[] chars = candidates.toCharArray();

        StringBuilder sb = new StringBuilder(30);

        for (int i = 0; i < 30; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }

    private List<ShoppingCartItem> refreshCartProducts(ShoppingCart cart) {
        Long[] ids = cart.getItemList().stream()
                .map(value -> value.getProductId())
                .toArray(Long[]::new);

        List<ProductDTO> resp = bookService.getBookList(ids);
        log.info("Test {}", resp);
        return cart.getItemList().stream()
                .map((cartItem -> {
                    ProductDTO matchingProduct = resp.stream()
                            .filter(prodServiceItem -> prodServiceItem.getId().equals(cartItem.getProductId()))
                            .findFirst()
                            .orElse(null);
                    if(matchingProduct != null) {
                        cartItem.setData(matchingProduct);

                    }
                    return cartItem;
                }))
                .collect(Collectors.toList());
    }


    private ShoppingCartItem getShoppingCartItem(Long prodId, ShoppingCart cart) {
        return cart.getItemList().stream()
                .filter(shoppingCartItem1 -> cart.getId().equals(shoppingCartItem1.getCart_id()))
                .filter(shoppingCartItem1 -> prodId.equals(shoppingCartItem1.getProductId()))
                .findAny()
                .orElse(null);
    }

}
