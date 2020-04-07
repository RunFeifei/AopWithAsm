package com.fei.asm.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.logging.Logger

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
                processDirectoryInput(directoryInput, outputProvider)
            }
            input.jarInputs.each { JarInput jarInput ->
                processJarInput(jarInput, outputProvider)
            }
        }
    }

    private void processDirectoryInput(DirectoryInput input, TransformOutputProvider outputProvider) {
        if (!input.file.isDirectory()) {
            return
        }
        input.file.eachFileRecurse { File file ->
            if (file.isFile()) {
                log.error "processDirectoryInput--directoryName--" + input.file.name + "--className--" + file.name
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


}






















