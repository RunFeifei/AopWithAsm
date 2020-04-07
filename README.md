# LearnAopWithAsm
学习ASM  
#### 修改AsmTransForm.groovy完成要实现的功能  
#### 命令行切到asm module下,升级build.gradle的publish节点publishVersion  
#### 在asm module下执行以下命令,把gradle插件发布到bintray上去  
#### ../gradlew clean build bintrayUpload -PbintrayUser=feifei -PbintrayKey=69175aa7101760b64a7fee5aea872d4d84d14c8c -PdryRun=false  
#### 在项目根目录修改对应插件版本,build查看日志  
