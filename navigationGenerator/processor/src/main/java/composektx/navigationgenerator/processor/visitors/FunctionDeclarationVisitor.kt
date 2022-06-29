package composektx.navigationgenerator.processor.visitors

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor

class FunctionDeclarationVisitor : KSEmptyVisitor<Unit, String>() {

        override fun defaultHandler(node: KSNode, data: Unit): String {
            TODO("Not yet implemented")
        }

        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit): String {
            return function.simpleName.asString()
        }
    }
