package composektx.navigationgenerator.processor

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import composektx.navigationgenerator.processor.processor.DestinationAnnotationProcessor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Destination Annotation Processor")
class DestinationProcessorTest : ProcessorTestBase() {


    @Test
    fun ` ignores non marked functions`() {

        val kotlinSource = SourceFile.kotlin(
            "FirstScreen.kt",
            """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Composable
                fun FirstDestination() {
                
                }"""
        )

        val compilationResult = compile(kotlinSource)
        assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

        val expected =
            """package de.todo

internal object Routes
""".trimIndent()

        assertSourceEquals(
            expected,
            compilationResult.sourceFor("NavHost.kt")
        )
    }


    @DisplayName("Route-Generator")
    @Nested
    inner class RouteGenerator {

        @Test
        fun `generates 1 'Simple' route`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected =
                """package de.todo

import composektx.navigationgenerator.processor.models.Route

internal object Routes {
  internal val FirstDestination: Route.SimpleRoute = Route.SimpleRoute("FirstDestination")
}
"""
                    .trimIndent()

            assertSourceEquals(
                expected,
                compilationResult.sourceFor("NavHost.kt")
            )
        }

        @Test
        fun `generates 2 'Simple' routes`() {

            val sourceFile1 = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val sourceFile2 = SourceFile.kotlin(
                "SecondScreen.kt",
                """package mypackage.ui.screens.second
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun SecondDestination() {
                
                }"""
            )

            val compilationResult = compile(sourceFile1, sourceFile2)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected =
                """package de.todo

import composektx.navigationgenerator.processor.models.Route

internal object Routes {
  internal val FirstDestination: Route.SimpleRoute = Route.SimpleRoute("FirstDestination")

  internal val SecondDestination: Route.SimpleRoute = Route.SimpleRoute("SecondDestination")
}

"""
                    .trimIndent()

            assertSourceEquals(
                expected,
                compilationResult.sourceFor("NavHost.kt")
            )
        }

        @Test
        fun `generates 3 'Simple' routes`() {

            val sourceFile1 = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val sourceFile2 = SourceFile.kotlin(
                "SecondScreen.kt",
                """package mypackage.ui.screens.second
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun SecondDestination() {
                
                }"""
            )

            val sourceFile3 = SourceFile.kotlin(
                "ThirdScreen.kt",
                """package mypackage.ui.screens.third
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun ThirdDestination() {
                
                }"""
            )

            val compilationResult = compile(sourceFile1, sourceFile2, sourceFile3)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected =
                """package de.todo

import composektx.navigationgenerator.processor.models.Route

internal object Routes {
  internal val FirstDestination: Route.SimpleRoute = Route.SimpleRoute("FirstDestination")

  internal val SecondDestination: Route.SimpleRoute = Route.SimpleRoute("SecondDestination")

  internal val ThirdDestination: Route.SimpleRoute = Route.SimpleRoute("ThirdDestination")
}

"""
                    .trimIndent()

            assertSourceEquals(
                expected,
                compilationResult.sourceFor("NavHost.kt")
            )
        }

        @Test
        fun `generates 1 'Non-Optional' route with 1 parameter`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination(val name: String) {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected =
                """package de.todo

import composektx.navigationgenerator.processor.models.Route

internal object Routes {
  internal val FirstDestination: Route.NonOptionalRoute = Route.NonOptionalRoute("FirstDestination",
       "name")
}
"""
                    .trimIndent()

            assertSourceEquals(
                expected,
                compilationResult.sourceFor("NavHost.kt")
            )
        }

        @Test
        fun `generates 1 'Non-Optional' route with 2 parameters`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination(val name: String, val age: Int) {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected =
                """package de.todo

import composektx.navigationgenerator.processor.models.Route

internal object Routes {
  internal val FirstDestination: Route.NonOptionalRoute = 
    Route.NonOptionalRoute("FirstDestination", "name", "age")
}
"""
                    .trimIndent()

            assertSourceEquals(
                expected,
                compilationResult.sourceFor("NavHost.kt")
            )
        }

