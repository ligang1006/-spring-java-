package net.gaven.utildemo.pdf;


import net.coobird.thumbnailator.Thumbnails;
import net.gaven.utildemo.file.FileUtil;
import net.gaven.utildemo.file.PdfCacheStatus;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.bouncycastle.jce.provider.X509CertParser;
import org.bouncycastle.x509.util.StreamParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class PDFUtils {
    private static Logger log = LoggerFactory.getLogger(PDFUtils.class);

    /**
     * 获取pdf的总页码
     * @param data
     */
    public static Integer getTotalPage(byte[] data) {
        try {
            PDDocument document = PDDocument.load(data);
            return document.getNumberOfPages();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String splitPdf(int pageNum, String source, String dest) {
        Path path = FileUtil.get(source);
        File indexFile = path.toFile();
        Path path1 = FileUtil.get(dest);
        File outFile = path1.toFile();
        PDDocument document = null;
        try {
            document = PDDocument.load(indexFile);
            // document.getNumberOfPages();
            Splitter splitter = new Splitter();
            splitter.setStartPage(pageNum);
            splitter.setEndPage(pageNum);
            List<PDDocument> pages = splitter.split(document);
            ListIterator<PDDocument> iterator = pages.listIterator();
            while (iterator.hasNext()) {
                PDDocument pd = iterator.next();
                if (outFile.exists()) {
                    outFile.delete();
                }
                pd.save(outFile);
                pd.close();
                if (outFile.exists()) {
                    return outFile.getPath();
                }
            }
            document.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void pdfFileToImage(InputStream inputStream, HttpServletResponse response) {
        PDDocument document = null;
        try (ServletOutputStream sos = response.getOutputStream()) {
            document = PDDocument.load(inputStream, MemoryUsageSetting.setupTempFileOnly());
            PDFRenderer renderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();
            if (pageCount > 0) {
                response.setHeader("pageCount", String.valueOf(pageCount));
                BufferedImage image = renderer.renderImageWithDPI(0, 150.0F, ImageType.RGB);
                image.flush();
                // 将图像输出到Servlet输出流中。
                ImageIO.write(image, "png", sos);
            }
        } catch (IOException e) {
            log.error("pdf file to image error :{}", e.getMessage(), e);
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.error("read pdf stream error", e);
                }
            }
        }
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }


    public static void partitionPdfFile(InputStream inputStream, HttpServletResponse response, int from) {
        PDDocument document = null;
        int end = from + 10;
        try (ServletOutputStream sos = response.getOutputStream()) {
            document = PDDocument.load(inputStream, MemoryUsageSetting.setupTempFileOnly());
            int total = document.getNumberOfPages();
            if (from > total) {
                return;
            }
            if (end > total) {
                if (from == 0) {
                    return;
                }
                end = total;
            }
            response.setHeader("pageCount", String.valueOf(total));
            PDDocument doc = new PDDocument();
            for (int j = from; j < end; j++) {
                doc.addPage(document.getPage(j));
            }
            doc.save(sos);
            doc.close();

        } catch (Exception e) {
            log.error("partition pdf file error:{}", e.getMessage(), e);
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.error("read pdf stream error", e);
                }
            }
        }
    }



    private static long COMPRESS_THRESHOLD = 180*1024;//大于此门限则开启图片压缩
    private static long SPLIT_THRESHOLD = 2*1024*1024;//小于此门限则不进行分页
    private static float PDF_SCALE = 2f;//pdf截图相对于dpi=72的比例

    /**
     * 文件分页分割成文件碎片保存到文件系统中,
     * @warn 如果缓存量过大则需要手动清除或定时清理
     */
    public static PdfCacheStatus partitionPdfFile(InputStream inputStream,  long size ,String splitDir, String fileName, int pagePatch) {
        PdfCacheStatus status = new PdfCacheStatus();
        try {
            PDDocument document = PDDocument.load(inputStream, MemoryUsageSetting.setupTempFileOnly());
            return  partitionPdfFile(document,size,splitDir,fileName,pagePatch);
        } catch (IOException e) {
            status.getIsSuccess().set(false);
            log.error("read pdf stream error", e);
        }
        return status;
    }

    public static PdfCacheStatus partitionPdfFile(PDDocument document,  long size ,String splitDir, String fileName, int pageBatchSize) {
        PdfCacheStatus status = new PdfCacheStatus();
        try {
            /*
                @Step 1 准备返回结果
             */
            int total = document.getNumberOfPages();
            long avg= size/total;
            log.debug("分页pdf总体大小{} 总页数{} 平均每页大小{}",size,total,avg);
            status.setTotalPage(total);
            status.setFileName(fileName);
            status.setFileSize(size);
            status.setLastUpdate(LocalDateTime.now());
            status.setDir(splitDir);
            status.setSourceFile(document);
            if (size < SPLIT_THRESHOLD){//比阈值还小的就不要分页了
                status.getIsSuccess().set(false);
                return status;
            }
            /*
                @Step 2 分页开始
             */
            PDFRenderer renderer = new PDFRenderer(document);
            int loopEnd =
                    total % pageBatchSize == 0
                    ? Math.floorDiv(total, pageBatchSize)
                    : Math.floorDiv(total, pageBatchSize) + 1;
            AtomicInteger batchIndex=new AtomicInteger(-1);
            Stream.generate(batchIndex::incrementAndGet).limit(loopEnd)
//                    .parallel()
                    .forEach(i->{
                if (!status.getIsSuccess().get()){
                    log.trace("有失败的就整体退出 后面的计算没有意义了");
                    return;
                }
                int from = i == 0 ? 0 : i * pageBatchSize;
                if (from > total) {
                    return;
                }
                int end = Math.min(total, ( (i+1) * pageBatchSize));
                try {
                    File slice = new File(splitDir + "/" + fileName + "_P" + from + ".pdf");
                    FileUtil.setFilePermission(slice);
                    FileOutputStream os = new FileOutputStream(slice);
                    PDDocument doc = new PDDocument();
                    for (int j = from; j < end; j++){
//                        log.debug("Thread[{}] is running index:{}",Thread.currentThread().getName(),j);
                        PDPage page =  document.getPage(j) ;
                        if (avg > COMPRESS_THRESHOLD){ //如果平均单页大小超过阈值,就进行整体压缩
                            try {
                                page = copyPageFormat(document.getPage(j)) ;//复制原页面的样式
                                BufferedImage image = renderer.renderImage(j,PDF_SCALE);
                                File imgFileMini = new File(splitDir + "/" + fileName + "_P" + j +".jpg");
                                FileUtil.setFilePermission(imgFileMini);
                                compressPicForScale(image, imgFileMini);
                                PDImageXObject pdImage = PDImageXObject.createFromFileByExtension(imgFileMini,document);
                                //新建page能减小存储大小
                                PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.OVERWRITE, true);
                                contents.drawImage(pdImage, 0, 0, page.getCropBox().getWidth()  , page.getCropBox().getHeight()  );
                                contents.close();
                                if (! imgFileMini.delete()){
                                    imgFileMini.deleteOnExit();
                                }

                            } catch (IOException e) {
                                log.info("pdf convert fail : {}",e.getMessage());
                            }
                        }
                        doc.addPage(page);
                    }
                    if (doc.getNumberOfPages()>0){
                        doc.save(os);
                        doc.close();
                        status.getSlices().add(slice);
                    }
//                    if (status.getSlices().size() % 20==0 || status.getSlices().size()==(loopEnd-1) )
//                    log.info("======================> pdf分割文件进度{}%",(float)status.getSlices().size()*100/(loopEnd-1));
                } catch (Exception e) {
                    status.getIsSuccess().set(false);
                    log.error("partition pdf file error:{}", e.getMessage(), e);
                }
            });

            document.close();
        } catch (IOException e) {
            log.error("read pdf stream error", e);
        }
        return status;

    }
    private static PDPage copyPageFormat(PDPage page){

        PDPage importedPage = new PDPage();
        importedPage.setCropBox(page.getCropBox());
        importedPage.setMediaBox(page.getMediaBox());
        importedPage.setRotation(page.getRotation());
        if (page.getResources() != null && !page.getCOSObject().containsKey(COSName.RESOURCES))
        {
            log.warn("inherited resources of source document are not imported to destination page");
        }
        return importedPage;
    }


    /**
     * 根据指定大小和指定精度压缩图片
     *
     * @param bim     源图片
     * @param desFile     目标图片地址
     * @return 目标文件路径
     */
    private static void compressPicForScale(BufferedImage bim, File desFile) {

        try {

            //获取图片信息
            int srcWidth = bim.getWidth();
            int srcHeight = bim.getHeight();

            //先转换成jpg
            Thumbnails.Builder builder = Thumbnails.of(bim).outputFormat("jpg");

            // 指定大小（宽或高超出会才会被缩放）
            builder.size(srcWidth, srcHeight);

            // 写入到内存
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); //字节输出流（写入到内存）
            builder.toOutputStream(baos);

            // 递归压缩，直到目标文件大小小于desFileSize
            byte[] bytes = commpressPicCycle(baos.toByteArray(), 0.55);

            // 输出到文件
            FileOutputStream fos = new FileOutputStream(desFile);
            fos.write(bytes);
            fos.close();
            log.debug("压缩图片：" + desFile.getPath() + "，大小" + desFile.length() / 1024 + "kb");
        } catch (Exception e) {
            log.info("压缩文件失败:{}", e.getMessage());
        }
    }

    private static byte[] commpressPicCycle(byte[] bytes, double accuracy) throws IOException {
        // File srcFileJPG = new File(desPath);
        long srcFileSizeJPG = bytes.length;
        // 2、判断大小，如果小于180kb，不压缩；如果大于等于500kb，压缩
        if (srcFileSizeJPG <= COMPRESS_THRESHOLD) {
            return bytes;
        }
        // 计算宽高
        BufferedImage bim = ImageIO.read(new ByteArrayInputStream(bytes));
        int srcWdith = bim.getWidth();
        int srcHeigth = bim.getHeight();
        int desWidth = new BigDecimal(srcWdith).multiply(
                new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(srcHeigth).multiply(
                new BigDecimal(accuracy)).intValue();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //字节输出流（写入到内存）
        Thumbnails.of(new ByteArrayInputStream(bytes)).size(desWidth, desHeight).outputQuality(accuracy).toOutputStream(baos);
        return commpressPicCycle(baos.toByteArray(), accuracy);
    }

    /**
     * Get digital signatures from pdf document
     *
     * @param docBytes pdf document in byte array
     * @return list of signatures in base64 string
     */

    public static List<SignInfo> getSignsAsBase64String(byte[] docBytes) {
        List<SignInfo> signList = new ArrayList<>();
        Set<String> existSignSignatures = new HashSet<>();
        if (docBytes.length == 0) {
            return signList;
        }
        // only deal with pdf doc for now
//        if (! isPdf(docBytes)){
//            throw new RuntimeException("Not a pdf document!");
//        }
        try {
            ByteArrayInputStream inputStream =
                    new ByteArrayInputStream(docBytes);
            PDDocument doc = PDDocument.load(inputStream);
            List<PDSignature> signs = doc.getSignatureDictionaries();
            for (PDSignature sign : signs) {
//                System.out.println(sign.getSignDate().getTimeInMillis());
//                System.out.println(sign.getContactInfo());
//                sign.getCOSObject().getValues().forEach(cos -> {
//                    System.out.println(cos.toString());
//                });
                byte[] signValue = sign.getContents(docBytes);
                SignInfo signInfo = new SignInfo();
//                signInfo.setSignature(Base64.encodeBase64URLSafeString(signValue));
                signInfo.setTimeStamp(sign.getCOSObject().getNameAsString("M"));
                X509CertParser certParser = new X509CertParser();
                certParser.engineInit(new ByteArrayInputStream(signValue));
                Collection<Certificate> certs = certParser.engineReadAll();
                if (certs == null || certs.size() == 0) {
                    continue;
                }
                X509Certificate certificate = (X509Certificate) certs.iterator().next();
                if (certificate == null) {
                    continue;
                }
//                System.out.println(certificate.getSubjectDN());
                signInfo.setSerialNumber(certificate.getSerialNumber().toString());
                X500Principal principal = certificate.getSubjectX500Principal();
                if (principal == null) {
                    continue;
                }
                //签章对应的证书的所有者
                LdapName ldapDN = new LdapName(principal.getName());
                boolean isIndividual = false;
                for (Rdn rdn : ldapDN.getRdns()) {
                    if ("OU".equals(rdn.getType())) {
                        if (String.valueOf(rdn.getValue()).contains("Individual")
                        ) {
                            isIndividual = true;
                        } else {
                            if (rdn.getValue().toString().contains("@")) {
                                String[] signNos = rdn.getValue().toString().split("@");
                                Arrays.stream(signNos).forEach(it -> {
                                    if (it.length() > 14) {//身份证号15位或18位,企业信用号码18位
                                        signInfo.setSignerNo(it);
                                    }
                                });
                            } else {
                                signInfo.setSignerNo(rdn.getValue().toString());
                            }
                        }
                    }
                    if ("CN".equals(rdn.getType())) {
                        signInfo.setSignerName(rdn.getValue().toString());
                    }
                }
                if (isIndividual) {
                    String[] names = signInfo.getSignerName().split("@");
                    if (names.length > 2) {
                        signInfo.setSignerNo(names[2]);
                        signInfo.setSignerName(names[1]);
                    }
                }
                if (existSignSignatures.contains(signInfo.getSignature())) {
                    log.debug("signature has been collected before! skipping this one:" +signInfo.toString());
                } else {
                    existSignSignatures.add(signInfo.getSignature());
                    signList.add(signInfo);
                }
            }
            doc.close();
        } catch (IOException | InvalidNameException | StreamParsingException e) {
            log.error(e.getMessage(), e);
        }
        return signList;
    }


}
