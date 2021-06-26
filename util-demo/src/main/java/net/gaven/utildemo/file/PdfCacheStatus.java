package net.gaven.utildemo.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * pdf分页缓存状态数据结构
 */

@ToString
public class PdfCacheStatus {
    @JsonIgnore
    private String dir;
    private String fileName;
    private long fileSize;
    /**
     * 缓存文件
     */
    private File file;
    @JsonIgnore
    private PDDocument sourceFile;
    private final AtomicBoolean isSuccess =new AtomicBoolean(true);
    private int totalPage;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime lastUpdate;
    private AtomicInteger usageCount = new AtomicInteger(1);
    @JsonIgnore
    private List<File> slices = new CopyOnWriteArrayList<>();

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public PDDocument getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(PDDocument sourceFile) {
        this.sourceFile = sourceFile;
    }

    public AtomicBoolean getIsSuccess() {
        return isSuccess;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public AtomicInteger getUsageCount() {
        return usageCount;
    }


    public List<File> getSlices() {
        return slices;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}