        @Test
        fun `generates 1 'Non-Optional' route with 3 parameters`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination(val name: String, val age: Int, val height: Double) {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected =
                """package de.todo

import composektx.navigationgenerator.processor.models.Route

internal object Routes {
  internal val FirstDestination: Route.NonOptionalRoute = 
    Route.NonOptionalRoute("FirstDestination", "name", "age", "height")
}
""".trimIndent()

            assertSourceEquals(
                expected,
                compilationResult.sourceFor("NavHost.kt")
            )
        }

        @Test
        fun `generates 2 'Non-Optional' route with 1 parameter each`() {

            val sourceFiles = arrayOf(
                SourceFile.kotlin(
                    "FirstScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination(val name1: String) {
                
                }"""
                ),
                SourceFile.kotlin(
                    "SecondScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun SecondDestination(val name2: String) {
                
                }"""
                )
            )

            val compilationResult = compile(*sourceFiles)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected =
                """package de.todo

import composektx.navigationgenerator.processor.models.Route

internal object Routes {
  internal val FirstDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("FirstDestination", "name1")

  internal val SecondDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("SecondDestination", "name2")
}
"""
                    .trimIndent()

            assertSourceEquals(
                expected,
                compilationResult.sourceFor("NavHost.kt")
            )
        }

        @Test
        fun `generates 2 'Non-Optional' routes with 2 parameters each`() {

            val sourceFiles = arrayOf(
                SourceFile.kotlin(
                    "FirstScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination(val name1: String) {
                
                }"""
                ),
                SourceFile.kotlin(
                    "SecondScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun SecondDestination(val name2: String) {
                
                }"""
                ),
                SourceFile.kotlin(
                    "ThirdScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun ThirdDestination(val name3: String) {
                
                }"""
                )
            )

            val compilationResult = compile(*sourceFiles)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected =
                """package de.todo

import composektx.navigationgenerator.processor.models.Route

internal object Routes {
  internal val FirstDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("FirstDestination", "name1")

  internal val SecondDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("SecondDestination", "name2")

  internal val ThirdDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("ThirdDestination", "name3")
}
"""
                    .trimIndent()

            assertSourceEquals(
                expected,
                compilationResult.sourceFor("NavHost.kt")
            )
        }

        @Test
        fun `generates 5 'Non-Optional' routes with different parameter count`() {

            val sourceFiles = arrayOf(
                SourceFile.kotlin(
                    "FirstScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination(val name1: String, val name2: String) {
                
                }"""
                ),
                SourceFile.kotlin(
                    "SecondScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun SecondDestination(val age: Int) {
                
                }"""
                ),
                SourceFile.kotlin(
                    "ThirdScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun ThirdDestination(val height: Double, val weight: Double) {
                
                }"""
                ),
                SourceFile.kotlin(
                    "FourthScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FourthDestination(val gender: Boolean) {
                
                }"""
                ),
                SourceFile.kotlin(
                    "FifthScreen.kt",
                    """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FifthDestination(val aValue: Any) {
                
                }"""
                )
            )

            val compilationResult = compile(*sourceFiles)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected =
                """package de.todo

import composektx.navigationgenerator.processor.models.Route

internal object Routes {
  internal val FirstDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("FirstDestination", "name1", "name2")

  internal val SecondDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("SecondDestination", "age")

  internal val ThirdDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("ThirdDestination", "height", "weight")

  internal val FourthDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("FourthDestination", "gender")

