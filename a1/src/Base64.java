
public class Base64 {

	/**
	 * byte数组中单个字节对应的字符
	 * */

	private static final char[] intToBase64 = {
			'A','B','C','D','E','F','G','H','I','J',
			'K','L','M','N','O','P','Q','R','S','T',
			'U','V','W','X','Y','Z','a','b','c','d',
			'e','f','g','h','i','j','k','l','m','n',
			'o','p','q','r','s','t','u','v','w','x',
			'y','z','0','1','2','3','4','5','6','7',
			'8','9','+','/'
	};

	/**
	 * 字符ASCII码对应intToBase64中的位置
	 * */
	private static final int[] base64Toint = {
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,62,-1,-1,-1,63,52,53,
			54,55,56,57,58,59,60,61,-1,-1,
			-1,-1,-1,-1,-1,0 ,1 ,2 ,3 , 4,
			5 ,6 ,7 ,8 ,9 ,10,11,12,13,14,
			15,16,17,18,19,20,21,22,23,24,
			25,-1,-1,-1,-1,-1,-1,26,27,28,
			29,30,31,32,33,34,35,36,37,38,
			39,40,41,42,43,44,45,46,47,48,
			49,50,51
	};

	/**
	 * base64加密
	 * 1、将byte数据每3个字节为一组，转成4个字节，单个字节上高位补0
	 * 2、每个字节上获得的数字从intToBase64中找到要转成的对应字符
	 * 3、判断分组是否是3的整数倍，如果不是，末尾使用=补齐
	 * */

	public static String encode(byte[] binaryData) {
		int totalLen = binaryData.length;
		int groupNum = binaryData.length/3;
		int lastGroup = totalLen - groupNum*3;
		int index = 0;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < groupNum ; i++) {
			byte first = (byte) (binaryData[index++] & 0xff);
			byte second = (byte) (binaryData[index++] & 0xff);
			byte third = (byte) (binaryData[index++] & 0xff);
			result.append(intToBase64[(first >> 2) & 0x3f]);
			result.append(intToBase64[(first << 4) & 0x30 | ((second >> 4) & 0x0f)]);
			result.append(intToBase64[(second << 2) & 0x3c | ((third >> 6) & 0x03)]);
			result.append(intToBase64[third & 0x3f]);
		}
		if(lastGroup != 0) {
			int first = binaryData[index++] & 0xff;
			result.append(intToBase64[first >> 2]);
			if(lastGroup == 1) {
				result.append(intToBase64[(first << 4) & 0x3f]);
				result.append("==");
			}else{
				int second = binaryData[index++] & 0xff;
				result.append(intToBase64[(first << 4) & 0x3f | second >> 4]);
				result.append(intToBase64[(second << 2) & 0x3f]);
				result.append("=");
			}
		}
		return result.toString();
	}

	/**
	 * base64解密
	 * 1、将字符串每4个字符一组，分别找到字符在intToBase64中对应的位置
	 * 2、将每个位置的高2位舍弃，即生成3个字节的byte
	 * 3、判断字符串末尾是否没填充过，是的话将填充部分舍弃，原部分按以上规则生成byte
	 * */

	public static byte[] decode(String s) {
		int strlen = s.length();
		int groupNum = strlen / 4;
		if(groupNum * 4 != strlen) {
			throw new IllegalArgumentException("String length must be a multiple of 4.");
		}
		int lastMissingNum = 0;
		int numFullGroup = groupNum;
		if(strlen != 0) {
			if(s.charAt(strlen - 1) == '=') {
				lastMissingNum ++;
				numFullGroup --;
			}
			if(s.charAt(strlen - 2) == '=') {
				lastMissingNum ++;
			}
		}
		byte[] result = new byte[groupNum*3 - lastMissingNum];
		int charIndex = 0;
		int resultIndex = 0;
		for (int i = 0; i < numFullGroup; i++) {
			int char0 = base64Toint[s.charAt(charIndex++)];
			int char1 = base64Toint[s.charAt(charIndex++)];
			int char2 = base64Toint[s.charAt(charIndex++)];
			int char3 = base64Toint[s.charAt(charIndex++)];
			result[resultIndex++] = (byte)(char0 << 2 | char1 >> 4);
			result[resultIndex++] = (byte)(char1 << 4 | char2 >> 2);
			result[resultIndex++] = (byte)(char2 << 6 | char3);
		}
		if(lastMissingNum != 0) {
			int char0 = base64Toint[s.charAt(charIndex++)];
			int char1 = base64Toint[s.charAt(charIndex++)];
			result[resultIndex++] = (byte)(char0 << 2 | char1 >> 4);
			if(lastMissingNum == 1) {
				int char2 = base64Toint[s.charAt(charIndex++)];
				result[resultIndex++] = (byte)(char1 << 4 | char2 >> 2);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		byte[] a = { 1, 2, 3, -7, -9, 110, 11 };
		String s = encode(a);
		System.out.println(s);
		byte[] b = decode(s);
		for(int i=0;i<b.length;i++) {
			System.out.print(b[i] + " ");
		}
		System.out.println();

	}

}
