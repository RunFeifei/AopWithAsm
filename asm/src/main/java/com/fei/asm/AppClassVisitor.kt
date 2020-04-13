package com.fei.asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by PengFeifei on 2020/4/9.
 */
class AppClassVisitor constructor(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM5, classVisitor) {


    private var isABSClass = false
    private var isInterface = false
    private var isTimeCacheUtilFile = false
    private lateinit var className: String

    override fun visit(version: Int, access: Int, className: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, className, signature, superName, interfaces)
        isABSClass = access and Opcodes.ACC_ABSTRACT > 0
        isInterface = access and Opcodes.ACC_INTERFACE > 0
        className?.let {
            val supers = superName ?: ""
            this.className = it
            this.isTimeCacheUtilFile = className.contains("TimeCache")
            System.out.println("AppClassVisitor--visit-->${className}--${supers}--${isABSClass}--${isInterface}")
        }

    }

    override fun visitMethod(access: Int, methodName: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val methodVisitor = super.visitMethod(access, methodName, descriptor, signature, exceptions)
        if (isABSClass || isInterface || isTimeCacheUtilFile) {
            return methodVisitor
        }
        methodName ?: return methodVisitor
        System.out.println("AppClassVisitor--visitMethod-->${className}--${methodName}")
        return AppMethodVisitor(className, methodVisitor, access, methodName, descriptor)

    }


}