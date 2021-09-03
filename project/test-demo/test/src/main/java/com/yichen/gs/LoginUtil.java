package com.yichen.gs;

/**
 * @author ducongcong
 * @date 2016年6月23日
 */
public class LoginUtil {
	public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";
    public static final String DES = "DES";
    public static final String AES = "AES";
	/**
	 * 生成随机salt
	 * @author ducongcong
	 * @createDate 2016年6月23日
	 * @updateDate 
	 * @return
	 * 老的加密盐保留不删除 ouyangbo 20180420
	 */
    
	/*public static String generateSalt() {
		return StringUtils.randomUUIDSplit().substring(0,  new Random().nextInt(12));
	}*/
    public static String generateSalt() {
    	return "d9b5e95cfbf";
	}    
	/**
	 * 根据加密类型获取用户加密密码
	 * @author ducongcong
	 * @createDate 2016年6月23日
	 * @updateDate 
	 * @param hasher 约定的加密方式
	 * @param str  原值
	 * @param salt 加密盐值
	 * @return  根据不同的加密方式返回不同的加密值，如果该加密方式不是约定的，返回原字符串
	 */
	public static String getPassword(String hasher,String str,String salt){
		if(hasher.equals(LoginUtil.MD5)){
			return EncryptUtil.getInstance().MD5(str, salt);
		}else if(hasher.equals(LoginUtil.DES)){
			return EncryptUtil.getInstance().DESencode(str, salt);
		}else if(hasher.equals(LoginUtil.AES)){
			return EncryptUtil.getInstance().AESencode(str, salt);
		}else if(hasher.equals(LoginUtil.SHA1)){
			return EncryptUtil.getInstance().SHA1(str, salt);
		}else{
			return str;
		}
	}

	/**
	 * 生成密码
	 * @param args
	 */
	public static void main(String[] args) {
		String password = getPassword("MD5", "yanqunfang", "d9b5e95cfbf");
		System.out.println(password);
		System.out.println("a".equals(null));
	}
}
