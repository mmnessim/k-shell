package org.example

import java.io.File

class ShellFunctions {
    fun cat(p: ParsedInput): String {
        if (p.arguments.isEmpty()) {
            return ""
        }

        try {
            var contents: String = ""
            if (!p.flags.isEmpty() && p.flags.contains('n')) {
                var lineNo = 1
                File(p.arguments[0]).useLines { lines ->
                    lines.forEach { line ->
                        contents += lineNo.toString() + ": " + line + "\n"
                        lineNo++
                    }
                }
            } else {
                File(p.arguments[0]).useLines { lines ->
                    lines.forEach { line ->
                        contents += line + "\n"
                    }
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

