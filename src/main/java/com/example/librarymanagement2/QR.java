package com.example.librarymanagement2;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.WriterException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QR {

    public static void main(String[] args) {
        String data = "https://books.google.com/books?id=<ID_SÁCH>"; // URL sách hoặc dữ liệu khác
        String filePath = "book_qr.png"; // Đường dẫn tệp hình ảnh mã QR

        try {
            generateQRCode(data, filePath);
            System.out.println("QR Code generated successfully!");
        } catch (WriterException | IOException e) {
            System.err.println("Error generating QR Code: " + e.getMessage());
        }
    }

    public static void generateQRCode(String data, String filePath) throws WriterException, IOException {
        int width = 300; // Chiều rộng mã QR
        int height = 300; // Chiều cao mã QR
        String fileType = "PNG"; // Định dạng tệp hình ảnh

        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.MARGIN, 1); // Giới hạn lề

        // Tạo mã QR từ dữ liệu
        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hintMap);

        // Chuyển đổi BitMatrix thành BufferedImage
        BufferedImage image = toBufferedImage(bitMatrix);

        // Lưu hình ảnh vào tệp
        ImageIO.write(image, fileType, new File(filePath));
    }

    // Chuyển đổi BitMatrix thành BufferedImage
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image.setRGB(j, i, matrix.get(j, i) ? 0x000000 : 0xFFFFFF); // Đen và trắng
            }
        }
        return image;
    }
}
