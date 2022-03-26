package com.example.fileuploadanddownload

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

interface IFileService{
    fun  save(file: MultipartFile):String
}

@Service
class FileService(properties: StorageProperties,private val fileRepository:FileRepository):IFileService{
    private val  rootLocation: Path
    init {
        this.rootLocation = Paths.get(properties.location)
    }

    override fun save(file: MultipartFile):String {
        val filename:String = StringUtils.cleanPath(file.originalFilename.toString())
        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file $filename")
            }
            if (filename.contains("..")) {
                throw StorageException(
                    "Cannot store file with relative path outside current directory $filename"
                )
            }
            val name:String = UUID.randomUUID().toString().replace("-","") +filename
            Files.copy(
                file.inputStream, this.rootLocation.resolve(name),
                StandardCopyOption.REPLACE_EXISTING
            )
            val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("img/upload-dir/")
                .path(name)
                .toUriString()

            val saveFile = File(
                fileName =  name,
                realName = filename,
                fileDownloadUri = fileDownloadUri,
                fileType = file.contentType,
                size = file.size
            )
            fileRepository.save(saveFile)
            return name;
        } catch (e: IOException) {
            throw StorageException("Failed to store file $filename", e)
        }
    }



    fun loadFileAsResourse(filename :String) : Resource {
        val filePath: Path = this.rootLocation.resolve(filename).normalize()
        val resource: Resource = UrlResource(filePath.toUri())
        if (resource.exists()){
            return resource
        }else{
            throw MyFileNotFoundException("file not fount $filename")
        }
    }


}