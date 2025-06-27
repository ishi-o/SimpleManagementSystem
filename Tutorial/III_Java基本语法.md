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

- `java`的`main`函数模板：

  ```java
  public class MainClass {
    public static void main(String[] args) {}
  }
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

- 归档后，可以通过`java -cp xxx.jar MainClass`运行主类`MainClass`，所有需要链接的类都在归档文件中找到

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
- `final`修饰引用对象时只能限制该变量**不能改变指向**，但没有限制用户通过该变量**改变其指向的实例**
  
  因为`final`修饰方法只代表该方法不能被重写，所以做不到`cpp`那样用一个类型声明可变或不可变对象

  在`java`中，一个类要么是可变类，要么是不可变类，**不可变类的所有属性都是常量**，例如`String`就是典型的不可变类，不要把实例方法和引用赋值搞混：

  ```java
  String s = "";    // 引用赋值
  s = s.trim();     // 实际上是改变了s的指向使其指向s.trim()的返回值，而没有改变原来指向堆中的实例
  StringBuilder sb = new StringBuilder("");   // 引用赋值
  sb.append("a");   // StringBuilder是一个可变类, append()修改了sb指向的实例的属性
  ```

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

## `java`的奇妙语法糖

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
- `java`和`cpp`均支持类型自动推断，在`cpp`中是`auto`而在`java`中是`var`
- `java`的`Type[]`整体表示一个数组类型

## 结构型语句

- `if-else if-else`
- `where-continue-break`
- `for(;;)-continue-break`
- `for(type e : c)`语法糖
- `try-catch-finally`
- `switch-case-break`
- `switch-case -> {}`

## 面向对象编程

### 数据类型

- `java`的数据类型分为两种：基础数据类型和对象类型
- `java`允许自动提升，不允许自动`down cast`，需要强制类型转换
- 基础数据类型只有八种，且全小写，分别为`byte、short、int、long、float、double、boolean、char`，它们直接存储在栈中
  - 不带后缀的整数均为`int`型字面量

  - 不带后缀的浮点数均为`double`型字面量

  - `boolean`类型字面量只有`true`和`false`

  - `char`类型字面量均为`Unicode`编码，一个字符占两个字节

  - 其它类型的字面量：`0x????、????h、0b????、?.??f`
- 对象类型是类类型，它们真正存在于堆中，由`new`关键字创建

  所有对象类型的变量**存储在栈中的值都是指针**，称为**引用**，指向在堆中的对象
- `java`得益于有`GC`，所以没有`delete`也没有析构函数，此乃一胜
- `null`为空指针

### 运算符

- 赋值运算符`=`：所有的赋值均为**传值**，是将**复制栈中的值**给另一个变量，给方法传参也是赋值
- 比较运算符：所有的比较均为比较栈中的值，所以针对引用变量时，没有意义
- `obj instanceof Type`：只用于引用类型，如果`obj`是`Type`或`Type`派生类的对象时，返回`true`
- 按位运算符：其中移位运算`>>>`为无符号右移，`>>`为有符号右移，`<<`在移动位数大于总位数时会将移动位数自动取模
- 逻辑运算符
- 算术运算符：针对基础数据类型中的整型类型，最小单位是`int`，例如两个`byte`运算时会把`byte`提升到`int`然后返回`int`
- 成员访问运算符`.`：针对引用类型变量，对比`cpp`就是`->`

### `Object`

- `Object`是所有类的基类，所有类若没有显式地继承哪个类，就会隐式地继承`Object`
- `Object`对象提供的方法一般没有意义，重要在于提供了一套**规范的方法名**，重点在于**重写**
  - `public boolean equals(Object o)`：判等，注意参数为`Object`类型

  - `public int hashCode()`：获取哈希值

  - `protected Object clone() throws CloneNotSupportedException`：创建并返回本对象的副本(浅拷贝副本)，会抛出**受检异常**`CloneNotSupportedException`，防止你没有重写但又在某个地方调用了该方法

    重写它一般需要声明`implements Cloneable`(一个空接口)并`public`地重写

    但是`clone()`是很没用的，一般使用一些集成框架提供的深拷贝或浅拷贝的克隆方法
  - `Class<?> getClass()`：获取引用指向的**对象所属的类**，是堆中正在运行的那个对象的类，而不是引用的类

    详见`java`反射机制

### `this、super`

- `this`引用：指向现在在`JVM`中**正在运行的那个对象**，无论`this`在哪个类中，其本质是一个动态绑定的引用

  例如以下例子：

  ```java
  class A {
    public void a() {}
    void b() {
        a();
    }
  }
  class B extends A {
    public void a() {
        b();
    }
  }
  ```

  若声明`A a = new B()`，并调用`a.b()`，即执行`A`类的`b()`方法，即执行`this.a()`，即执行`B`类中重写的方法(因为`this`指向一个`B`类的实例)，所以这是一个无限循环的调用
- `super`关键字：`super`和`this`不同，其本质只是一个关键字，**静态绑定**所在类的父类的一个对象

  同样是上面的例子：

  ```java
  class BaseA {
    public void a() {}
  }
  class A extends BaseA {
    public void a() {}
    void b() {
        a();
        super.a();
    }
  }
  class B extends A {
    public void a() {
        b();
    }
  }
  ```

  同样，调用`b()`后会调用`this.a()`，执行`B`类的`a()`

  而`super.a()`静态绑定了所在类`A`的父类`BaseA`的`a()`方法，和`this`指向的对象隔了两代，所以区别还是很大的
- 但总而言之，`this`和`super`都不应该用来访问类的静态成员
- `this(参数)`和`super(参数)`还可以引用构造方法，同上`this`动态绑定当前运行的对象，`super`静态绑定所属类的父类对象

### 类的构造器

- 构造器：
  - 若没有自定义构造器，编译器提供一个没有参数的构造器
  
  - 若有自定义构造器，编译器不再提供没有参数的构造器

  - 所有构造器的第一条语句，如果不是通过`this`或`super`引用的构造方法，则会隐式地添加`super()`，即父类的无参构造器

  所以当父类没有无参构造器且子类没有构造器时，编译器会检查出错误
- `java`创建一个对象分为四步(初始化的顺序)：
  - `new`：分配内存

  - 默认赋值：基础类型赋为零，引用类型赋为`null`

  - 显式初始化：若在声明属性时用`=`赋值了，那么会将其放在`this`或`super`引用的构造器之后, 构造方法代码之前

  - 执行用户的代码

## 注解和接口

### 注解(`Annotation`)

- 注解是一种元数据，编译器不会跳过注解，而是根据注解**进行检查**或其它操作
- 注解以`@`开头，例如重写方法要用`@Override`注解，下面介绍一下`java`标准库的注解
- 针对代码的注解：
  - `@Override`：表明方法被重写

  - `@Deprecated`：表明方法已过时，检查到使用该方法时，会警告

  - `@SuppressWarnings(value={"key"})`：标注部分内容，令编译器忽视被标注内容发出的警告
- 针对注解的注解(元注解)：
  - `@Retention`：标识该如何存储注解

  - `@Documented`：标识注解应该包含在用户文档中

  - `@Inherited`：标识注解继承于哪个注解
- 自定义注解模板：

  ```java
  @Documented // 可选, 表明该注解可以存在于Javadoc中
  @Target({ElementType.TYPE, ...})    // 指定注解的类型, ElementType是一个枚举, 用于限制该注解作用的范围
  public @interface MyAnnocation {}   // 自定义注解需要实现Annocation接口, @interface表明该注解实现了它
  ```

  然而但是本人不太清楚自定义注解有什么用

### 接口

- `Java 8`后支持在接口内存在有实现的方法或类，具体规则在本节不讨论
- 接口同样支持多态，可以通过接口类型的引用访问实现了该接口的类对象实例
- 接口的作用：
  - 空接口：接口支持多重实现，空接口可以作为一个标记，然后通过泛型编程就能筛选掉那些没有这个标记的类

    例如`java.io.Serializable`，可序列化空接口

  - 接口可以将程序划分为多个模块，模块解耦实现多人分工

  - 用接口定义公共`API`更为清晰
- 标准库常用接口：
  - `Comparable<T>`：包含`public int compareTo(T o)`

  - `Runnable`：包含`void run()`，用于多线程编程

  - 集合框架的接口

## 泛型编程

### 泛型声明

- 泛型类似`cpp`的模板编程，用于重用代码
- **泛型类型必须为引用类型**，所以基础数据类型都提供了包装类
- 泛型类或接口：

  ```java
  public class ClassName <T> {}
  ```

- 泛型方法：

  ```java
  modifiers <T> returnType methodName(必须包含类型为T的参数) {}
  ```

### 泛型参数命名规范

- `E`：集合或数组元素的类型
- `T/U/S/P`：泛指所有类
- `K/V`：键值类
- `N`：`Number`类

### 通配符`?`

- 通配符`?`不是一种类型参数，也不是标记符，**不能在声明泛型参数时**使用，但可以在类型里面使用：

  ```java
  class ClassName <?> {}    // 错误
  class ClassName <T> {
    public static method() {
        ClassName<?> a;     // 正确, 相当于ClassName a;
    }
  }
  ```

- 仅使用`?`和省略尖括号没有区别，如果省略尖括号会有`RawTypes`警告
- 在较新版本中，使用`<>`可由编译器自动推断类型，例如：

  ```java
  Map<String> map = new HashMap<>();
  ```

### 泛型类或接口的继承

- 泛型类是一个整体，即时泛型参数之间有继承关系，泛型类或接口也没有继承关系：

  ```java
  class A {}
  class B extends A {}      // B是A的子类
  class ClassName <A> {}
  class ClassName <B> {}    // 但ClassName<B>不是ClassName<A>的子类
  ```

- 泛型类的继承规则：
  - 若子类不是泛型类，则父类必须确定泛型参数

  ```java
  class Father <T> {}
  class Son extends Father<String> {}   // Father<>必须指定参数
  ```

  - 若子类是泛型类，则父类的类型参数必须是子类所使用的参数，或者是具体的参数

  ```java
  class Father <T> {}
  class Son <T> extends Father<String> {}   // 具体的参数
  class Son <T, S> extends Father<T> {}     // Son提供的泛型参数
  ```

### 限定符`extends、super`

- 正因上述问题，`java`提供了`extends`和`super`表示了其上界和下界
- `extends TypeOrInterface`：虽然只提供了`extends`，但这个限定符后面可以跟类，也**可以跟接口**，表示这个类型参数必须继承或实现自`TypeOrInterface`类或接口
- `super TypeOrInterface`：表明这个类型参数必须是`TypeOrInterface`的父类或父接口
- 两者可以一起使用

## 异常处理

### 异常的分类

- 所有异常分为受检异常、非受检异常、错误
- 声明某方法可能会抛出某异常：`throws`关键字

  在某方法内抛出某异常`new`后跟一个异常类对象

  ```java
  void method() throws Exception {
    throw new Exception();
  }
  ```

- 受检异常：编译器会在编译期检查的异常，如果用户的某个方法**调用了会抛出受检异常的方法**，但既没有**处理**也没有**显式地抛出**，就会有红色波浪线提醒

  所有受检异常都是`Exception`的子类且不是`RuntimeException`的子类

- 非受检异常：运行时可能抛出的异常，不需要显式地用`throws`声明

  所有非受检异常都是`RuntimeException`的子类

- 错误：这种异常是脱离程序的，可能是硬件出了问题，例如内存溢出

### 异常的实质

- ```mermaid
  graph
  Throwable --> Error
  Throwable --> Exception
  Exception --> RuntimeException
  Exception --- Checked(("检查性异常\nChecked Exception")) --> IOException
  RuntimeException --> A(...)
  Checked --> ParseException
  Checked --> ...
  ```

- 所有异常和错误类都实现了`Throwable`接口

- 自定义异常：很简单，只需要根据需求继承`Exception`或`RuntimeException`(通常是后者)并实现构造方法即可

  通常由业务层抛出，用户接口层捕获并处理

- `catch-try-finally`：`finally`可选，表示`try`后(即时出现异常)必须执行的代码块，例如关闭文件等
