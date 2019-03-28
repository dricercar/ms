package doubleone.mobilesearch.util;
public class TPYNoiseReducer{
    public static String reduceNoise(String value) {
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		for(int i = 0; i < value.length(); i++) {
			if(value.charAt(i) == '•') {
				flag = false;
			}else if(value.charAt(i) == ',') {
				flag = true;
			}
			if(flag)
				sb.append(value.charAt(i));
		}
		return sb.toString();
	}
}