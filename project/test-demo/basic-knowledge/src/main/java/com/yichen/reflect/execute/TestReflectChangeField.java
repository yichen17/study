package com.yichen.reflect.execute;

import com.yichen.reflect.base.StudentSet;
import com.yichen.reflect.base.Teacher;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/10 10:12
 */
public class TestReflectChangeField {

    public static void main(String[] args)throws  Exception{

        Object object=null;
        Class clazz;

        object=Class.forName("com.yichen.reflect.base.Teacher");

//        object=AccessController.doPrivileged(new PrivilegedAction<Object>() {
//            @Override
//            public Object run() {
//                try {
//                    return Class.forName("com.yichen.reflect.base.Teacher",false,Teacher.class.getClassLoader());
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//        });
        Teacher teacher=new Teacher();
        // 获取字段声明类型
        System.out.println(teacher.getClass().getDeclaredField("students").getType());
        System.out.println();
        StudentSet studentSet=new StudentSet();
        if(object!=null&&object instanceof Class){
            clazz=(Class)object;

                Constructor<Unsafe> declaredConstructor = Unsafe.class.getDeclaredConstructor();
                // 设置权限，使得能够访问 保护属性以及私有属性
                declaredConstructor.setAccessible(true);
                Unsafe unsafe = declaredConstructor.newInstance();
//                AccessController.doPrivileged(new PrivilegedAction<Object>() {
//                    @Override
//                    public Object run() {
//                        try {
//                            Field students = clazz.getDeclaredField("students");
//                            students.setAccessible(true);
//                            long studentsFieldOffset=unsafe.objectFieldOffset(students);
//                            unsafe.putObject(teacher,studentsFieldOffset,studentSet);
//                            students.set(teacher,studentSet);
//                        } catch (IllegalAccessException | NoSuchFieldException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                });

            Field students = clazz.getDeclaredField("students");
            students.setAccessible(true);
            long studentsFieldOffset=unsafe.objectFieldOffset(students);
            unsafe.putObject(teacher,studentsFieldOffset,studentSet);
            students.set(teacher,studentSet);



        }
        System.out.println(teacher.getStudents().getClass());
        System.out.println(teacher.getStudents().equals(studentSet));
        System.out.println(teacher.getStudents()==studentSet);

    }

}
