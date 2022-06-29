package composektx.navigationgenerator.annotation

/**
 * TODO
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class Destination(val name: String = "", val useNavigationDefaults: Boolean = false)
