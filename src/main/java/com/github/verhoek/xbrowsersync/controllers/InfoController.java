package com.github.verhoek.xbrowsersync.controllers;

import com.github.verhoek.xbrowsersync.models.ServerInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class InfoController {

    @Value("${application.version}")
    private String version;

//
//
//    @RequestMapping(value = "/info", method = RequestMethod.OPTIONS)
//    ResponseEntity<?> infoOptions() {
//        return ResponseEntity
//                .ok()
//                .allow(HttpMethod.GET, HttpMethod.OPTIONS)
//                .build();
//    }


    @GetMapping("/info")
    public ResponseEntity<?> info() {
        var serverInfo = new ServerInfo(version);
        return new ResponseEntity<>(serverInfo, HttpStatus.OK);
    }
}
