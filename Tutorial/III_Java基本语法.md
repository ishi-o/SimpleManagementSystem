# `Java`基本语法

## 包管理

### 包的声明

- `Java`包的功能类似`C++`的命名空间，减少类名冲突；但它并不是随意声明和引用的：
  - 包名必须是当前文件/目录的**父目录**相对于**项目根目录**的路径，用`.`运算符表明父子关系

  例如，若`.`为根目录，当前文件的路径为`./test/math`，则声明如下：
  
  ```java
  package test.math;
  ```

  - 一个文件除非直接在项目文件夹下，否则必须声明包，且必须在文件开头(在`import`前)
- 一个`.java`文件只能有一个`public`类或接口，且**类名必须和文件名相同**
- 不同`.java`文件在同一个包体内，指的是它们处于同一个目录下，声明的`package`路径相同

### 外部包的类或接口的引用

- 引用外部包的类或接口需要使用`import`关键字，用于减少包名冗余导致的可读性下降

  ```java
  import pkg1.pkg2.MyClass;     // 可快捷使用MyClass
  import pkg1.*;                // 可快捷使用pkg1下的所有公有类或接口, 但不包含pkg2下的公有类或接口
  ```
- 实践时，不推荐使用通配符`*`，推荐以下顺序引入：

  ```java
  import java.util.?;               // 先引入java标准库

  import org.springframework.?;     // 再引入java扩展的第三方库, 按照依赖顺序, 每个不同的库之间隔一空行

  import jakarta.persistence.?;     // 第三方库

  import dlut.?;                    // 最后引入自己开发的模块
  ```
- 实践时，推荐使用格式化插件

### `javac`命令

- `javac`用于编译`.java`文件，生成可被`JVM`执行的`.class`文件
- 最常用的编译命令如下(多个文件使用`:`分隔，`Windows`下是`;`)：

  ```shell
  # -d(destination)参数表示保留文件的包路径信息，并生成到目标目录下
  javac -d 目标目录 待编译的.java文件
  ```

- 其它参数：`-encoding`，指定文件编码
- 实践中，推荐使用`IDE`插件提供的脚本服务自动编译

### `java`命令

- `java`用于运行`.class`文件
- 最常用的命令如下：

  ```shell
  # -cp(classpath)参数标明`JVM`应从哪些路径下查找
  # 主类名必须包含`main`，链接由JVM在运行时进行，被链接的类同样是在指定的目录下查找
  # 注意主类名不是文件名，不能包含`.class`
  java -cp 目录 主类名
  ```

### `jar`命令

- `jar`命令与`tar`命令极其相似，用于归档
- 常用参数如下：
  - `-v`：`verbose`，显示归档/解压详情

  - `-c`：`create`，创建归档文件

  - `-f`：`file`，指明归档文件名

  - `-u`：`update`，更新归档文件

  - `-x`：`extract`，解压归档文件
- 常用语句如下：

  ```shell
  jar -cf a.jar 类列表
  jar -uf a.jar 新加入类列表
  jar -xf a.jar 解压a.jar
  ```

