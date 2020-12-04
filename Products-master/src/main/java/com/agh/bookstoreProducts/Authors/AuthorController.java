package com.agh.bookstoreProducts.Authors;

import com.agh.bookstoreProducts.NotFoundException;

import com.agh.bookstoreProducts.Pictures.PictureService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@Slf4j
@Validated
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PictureService proxy;

    private final AuthorRepository repo;

    AuthorController(AuthorRepository repo){
        this.repo = repo;
    }

    @GetMapping("/author")
    List<Author> all(){
        return repo.findAll();
    }

    @GetMapping("/author/{id}")
    Author one(@PathVariable Long id){
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Book"));
    }

    @GetMapping("/author/name/{name}")
    Author oneByName(@PathVariable String name){
        return repo.findByName(name);
    }


    @DeleteMapping("/admin/author/{id}")
    @Transactional
    HashMap<String, String> deleteAuthor(@PathVariable Long id){
        repo.deleteById(id);

        HashMap<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("message", "Deleted successfully");
        return map;
    }


    @PutMapping("/admin/author/{id}")
    @ResponseBody
    public ResponseEntity<String> editAuthor(@ModelAttribute @RequestPart Author authorData, @RequestPart(name = "file", required = false) Optional<MultipartFile> file, @PathVariable Long id) {
        Optional<Author> authorFromDb = repo.findById(id);
        if(authorFromDb.isPresent()){
            Author authorDb = authorFromDb.get();

            if(file.isPresent()){
                Response resp = null;
                try {
                    resp = proxy.handleFileUpload(file.get(), "autor image");
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, String> respMap = mapper.readValue(resp.body().asInputStream(), Map.class);
                    authorData.setPhotoName(respMap.get("name"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            authorDb.setName(authorData.getName());
            authorDb.setDescription(authorData.getDescription());
            authorDb.setYear(authorData.getYear());
            repo.save(authorDb);
            return new ResponseEntity<>("Successful updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error updating entity", HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/admin/author")
    public ResponseEntity addAuthor(@ModelAttribute @RequestPart @Valid Author authorData, @RequestPart(name = "file") MultipartFile file) throws IOException {

        log.info("Adding author with data: ", authorData);

        Response resp = proxy.handleFileUpload(file, "autor image");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> respMap = mapper.readValue(resp.body().asInputStream(), Map.class);

        authorData.setPhotoName(respMap.get("name"));
        authorService.save(authorData);

        HashMap<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("message", "Added author successfully!");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(map);
    }
}
