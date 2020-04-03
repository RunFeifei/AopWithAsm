package com.fei.asm

import com.android.build.gradle.AppExtension
import com.fei.asm.transform.AsmTransForm
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class ASMPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        def log = project.logger
        log.error "================================================"
        log.error "ASMPlugin"
        log.error "================================================"

        if (!project.plugins.hasPlugin("com.android.plugin")) {
            throw new GradleException("ASMPlugin--> can not found Android Application")
        }
        def android = project.extensions.getByType(AppExtension)
        AsmTransForm asmTransForm = new AsmTransForm(log)
        android.registerTransform(transform)
    }
}