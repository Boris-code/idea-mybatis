基于<https://gitee.com/wuzhizhan/free-mybatis-plugin>升级，支持2020.2.4版本。

[中文](README.md) | 
[ENGLISH](README_EN.md)

## Description
A idea plugin for mybatis .
idea-mybatis 是一款增强idea对mybatis支持的插件，主要功能如下：
- 生成mapper xml文件
- 快速从代码跳转到mapper及从mapper返回代码
- mybatis自动补全及语法错误提示
- **支持全局默认配置，避免重复填写**

## 使用方法
idea-mybatis是一个提高mybatis编码的插件。实现了dao代码跳转到mapper，mapper跳转回dao，mapper文件、statement查询自动生成功能。

### 灵活使用alt+enter和ctrl+B实现提示和跳转
- 生成mapper文件
    - alter+enter弹出
![](https://images.gitee.com/uploads/images/2020/0121/151849_26a01dec_131460.png)
![](https://images.gitee.com/uploads/images/2020/0121/151849_59d74c18_131460.jpeg)
- 生成statement语句
    - alter+enter弹出
![](https://images.gitee.com/uploads/images/2020/0121/151849_594bfd4d_131460.jpeg)
- dao跳转到mapper（也可以ctrl+B跳入）
- ![](https://images.gitee.com/uploads/images/2020/0121/151850_9821ea07_131460.jpeg)
- mapper跳转回dao（也可以ctrl+b跳入)
- ![](https://images.gitee.com/uploads/images/2020/0121/151850_6ff9859f_131460.jpeg)
<br><br><br>

## mybatis generator gui使用方法

### 1. 全局配置（推荐）
在使用mybatis generator之前，可以先进行全局配置，这样每次生成代码时会自动加载默认配置，无需重复填写。

**配置路径：** 
- Mac: `IntelliJ IDEA` → `Preferences` → `Tools` → `Mybatis Generator Setting`
- Windows: `File` → `Settings` → `Tools` → `Mybatis Generator Setting`

**可配置项：**
| 配置项 | 说明 |
|-------|------|
| project folder | 项目目录 |
| model package | 实体类包名 |
| model path | 实体类生成路径 |
| dao postfix | DAO后缀（如Mapper、Dao） |
| dao package | DAO包名 |
| dao path | DAO生成路径 |
| xml package | XML包名 |
| xml path | XML生成路径（如 src/main/resources/mappers） |
| options | 生成选项（分页、注释、Lombok等） |

配置完成后点击 **Apply** 保存，之后每次打开generator界面都会自动加载这些配置。

### 2. 配置数据库
![](https://images.gitee.com/uploads/images/2020/0121/151850_7decd93e_131460.png)
![](https://images.gitee.com/uploads/images/2020/0121/151849_3b35abd0_131460.png)

### 3. 生成代码
- 在需要生成代码的表上右键，选择mybatis generator，打开预览界面。
![](https://images.gitee.com/uploads/images/2020/0121/151849_6552ab20_131460.png)
- 配置生成参数（如果已配置全局配置，这里会自动加载）
![](https://images.gitee.com/uploads/images/2020/0121/151849_7fd4ada5_131460.png)
- 注意：当数据库用mysql8，在URL上定义时区，推荐使用'?serverTimezone=GMT'，配置中勾选上mysql8选项。

### 默认配置
如果未进行全局配置，插件会使用以下默认值：
- dao postfix: `Mapper`
- xml path: `src/main/resources/mappers`
- 默认勾选: `Page`、`comment`、`Use-Schema`、`Use-Example`、`Overwrite-Xml`、`Overwrite-Java`、`Use-Lombox`、`toString/hashCode/equals`、`Use-Swagger`

## 编译打包

### 环境要求
- JDK 8+
- Gradle 7.x

### 编译命令
```bash
# 进入项目目录
cd idea-mybatis

# 编译并打包插件
./gradlew buildPlugin
```

### 输出位置
编译成功后，插件zip包位于：
```
build/distributions/idea-mybatis-{version}.zip
```

### 安装插件
1. 打开 IntelliJ IDEA
2. 进入 `Preferences/Settings` → `Plugins`
3. 点击右上角 ⚙️ → `Install Plugin from Disk...`
4. 选择编译生成的 `idea-mybatis-{version}.zip` 文件
5. 重启 IDEA


## 参考
- better-mybatis-generator https://github.com/kmaster/better-mybatis-generator
- mybatis-generator-gui https://github.com/zouzg/mybatis-generator-gui
- MyBatisCodeHelper-Pro https://github.com/gejun123456/MyBatisCodeHelper-Pro