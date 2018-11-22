package com.gupaoedu.vip.mongo.explorer.mvc.controller;

import com.alibaba.fastjson.JSON;
import com.gupaoedu.vip.mongo.explorer.common.constants.ExplorerConstants;
import com.gupaoedu.vip.mongo.explorer.service.IUFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.activation.MimetypesFileTypeMap;
import javax.core.common.ResultMsg;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 2018/8/20.
 */
@RestController
@RequestMapping("/web")
public class ExplorerController {

    @Autowired
    private IUFileService uFileService;

    @RequestMapping("/upload/progress.json")
    public ResponseEntity progress(@RequestParam("X-Progress-ID") String progressId,
                                 @RequestParam("callback") String callback){

        Progress progress = new Progress();
        progress.setFinish(1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        String json = JSON.toJSONString(progress);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/json"))
                .body(((callback == null) ? json : (callback +"(" + json + ")")));

    }

    @GetMapping(value="/preview/{id:\\w+}.file")
    public ResponseEntity preview(@PathVariable(name="id") String id){

        ResultMsg<?> resultMsg = uFileService.download(ExplorerConstants.ANONYMOUS,id);
        File file = (File) resultMsg.getData();

        MimetypesFileTypeMap mimeTypeMap = new MimetypesFileTypeMap();
        HttpHeaders headers = new HttpHeaders();
        try {
            //预览一定要设置Content-Type，否则打不开
            headers.add("Content-Type",mimeTypeMap.getContentType(file));

        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new FileSystemResource(file));
    }


    @RequestMapping("/download/{id:\\w+}.file")
    public ResponseEntity download(@PathVariable(name="id") String id){

        ResultMsg<?> resultMsg = uFileService.download(ExplorerConstants.ANONYMOUS,id);
        File file = (File) resultMsg.getData();

        MimetypesFileTypeMap mimeTypeMap = new MimetypesFileTypeMap();
        HttpHeaders headers = new HttpHeaders();
        try {

            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", "attachment; filename=" + new String(file.getName().getBytes("UTF-8"), "iso8859-1"));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Last-Modified", new Date().toString());
            headers.add("ETag", String.valueOf(System.currentTimeMillis()));
            headers.add("Content-Type",mimeTypeMap.getContentType(file));

        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/x-msdownload"))
                .body(new FileSystemResource(file));
    }



    @RequestMapping(value="/list.json")
    @ResponseBody
    public Mono<Object> list(@RequestParam(name="path") String path){
        ResultMsg<?> msg = uFileService.list(ExplorerConstants.FILE_GROUP_USERS,ExplorerConstants.ANONYMOUS, (path + "/").replaceAll("/+","/"));
        return Mono.just(msg);
    }

    @RequestMapping(value="/createFolder.json")
    public Mono<Object> createFolder(@RequestParam("path") String path,@RequestParam("name") String name){
        ResultMsg<?> msg = uFileService.createFolder(ExplorerConstants.FILE_GROUP_USERS,ExplorerConstants.ANONYMOUS, path.replace("my:","") + "/" + name);
        return Mono.just(msg);
    }


    /**
     * 在个人目录下上传文件
     */
    @PostMapping(value="/upload.json")
    public Mono<Object> upload(@RequestParam("upload") List<MultipartFile> files,  //默认支持多文件同时上传
                               @RequestParam("X-Progress-ID") String progressId,
                               @RequestParam("path") String path){  //文件上传到哪个路径
        final int bufferSize = 1024 * 100;
        Map<String, File> uploads = new HashMap<String, File>();
        for (MultipartFile file : files) {
            try{
                InputStream stream = file.getInputStream();
                String fileName = file.getOriginalFilename();
                //正式环境，我会改成一个多线程
                String filePath = uFileService.getTempPath().getData() + progressId + ".temp";
                File f = new File(filePath);
                OutputStream bos = new FileOutputStream(f);
                int bytesRead = 0;
                byte[] buffer = new byte[bufferSize];
                while ((bytesRead = stream.read(buffer, 0, bufferSize)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                bos.close();
                stream.close();
                uploads.put(fileName, f);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        ResultMsg<?> msg = uFileService.upload(ExplorerConstants.FILE_GROUP_USERS,ExplorerConstants.ANONYMOUS, path, uploads);
        return Mono.just(msg);
    }

    class Progress{
        private long received = 0;
        private long size = 0;
        private long finish = 0;

        public long getReceived() {
            return received;
        }
        public void setReceived(long received) {
            this.received = received;
        }
        public long getSize() {
            return size;
        }
        public void setSize(long size) {
            this.size = size;
        }
        public long getFinish() {
            return finish;
        }
        public void setFinish(long finish) {
            this.finish = finish;
        }
    }

}
