package composektx.navigationgenerator.processor.models

import composektx.navigationgenerator.processor.models.Route.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("Routes: ")
@Nested
internal class RouteTest {

    companion object {
        const val SIMPLE_ROUTE = "simpleRoute"
        const val NON_OPTIONAL_ROUTE = "nonOptionalRoute"
        const val OPTIONAL_ROUTE = "optionalRoute"

        const val ID_PARAM_1 = "parameter1"
        const val ID_PARAM_2 = "parameter2"
        const val ID_PARAM_3 = "parameter3"
        const val ID_PARAM_4 = "parameter4"
        const val ID_PARAM_5 = "parameter5"
        const val ID_PARAM_6 = "parameter6"

        const val ID_WRONG_1 = "wrong1"

        const val VALUE_PARAM_1 = "value1"
        const val VALUE_PARAM_2 = "value2"
        const val VALUE_PARAM_3 = "value3"
        const val VALUE_PARAM_4 = "value4"
        const val VALUE_PARAM_5 = "value5"
        const val VALUE_PARAM_6 = "value6"
    }

    @Test
    fun `empty base path throws exception`() {
        var exception1: Exception = Exception()
        var exception2: Exception = Exception()
        var exception3: Exception = Exception()

        assertAll(
            "Should throw exception",
            {
                exception1 = assertThrows<IllegalArgumentException>("An empty base path is not allowed!") {
                    SimpleRoute("")
                }
            }, {
                exception2 = assertThrows<IllegalArgumentException>("An empty base path is not allowed!") {
                    OptionalRoute("")
                }
            }, {
                exception3 = assertThrows<IllegalArgumentException>("An empty base path is not allowed!") {
                    NonOptionalRoute("")
                }
            }
        )

        assertAll(
            "Should have correct error message",
            { assertEquals("The base route of the destination must not be blank or empty.", exception1.message) },
            { assertEquals("The base route of the destination must not be blank or empty.", exception2.message) },
            { assertEquals("The base route of the destination must not be blank or empty.", exception3.message) },
        )
    }

    @Test
    fun `blank base path throws exception`() {
        var exception1: Exception = Exception()
        var exception2: Exception = Exception()
        var exception3: Exception = Exception()

        assertAll(
            "Should throw exception",
            {
                exception1 = assertThrows<IllegalArgumentException>("An empty base path is not allowed!") {
                    SimpleRoute("          ")
                }
            }, {
                exception2 = assertThrows<IllegalArgumentException>("An empty base path is not allowed!") {
                    OptionalRoute("              ")
                }
            }, {
                exception3 = assertThrows<IllegalArgumentException>("An empty base path is not allowed!") {
                    NonOptionalRoute("            ")
                }
            }
        )

        assertAll(
            "Should have correct error message",
            { assertEquals("The base route of the destination must not be blank or empty.", exception1.message) },
            { assertEquals("The base route of the destination must not be blank or empty.", exception2.message) },
            { assertEquals("The base route of the destination must not be blank or empty.", exception3.message) },
        )
    }

    @DisplayName("Simple route")
    @Nested
    inner class SimpleRouteTest {

        @Test
        fun `returns only the base path as route`() {
            val simpleRoute = SimpleRoute(SIMPLE_ROUTE)

            val path = simpleRoute.asString()

            assertEquals(SIMPLE_ROUTE, path)
        }

        @Test
        fun `returns only the base path as navigation path`() {
            val simpleRoute = SimpleRoute(SIMPLE_ROUTE)

            val path = simpleRoute.asNavigation()

            assertEquals(SIMPLE_ROUTE, path)
        }

    }

    @DisplayName("Non-Optional route")
    @Nested
    inner class NonOptionalRouteTest {

        @Test
        fun `with 0 arguments throws exception`() {
            val exception = assertThrows<IllegalArgumentException>("A blank base path is not allowed!") {
                NonOptionalRoute(NON_OPTIONAL_ROUTE)
            }
            assertEquals("Required arguments: Use Route.SimpleRoute for destinations with no arguments.", exception.message)
        }

        @Test
        fun `with 1 argument returns the correct path`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1)

