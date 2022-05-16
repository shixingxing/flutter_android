# flutter_android
flutter和android混合开发

## 创建Flutter模块
假设你在 some/path/MyApp 路径下已有一个 Android 应用，并且你希望 Flutter 项目作为同级项目：
```
cd some/path/
flutter create -t module --org com.example.flutter my_flutter
```

这会创建一个 some/path/my_flutter/ 的 Flutter 模块项目，其中包含一些 Dart 代码来帮助你入门以及一个隐藏的子文件夹 .android/。 .android 文件夹包含一个 Android 项目，该项目不仅可以帮助你通过 flutter run 运行这个 Flutter 模块的独立应用，而且还可以作为封装程序来帮助引导 Flutter 模块作为可嵌入的 Android 库。
> 将自己的 Android 代码添加到你现有应用程序的项目或插件中，而不是添加到 .android/ 中的模块。在模块的 .android/ 目录中所做的任何更改并不会显示在使用该模块的现有 Android 项目中。
> 由于 .android/ 目录是自动生成的，因此不需要对它的代码进行版本控制，在新机器上构建模块之前，可以先在 my_flutter 目录中运行 flutter pub get 来重新生成 .android/ 目录，然后再使用 Flutter 模块构建 Android 项目
### 初始化现有的Flutter模块
```
flutter clean
flutter pub get
```
### 将 Flutter 模块作为子项目添加到宿主应用的 settings.gradle 中
```
// Include the host app project.
include ':app'                                    // assumed existing content
setBinding(new Binding([gradle: this]))                                // new
evaluate(new File(                                                     // new
  settingsDir.parentFile,                                              // new
  'my_flutter/.android/include_flutter.groovy'                         // new
))                                                                     // new
//方便android项目中直接编辑flutter模块，非必须
include ':my_flutter'
project(':my_flutter').projectDir = new File('../my_flutter')
```

### 在你的应用中引入对 Flutter 模块的依赖：
```
dependencies {
  implementation project(':flutter')
}
```

### 在 AndroidManifest.xml 中添加 FlutterActivity
```
<activity
  android:name="io.flutter.embedding.android.FlutterActivity"
  android:theme="@style/LaunchTheme"
  android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale|layoutDirection|fontScale|screenLayout|density|uiMode"
  android:hardwareAccelerated="true"
  android:windowSoftInputMode="adjustResize"
  />
```

### 启动flutter模块
```
myButton.addOnClickListener(new OnClickListener() {
  @Override
  public void onClick(View v) {
    startActivity(
      FlutterActivity
        .withNewEngine()
        .initialRoute("/my_route")
        .build(currentActivity)
      );
  }
});
```

## 使用AAR引入flutter打包
### 生成aar模块
进入my_flutter目录，运行
```
flutter build aar
```
将生成的build文件夹中内容，复制到android app目录下的flutteraar文件夹下面
### 添加gradle配置
```
String rootDir = rootProject.rootDir.toString()
maven {
    url "$rootDir/flutteraar/host/outputs/repo"
}
String storageUrl = System.env.FLUTTER_STORAGE_BASE_URL ?: "https://storage.googleapis.com"
maven {
    url "$storageUrl/download.flutter.io"
}
```

```
if (isFlutterAAR.toBoolean()) {
    debugImplementation 'com.example.flutter.my_flutter:flutter_debug:1.0'
    profileImplementation 'com.example.flutter.my_flutter:flutter_profile:1.0'
    releaseImplementation 'com.example.flutter.my_flutter:flutter_release:1.0'
} else {
    implementation project(':flutter')
}
```
