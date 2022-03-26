package com.example.fileuploadanddownload

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class StorageException : RuntimeException {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}
class StorageFileNotFoundException : StorageException {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class MyFileNotFoundException : java.lang.RuntimeException {
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
}