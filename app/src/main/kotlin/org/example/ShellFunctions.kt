package org.example

import java.io.File

class ShellFunctions {
    fun cat(p: ParsedInput): String {
        if (p.arguments.isEmpty()) {
            return ""
        }

        val flagSet = p.flags.toSet()
        val showLineNo = flagSet.contains('n')
        val upperCase = flagSet.contains('u')
        val showEnds = flagSet.contains('E')

        try {

            var contents = ""
            var lineNo = 1
            File(p.arguments[0]).useLines() { lines ->
                lines.forEach { line ->
                    var outputLine = line
                    if (showLineNo) {
                        outputLine = "${lineNo}: $outputLine"
                        lineNo++
                    }
                    if (upperCase) {
                        outputLine = outputLine.uppercase()
                    }
                    if (showEnds) {
                        outputLine += "$"
                    }
                    contents += outputLine + "\n"
                }
            }

            return contents
        } catch (e: Exception) {
            return "Error: ${e.message}"
        }
    }

    fun stats(): String {
        Chess().getStats()
        return ""
    }

    fun ls(p: ParsedInput): String {
        val filePath = if (p.arguments.isNotEmpty()) p.arguments[0] else "."
        try {
            var contents = ""
            val dir = File(filePath)
            val files = dir.listFiles()

            if (files != null) {
                for (file in files) {
                    if (file.isDirectory) {
                        contents += "\u001B[32m" + file.name + "\n"
                    } else {
                        contents += "\u001B[0m" + file.name + "\n"
                    }

                }
            }
            contents += "\u001B[0m"
            return contents
        } catch (e: Exception) {
            return e.toString()
        }
    }
}

