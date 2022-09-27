package com.skyroam.language_script

import com.skyroam.language_script.helper.ExcelHelper
import com.skyroam.language_script.helper.WordHelper
import com.skyroam.language_script.logger.Log
import java.io.File

/**
 * 从excel导入string
 */
const val TAG = "ImportFromExcel"
fun main() {
    val f = File("./")
    val moduleDirList = f.listFiles { file ->
        Log.d(TAG, "file: ${file.absolutePath}")
        // 过滤隐藏文件
        if (file.name.startsWith(".")) {
            Log.d(TAG, "file -> 1")
            return@listFiles false
        }
        // 过滤文件
        if (!file.isDirectory) {
            Log.d(TAG, "file -> 2")
            return@listFiles false
        }
        val f1 = File(file.absolutePath, "src/main/res/values/strings.xml")
        if (f1.exists()) {
            Log.d(TAG, "file -> 3")
            return@listFiles true
        }
        Log.d(TAG, "file -> 4")
        return@listFiles false
    }
    // 打印相关可以使用的模块
    Log.i("ImportFromExcelScript", moduleDirList?.map { it.name }.toString())

    val sheetsData = ExcelHelper.getSheetsData(Config.INPUT_PATH)
//    Log.i("ImportFromExcelScript", sheetsData.toString())
    moduleDirList.forEach {
        // 模块名对应excel表数据  <name，<语言目录，值>>
        val newData = sheetsData[it.name]
        Log.i("ImportFromExcelScript", "${it.name} -> ${newData.toString()}")
        if (newData != null) {
            val newLangNameMap = WordHelper.revertResData(newData)
            val parentFile = File(it, "src/main/res")
            // 项目中读出的string map，<语言目录（如values），<name，word>>
            var resLangNameMap = WordHelper.collectRes(parentFile)
            WordHelper.mergeLangNameString(newLangNameMap, resLangNameMap)
            if (Config.useInclude) {
                resLangNameMap = resLangNameMap.filterKeys { lang ->
                    Config.importInclude.contains(lang)
                } as LinkedHashMap<String, LinkedHashMap<String, String>>
            }
            WordHelper.importWords(resLangNameMap, parentFile)
        }
    }
//    println("ImportFromExcelScript" + moduleDirList?.map { it.name }.toString())
}