import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import de.itkl.providers.StateAnnotationProcessorProvider
import org.intellij.lang.annotations.Language
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class StateProcessorTest : ProcessorTestBase() {

    @Test
    fun `Single state added to states enum`() {
        val kotlinSource = SourceFile.kotlin(
                "FirstState.kt", """
        package de.itkl.generated.statemachine
        
        import State

          @State
          class FirstState
    """
        )

        val compilationResult = compile(kotlinSource)
        assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)
        assertSourceEquals(
                """
                @file:Suppress("RedundantVisibilityModifier", "unused")

                package de.itkl.generated.statemachine
                
                import kotlin.String
                import kotlin.Suppress

                public enum class DeclaredStates(
                   public val stateName: String,
                ) {
                    FirstState(""),
                    ;
                }
                """,
                compilationResult.sourceFor("DeclaredStates.kt")
        )
    }

    @Test
    fun `Multiple states added to states enum`() {

        val kotlinSource = SourceFile.kotlin(
                "FirstState.kt", """
        package de.itkl.generated.statemachine
        
        import State

          @State
          class FirstState

          @State
          class SecondState

          @State
          class ThirdState
    """
        )

        val compilationResult = compile(kotlinSource)
        assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)
        assertSourceEquals(
                """
                @file:Suppress("RedundantVisibilityModifier", "unused")

                package de.itkl.generated.statemachine
                
                import kotlin.String
                import kotlin.Suppress

                public enum class DeclaredStates(
                   public val stateName: String,
                ) {
                    FirstState(""),
                    SecondState(""),
                    ThirdState(""),
                    ;
                }
                """,
                compilationResult.sourceFor("DeclaredStates.kt")
        )
    }

}
