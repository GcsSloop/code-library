# Logger

日志工具类。

## 主要文件

| 名称                | 作用    |
| ----------------- | ----- |
| /java/Logger.java | 日志工具类 |
| /java/Config.java | 相关配置  |

## 使用方法

### 1. 初始化

初始化方法应该仅执行一次，建议放在 Application 里面进行初始化。

在初始化时可以设置**日志的输出级别**，这个级别控制是全局的，所以限制在初始化时进行配置，以防止随意配置导致日志输出不正确。初始化有多种方案，如下：

#### 1.1 全部默认

```java
Logger.init();
```

在这种模式下，日志的 TAG 会显示调用者的类名、方法名和调用位置，如下：

```
MainActivity#onCreate:(MainActivity.java:28): hello
```

#### 1.2 提供全局 TAG

```
Logger.init("GCS-LOG");
```

这种模式下如果不设置 tag，则会默认使用全局 tag 如下：

```
GCS-LOG: hello
```

#### 1.3 设置级别

```java
Logger.init().setLevel(Config.LEVEL_FULL);
```

在 init 之后可以设置日志输出级别，级别有一下几种：

| 名称            | 简介               |
| ------------- | ---------------- |
| LEVEL_NONE    | 关闭所有             |
| LEVEL_FULL    | 全部输出             |
| LEVEL_VERBOSE | Verbose 以上，相当于全部 |
| LEVEL_DEBUG   | Debug 级别以上       |
| LEVEL_INFO    | Info 级别以上        |
| LEVEL_WARN    | Warn 级别以上        |
| LEVEL_ERROR   | Error 级别以上       |
| LEVEL_ASSERT  | Assert 级别以上      |

可以在 debug 时输出全部日志，在发布时关闭所有的日志。

### 2. Logger 基本使用

Logger 使用起来和系统的 Log 基本一致，不过可以少写一个 tag，当然也可以自定义 tag。

```java
Logger.i("hello");			// 使用全局 TAG
Logger.i("TAG", "hello");	// 使用当前 TAG
```

### 3. Logger 高级用法

Logger 除了能够根据日志级别调整输出之外，还能根据 TAG 和 当前类来控制输出。

#### 3.1 屏蔽某个 TAG

```java
Logger.addMaskedTag("GCS-LOG");
Logger.removeMaskedTag("GCS-LOG");
```

屏蔽所有 TAG 是 “GCS-LOG” 的日志输出和移除屏蔽，**控制级别是全局的**，即所有能够与当前 TAG 匹配的日志输出都会被屏蔽。

当然你也可以使用 addMaskedTags 或者 removeMaskedTags 来添加或者移除一组需要屏蔽掉 TAG。

#### 3.2 屏蔽当前类

```java
Logger.maskThis(true);
```

是否屏蔽当前类的日志输出，参数为 true 表示关闭当前类的日志输出，false 表示开启日志的输出。

这个控制级别是局部的，例如在 MainActivity 中使用了，其它 Activity 的日志依旧会正常输出。

注意：在屏蔽某个类时，它的内部类和内部匿名类也会被屏蔽，但屏蔽内部类时不会影响到包裹它的类。

**可以在调试某个类时打开，当调试完毕后关闭即可。**这样可以减少控制台的日志输出，方便找到所需的信息。

## 更新日志

### v1.0.0

添加屏蔽类功能，可以屏蔽某个类中所有的日志.

### v0.0.2

添加 tag 屏蔽功能，根据需要屏蔽相关 tag

### v0.0.1

完善日志框架基本功能。
1. 提供默认 TAG
2. 允许按照日志级别开启和关闭日志
