package com.povio.exam.flowerspot.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController()
@RequestMapping("/images")
public class ImageController {

    @RequestMapping("/{imageName}")
    public void getImage(HttpServletResponse response, @PathVariable String imageName) throws IOException {
        try {
            final InputStream inputStream = new ClassPathResource("images/" + imageName).getInputStream();
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (FileNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

}
