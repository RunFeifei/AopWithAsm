package com.fei.asm

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * Created by PengFeifei on 2020/4/9.
 * 利用插件生成asm代码时,需要先对目标文件TimeCache先生成,然后再对Test文件生成
 */
class AppMethodVisitor constructor(private val className: String, methodVisitor: MethodVisitor, access: Int, private val methodName: String,  descriptor: String?) : AdviceAdapter(Opcodes.ASM5, methodVisitor, access, methodName, descriptor) {


    override fun onMethodEnter() {


        System.out.println("AppMethodVisitor--onMethodEnter-->${className}--${methodName}--0")
        mv.visitLdcInsn(methodName)
        mv.visitMethodInsn(INVOKESTATIC, "com/fei/asmdepend/TimeCache", "setStartTime", "(Ljava/lang/String;)V", false)
        System.out.println("AppMethodVisitor--onMethodEnter-->${className}--${methodName}--1")

    }

    override fun onMethodExit(opcode: Int) {

        System.out.println("AppMethodVisitor--onMethodExit-->${className}--${methodName}")
        mv.visitLdcInsn(methodName)
        mv.visitMethodInsn(INVOKESTATIC, "com/fei/asmdepend/TimeCache", "setEndTime", "(Ljava/lang/String;)V", false)

        mv.visitLdcInsn(methodName)
        mv.visitMethodInsn(INVOKESTATIC, "com/fei/asmdepend/TimeCache", "computeMethodTime", "(Ljava/lang/String;)V", false)
    }

}