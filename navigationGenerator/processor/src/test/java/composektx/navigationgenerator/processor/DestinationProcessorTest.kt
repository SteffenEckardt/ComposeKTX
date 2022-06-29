import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import composektx.navigationgenerator.processor.ProcessorTestBase
import org.junit.Assert.assertEquals
import org.junit.Test

class DestinationProcessorTest : ProcessorTestBase() {

    @Test
    fun `TEST`() {
        val kotlinSource = SourceFile.kotlin(
            "FirstState.kt", """
        package de.itkl.generated.statemachine
        
        import composektx.navigationgenerator.annotation.Destination

          @Destination
          fun FirstDestination() {
          
          }
    """
        )

        val compilationResult = compile(kotlinSource)
        assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)


        //assertSourceEquals(
        //    """
        //        @file:Suppress("RedundantVisibilityModifier", "unused")
//
        //        package de.itkl.generated.statemachine
        //
        //        import kotlin.String
        //        import kotlin.Suppress
//
        //        public enum class DeclaredStates(
        //           public val stateName: String,
        //        ) {
        //            FirstState(""),
        //            ;
        //        }
        //        """,
        //    compilationResult.sourceFor("DeclaredStates.kt")
        //)
    }

}
