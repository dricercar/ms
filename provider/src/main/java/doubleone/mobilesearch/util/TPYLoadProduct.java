package doubleone.mobilesearch.util;

import java.util.Optional;

import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.entity.TPYContent;

public final class TPYLoadProduct extends AbstractLoadProduct {

    @Override
    protected void match(Product product, String line) {
        line = NoiseReducer.reduceNoise(line, '•', ',');
        String[] temp = line.split(":", 2);
		Optional<String> optional = Optional.empty();
		if(temp[1].contains("null")) {
			optional = Optional.ofNullable(null);
		}else {
			optional = Optional.of(temp[1].trim());
		}
		switch(temp[0]) {
			case TPYContent.BRAND:
				product.setBrand(optional.orElse(null));
				break;
			case TPYContent.NAME:
				product.setName(optional.orElse(null));
				break;
			case TPYContent.PRICE:
				product.setPrice(optional.orElse(null));
				break;
			case TPYContent.TYPE:
				product.setType(optional.orElse(null));
				break;
			case TPYContent.SIZE:
				product.setSize(optional.orElse(null));
				break;
			case TPYContent.OS:
				product.setOs(optional.orElse(null));
				break;
			case TPYContent.IMAGEURL:
				product.setImgUrl(optional.orElse(null));
				break;
			case TPYContent.CPU:
				product.setCpu(optional.orElse(null));
				break;
		}
    }

}