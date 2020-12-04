package com.agh.storage_service.Picture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.Response;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j

public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private final PictureRepository repo;

    PictureController(PictureRepository repo, PictureService ps){
        this.repo = repo;
        this.pictureService = ps;
    }

    @GetMapping("/picture")
    List<Picture> all(){
        return repo.findAll();
    }


    @PostMapping("/picture")
    public HashMap<String, String> handleFileUpload(@RequestPart("file") MultipartFile file, @RequestParam("name") String name1) {
        log.info("TESOTR");
        String name = pictureService.store(file, "picture");
        Path fullPath = pictureService.load(name);
        log.info("successfully uploaded " + file.getOriginalFilename() + " as: " + name + "!");

        HashMap<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("name", name);
        map.put("message", "Added successfully");
        return map;
       // return "OK";
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(map);
    }

}
