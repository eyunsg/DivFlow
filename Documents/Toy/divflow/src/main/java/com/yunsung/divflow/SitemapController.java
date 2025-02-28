package com.yunsung.divflow;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SitemapController {

    @GetMapping("/sitemap.xml")
    public ResponseEntity<ClassPathResource> sitemap() {
        // resources 폴더에 있는 sitemap.xml 파일을 읽어옵니다.
        ClassPathResource resource = new ClassPathResource("static/sitemap.xml");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)  // XML로 응답
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"sitemap.xml\"")  // 'inline' 설정
                .body(resource);
    }
}