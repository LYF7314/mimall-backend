package edu.hit.service;

import edu.hit.pojo.Product;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IProductServiceTest extends TestCase {

    public void testList() {
        List<Product>productList=new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setDetail("liyou");
        productList.add(product1);
        Map<Integer, Product> map  = productList.stream()
                .collect(Collectors.toMap(Product::getId, s -> s));
        System.out.println(productList);
    }

    public void testDetail() {
    }
}