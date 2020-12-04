package com.agh.bookstoreProducts.Book;

import com.agh.bookstoreProducts.Pictures.PictureService;
import com.agh.bookstoreProducts.StockUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@RestController
@Slf4j
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository repo;

    @Autowired
    private PictureService pictureService;


    @GetMapping("/book")
    List<Book> allBooks(){
        return repo.findAll();
    }


    @PostMapping("/admin/book")
    public ResponseEntity addBook(@ModelAttribute @RequestPart Book bookData, @RequestPart(name = "file") MultipartFile file) throws IOException {
        log.info("Adding book with data: ", bookData);

        Response resp = pictureService.handleFileUpload(file, "autor image");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> respMap = mapper.readValue(resp.body().asInputStream(), Map.class);

        bookData.setPhotoName(respMap.get("name"));
        bookService.save(bookData);

        HashMap<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("message", "Added book successfully!");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(map);
    }

    @PutMapping("/admin/books/{id}")
    @ResponseBody
    public ResponseEntity editBook(@ModelAttribute @RequestPart Book bookData, @RequestPart(name = "file") Optional<MultipartFile> file) throws IOException {
        Optional<Book> bookFromDb = repo.findById(bookData.getId());
        if (bookFromDb.isPresent()) {
            Book bookDb = bookFromDb.get();

            bookDb.setName(bookData.getName());
            bookDb.setAuthor(bookData.getAuthor());
            bookDb.setLength(bookData.getLength());
            bookDb.setStock(bookData.getStock());
            bookDb.setDescription(bookData.getDescription());
            if(file.isPresent()){
                Response resp = null;
                try {
                    resp = pictureService.handleFileUpload(file.get(), "autor image");
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, String> respMap = mapper.readValue(resp.body().asInputStream(), Map.class);
                    bookDb.setPhotoName(respMap.get("name"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            repo.save(bookDb);

            return new ResponseEntity<>("Succesful updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error updating", HttpStatus.BAD_REQUEST);
        }
    //    return new ResponseEntity<>("Error updating", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/admin/books/{id}")
    @Transactional
    HashMap<String, String> deleteBook(@PathVariable Long id){
        repo.deleteById(id);

        HashMap<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("message", "Deleted successfully");
        return map;
    }


    @GetMapping("/categories")
    List<CategorySummary> allCategories() {
        List<CategorySummary> summary = new ArrayList<>();
        for (Category category : Category.values()) {
            summary.add(new CategorySummary(category, repo.findByCategory(category).size()));
        }
        return summary;
    }

    @GetMapping("/book/{id}")
    Book one(@PathVariable Long id){
        return repo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @RequestMapping(value="/books/list", method=RequestMethod.GET)
    List<Book> getBookList(@RequestParam  List<Long> prodIds){
        log.info("Weszeło tu");

       Long l = new Long(1);
        return repo.findByIdIn(prodIds);
    }

    @RequestMapping(value = "/books/updateStocks", method = RequestMethod.POST)
    ResponseEntity updateStocks(@RequestBody List<StockUpdateDTO> prodIds){

        prodIds.forEach(stockUpdateDTO -> {
            Optional<Book> book = repo.findById(stockUpdateDTO.getProductId());
            if(book.isPresent()){
                book.get().setStock((int)(book.get().getStock() - stockUpdateDTO.getStockToReduce()));
                repo.save(book.get());
            }
        });


        log.info("StocksUpdated {}", prodIds);
        return ResponseEntity.status(HttpStatus.OK).body("UPDATED STOCKS");

    }


    @GetMapping("/books/author/{name}")
    List<Book> allByAuthor(@PathVariable String name) {
        return repo.findByAuthor(name);
    }

    @GetMapping("/books/count/author/{name}")
    int numberOfBooksByAuthor(@PathVariable String name) {
        return repo.findByAuthor(name).size();
    }

    @GetMapping("/books/list/{category}")
    List<Book> allByCategory(@PathVariable Category category) {
        return repo.findByCategory(category);
    }

    @GetMapping("/books/unreserved")
    List<Book> allUnreserved() {
        return repo.findByStockGreaterThan(0);
    }




}
