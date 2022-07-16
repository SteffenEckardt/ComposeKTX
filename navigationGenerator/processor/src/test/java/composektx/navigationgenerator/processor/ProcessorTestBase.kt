package composektx.navigationgenerator.processor

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import composektx.navigationgenerator.processor.provider.DestinationAnnotationProcessorProvider
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.io.TempDir
import java.io.File

abstract class ProcessorTestBase {

    @field:TempDir
    lateinit var tempFolder: File


    protected fun compile(vararg source: SourceFile) = KotlinCompilation().apply {
        sources = source.toList()
        symbolProcessorProviders = listOf(DestinationAnnotationProcessorProvider())
        workingDir = tempFolder
        inheritClassPath = true
        verbose = true
    }.compile()

    protected fun assertSourceEquals(@Language("kotlin") expected: String, actual: String) {
        val cleanExpected = expected.trim().replace(" ", "").replace("\t", "").trimIndent()
        val cleanActual = actual.trim().replace(" ", "").replace("\t", "").trimIndent()

        if (cleanActual == cleanExpected) {
            assertEquals(cleanExpected, cleanActual)
        } else {
            assertEquals(expected, actual)
        }
    }

    protected fun KotlinCompilation.Result.sourceFor(fileName: String) = kspGeneratedSources()
        .find { it.name == fileName }
        ?.readText()
        ?: throw IllegalArgumentException("Could not find file $fileName in ${kspGeneratedSources()}")

    private fun KotlinCompilation.Result.kspGeneratedSources(): List<File> {
        val kspWorkingDir = workingDir.resolve("ksp")
        val kspGeneratedDir = kspWorkingDir.resolve("sources")
        val kotlinGeneratedDir = kspGeneratedDir.resolve("kotlin")
        val javaGeneratedDir = kspGeneratedDir.resolve("java")
        return kotlinGeneratedDir.walk().toList() +
                javaGeneratedDir.walk().toList()
    }

    private val KotlinCompilation.Result.workingDir: File
        get() = checkNotNull(outputDirectory.parentFile)


}