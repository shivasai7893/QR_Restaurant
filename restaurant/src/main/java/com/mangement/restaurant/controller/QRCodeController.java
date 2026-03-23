package com.mangement.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mangement.restaurant.service.QRCodeService;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QRCodeController {

    private final QRCodeService qrCodeService;
    
    public QRCodeController(QRCodeService qrCodeService)
    {
    	this.qrCodeService=qrCodeService;
    }

    // GET /api/qr/generate/5
    // returns QR image for table 5
    @GetMapping(value = "/generate/{tableId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable int tableId) {

        byte[] qrImage = qrCodeService.generateTableQRCode(tableId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        // this makes browser download it as file
        headers.setContentDispositionFormData("attachment", "table_" + tableId + "_qr.png");

        return ResponseEntity.ok()
                .headers(headers)
                .body(qrImage);
    }

    // GET /api/qr/generate/all?totalTables=10
    // generates QR for all tables at once
    @GetMapping("/generate/all")
    public ResponseEntity<String> generateAllTables(@RequestParam int totalTables) {
        StringBuilder result = new StringBuilder();
        result.append("QR Code URLs generated:\n");

        for (int i = 1; i <= totalTables; i++) {
            result.append("Table ").append(i)
                  .append(" → http://localhost:8080/api/qr/generate/").append(i)
                  .append("\n");
        }

        return ResponseEntity.ok(result.toString());
    }
}