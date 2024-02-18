# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

##---------------Begin: proguard configuration for retrofit  ----------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
-dontwarn okhttp3.internal.platform.ConscryptPlatform
#-dontwarn org.conscrypt.ConscryptHostnameVerifier
-keep class okio.** { *; }
##---------------END: proguard configuration for retrofit  ----------

##---------------Begin: proguard configuration for Glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}
##---------------END: proguard configuration for Glide  ----------

#region data
-keepnames @kotlin.Metadata class com.arif.pokemonlist.data.local.entity.**
-keepclassmembers class com.arif.pokemonlist.data.local.entity.** {
    <init>(...);
    <fields>;
}

-keepnames @kotlin.Metadata class com.arif.pokemonlist.data.remote.model.**
-keepclassmembers class com.arif.pokemonlist.data.remote.model.** {
    <init>(...);
    <fields>;
}
#endregion

#region domain
-keepnames @kotlin.Metadata class com.arif.pokemonlist.domain.model.**
-keepclassmembers class com.arif.pokemonlist.domain.model.** {
    <init>(...);
    <fields>;
}

-keepnames @kotlin.Metadata class com.arif.pokemonlist.domain.usecase.**
-keepclassmembers class com.arif.pokemonlist.domain.usecase.** {
    <init>(...);
    <fields>;
}
#endregion

# Missing classes is handle as an exception for gradle 8 and above so need to ignore warnings
-dontwarn java.lang.**
-dontwarn java8.util.**
-ignorewarnings

-keepattributes Signature
-keepattributes AnnotationDefault,RuntimeVisibleAnnotations

# Keep class TypeToken (respectively its generic signature) if present
-if class com.google.gson.reflect.TypeToken
-keep class com.google.gson.reflect.TypeToken { *; }

# Keep any (anonymous) classes extending TypeToken
-keep class * extends com.google.gson.reflect.TypeToken

# Keep classes with @JsonAdapter annotation
-keep,allowobfuscation,allowoptimization @com.google.gson.annotations.JsonAdapter class *

# Keep fields with any other Gson annotation
# Also allow obfuscation, assuming that users will additionally use @SerializedName or
# other means to preserve the field names
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.Expose <fields>;
  @com.google.gson.annotations.JsonAdapter <fields>;
  @com.google.gson.annotations.Since <fields>;
  @com.google.gson.annotations.Until <fields>;
}

# Keep no-args constructor of classes which can be used with @JsonAdapter
# By default their no-args constructor is invoked to create an adapter instance
-keepclassmembers class * extends com.google.gson.TypeAdapter {
  <init>();
}
-keepclassmembers class * implements com.google.gson.TypeAdapterFactory {
  <init>();
}
-keepclassmembers class * implements com.google.gson.JsonSerializer {
  <init>();
}
-keepclassmembers class * implements com.google.gson.JsonDeserializer {
  <init>();
}

# Keep fields annotated with @SerializedName for classes which are referenced.
# If classes with fields annotated with @SerializedName have a no-args
# constructor keep that as well. Based on
# https://issuetracker.google.com/issues/150189783#comment11.
# See also https://github.com/google/gson/pull/2420#discussion_r1241813541
# for a more detailed explanation.
-if class *
-keepclasseswithmembers,allowobfuscation class <1> {
  @com.google.gson.annotations.SerializedName <fields>;
}
-if class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-keepclassmembers,allowobfuscation,allowoptimization class <1> {
  <init>();
}

-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>

-keepnames class * implements android.os.Parcelable

-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
