package composektx.navigationgenerator.processor.visitors

import androidx.navigation.navArgument
import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.ksp.toTypeName
import composektx.navigationgenerator.processor.models.DestinationInformation
import composektx.navigationgenerator.processor.models.DestinationParameter
import java.io.File

class FunctionDeclarationVisitor : KSEmptyVisitor<Unit, DestinationInformation>() {

    override fun defaultHandler(node: KSNode, data: Unit): DestinationInformation {
        TODO("Not yet implemented")
    }

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit): DestinationInformation {
        val rawFileProcessor = RawFileProcessor((function.location as FileLocation).filePath)

        val destinationName = function.simpleName.asString()
        val destinationPackage = function.packageName.asString()

        val destinationParameters = function.parameters
            .filterNot {
                it.name == null
            }
            .map {
                val name = it.name!!.asString()
                val isOptional = rawFileProcessor.isOptionalParameter((it.location as FileLocation).lineNumber, it.name.toString())
                val isDefault = it.hasDefault

                DestinationParameter(name, it.type.javaClass, isOptional, isDefault)
            }


        return DestinationInformation(destinationName, destinationPackage, destinationParameters)
    }

    /**
     * TODO
     */
    class RawFileProcessor(filePath: String) {
        private val lines: List<String>

        init {
            val file = File(filePath)
            require(file.exists())
            lines = file.readLines()
        }

        fun isOptionalParameter(lineNumber: Int, name: String): Boolean {

            val line = lines[lineNumber]

            val parameterDefinitionString = line
                .substringAfter(name)
                .substring(2)
                .substringBefore(',')
                .substringBeforeLast(')')

            return parameterDefinitionString.contains('?')
        }

    }
}



