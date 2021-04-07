package com.yichen.nettydemo.objectPoolDemo;
import io.netty.util.Recycler;
/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/2 16:03
 * @describe 测试对象池
 */
public class UserCache {
    private static final Recycler<User> userRecycler = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };

    public static void main(String[] args) {
        User user1 = userRecycler.get(); // 1、从对象池获取 User 对象
        user1.setName("hello"); // 2、设置 User 对象的属性
        user1.recycle(); // 3、回收对象到对象池
        User user2 = userRecycler.get(); // 4、从对象池获取对象
        System.out.println(user2.getName());
        System.out.println(user1 == user2);
    }

    static final class User {
        private String name;
        private Recycler.Handle<User> handle;
        public User(Recycler.Handle<User> handle) {
            this.handle = handle;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void recycle() {
            handle.recycle(this);
        }
    }
}
