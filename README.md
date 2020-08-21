项目笔记

1、@ComponentScan、@MapperScan注解的作用？
答：@ComponentScan 让spring扫描指定路径下的所有类，并自动装入spring容器中。
   @Mapper 注解针对的是一个一个的类，相当于是一个一个 Mapper.xml 文件。而一个接口一个接口的使用 @Mapper，太麻烦了，
   于是 @MapperScan 就应用而生了。@MapperScan 配置一个或多个包路径，自动的扫描这些包路径下的类，自动的为它们生成代理类。

