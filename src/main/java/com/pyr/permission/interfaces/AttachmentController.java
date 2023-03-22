package com.pyr.permission.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// TODO: 2023/3/21  文件上传支阿里云  https://blog.csdn.net/weixin_43591980/article/details/109412083
@Slf4j
@RestController
public class AttachmentController {
    private static final String DATE_FORMAT = "MM/dd/yyyy_HH-mm-ss";
    private static final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    /**
     * 将指定目录的单个文件下载到浏览器
     * 前端： console: window.location.href = " http://localhost:9099/file/download";
     */
    @GetMapping("file/download")
    public void downloadDevtool(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File(getFileUrls().get(0));
        String filename = "测试文件下载";
        response.setContentType("application/x-download");
        response.setHeader("content-Disposition", "attachment;filename=" + filename);
        InputStream in = null;
        try {
            in = Files.newInputStream(file.toPath());
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 将指定目录的多个文件下载到浏览器，并打包成zip
     * 前端： console: window.location.href = " http://localhost:9099/file/batch/download";
     */
    @GetMapping("file/batch/download")
    public void zipFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String zipFileName = "压缩包" + formatter.format(new Date()) + ".zip";
        BufferedOutputStream bos;
        FileInputStream in;
        ZipOutputStream out;
        try {
            setResponse(response, zipFileName, request);
            bos = new BufferedOutputStream(response.getOutputStream());
            out = new ZipOutputStream(bos);
            writeToZip(out);
            out.close();
            bos.close();
            log.info("========= 文件压缩成功 ============");
        } catch (Exception e) {
            throw new Exception("zipFile_error" + this.getClass().getSimpleName() + "zipfile_download_error");
        }
    }

    private static void writeToZip(ZipOutputStream out) throws IOException {
        FileInputStream in;
        for (String str : getFileUrls()) {
            File file = new File(str);
            if (!file.exists()) {
                log.error("文件被删除");
                continue;
            }
            ZipEntry zEntry = new ZipEntry(file.getName());
            out.putNextEntry(zEntry);
            in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }
    }

    private static List<String> getFileUrls() {
        String property = System.getProperty("user.dir");
        String path1 = property + "/src/main/resources/file/a.txt";
        String path2 = property + "/src/main/resources/file/b.txt";
        return Arrays.asList(path1, path2);
    }

    private static void setResponse(HttpServletResponse response, String zipFileName, HttpServletRequest request) {
        response.reset();
        response.setContentType("multipart/form-data");
        //response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("utf-8");
        String agent = request.getHeader("USER-AGENT");
        zipFileName = getZipFileName(zipFileName, agent);
        response.setHeader("Content-disposition", "attachment;filename=" + zipFileName + ".zip");
    }

    private static String getZipFileName(String zipFileName, String agent) {
        try {
            //针对IE或者以IE为内核的浏览器：
            if (agent.contains("MSIE") || agent.contains("Trident")) {
                zipFileName = java.net.URLEncoder.encode(zipFileName, "UTF-8");
            } else {
                //非IE浏览器的处理：
                zipFileName = new String(zipFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipFileName;
    }
}
