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
}

