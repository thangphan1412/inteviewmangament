package fa.training.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/upload")
@Controller
public class UploadFileController {

    @PostMapping("/cloud")
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        Map config = new HashMap();
        config.put("cloud_name", "dfkvp7x35");
        config.put("api_key", "536593263684142");
        config.put("api_secret", "29mquvkAuRme0JdrlNhdiI2NG60");
        Cloudinary cloudinary = new Cloudinary(config);

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String fileUrl = (String) uploadResult.get("url");
        return ResponseEntity.ok(fileUrl);
    }

    private static final String APPLICATION_NAME = "My App";

    @PostMapping("/drive")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Set the refresh token obtained from the Google OAuth flow
        String refreshToken = "1//04FwqcX6qCiljCgYIARAAGAQSNwF-L9IrGSH2C_IVUmOJjprvcAZ_EQv6owo6yBtDbUiYx-wxWE2m6xgQwBv7pL9vmvEZ4KkCvZc";

        // Set the HTTP transport and JSON factory
        HttpTransport httpTransport = new com.google.api.client.http.javanet.NetHttpTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // Create a new Google credential using the refresh token
        Credential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setClientSecrets("693337025926-vo12r42hh26m4qa114i914j392vue7st.apps.googleusercontent.com", "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")
                .build()
                .setRefreshToken(refreshToken);

        // Build the Drive API client
        Drive drive = new Drive.Builder(httpTransport, jsonFactory, credential)
                .setHttpRequestInitializer(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) throws IOException {
                        credential.initialize(request);
                        request.setConnectTimeout(3 * 60000); // 3 minutes connect timeout
                        request.setReadTimeout(3 * 60000); // 3 minutes read timeout
                    }
                })
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Set the file name of the file you want to upload to Google Drive
        String fileName = file.getOriginalFilename();
        String folderId = "1etzbwU1C0tdSk1QFM5hqPkU15jlqLzWU";
        // Create a new file to upload
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(folderId));

        // Set the content of the file to upload
        InputStreamContent mediaContent = new InputStreamContent(
                file.getContentType(), file.getInputStream());

        // Create the upload request
        Drive.Files.Create create = drive.files().create(fileMetadata, mediaContent)
                .setFields("id");

        // Set the permissions of the uploaded file to public
        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        permission.setAllowFileDiscovery(false);

        // Execute the upload request and print the ID and webContentLink of the uploaded file
        File uploadedFile = create.execute();
        String fileId = uploadedFile.getId();
        drive.permissions().create(fileId, permission).execute();
        String urlLink = "https://drive.google.com/file/d/" + fileId;
        return new ResponseEntity<>(urlLink, HttpStatus.OK);
    }
}
