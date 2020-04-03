package com.fei.asm.transform

import com.android.build.api.transform.*
import org.gradle.api.logging.Logger

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
        return QualifiedContent.Scope.PROJECT
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

    private static void processDirectoryInput(DirectoryInput input, TransformOutputProvider outputProvider) {
        if (!input.file.isDirectory()) {
            return
        }
        input.file.eachFile { File file ->
            log.error "processDirectoryInput--" + file.name

        }


    }

    private static void processJarInput(JarInput input, TransformOutputProvider outputProvider) {
        if (!input.file.isDirectory()) {
            return
        }
        input.file.eachFile { File file ->
            log.error "processJarInput--" + file.name

        }
    }


}






















