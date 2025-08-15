package org.example

import java.io.File

/**
 * All shell builtin recreations live in this class
 *
 * TODO pass @param cwd to all functions and @return Pair<String, String>
 * with both the function output and the cwd (to handle cases where it changes)
 * This would alter the logic in the repl loop in App.kt
 */
class ShellFunctions {

    /**
     * cat builtin function
     * @param p arguments and flags
     * TODO handle multiple arguments
     */
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

    /**
     * Simply displays my chess stats, not really related to the rest of the project
     */
    fun stats(): String {
        Chess().getStats()
        return ""
    }

    /**
     * Recreation of ls builtin
     * @param cwd is required in order to display the right output after changing directory
     * @param p the arguments and flags
     *
     * Uses ASCII green for directories "\u001B[32m"
     */
    fun ls(cwd: String, p: ParsedInput): String {
        val filePath = if (p.arguments.isNotEmpty()) p.arguments[0] else cwd
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

    /**
     * cd builtin function
     * @param cwd is required because Kotlin does not really change directories
     * @param p arguments and flags
     * @return the new cwd to display in prompt and pass to other functions as needed
     */
    fun cd(cwd: String, p: ParsedInput): Pair<String, String> {
        val newDir = File(cwd, p.arguments[0]).canonicalFile

        return if (newDir.exists() && newDir.isDirectory) {
            Pair(newDir.toString(), "")
        } else {
            Pair(cwd, "No such directory \"${p.arguments[0]}\"")
        }
    }

    /**
     * Display a message for unsupported or unknown commands
     * @param p ParsedInput
     * @return error String
     */
    fun unknownCommand(p: ParsedInput): String {
        return "Unrecognized command: ${p.command}"
    }
}

