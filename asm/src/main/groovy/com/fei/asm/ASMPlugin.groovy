package com.fei.asm

import com.android.build.gradle.AppExtension
import com.fei.asm.transform.AsmTransForm
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ASMPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        def log = project.logger
        log.error "================================================"
        log.error "ASMPlugin"
        log.error "================================================"
        def android = project.extensions.getByType(AppExtension)
        AsmTransForm asmTransForm = new AsmTransForm(log)
        android.registerTransform(asmTransForm)
    }
}