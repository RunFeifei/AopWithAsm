package com.fei.asm.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.fei.asm.AppClassVisitor
import org.apache.commons.io.FileUtils
import org.gradle.api.logging.Logger
import org.apache.commons.codec.digest.DigestUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * https://juejin.im/post/5d8f285de51d4578495472aa
 */
public class AsmTransForm extends Transform {

    Logger log


    AsmTransForm(Logger logger) {
        log = logger
    }

    @Override
    String getName() {
        return "AsmTransFrom"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        log.error "AsmTransForm-->transform"
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        if (outputProvider != null) {
            outputProvider.deleteAll()
        }
        Collection<TransformInput> inputs = transformInvocation.inputs
        if (inputs == null) {
            return
        }
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                processDirectoryInput(directoryInput)
                def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
//            input.jarInputs.each { JarInput jarInput ->
//                processJarInput(jarInput, outputProvider)
//            }

            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }


        }
    }

    private void processDirectoryInput(DirectoryInput directoryInput) {
        if (directoryInput.file.isDirectory()) {
            directoryInput.file.eachFileRecurse { File file ->
                processClassFile(file)
            }
        }


    }

    private void processJarInput(JarInput jarInput, TransformOutputProvider outputProvider) {
        if (!jarInput.file.getAbsolutePath().endsWith(".jar")) {
            return
        }
        def jarName = jarInput.name
        JarFile jarFile = new JarFile(jarInput.file)
        Enumeration enumeration = jarFile.entries()
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement()
            log.error "processJarInput--jarName--" + jarName + "--className--" + jarEntry.getName()
        }
    }

    private void processClassFile(File file) {
        def name = file.name
        //过滤掉R文件 BuildConfig 匿名内部类
        if (!name.contains("\$") && name.endsWith(".class") && !name.startsWith("R\$") && !"R.class".equals(name) && !"BuildConfig.class".equals(name)) {
            log.error "processDirectoryInput--directoryName--" + file.absolutePath + "--className--" + file.name
            ClassReader classReader = new ClassReader(file.bytes)
            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
            AppClassVisitor appClassVisitor = new AppClassVisitor(classWriter)
            classReader.accept(appClassVisitor, ClassReader.EXPAND_FRAMES)
            byte[] code = classWriter.toByteArray()
            FileOutputStream fos = new FileOutputStream(file.parentFile.absolutePath + File.separator + name)
            fos.write(code)
            fos.close()
        }
    }


}






















