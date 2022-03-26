package com.example.fileuploadanddownload

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix =  "storage")
class StorageProperties {

    var location = "\\upload-dir"
}