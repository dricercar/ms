package doubleone.mobilesearch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import doubleone.mobilesearch.entity.Product;

public abstract class AbstractLoadProduct {
    private String baseDir;
	
	public Product loadProduct(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		if(Files.exists(path))
			return loadProduct(path);
		return null;
	}
	private Product loadProduct(Path path) throws IOException {
		// TODO Auto-generated method stub
		Product product = new Product();
		boolean b = false;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path.toUri()))));
		String line;
		while((line = br.readLine()) != null) {
//			System.out.println(line);
			match(product, line);
		}
		br.close();
		if(product.getName() == null)
			return null;
		return product;
	}
	public List<Product> loadProducts(String dir) throws IOException {
		if(dir == null)
			dir = baseDir;
		List<Product> list = new ArrayList<Product>();
		Path path = Paths.get(dir);
		if( Files.isDirectory(path)) {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
					Product p = loadProduct(file);
					if( p != null)
						list.add(p);
					return FileVisitResult.CONTINUE;
				}
			});
		}
		return list;
		
    }
    protected abstract void match(Product product, String line);
}