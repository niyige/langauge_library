package com.skyroam.language_script.bean

data class CsvBean(
    var head: ArrayList<String>, // 头,语言目录
    var data: LinkedHashMap<String, LinkedHashMap<String, String>> // <中文，<语言目录，值>>
)
