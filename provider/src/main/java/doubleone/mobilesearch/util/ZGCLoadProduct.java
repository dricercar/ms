package doubleone.mobilesearch.util;

import java.util.Optional;

import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.entity.ZGCContent;

public final class ZGCLoadProduct extends AbstractLoadProduct {

    @Override
    protected void match(Product product, String line) {
        String[] temp = line.split(":", 2);
		Optional<String> optional = Optional.empty();
		if(temp[1].contains("null")) {
			System.out.println("has a null");
			optional = Optional.ofNullable(null);
		}else {
			optional = Optional.of(temp[1].trim());
		}
		
		switch(temp[0]) {
			case ZGCContent.BRAND:
				product.setBrand(optional.orElse(null));
				break;
			case ZGCContent.NAME:
				product.setName(optional.orElse(null));
				break;
			case ZGCContent.PRICE:
				product.setPrice(optional.orElse(null));
				break;
			case ZGCContent.TYPE:
				product.setType(optional.orElse(null));
				break;
			case ZGCContent.SIZE:
				product.setSize(optional.orElse(null));
				break;
			case ZGCContent.OS:
				product.setOs(optional.orElse(null));
				break;
			case ZGCContent.IMAGEURL:
				product.setImgUrl(optional.orElse(null));
				break;
			case ZGCContent.CPU:
				product.setCpu(optional.orElse(null));
				break;
		}
    }

}