package com.example.fileuploadanddownload

import javax.persistence.*

@Entity
data class File(
    private var fileName:String,
    private var realName:String,
    private var fileDownloadUri:String,
    private var fileType:String?=null,
    private var size:Long
) :IdGenerator()


@MappedSuperclass
abstract class IdGenerator(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
)