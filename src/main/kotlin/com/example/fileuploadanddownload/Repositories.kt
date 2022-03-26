package com.example.fileuploadanddownload

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FileRepository: JpaRepository<File, Long> {
    fun findFileByFileDownloadUri(fileUri:String):String
}