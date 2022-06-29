package composektx.navigationgenerator.processor.processor

import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import composektx.navigationgenerator.annotation.Destination
import composektx.navigationgenerator.processor.visitors.FunctionDeclarationVisitor

class DestinationAnnotationProcessor(
    private val options: Map<String, String>,
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {

        val symbolsWithAnnotation = resolver.getSymbolsWithAnnotation(Destination::class.qualifiedName!!)
        val unableToProcess = symbolsWithAnnotation.filterNot { it.validate() }

        val functionNames = symbolsWithAnnotation
            .filter { it is KSFunctionDeclaration && it.validate() }
            .map { it.accept(FunctionDeclarationVisitor(), Unit) }

        functionNames.forEach {
            logger.info("Function \"$it\" discovered")
        }

        return unableToProcess.toList()
    }

}