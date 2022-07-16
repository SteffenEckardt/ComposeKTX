package composektx.navigationgenerator.processor.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.writeTo
import composektx.navigationgenerator.annotation.Destination
import composektx.navigationgenerator.processor.models.DestinationInformation
import composektx.navigationgenerator.processor.models.Route
import composektx.navigationgenerator.processor.visitors.FunctionDeclarationVisitor

class DestinationAnnotationProcessor(
    private val options: Map<String, String>,
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    companion object {
        const val NavDestinationClassName = "NavDestinations"
    }

    private val functionDeclarationVisitor = FunctionDeclarationVisitor()

    private val routes = mutableListOf<PropertySpec>()

    override fun process(resolver: Resolver): List<KSAnnotated> {

        val symbolsWithAnnotation = resolver.getSymbolsWithAnnotation(Destination::class.qualifiedName!!)
        val unableToProcess = symbolsWithAnnotation.filterNot {
            it.validate()
        }

        symbolsWithAnnotation
            .filter { it.validate() }
            .mapNotNull {
                if (it is KSFunctionDeclaration) {
                    functionDeclarationVisitor.visitFunctionDeclaration(it, Unit)
                } else {
                    null
                }
            }
            .map {
                when {
                    it.destinationParams.isEmpty() -> PropertySpec
                        .builder(it.name, Route.SimpleRoute::class)
                        .addModifiers(KModifier.INTERNAL)
                        .initializer(CodeBlock.of("Route.SimpleRoute(%S)", it.name))
                        .build()
                    it.destinationParams.none { parameter -> parameter.isOptional } -> PropertySpec
                        .builder(it.name, Route.NonOptionalRoute::class)
                        .addModifiers(KModifier.INTERNAL)
                        .initializer(
                            CodeBlock.of(
                                "Route.NonOptionalRoute(%S${"·,%S".repeat(it.destinationParams.count())})",
                                it.name,
                                *it.destinationParams.map { parameter -> parameter.name }.toTypedArray()
                            )
                        )
                        .build()
                    else -> PropertySpec
                        .builder(it.name, Route.OptionalRoute::class)
                        .initializer("%S", it.name, it.destinationParams.map { parameter -> parameter.name })
                        .build()
                }
            }.let { propertySpecSequence ->
                routes.addAll(propertySpecSequence)
            }

        return unableToProcess.toList()
    }

    override fun finish() {
        FileSpec
            .builder("de.todo", "NavHost").apply {
                addType(
                    TypeSpec
                        .objectBuilder("Routes")
                        .addModifiers(KModifier.INTERNAL).apply {
                            routes.forEach {
                                addProperty(it)
                            }
                        }.build()
                )
            }
            .build()
            .writeTo(codeGenerator, true)
    }

    private fun generateDestinationsClass(destinationsInformation: Sequence<DestinationInformation>): TypeSpec {
        val packageName = "TODO"

        // TODO:
        //  - Validate destination name is unique
        //  - Validate parameter names are unique within each destination

        val destinationClassName = ClassName(packageName, NavDestinationClassName) // TODO: Get package name from source project

        val constructor = FunSpec
            .constructorBuilder()
            .addParameter("route", String::class)
            .build()

        val destinationClassSpec = TypeSpec
            .classBuilder(NavDestinationClassName)
            .addModifiers(KModifier.SEALED)
            .primaryConstructor(constructor)

        destinationsInformation.forEach {
            val destinationPropertySpec = PropertySpec
                .builder(it.name, destinationClassName)
                .initializer(
                    CodeBlock.of(
                        "%L",
                        "$NavDestinationClassName(${generateDestinationRoute(it)})"
                    )
                )
        }

        return destinationClassSpec.build()
    }

    private fun generateDestinationRoute(destinationInformation: DestinationInformation) = destinationInformation.destinationParams
        // TODO: Escape strings
        .sortedBy { it.isOptional }
        .joinToString(
            separator = "",
            prefix = destinationInformation.name,
        ) {
            if (it.isOptional) {
                "/{${it.name}}"
            } else {
                "?${it.name}"
            }
        }


    //private fun generateNavigationDestinationObject(navDestinationsClassName: ClassName, destinations: Map<String, List<NamedNavArgument>>) {
    //    destinations.map { (destinationName, navArguments) ->
//
    //        val nonNullableNavArguments = navArguments.map {
    //            "/{${it.name}}"
    //        }
//
    //        PropertySpec
    //            .builder(destinationName, navDestinationsClassName)
    //            // TODO: Initialize route
    //    }
    //}

    private fun isValidComposeFunctionDeclaration(ksAnnotated: KSAnnotated): Boolean {
        return ksAnnotated is KSFunctionDeclaration && ksAnnotated.validate()
        //&& ksAnnotated.annotations.any { it.shortName == "Composable" } // TODO: Check, if function is compose function
    }

}