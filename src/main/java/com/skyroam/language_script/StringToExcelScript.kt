package com.skyroam.language_script


import com.skyroam.language_script.helper.ExcelHelper
import com.skyroam.language_script.helper.WordHelper
import com.skyroam.language_script.logger.Log
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*

/**
 * 导出项目string到excel
 */
fun main() {
    // 查找当前项目的模块
    val f = File("./")
    val moduleDirList = f.listFiles { file ->
        // 过滤隐藏文件
        if(file.name.equals("app")) return@listFiles false  //过滤掉app里面的
        if (file.name.startsWith(".")) return@listFiles false
        if (!file.isDirectory) return@listFiles false
        val f1 = File(file.absolutePath, "src/main/res/values/strings.xml")
        if (f1.exists()) return@listFiles true

        return@listFiles false
    }

    Log.i("ExportStrings2ExcelScript", moduleDirList?.map { it.name }.toString())
    val excelWBook = XSSFWorkbook()
    moduleDirList.forEachIndexed { index, it ->

        // 解析当前项目的多语言内容 <语言目录（如values），<name，word>>
        val hashMap = WordHelper.collectRes(File(it, "src/main/res"))
        if (hashMap.isEmpty())
            return@forEachIndexed
        // 将资源转换结构 <name，<语言目录，值>>
        val nameLangMap = WordHelper.transformResData(hashMap)
        if (nameLangMap.isEmpty())
            return@forEachIndexed
        val resData = WordHelper.processSameWords(nameLangMap)

        ExcelHelper.resource2File(
            Config.OUTPUT_PATH,
            it.name,
            hashMap.keys.toList() as ArrayList<String>,
            resData, excelWBook, index, (moduleDirList.size - 1)
        )
    }

}