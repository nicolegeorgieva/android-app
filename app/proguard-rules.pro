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

# Keep Compose-specific annotations and internal structures
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# Preserve the line number information for debugging stack traces.
-keepattributes SourceFile, LineNumberTable

# Hide the original source file name.
-renamesourcefileattribute SourceFile

# Keep all data models in your networking/model package
-keep class com.example.app.data.datasource.** { *; }

# Strip standard Android Log calls
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}