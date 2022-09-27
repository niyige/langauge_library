package com.skyroam.language_script

object Config {

    // 导入导出时的excel路径
    const val OUTPUT_PATH = "./module_language_script/output.xlsx"
    const val INPUT_PATH = "./module_language_script/input.xlsx"

    // 是否只导入指定语言
    const val useInclude = false

    // 导入指定语言
    val importInclude = listOf<String>(
        "values-ja",
        "values-it"
    )

    // 导出时排除某些name
    val exportExcludeNames = listOf<String>(
    )
    // 是否将基准语言的值相同（即使name不同）的string也视为同一个string
    // 比如以中文为基准，那么<string name="name1">相同值</string> 等同于 <string name="name2">相同值</string>
    // 这样的话可以以某一语言为基准[导出时去重/导入时恢复]恢复内容重复的string
    // 导入导出时应该用同一个基准
    const val isBaseOnWord = true
    // 去重/恢复 基准语言，找不到用默认
    const val BASE_LANG = "values"
    const val DEFAULT_LANG = "values"
}
