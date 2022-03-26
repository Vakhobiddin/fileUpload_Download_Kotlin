package com.example.fileuploadanddownload

import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/img")
class FileController(private val fileService: FileService){
    @PostMapping("/upload")
    fun storeImg(file: MultipartFile):String{
        return fileService.save(file);
    }

    @GetMapping("/upload-dir/{fileName}")
    fun  downloadImg(@PathVariable fileName :String, request: HttpServletRequest): ResponseEntity<Resource> {
        val downloadFile = fileService.loadFileAsResourse(fileName)
        var contentType: String? = null
        contentType = request.servletContext.getMimeType(downloadFile.file.absolutePath);

        return  ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\"" + downloadFile.getFilename() + "\"")
            .body(downloadFile)
    }

}