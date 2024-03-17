import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.internal.model.DefaultObjectFactory
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.objectweb.asm.Opcodes
import java.io.Serializable

interface LogFilterParameters : InstrumentationParameters {
    // - In plugin 'com.android.internal.version-check' type 'com.android.build.gradle.tasks.TransformClassesWithAsmTask' property 'visitorsList.$1.parameters.config' is missing an input or output annotation.
    @get:Input
    val config: Property<LogFilterConfig>
}

public class LogFilterConfig(
    // 是否启用此插件
    val enable: Boolean = true,
    // 使用哪个文件来替换输出 log 的函数
    val className: String = "com/study/asm/LogFilter",
    // 需要处理的 hook 点
    val listHookPoint: List<LogFilterHookPoint> = defaultHookPointList
) : Serializable

val defaultHookPointList = mutableListOf<LogFilterHookPoint>(
    LogFilterHookPoint(
        "android/util/Log",
        arrayOf("d", "e", "i", "v", "w", "wtf", "d", "e", "i", "v", "w", "wtf", "w", "wtf"),
        arrayOf(
            "(Ljava/lang/String;Ljava/lang/String;)I",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            "(Ljava/lang/String;Ljava/lang/String;Ljava.lang.Throwable;)I",
            "(Ljava/lang/String;Ljava/lang/String;Ljava.lang.Throwable;)I",
            "(Ljava/lang/String;Ljava/lang/String;Ljava.lang.Throwable;)I",
            "(Ljava/lang/String;Ljava/lang/String;Ljava.lang.Throwable;)I",
            "(Ljava/lang/String;Ljava/lang/String;Ljava.lang.Throwable;)I",
            "(Ljava/lang/String;Ljava/lang/String;Ljava.lang.Throwable;)I",
            "(Ljava/lang/String;Ljava.lang.Throwable;)I",
            "(Ljava/lang/String;Ljava.lang.Throwable;)I",
        ),
        LogFilterMethodType.INVOKE_STATIC
    ), LogFilterHookPoint(
        "java/io/PrintStream",
        arrayOf("print", "println"),
        null,
        LogFilterMethodType.INVOKE_VIRTUAL
    )
)

// 一个类对应多个方法名和方法描述
// 比如类名 android.util.Log
// 方法名 d e i v w
// 方法描述 android.util.Log.d(java.lang.String, java.lang.String) 要改成虚拟机可识别的格式
// methodName 与 methodDesc 按照顺序一一对应
// 如果 methodDesc 为 null 表示不判断方法描述符
public class LogFilterHookPoint(
    val className: String,
    val methodNameArray: Array<String>,
    val methodDescArray: Array<String>?,
    val methodType: LogFilterMethodType
) : Serializable

public enum class LogFilterMethodType(val opcode: Int) {
    // 调用非私有实例方法
    INVOKE_VIRTUAL(Opcodes.INVOKEVIRTUAL),
    // 调用静态方法
    INVOKE_STATIC(Opcodes.INVOKESTATIC),
    // 调用私用实例方法、构造器以及使用 super 关键字调用父类的实例方法或构造器，和所实现接口的默认方法
    INVOKE_SPECIAL(Opcodes.INVOKESPECIAL),
    // 调用动态方法
    INVOKE_DYNAMIC(Opcodes.INVOKEDYNAMIC),
    // 调用接口方法，运行时解析调用点
    INVOKE_INTERFACE(Opcodes.INVOKEINTERFACE)
}