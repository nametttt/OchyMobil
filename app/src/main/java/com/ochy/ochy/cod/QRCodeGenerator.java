package com.ochy.ochy.cod;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {

    public static void generateQRCodeAndUpload(AirlineTicket airlineTicket, String splittedPathChild) {
        // Внесение данных в Firebase Database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("user").child(splittedPathChild).child("tickets");
        String ticketId = databaseRef.push().getKey();
        databaseRef.child(ticketId).setValue(airlineTicket);

        // Генерация QR-кода
        Bitmap qrCodeBitmap = generateQRCode(airlineTicket);

        // Преобразование QR-кода в массив байтов
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] qrCodeBytes = baos.toByteArray();

        // Генерация уникального имени файла для сохранения QR-кода в Firebase Storage
        String fileName = UUID.randomUUID().toString() + ".png";

        // Получение ссылки на Firebase Storage и создание пути для сохранения файла
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference qrCodeRef = storageRef.child("qr_codes").child(fileName);

        // Загрузка QR-кода в Firebase Storage
        qrCodeRef.putBytes(qrCodeBytes)
                .addOnSuccessListener(taskSnapshot -> {
                    // QR-код успешно загружен в Firebase Storage
                    // Получение ссылки на загруженный файл
                    qrCodeRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                // Получена ссылка на изображение QR-кода
                                String qrCodeUrl = uri.toString();
                                // Установка ссылки в объекте AirlineTicket
                                airlineTicket.setQrCodeUrl(qrCodeUrl);

                                // Обновление данных в Firebase Database
                                if (qrCodeUrl != null && !qrCodeUrl.isEmpty()) {
                                    // Обновление данных в Firebase Database
                                    airlineTicket.setQrCodeUrl(qrCodeUrl);
                                    databaseRef.child(ticketId).setValue(airlineTicket);
                                } else {
                                    // Ошибка получения ссылки на изображение QR-кода
                                    // Обработка ошибки
                                }
                            })
                            .addOnFailureListener(e -> {
                                // Ошибка получения ссылки на изображение QR-кода
                                // Обработка ошибки
                            });
                })
                .addOnFailureListener(e -> {
                    // Ошибка загрузки QR-кода в Firebase Storage
                    // Обработка ошибки
                });
    }

    private static Bitmap generateQRCode(AirlineTicket airlineTicket) {
        String qrCodeData = generateQRCodeData(airlineTicket);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 500;
        int height = 500;

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height);
            Bitmap qrCodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrCodeBitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return qrCodeBitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String generateQRCodeData(AirlineTicket airlineTicket) {
        // Генерация данных для QR-кода в формате, который вам необходим
        String MAsr = airlineTicket.getMarshr().toString();
        String date = airlineTicket.getDates().toString();
        String FI = airlineTicket.getDocFI().toString();
        String documentType = airlineTicket.getDocumentType().toString();
        String documentNumber = airlineTicket.getDocumentNumber().toString();
        String flightNumber = airlineTicket.getFlightNumber();
        String seatNumber = airlineTicket.getSeatNumber();

        // Возвращаемая строка данных для QR-кода
        return MAsr + "\n"+ date+"\n"+ FI + "\n"+ documentType + ";\n" + documentNumber + ";\nНомер рейса: " + flightNumber + ";\nВаше место: " + seatNumber;
    }
}
