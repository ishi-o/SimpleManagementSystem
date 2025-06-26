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
- 命名规范：所有包名均应小写

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

## 关键字及声明型语句

### 访问控制修饰符

- 本节不讨论内部类
- `java`有四个访问控制权限，分别对应`public、protected、default、private`四个修饰符
- `public`：可以修饰类、接口、方法、属性，表示所有包可访问，是最常用的修饰符
- `protected`：可以修饰方法、属性，表示同包内均可访问，外部包中仅子孙类可通过`this`(自己或子孙类的对象)访问，例如

  ```java
  package a;
  import b.A;   // 假设含有protected void methodA() {}方法
  class B extends A {
    void func() {
        methodA();  // 正确
        A a = new A();
        a.methodA();    // 错误
    }
  }
  ```

- `default`：实际上不存在这个关键字，当类或方法或属性没有显式地声明访问控制修饰符时，含有`default`权限，常称为**包级私有**，仅包内所有类可访问
- `private`：可以修饰方法、属性，仅类内可访问

### `static、abstract、final`

- `static`：声明静态对象或方法或内部类，和`cpp`类似
- `abstract`：声明抽象方法或抽象类，和`cpp`的`virtual`定义类似
- `final`：
  - 修饰类时：表明该类不能被继承
  
  - 修饰方法时：表明该方法不能被重写

  - 修饰变量时：声明常变量，注意只是限制在栈中的值不能改变
- `abstract`不能和`final`或`private`或`static`同时出现，因为没有意义
- `final`不能修饰接口，因为没有意义
- 修饰符的顺序对程序没有影响，但规范是先是访问控制修饰符，然后是其它修饰符

### `synchronized、transient、volatile、sealed、permits`

- 待补

### 标识符声明

- 声明变量：仅支持用`=`初始化新变量，且需要指明类型

  ```java
  String obj = "";
  ```

- 声明类：`java`没有函数，所有函数体**都在类内**，抽象类不能被实例化

  ```java
  modifiers class ClassName {ClassBody}  // 不需要';'
  ```

- 声明方法：抽象方法所属类必须为抽象类

  ```java
  modifiers returnType methodName(paramList) {methodBody}
  ```

- 声明接口：`java`提供所谓接口，是一系列抽象方法的集合，是其它模块访问你编写的模块的入口，编译时会自动在接口的所有方法前加上`public`修饰符

  ```java
  public interface InterfaceName {
    // 接口内的方法默认为public，且没有实现
    returnType methodName(paramList);
  }
  ```

- 声明一个实现了某个接口的类，一个类可以实现多个接口：

  ```java
  public class ClassName implements InterfaceName {
    // 必须重写所有该接口的方法
  }
  ```

- 声明一个继承了某个类的类，或一个继承了某个接口的接口，只能单继承：

  ```java
  public class ClassName extends SuperClass {}
  public interface InterfaceName extends SuperInterface {}
  ```

- `extends`应该在`implements`之前

### 标识符规范

- 包名规范：应全小写
- 变量名和方法名规范：构成标识符的所有单词，首单词全小写，其余单词仅首字母大写
- 类名和接口名规范：构成标识符的所有单词首字母均大写
- 常量名规范：应全大写

### `java`和`cpp`部分语法特性异同

- `java`不支持运算符重载，仅有部分特殊的类含有类似的语法糖(`String`支持`+`拼接，但没有`[]`运算符)
- `java`不支持默认参数
- `java`和`cpp`均支持函数重载，重载均要求方法名相同但参数列表不同
- `java`和`cpp`均支持变长参数，`java`变长参数的语法如下：

  ```java
  // paramList可以是零或多个String参数
  void method(String... paramList) {}
  ```

- `java`不支持多继承
- `java`不支持常方法，`cpp`支持用`const`修复一个方法保证不能被非常量调用

### `protected`权限的解释

- 在引入类(链接在`.class`文件中的字节码)时，`JVM`会加载类/接口的元数据进方法区，并构建**方法表**来区分不同的访问控制域

- 在创建对象时，`JVM`会申请一段内存，并创建**对象的方法表**，用于**单独存储所有非静态成员**
  类、对象的方法表是不同的

- 每个类都会维护一个引用，指向允许访问的类或对象的方法表

  - 这个引用能指向所有同一包的类及其对象、所有引入的外包类的方法表

  - 能指向，同时符合访问修饰符的范围，才能正常调用

- 因此`protected`是极其特殊的：

  - 包外子类**不能**通过**基类的对象**访问**保护型方法**，因为包外的子类不能指向对象的方法表

  - 包外子类可以通过**基类**访问**保护型静态方法**，因为包外子类能指向类的方法表，同时符合`protected`的范围
  
  - 包内子类可以通过基类及其对象访问保护型方法，因为包内类能指向类及其对象的方法表，同时符合`protected`的范围