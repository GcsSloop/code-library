# 代码仓库

一些托管在我本地仓库上的代码。

| 仓库名称                 | 仓库简介                                     | 依赖                                       |
| -------------------- | ---------------------------------------- | ---------------------------------------- |
| acache               | 缓存工具，来源[ASimpleCache](https://github.com/yangfuhai/ASimpleCache) | `compile 'com.gcssloop:acache:0.0.1'`    |
| animation            | 动画相关工具                                   | `compile 'com.gcssloop.view:animation:0.0.1'` |
| app                  | 应用相关组件                                   | `compile 'com.gcssloop.support:app:0.0.1'` |
| app-util             | 应用相关工具                                   | `compile 'com.gcssloop.util:app:0.0.1'`  |
| assert               | 断言工具                                     | `compile 'com.gcssloop:assert:0.0.2'`    |
| closeutil            | 关闭流工具                                    | `compile 'com.gcssloop.util:closeutil:0.0.1'` |
| crash-catcher        | 异常捕获工具                                   | `compile 'com.gcssloop.support:crash-catcher:0.0.2'` |
| crashhandler         | 异常捕获工具                                   | `compile 'com.gcssloop.util:crashhandler:0.0.2'` |
| customview           | 自定义View支持包                               | `compile 'com.gcssloop.support:customview:0.0.2'` |
| fileutil             | 文件相关工具                                   | `compile 'com.gcssloop.utils:fileutil:0.0.1'` |
| idcard               | 身份证信息解析工具                                | `compile 'com.gcssloop.util:idcard:0.0.2'` |
| listview-support     | listview 相关工具                            | `compile 'com.gcssloop.support:listview:0.0.1'` |
| recyclerview-support | recyclerview 相关工具                        | `compile 'com.gcssloop.support:recyclerview:0.0.6'` |
| time-util            | 时间工具                                     | `compile 'com.gcssloop.util:time:0.0.1'` |
| logger               | 日志                                       | `compile 'com.gcssloop.util:logger:0.0.1'` |

## 导入仓库注意事项

由于我没有将代码发布到公共仓库中，所以如果需要引用我的仓库，需要在你的项目根 `build.gradle` 中添加上这样一行代码：

```groovy
allprojects {
    repositories {
        jcenter()
        // 就是下面这一行
        maven { url "http://lib.gcssloop.com/repository/gcssloop-central/" }
    }
}
```

之后在你需要引用我的项目的 Module 下添加 `compile '<包名>:<项目名>:<版本号>'` 才会起效，除了需要在项目根 `build.gradle` 添加我的仓库地址外，其余的使用方式和使用中央仓库的方式是完全一致的。 

当然，你也可以到我的 [私人仓库](http://library.gcssloop.com:8081/#browse/browse/components:gcs-repository) 中查看我都发布了哪些内容，后续我会将之前的一些Java项目从我的本地仓库迁移到这个仓库中。

## About Me

### 微博: [@GcsSloop](http://weibo.com/GcsSloop)

<a href="https://github.com/GcsSloop/README/blob/master/README.md" target="_blank"> <img src="http://ww4.sinaimg.cn/large/005Xtdi2gw1f1qn89ihu3j315o0dwwjc.jpg" width="300" height="100" /> </a>

## License

```
Copyright (c) 2017 GcsSloop

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```