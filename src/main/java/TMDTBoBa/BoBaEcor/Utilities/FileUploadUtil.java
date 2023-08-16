package TMDTBoBa.BoBaEcor.Utilities;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

public class FileUploadUtil {
    private final static Cloudinary cloudinary = Singleton.getCloudinary();
    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        String UPLOAD_DIRECTORY = Paths.get(".").normalize().toAbsolutePath()
                .toString();
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY + uploadDir);


        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
    public static String upLoadCloud(String fileName,
                                  MultipartFile multipartFile) throws IOException {
        var result = cloudinary.uploader().upload(multipartFile.getBytes(), (Map.of("public_id", fileName)));
        System.out.println(result);
        return  result.get("url").toString();
    }
}


