package product.demo_wave.user;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * セッションから復元するためのカスタムMultipartFile実装
 */
public class CustomMultipartFile implements MultipartFile {
    
    private final byte[] bytes;
    private final String name;
    private final String originalFilename;
    private final String contentType;
    
    public CustomMultipartFile(byte[] bytes, String name, String originalFilename, String contentType) {
        this.bytes = bytes;
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }
    
    @Override
    public String getContentType() {
        return this.contentType;
    }
    
    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }
    
    @Override
    public long getSize() {
        return this.bytes.length;
    }
    
    @Override
    public byte[] getBytes() throws IOException {
        return this.bytes;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.bytes);
    }
    
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        // 今回は使用しないため省略
        throw new UnsupportedOperationException("transferTo not supported");
    }
}