  internal val FifthDestination: Route.NonOptionalRoute =
    Route.NonOptionalRoute("FifthDestination", "aValue")
}
"""
                    .trimIndent()

            assertSourceEquals(
                expected,
                compilationResult.sourceFor("NavHost.kt")
            )

        }
    }

    @DisplayName("Destination-Generator")
    @Nested
    inner class DestinationGenerator {

        @Test
        fun `generate single destination object with no arguments`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected = """
        @file:Suppress("RedundantVisibilityModifier", "unused")

        import mypackage.ui.screens.first.FirstDestination

        public sealed class NavDestinations(route: String) {
            object FirstDestination : NavDestinations("firstDestination")
        }
        """
            assertSourceEquals(
                expected,
                compilationResult.sourceFor("${DestinationAnnotationProcessor.NavDestinationClassName}.kt")
            )
        }

        @Test
        fun `generate single destination object with one non-optional argument`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected = """
        @file:Suppress("RedundantVisibilityModifier", "unused")

        import mypackage.ui.screens.first.FirstDestination

        public sealed class NavDestinations(route: String) {
            object FirstDestination : NavDestinations("firstDestination")
        }
        """
            assertSourceEquals(
                expected,
                compilationResult.sourceFor("${DestinationAnnotationProcessor.NavDestinationClassName}.kt")
            )
        }

        @Test
        fun `generate single destination object with two non-optional arguments`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected = """
        @file:Suppress("RedundantVisibilityModifier", "unused")

        import mypackage.ui.screens.first.FirstDestination

        public sealed class NavDestinations(route: String) {
            object FirstDestination : NavDestinations("firstDestination")
        }
        """
            assertSourceEquals(
                expected,
                compilationResult.sourceFor("${DestinationAnnotationProcessor.NavDestinationClassName}.kt")
            )
        }

        @Test
        fun `generate single destination object with three non-optional arguments`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected = """
        @file:Suppress("RedundantVisibilityModifier", "unused")

        import mypackage.ui.screens.first.FirstDestination

        public sealed class NavDestinations(route: String) {
            object FirstDestination : NavDestinations("firstDestination")
        }
        """
            assertSourceEquals(
                expected,
                compilationResult.sourceFor("${DestinationAnnotationProcessor.NavDestinationClassName}.kt")
            )
        }

    }

    @DisplayName("NavHost Generator")
    @Nested
    inner class NavHostGenerator {

        @Test
        fun `generate single NavHost entry with no arguments`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected = """
        @file:Suppress("RedundantVisibilityModifier", "unused")

        import mypackage.ui.screens.first.FirstDestination

        public sealed class NavDestinations(route: String) {
            object FirstDestination : NavDestinations("firstDestination")
        }
        """
            assertSourceEquals(
                expected,
                compilationResult.sourceFor("${DestinationAnnotationProcessor.NavDestinationClassName}.kt")
            )
        }

        @Test
        fun `generate single NavHost entry with one non-optional argument`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected = """
        @file:Suppress("RedundantVisibilityModifier", "unused")

        import mypackage.ui.screens.first.FirstDestination

        public sealed class NavDestinations(route: String) {
            object FirstDestination : NavDestinations("firstDestination")
        }
        """
            assertSourceEquals(
                expected,
                compilationResult.sourceFor("${DestinationAnnotationProcessor.NavDestinationClassName}.kt")
            )
        }

        @Test
        fun `generate single NavHost entry with two non-optional arguments`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected = """
        @file:Suppress("RedundantVisibilityModifier", "unused")

        import mypackage.ui.screens.first.FirstDestination

        public sealed class NavDestinations(route: String) {
            object FirstDestination : NavDestinations("firstDestination")
        }
        """
            assertSourceEquals(
                expected,
                compilationResult.sourceFor("${DestinationAnnotationProcessor.NavDestinationClassName}.kt")
            )
        }

        @Test
        fun `generate single NavHost entry with three non-optional arguments`() {

            val kotlinSource = SourceFile.kotlin(
                "FirstScreen.kt",
                """package mypackage.ui.screens.first
        
                import androidx.compose.runtime.Composable
                import composektx.navigationgenerator.annotation.Destination
    
                @Destination
                @Composable
                fun FirstDestination() {
                
                }"""
            )

            val compilationResult = compile(kotlinSource)
            assertEquals(KotlinCompilation.ExitCode.OK, compilationResult.exitCode)

            val expected = """
        @file:Suppress("RedundantVisibilityModifier", "unused")

        import mypackage.ui.screens.first.FirstDestination

        public sealed class NavDestinations(route: String) {
            object FirstDestination : NavDestinations("firstDestination")
        }
        """
            assertSourceEquals(
                expected,
                compilationResult.sourceFor("${DestinationAnnotationProcessor.NavDestinationClassName}.kt")
            )
        }

    }

}
