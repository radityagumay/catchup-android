object AppVersions {
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0

    const val versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val minSdk = 21
    const val targetSdk = 30
    const val buildVersion = "30.0.3"
}
