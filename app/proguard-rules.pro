# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# General Android & R8 Optimization
-keepattributes SourceFile, LineNumberTable, *Annotation*, Signature, EnclosingMethod,
RuntimeVisibleAnnotations, AnnotationDefault
-renamesourcefileattribute SourceFile

# Jetpack Compose
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
    @androidx.compose.runtime.ReadOnlyComposable *;
}

# Ktor, Kotlin Serialization & DTOs
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
    *** Companion;
    *** serializer(...);
}
-keep class com.example.app.data.datasource.model.** { *; }

# Strip standard Android Log calls
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}