            val path = nonOptionalRoute.asString()

            assertEquals("$NON_OPTIONAL_ROUTE/{$ID_PARAM_1}", path)
        }

        @Test
        fun `with 1 argument returns the correct navigation path`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1)

            val path = nonOptionalRoute.asNavigation(ID_PARAM_1 to VALUE_PARAM_1)

            assertEquals("$NON_OPTIONAL_ROUTE/$VALUE_PARAM_1", path)
        }

        @Test
        fun `with 1 argument but no values throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1)

            val exception = assertThrows<IllegalArgumentException>("Missing values for arguments provided") {
                nonOptionalRoute.asNavigation()
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 1 argument but wrong value-key throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                nonOptionalRoute.asNavigation(ID_WRONG_1 to VALUE_PARAM_1)
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 1 argument but 2 values throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                nonOptionalRoute.asNavigation(
                    ID_PARAM_1 to VALUE_PARAM_1,
                    ID_PARAM_2 to VALUE_PARAM_2,
                )
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 2 arguments returns the correct path`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val path = nonOptionalRoute.asString()

            assertEquals("$NON_OPTIONAL_ROUTE/{$ID_PARAM_1}/{$ID_PARAM_2}", path)
        }

        @Test
        fun `with 2 arguments returns the correct navigation path`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val path = nonOptionalRoute.asNavigation(
                ID_PARAM_1 to VALUE_PARAM_1,
                ID_PARAM_2 to VALUE_PARAM_2,
            )

            assertEquals("$NON_OPTIONAL_ROUTE/$VALUE_PARAM_1/$VALUE_PARAM_2", path)
        }

        @Test
        fun `with 2 arguments but no values throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val exception = assertThrows<IllegalArgumentException>("Missing values for arguments provided") {
                nonOptionalRoute.asNavigation()
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 2 arguments but only 1 value throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val exception = assertThrows<IllegalArgumentException>("Missing values for arguments provided") {
                nonOptionalRoute.asNavigation(ID_PARAM_1 to VALUE_PARAM_1)
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 2 arguments but wrong value-keys throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                nonOptionalRoute.asNavigation(
                    ID_WRONG_1 to VALUE_PARAM_1,
                    ID_PARAM_2 to VALUE_PARAM_1
                )
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 2 argument but 3 values throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                nonOptionalRoute.asNavigation(
                    ID_PARAM_1 to VALUE_PARAM_1,
                    ID_PARAM_2 to VALUE_PARAM_2,
                    ID_PARAM_3 to VALUE_PARAM_3,
                )
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 5 arguments returns the correct path`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val path = nonOptionalRoute.asString()

            assertEquals("$NON_OPTIONAL_ROUTE/{$ID_PARAM_1}/{$ID_PARAM_2}", path)
        }

        @Test
        fun `with 5 arguments returns the correct navigation path`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val path = nonOptionalRoute.asNavigation(
                ID_PARAM_1 to VALUE_PARAM_1,
                ID_PARAM_2 to VALUE_PARAM_2,
            )

            assertEquals("$NON_OPTIONAL_ROUTE/$VALUE_PARAM_1/$VALUE_PARAM_2", path)
        }

        @Test
        fun `with 5 arguments but no values throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1)

            val exception = assertThrows<IllegalArgumentException>("Missing values for arguments provided") {
                nonOptionalRoute.asNavigation()
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 5 arguments but less values throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)

            var exception1 = Exception()
            var exception2 = Exception()
            var exception3 = Exception()
            var exception4 = Exception()

            assertAll(
                "Exceptions should be thrown",
                {
                    exception1 = assertThrows<IllegalArgumentException>("Missing values for arguments provided") {
                        nonOptionalRoute.asNavigation(ID_PARAM_1 to VALUE_PARAM_1)
                    }
                },
                {
                    exception2 = assertThrows<IllegalArgumentException>("Missing values for arguments provided") {
                        nonOptionalRoute.asNavigation(
                            ID_PARAM_1 to VALUE_PARAM_1,
                            ID_PARAM_2 to VALUE_PARAM_2,
                        )
                    }
                },
                {
                    exception3 = assertThrows<IllegalArgumentException>("Missing values for arguments provided") {
                        nonOptionalRoute.asNavigation(
                            ID_PARAM_1 to VALUE_PARAM_1,
                            ID_PARAM_2 to VALUE_PARAM_2,
                            ID_PARAM_3 to VALUE_PARAM_3,
                        )
                    }
                },
                {
                    exception4 = assertThrows<IllegalArgumentException>("Missing values for arguments provided") {
                        nonOptionalRoute.asNavigation(
                            ID_PARAM_1 to VALUE_PARAM_1,
                            ID_PARAM_2 to VALUE_PARAM_2,
                            ID_PARAM_3 to VALUE_PARAM_3,
                            ID_PARAM_4 to VALUE_PARAM_4,
                        )
                    }
                },
            )

            assertAll(
                "Exception messages should be correct",
                { assertEquals("The provided key-value list did not match the required parameters of the route.", exception1.message) },
                { assertEquals("The provided key-value list did not match the required parameters of the route.", exception2.message) },
                { assertEquals("The provided key-value list did not match the required parameters of the route.", exception3.message) },
                { assertEquals("The provided key-value list did not match the required parameters of the route.", exception4.message) },
            )
        }

        @Test
        fun `with 5 arguments but more values throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)

            val exception = assertThrows<IllegalArgumentException>("Missing values for arguments provided") {
                nonOptionalRoute.asNavigation(
                    ID_PARAM_1 to VALUE_PARAM_1,
                    ID_PARAM_2 to VALUE_PARAM_2,
                    ID_PARAM_3 to VALUE_PARAM_3,
                    ID_PARAM_4 to VALUE_PARAM_4,
                    ID_PARAM_5 to VALUE_PARAM_5,
                    ID_PARAM_6 to VALUE_PARAM_6,
                )
            }

            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 5 arguments but wrong value-keys throws exception`() {
            val nonOptionalRoute = NonOptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                nonOptionalRoute.asNavigation(
                    ID_WRONG_1 to VALUE_PARAM_1,
                    ID_PARAM_2 to VALUE_PARAM_2,
                    ID_PARAM_3 to VALUE_PARAM_3,
                    ID_PARAM_4 to VALUE_PARAM_4,
                    ID_PARAM_5 to VALUE_PARAM_5,
                )
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

    }

    @DisplayName("Optional route")
    @Nested
    inner class OptionalRouteTest {

        @Test
        fun `with 0 arguments throws exception`() {
            val exception = assertThrows<IllegalArgumentException> {
                OptionalRoute(OPTIONAL_ROUTE)
            }
            assertEquals("Required arguments: Use Route.SimpleRoute for destinations with no arguments.", exception.message)
        }


        @Test
        fun `with 1 argument returns the correct path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1)

            val path = optionalRoute.asString()

            assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1={$ID_PARAM_1}", path)
        }

        @Test
        fun `with 1 argument returns the correct navigation path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1)

            val path = optionalRoute.asNavigation(ID_PARAM_1 to VALUE_PARAM_1)

            assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1=$VALUE_PARAM_1", path)
        }

        @Test
        fun `with 1 argument but no values returns correct path`() {
            val optionalRoute = OptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1)

            val path = optionalRoute.asNavigation()

            assertEquals(NON_OPTIONAL_ROUTE, path)
        }

        @Test
        fun `with 1 argument but wrong value-key throws exception`() {
            val optionalRoute = OptionalRoute(NON_OPTIONAL_ROUTE, ID_PARAM_1)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                optionalRoute.asNavigation(ID_WRONG_1 to VALUE_PARAM_1)
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 2 arguments returns the correct path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val path = optionalRoute.asString()

            assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1={$ID_PARAM_1}&$ID_PARAM_2={$ID_PARAM_2}", path)
        }

        @Test
        fun `with 2 arguments and 2 values returns the correct navigation path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val path = optionalRoute.asNavigation(
                ID_PARAM_1 to VALUE_PARAM_1,
                ID_PARAM_2 to VALUE_PARAM_2,
            )

            assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1=$VALUE_PARAM_1&$ID_PARAM_2=$VALUE_PARAM_2", path)
        }

        @Test
        fun `with 2 arguments and 2 values in different order returns the correct navigation path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val path = optionalRoute.asNavigation(
                ID_PARAM_2 to VALUE_PARAM_2,
                ID_PARAM_1 to VALUE_PARAM_1,
            )

            assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_2=$VALUE_PARAM_2&$ID_PARAM_1=$VALUE_PARAM_1", path)
        }

        @Test
        fun `with 2 arguments and 1 value returns the correct navigation path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)


            val path1 = optionalRoute.asNavigation(ID_PARAM_1 to VALUE_PARAM_1)
            val path2 = optionalRoute.asNavigation(ID_PARAM_2 to VALUE_PARAM_2)

            assertAll(
                "Paths should be correct",
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1=$VALUE_PARAM_1", path1) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_2=$VALUE_PARAM_2", path2) },
            )
        }

        @Test
        fun `with 2 arguments and 0 values returns the correct navigation path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val path = optionalRoute.asNavigation()

            assertEquals(OPTIONAL_ROUTE, path)
        }

        @Test
        fun `with 2 arguments but wrong value-keys throws exception`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                optionalRoute.asNavigation(
                    ID_WRONG_1 to VALUE_PARAM_1,
                    ID_PARAM_2 to VALUE_PARAM_1
                )
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 2 argument but 3 values throws exception`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                optionalRoute.asNavigation(
                    ID_PARAM_1 to VALUE_PARAM_1,
                    ID_PARAM_2 to VALUE_PARAM_2,
                    ID_PARAM_3 to VALUE_PARAM_3,
                )
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 5 arguments returns the correct path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)

            val path = optionalRoute.asString()

            assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1={$ID_PARAM_1}&$ID_PARAM_2={$ID_PARAM_2}&$ID_PARAM_3={$ID_PARAM_3}&$ID_PARAM_4={$ID_PARAM_4}&$ID_PARAM_5={$ID_PARAM_5}", path)
        }

        @Test
        fun `with 5 arguments and 5 values returns the correct navigation path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)

            val path = optionalRoute.asNavigation(
                ID_PARAM_1 to VALUE_PARAM_1,
                ID_PARAM_2 to VALUE_PARAM_2,
                ID_PARAM_3 to VALUE_PARAM_3,
                ID_PARAM_4 to VALUE_PARAM_4,
                ID_PARAM_5 to VALUE_PARAM_5,
            )

            assertEquals(
                "$OPTIONAL_ROUTE?$ID_PARAM_1=$VALUE_PARAM_1&$ID_PARAM_2=$VALUE_PARAM_2&$ID_PARAM_3=$VALUE_PARAM_3&$ID_PARAM_4=$VALUE_PARAM_4&$ID_PARAM_5=$VALUE_PARAM_5",
                path
            )
        }

        @Test
        fun `with 5 arguments and 5 values in different order returns the correct navigation path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)

            val path = optionalRoute.asNavigation(
                ID_PARAM_5 to VALUE_PARAM_5,
                ID_PARAM_1 to VALUE_PARAM_1,
                ID_PARAM_2 to VALUE_PARAM_2,
                ID_PARAM_4 to VALUE_PARAM_4,
                ID_PARAM_3 to VALUE_PARAM_3,
            )

            assertEquals(
                "$OPTIONAL_ROUTE?$ID_PARAM_5=$VALUE_PARAM_5&$ID_PARAM_1=$VALUE_PARAM_1&$ID_PARAM_2=$VALUE_PARAM_2&$ID_PARAM_4=$VALUE_PARAM_4&$ID_PARAM_3=$VALUE_PARAM_3",
                path
            )
        }

        @Test
        fun `with 5 arguments and less values returns the correct navigation path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)


            val path11 = optionalRoute.asNavigation(ID_PARAM_1 to VALUE_PARAM_1)
            val path12 = optionalRoute.asNavigation(ID_PARAM_2 to VALUE_PARAM_2)
            val path13 = optionalRoute.asNavigation(ID_PARAM_3 to VALUE_PARAM_3)
            val path14 = optionalRoute.asNavigation(ID_PARAM_4 to VALUE_PARAM_4)
            val path15 = optionalRoute.asNavigation(ID_PARAM_5 to VALUE_PARAM_5)

            val path21 = optionalRoute.asNavigation(ID_PARAM_1 to VALUE_PARAM_1, ID_PARAM_2 to VALUE_PARAM_2)
            val path22 = optionalRoute.asNavigation(ID_PARAM_2 to VALUE_PARAM_2, ID_PARAM_3 to VALUE_PARAM_3)
            val path23 = optionalRoute.asNavigation(ID_PARAM_3 to VALUE_PARAM_3, ID_PARAM_4 to VALUE_PARAM_4)
            val path24 = optionalRoute.asNavigation(ID_PARAM_4 to VALUE_PARAM_4, ID_PARAM_5 to VALUE_PARAM_5)
            val path25 = optionalRoute.asNavigation(ID_PARAM_5 to VALUE_PARAM_5, ID_PARAM_1 to VALUE_PARAM_1)

            val path31 = optionalRoute.asNavigation(ID_PARAM_1 to VALUE_PARAM_1, ID_PARAM_2 to VALUE_PARAM_2, ID_PARAM_3 to VALUE_PARAM_3)
            val path32 = optionalRoute.asNavigation(ID_PARAM_2 to VALUE_PARAM_2, ID_PARAM_3 to VALUE_PARAM_3, ID_PARAM_4 to VALUE_PARAM_4)
            val path33 = optionalRoute.asNavigation(ID_PARAM_3 to VALUE_PARAM_3, ID_PARAM_4 to VALUE_PARAM_4, ID_PARAM_5 to VALUE_PARAM_5)
            val path34 = optionalRoute.asNavigation(ID_PARAM_4 to VALUE_PARAM_4, ID_PARAM_5 to VALUE_PARAM_5, ID_PARAM_1 to VALUE_PARAM_1)
            val path35 = optionalRoute.asNavigation(ID_PARAM_5 to VALUE_PARAM_5, ID_PARAM_1 to VALUE_PARAM_1, ID_PARAM_2 to VALUE_PARAM_2)

            val path41 = optionalRoute.asNavigation(ID_PARAM_1 to VALUE_PARAM_1, ID_PARAM_2 to VALUE_PARAM_2, ID_PARAM_3 to VALUE_PARAM_3, ID_PARAM_4 to VALUE_PARAM_4)
            val path42 = optionalRoute.asNavigation(ID_PARAM_2 to VALUE_PARAM_2, ID_PARAM_3 to VALUE_PARAM_3, ID_PARAM_4 to VALUE_PARAM_4, ID_PARAM_5 to VALUE_PARAM_5)
            val path43 = optionalRoute.asNavigation(ID_PARAM_3 to VALUE_PARAM_3, ID_PARAM_4 to VALUE_PARAM_4, ID_PARAM_5 to VALUE_PARAM_5, ID_PARAM_1 to VALUE_PARAM_1)
            val path44 = optionalRoute.asNavigation(ID_PARAM_4 to VALUE_PARAM_4, ID_PARAM_5 to VALUE_PARAM_5, ID_PARAM_1 to VALUE_PARAM_1, ID_PARAM_2 to VALUE_PARAM_2)
            val path45 = optionalRoute.asNavigation(ID_PARAM_5 to VALUE_PARAM_5, ID_PARAM_1 to VALUE_PARAM_1, ID_PARAM_2 to VALUE_PARAM_2, ID_PARAM_3 to VALUE_PARAM_3)

            assertAll(
                "Paths should be correct",

                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1=$VALUE_PARAM_1", path11) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_2=$VALUE_PARAM_2", path12) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_3=$VALUE_PARAM_3", path13) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_4=$VALUE_PARAM_4", path14) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_5=$VALUE_PARAM_5", path15) },

                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1=$VALUE_PARAM_1&$ID_PARAM_2=$VALUE_PARAM_2", path21) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_2=$VALUE_PARAM_2&$ID_PARAM_3=$VALUE_PARAM_3", path22) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_3=$VALUE_PARAM_3&$ID_PARAM_4=$VALUE_PARAM_4", path23) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_4=$VALUE_PARAM_4&$ID_PARAM_5=$VALUE_PARAM_5", path24) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_5=$VALUE_PARAM_5&$ID_PARAM_1=$VALUE_PARAM_1", path25) },

                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1=$VALUE_PARAM_1&$ID_PARAM_2=$VALUE_PARAM_2&$ID_PARAM_3=$VALUE_PARAM_3", path31) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_2=$VALUE_PARAM_2&$ID_PARAM_3=$VALUE_PARAM_3&$ID_PARAM_4=$VALUE_PARAM_4", path32) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_3=$VALUE_PARAM_3&$ID_PARAM_4=$VALUE_PARAM_4&$ID_PARAM_5=$VALUE_PARAM_5", path33) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_4=$VALUE_PARAM_4&$ID_PARAM_5=$VALUE_PARAM_5&$ID_PARAM_1=$VALUE_PARAM_1", path34) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_5=$VALUE_PARAM_5&$ID_PARAM_1=$VALUE_PARAM_1&$ID_PARAM_2=$VALUE_PARAM_2", path35) },

                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_1=$VALUE_PARAM_1&$ID_PARAM_2=$VALUE_PARAM_2&$ID_PARAM_3=$VALUE_PARAM_3&$ID_PARAM_4=$VALUE_PARAM_4", path41) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_2=$VALUE_PARAM_2&$ID_PARAM_3=$VALUE_PARAM_3&$ID_PARAM_4=$VALUE_PARAM_4&$ID_PARAM_5=$VALUE_PARAM_5", path42) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_3=$VALUE_PARAM_3&$ID_PARAM_4=$VALUE_PARAM_4&$ID_PARAM_5=$VALUE_PARAM_5&$ID_PARAM_1=$VALUE_PARAM_1", path43) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_4=$VALUE_PARAM_4&$ID_PARAM_5=$VALUE_PARAM_5&$ID_PARAM_1=$VALUE_PARAM_1&$ID_PARAM_2=$VALUE_PARAM_2", path44) },
                { assertEquals("$OPTIONAL_ROUTE?$ID_PARAM_5=$VALUE_PARAM_5&$ID_PARAM_1=$VALUE_PARAM_1&$ID_PARAM_2=$VALUE_PARAM_2&$ID_PARAM_3=$VALUE_PARAM_3", path45) },
            )
        }

        @Test
        fun `with 5 arguments and 0 values returns the correct navigation path`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)

            val path = optionalRoute.asNavigation()

            assertEquals(OPTIONAL_ROUTE, path)
        }


        @Test
        fun `with 5 arguments but wrong value-keys throws exception`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                optionalRoute.asNavigation(
                    ID_WRONG_1 to VALUE_PARAM_1,
                    ID_PARAM_2 to VALUE_PARAM_2,
                    ID_PARAM_3 to VALUE_PARAM_3,
                    ID_PARAM_4 to VALUE_PARAM_4,
                    ID_PARAM_5 to VALUE_PARAM_5,
                )
            }

            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }

        @Test
        fun `with 5 argument but 6 values throws exception`() {
            val optionalRoute = OptionalRoute(OPTIONAL_ROUTE, ID_PARAM_1, ID_PARAM_2, ID_PARAM_3, ID_PARAM_4, ID_PARAM_5)

            val exception = assertThrows<IllegalArgumentException>("Incorrect values for arguments provided") {
                optionalRoute.asNavigation(
                    ID_PARAM_1 to VALUE_PARAM_1,
                    ID_PARAM_2 to VALUE_PARAM_2,
                    ID_PARAM_3 to VALUE_PARAM_3,
                    ID_PARAM_4 to VALUE_PARAM_4,
                    ID_PARAM_5 to VALUE_PARAM_5,
                    ID_PARAM_6 to VALUE_PARAM_6,
                )
            }
            assertEquals("The provided key-value list did not match the required parameters of the route.", exception.message)
        }
    }
}