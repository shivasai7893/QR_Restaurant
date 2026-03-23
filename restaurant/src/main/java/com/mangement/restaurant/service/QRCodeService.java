package com.mangement.restaurant.service;

import com.google.zxing.WriterException;
import com.mangement.restaurant.utility.QRCodeGenerator;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QRCodeService {

    // base URL of your app — change this when you deploy
    private static final String BASE_URL = "http://localhost:8080/menu";

    public byte[] generateTableQRCode(int tableId) {
        try {
            // QR will encode this URL
            // when scanned → opens menu page for that table
            String url = BASE_URL + "?tableId=" + tableId;

            return QRCodeGenerator.generateQRCode(url, 300, 300);

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR code for table: " + tableId, e);
        }
    }

    public byte[] generateQRCodeForUrl(String customUrl) {
        try {
            return QRCodeGenerator.generateQRCode(customUrl, 300, 300);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }
}