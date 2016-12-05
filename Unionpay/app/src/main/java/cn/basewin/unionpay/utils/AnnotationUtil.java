package cn.basewin.unionpay.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;

import cn.basewin.unionpay.AppContext;
import dalvik.system.DexFile;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/20 11:35<br>
 * 描述:  <br>
 */
public class AnnotationUtil {
    private static DexFile df;
    //"cn.basewin.unionpay.net"

    /**
     * 扫描包下类是否有关于annotation的注解有就返回这个类的class
     *
     * @param packName   包名 扫描这个包下 不填 就扫描 项目路劲下所有类
     * @param annotation 有这个注解类的 注解的类 就符合条件
     * @return 返回匹配到的class 集合
     */
    public static ArrayList<Class<?>> net(String packName, Class annotation) throws Exception {
        ArrayList<Class<?>> classes = new ArrayList<>();
        df = new DexFile(AppContext.getInstance().getPackageResourcePath());
        Enumeration<String> n = df.entries();
        while (n.hasMoreElements()) {
            String s = n.nextElement();
            if (s.contains(packName)) {
                Class<?> entryClass = Class.forName(s);
                if (entryClass != null) {
                    Annotation annotation1 = entryClass.getAnnotation(annotation);
                    if (annotation1 != null && !entryClass.getSimpleName().contains("$")) {// && !entryClass.getSimpleName().contains("$")
                        classes.add(entryClass);
                    } else {
                    }
                } else {
                }
            }
        }
        //*----------------------显示扫描到结果
        TLog.l("扫描结果个数：" + classes.size());
        if (classes != null && classes.size() > 0) {
            for (Class z : classes) {
                Annotation annotation1 = z.getAnnotation(annotation);
                TLog.d("扫描类：" + z.getName() + "参数：" + annotation1.toString());
            }
        }
        //*----------------------
        return classes;
    }
}
