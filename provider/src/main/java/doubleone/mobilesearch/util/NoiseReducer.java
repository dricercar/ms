package doubleone.mobilesearch.util;
public class NoiseReducer{
    public static String reduceNoise(String value, char begin, char end) {
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		for(int i = 0; i < value.length(); i++) {
			if(value.charAt(i) == begin) {
				flag = false;
			}else if(value.charAt(i) == end) {
				flag = true;
			}
			if(flag)
				sb.append(value.charAt(i));
		}
		return sb.toString();
	}
}